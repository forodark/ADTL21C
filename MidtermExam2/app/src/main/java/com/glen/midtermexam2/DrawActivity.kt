// DrawActivity.kt

package com.glen.midtermexam2

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class DrawActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))

        supportActionBar?.title = "Doodler"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Custom View for drawing
    class DrawView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
        private val path = Path()
        private val paint = Paint()
        private var isEraserMode = false

        init {
            setupPaint()
        }

        private fun setupPaint() {
            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.ROUND
            paint.strokeWidth = 10f

            // Set the paint color based on the theme
            val textColor = if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                Color.WHITE
            } else {
                Color.BLACK
            }
            paint.color = textColor
        }

        // Method to toggle between pencil and eraser modes
        fun toggleEraserMode() {
            isEraserMode = !isEraserMode

            if (isEraserMode) {
                paint.color = Color.WHITE // Set color to white for eraser
            } else {
                // Set the paint color based on the theme for pencil mode
                val textColor = if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    Color.WHITE
                } else {
                    Color.BLACK
                }
                paint.color = textColor
            }
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawPath(path, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x = event.x
            val y = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(x, y)
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(x, y)
                }
                MotionEvent.ACTION_UP -> {
                    // Nothing to do here for now
                }
                else -> return false
            }

            // Invalidate the whole view to redraw it
            invalidate()
            return true
        }
    }
}
