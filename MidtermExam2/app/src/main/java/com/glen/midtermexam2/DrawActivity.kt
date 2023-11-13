// DrawActivity.kt

package com.glen.midtermexam2

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.PathParser

class DrawActivity : BaseActivity() {

    lateinit var drawView: DrawView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawView = DrawView(this)


        setContentView(drawView)

        supportActionBar?.title = "Doodler"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.draw_clear -> {
                drawView.clearDrawing()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val clearMenuItem = menu.findItem(R.id.draw_clear)

        if (clearMenuItem != null) {
            clearMenuItem.isVisible = true
        }
        // You may need to call invalidateOptionsMenu() if your changes don't reflect immediately.
        // invalidateOptionsMenu()

        return super.onPrepareOptionsMenu(menu)
    }


    // Custom View for drawing
    class DrawView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
        private val path = Path()
        private val paint = Paint()

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

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawPath(path, paint)
        }

        fun clearDrawing() {
            path.reset()
            // Invalidate the whole view to redraw it
            invalidate()
        }

        fun getPathData(): String {
            return path.toString()
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
