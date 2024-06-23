package com.example.fitnesstrackerandplanner

import Exercise
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SharedPrefManagerTest {

    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)
        `when`(sharedPreferences.edit()).thenReturn(editor)

        val context = mock(Context::class.java)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)

        sharedPrefManager = SharedPrefManager(context)
    }

    @Test
    fun testAddCurrentUserCaloriesConsumed() {
        `when`(sharedPreferences.getInt("currentUserCaloriesConsumed", 0)).thenReturn(100)
        sharedPrefManager.addCurrentUserCaloriesConsumed(200)
        verify(editor).putInt("currentUserCaloriesConsumed", 300)
        verify(editor).apply()
    }

    @Test
    fun testRemoveCurrentUserCaloriesConsumed() {
        sharedPrefManager.removeCurrentUserCaloriesConsumed()
        verify(editor).remove("currentUserCaloriesConsumed")
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserCaloriesConsumed() {
        `when`(sharedPreferences.getInt("currentUserCaloriesConsumed", 0)).thenReturn(150)
        assertEquals(150, sharedPrefManager.getCurrentUserCaloriesConsumed())
    }

    @Test
    fun testSaveCurrentUserFirstName() {
        sharedPrefManager.saveCurrentUserFirstName("John")
        verify(editor).putString("currentUserFirstName", "John")
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserFirstName() {
        `when`(sharedPreferences.getString("currentUserFirstName", null)).thenReturn("John")
        assertEquals("John", sharedPrefManager.getCurrentUserFirstName())
    }

    @Test
    fun testSaveCurrentUserHeight() {
        sharedPrefManager.saveCurrentUserHeight(180)
        verify(editor).putInt("currentUserHeight", 180)
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserHeight() {
        `when`(sharedPreferences.getInt("currentUserHeight", 0)).thenReturn(180)
        assertEquals(180, sharedPrefManager.getCurrentUserHeight())
    }

    @Test
    fun testSaveCurrentUserDailyCaloriesBurned() {
        sharedPrefManager.saveCurrentUserDailyCaloriesBurned(250.5)
        verify(editor).putFloat("userDailyCaloriesBurned", 250.5f)
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserDailyCaloriesBurned() {
        `when`(sharedPreferences.getFloat("userDailyCaloriesBurned", 0.0f)).thenReturn(250.5f)
        assertEquals(250.5f, sharedPrefManager.getCurrentUserDailyCaloriesBurned(), 0.0f)
    }

    @Test
    fun testClearAllValues() {
        sharedPrefManager.clearAllValues()
        verify(editor).clear()
        verify(editor).apply()
    }

    @Test
    fun testSaveCurrentUserWeight() {
        sharedPrefManager.saveCurrentUserWeight(70)
        verify(editor).putInt("currentUserWeight", 70)
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserWeight() {
        `when`(sharedPreferences.getInt("currentUserWeight", 0)).thenReturn(70)
        assertEquals(70, sharedPrefManager.getCurrentUserWeight())
    }

    @Test
    fun testSaveCurrentUsername() {
        sharedPrefManager.saveCurrentUsername("user123")
        verify(editor).putString("currentUsername", "user123")
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUsername() {
        `when`(sharedPreferences.getString("currentUsername", null)).thenReturn("user123")
        assertEquals("user123", sharedPrefManager.getCurrentUsername())
    }

    @Test
    fun testSaveCurrentUserAge() {
        sharedPrefManager.saveCurrentUserAge(25)
        verify(editor).putInt("currentUserAge", 25)
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserAge() {
        `when`(sharedPreferences.getInt("currentUserAge", 0)).thenReturn(25)
        assertEquals(25, sharedPrefManager.getCurrentUserAge())
    }

    @Test
    fun testSaveCurrentUserGender() {
        sharedPrefManager.saveCurrentUserGender(true)
        verify(editor).putBoolean("currentUserGender", true)
        verify(editor).apply()
    }

    @Test
    fun testGetCurrentUserGender() {
        `when`(sharedPreferences.getBoolean("currentUserGender", false)).thenReturn(true)
        assertTrue(sharedPrefManager.getCurrentUserGender())
    }

    @Test
    fun testSaveSelectedExercises() {
        val exerciseIds = listOf(1, 2, 3)
        sharedPrefManager.saveSelectedExercises(exerciseIds)
        verify(editor).putString("selected_exercises", "1,2,3")
        verify(editor).apply()
    }

    @Test
    fun testGetSelectedExercises() {
        `when`(sharedPreferences.getString("selected_exercises", "")).thenReturn("1,2,3")
        assertEquals(listOf(1, 2, 3), sharedPrefManager.getSelectedExercises())
    }

    @Test
    fun testSaveSelectedExercisesGO() {
        val exerciseIds = listOf(4, 5, 6)
        sharedPrefManager.saveSelectedExercisesGO(exerciseIds)
        verify(editor).putString("selected_exercises_go", "4,5,6")
        verify(editor).apply()
    }

    @Test
    fun testGetSelectedExercisesGO() {
        `when`(sharedPreferences.getString("selected_exercises_go", "")).thenReturn("4,5,6")
        assertEquals(listOf(4, 5, 6), sharedPrefManager.getSelectedExercisesGO())
    }

    @Test
    fun testRemoveSubExerciseFromSelectedGO() {
        `when`(sharedPreferences.getString("selected_exercises_go", "")).thenReturn("4,5,6")
        sharedPrefManager.removeSubExerciseFromSelectedGO(5)
        verify(editor).putString("selected_exercises_go", "4,6")
        verify(editor).apply()
    }

    @Test
    fun testClearSelectedExercisesGO() {
        sharedPrefManager.clearSelectedExercisesGO()
        verify(editor).remove("selected_exercises_go")
        verify(editor).apply()
    }

    @Test
    fun testClearSelectedExercises() {
        sharedPrefManager.clearSelectedExercises()
        verify(editor).remove("selected_exercises")
        verify(editor).apply()
    }

    @Test
    fun testSaveAllExercises() {
        val exercises = listOf(
            Exercise("Push-up", 30),
            Exercise("Squat", 45)
        )
        val gson = Gson()
        val json = gson.toJson(exercises)
        sharedPrefManager.saveAllExercises(exercises)
        verify(editor).putString("exercises", json)
        verify(editor).apply()
    }

    @Test
    fun testClearAllExercises() {
        sharedPrefManager.clearAllExercises()
        verify(editor).remove("exercises")
        verify(editor).apply()
    }
}
