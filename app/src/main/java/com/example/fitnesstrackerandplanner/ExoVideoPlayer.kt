package com.example.fitnesstrackerandplanner

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable

fun VideoPlayerExo() {
     val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build()


    // Create a MediaSource
    val mediaSource = remember(EXAMPLE_VIDEO_URI) {
        MediaItem.fromUri(EXAMPLE_VIDEO_URI)
    }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }


    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

/*private fun initializePlayer() {
    player = ExoPlayer.Builder(requireContext()).build()
    binding.videoView.player = player

    object : YouTubeExtractor(requireContext()) {
        override fun onExtractionComplete(
            ytFiles: SparseArray<YtFile>?,
            videoMeta: VideoMeta?
        ) {
            if (ytFiles != null) {

                val iTag = 137//tag of video 1080
                val audioTag = 140 //tag m4a audio
                // 720, 1080, 480
                var videoUrl = ""
                val iTags: List<Int> = listOf(22, 137, 18)
                for (i in iTags) {
                    val ytFile = ytFiles.get(i)
                    if (ytFile != null) {
                        val downloadUrl = ytFile.url
                        if (downloadUrl != null && downloadUrl.isNotEmpty()) {
                            videoUrl = downloadUrl
                        }
                    }
                }
                if (videoUrl == "")
                    videoUrl = ytFiles[iTag].url
                val audioUrl = ytFiles[audioTag].url
                val audioSource: MediaSource = ProgressiveMediaSource
                    .Factory(DefaultHttpDataSource.Factory())
                    .createMediaSource(MediaItem.fromUri(audioUrl))
                val videoSource: MediaSource = ProgressiveMediaSource
                    .Factory(DefaultHttpDataSource.Factory())
                    .createMediaSource(MediaItem.fromUri(videoUrl))
                player?.setMediaSource(
                    MergingMediaSource(true, videoSource, audioSource), true
                )
                player?.prepare()
                player?.playWhenReady = playWhenReady
                player?.seekTo(currentWindow, playbackPosition)
                player?.addListener(this@VideoPlayerFragment)
            }
        }

    }.extract(youtubeLink)

}*/

