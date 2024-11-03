package com.note.note.presentation.photoviewer


import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.appcompat.widget.AppCompatImageView


class ZoomableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private var currentZoom = 1f
    private var matrix: Matrix = Matrix()
    private var savedMatrix: Matrix = Matrix()
    private var scaleGestureDetector: ScaleGestureDetector
    private var lastTouchPoint = PointF()
    private var startTouchPoint = PointF()
    private var mode = NONE

    companion object {
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2
    }

    init {
        scaleType = ScaleType.MATRIX
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        setOnTouchListener(TouchListener())
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            currentZoom *= scaleFactor
            currentZoom = currentZoom.coerceIn(1f, 5f)
            matrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
            imageMatrix = matrix
            return true
        }
    }

    private inner class TouchListener : OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            scaleGestureDetector.onTouchEvent(event)

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    savedMatrix.set(matrix)
                    startTouchPoint.set(event.x, event.y)
                    lastTouchPoint.set(event.x, event.y)
                    mode = DRAG
                }
                MotionEvent.ACTION_MOVE -> {
                    if (mode == DRAG) {
                        val dx = event.x - lastTouchPoint.x
                        val dy = event.y - lastTouchPoint.y
                        matrix.postTranslate(dx, dy)
                        imageMatrix = matrix
                        lastTouchPoint.set(event.x, event.y)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mode = NONE
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    mode = ZOOM
                }
            }
            return true
        }
    }
}