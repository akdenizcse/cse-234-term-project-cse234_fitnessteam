package com.example.fitnesstrackerandplanner

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackerandplanner.BuildConfig
import com.example.fitnesstrackerandplanner.ui.theme.Beige
import com.example.fitnesstrackerandplanner.ui.theme.Silver
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties


interface OpenAIApi {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    fun getChatResponse(@Body request: ChatRequest): Call<ChatResponse>
}

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

fun getChatbotResponse(userInput: String, callback: (String) -> Unit) {
    val messages = listOf(
        Message(role = "user", content = userInput) ,
        Message(role = "system", content = "You are a helpful assistant and your name is FitAI"),
        Message(role = "system", content = "Your job is to help users of Fittie app, which is a fitness health tracker app"),
        Message(role = "system", content = "You are FitAI and you have vast knowledge on exercises")


        )
    val request = ChatRequest(model = "gpt-3.5-turbo", messages = messages)

    RetrofitClient.instance.getChatResponse(request).enqueue(object : retrofit2.Callback<ChatResponse> {
        override fun onResponse(call: Call<ChatResponse>, response: retrofit2.Response<ChatResponse>) {
            if (response.isSuccessful) {
                val chatResponse = response.body()
                val botReply = chatResponse?.choices?.firstOrNull()?.message?.content
                Log.e("Retrofit", "Fetched reply: $botReply")

                // Determine role based on botReply content
                val role =  if (botReply?.startsWith("assistant:", true) == true) "assistant" else "user"
                callback(botReply ?: "Error: Empty response")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("Retrofit", "Error: ${response.message()} , $errorBody")
                callback("Error: ${response.message()} empty message")
            }
        }

        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
            Log.e("Retrofit", "Failed to fetch data from API: ${t.message}", t)
            callback("Error: ${t.message} could not fetch")
        }
    })
}

object RetrofitClient {
    private const val BASE_URL = "https://api.openai.com/"
    // Replace with your actual API key or read from file as needed

    private const val API_KEY= BuildConfig.API_KEY

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }
        .build()

    val instance: OpenAIApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        retrofit.create(OpenAIApi::class.java)
    }

}

@Composable
fun MessageCard(message: Message, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = if (isUser) Beige else Silver),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.elevatedCardElevation(12.dp),
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = buildAnnotatedString {
                    append("${message.role}:\n${message.content}")
                },
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ChatScreen() {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Message>()) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPrefManager by lazy { SharedPrefManager(context) }
    val userName = sharedPrefManager.getCurrentUsername()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(chatHistory) { message ->
                MessageCard(
                    message = message,
                    isUser = message.role == userName
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Row {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (userInput.isNotBlank()) {
                    isLoading = true
                    getChatbotResponse(userInput) { response ->
                        isLoading = false
                        if (userName != null) {
                            chatHistory = chatHistory + Message(role = userName, content = userInput)
                            chatHistory = chatHistory + Message(role = "FitAI", content = response)
                            userInput = ""
                        }
                    }
                }
            }) {
                Text("Send")
            }
        }
    }
}

