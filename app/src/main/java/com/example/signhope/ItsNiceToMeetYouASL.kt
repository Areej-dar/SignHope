package com.example.signhope

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class ItsNiceToMeetYouASL : AppCompatActivity() {
    private lateinit var videoView: VideoTutorials
    private lateinit var playPauseButton: ImageButton
    private lateinit var videoSeekBar: SeekBar
    private lateinit var currentTime: TextView
    private lateinit var remainingTime: TextView
    private lateinit var handler: Handler
    private var isDragging = false
    private var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videos)

        videoView = findViewById(R.id.videoView1)
        playPauseButton = findViewById(R.id.playPauseButton)
        videoSeekBar = findViewById(R.id.videoSeekBar)
        currentTime = findViewById(R.id.currentTime)
        remainingTime = findViewById(R.id.remainingTime)
        handler = Handler()

        // Specify the path of your video clip
        val videoPath = "android.resource://" + packageName + "/" + R.raw.it_is_nice_to_meet_you
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener { mp: MediaPlayer ->
            videoView.setVideoSize(mp.videoWidth, mp.videoHeight)
            videoSeekBar.max = mp.duration
            videoView.start()
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
            startSeekBarUpdate()
        }

        videoView.setOnCompletionListener {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            stopSeekBarUpdate()
        }

        playPauseButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                playPauseButton.setImageResource(android.R.drawable.ic_media_play)
                stopSeekBarUpdate()
            } else {
                videoView.start()
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
                startSeekBarUpdate()
            }
        }

        videoSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoView.seekTo(progress)
                    updateTimeDisplay()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isDragging = true
                stopSeekBarUpdate()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                isDragging = false
                videoView.seekTo(seekBar.progress)
                updateTimeDisplay()
                startSeekBarUpdate()
            }
        })
    }

    private fun startSeekBarUpdate() {
        isUpdating = true
        handler.post(updateSeekBarRunnable)
    }

    private fun stopSeekBarUpdate() {
        isUpdating = false
        handler.removeCallbacks(updateSeekBarRunnable)
    }

    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            if (isUpdating) {
                videoSeekBar.progress = videoView.currentPosition
                updateTimeDisplay()
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun updateTimeDisplay() {
        val currentPos = videoView.currentPosition
        val duration = videoView.duration
        currentTime.text = formatTime(currentPos)
        remainingTime.text = formatTime(duration - currentPos)
    }

    private fun formatTime(time: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(time.toLong()) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
