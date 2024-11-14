package com.example.signhope

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class VideoTutorials : VideoView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private var videoWidth: Int = 0
    private var videoHeight: Int = 0

    fun setVideoSize(width: Int, height: Int) {
        videoWidth = width
        videoHeight = height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = getDefaultSize(videoWidth, widthMeasureSpec)
        var height = getDefaultSize(videoHeight, heightMeasureSpec)

        if (videoWidth > 0 && videoHeight > 0) {
            if (videoWidth * height > width * videoHeight) {
                // video height is too large, fix height based on width
                height = width * videoHeight / videoWidth
            } else if (videoWidth * height < width * videoHeight) {
                // video width is too small, fix width based on height
                width = height * videoWidth / videoHeight
            }
        }

        setMeasuredDimension(width, height)
    }
}