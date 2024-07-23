package test.application.grafic.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import test.application.grafic.data.point.Point

class CustomChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }
    private val pointPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
        style = Paint.Style.FILL
    }

    private var points: List<Point> = emptyList()

    fun setPoints(points: List<Point>) {
        this.points = points
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (points.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()
        val padding = 50f

        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }

        val scaleX = (width - 2 * padding) / maxX
        val scaleY = (height - 2 * padding) / maxY

        canvas.drawLine(padding, height - padding, width - padding, height - padding, paint)
        canvas.drawLine(padding, padding, padding, height - padding, paint)

        for (i in points.indices) {
            val x = padding + points[i].x * scaleX
            val y = height - padding - points[i].y * scaleY

            if (i > 0) {
                val prevX = padding + points[i - 1].x * scaleX
                val prevY = height - padding - points[i - 1].y * scaleY
                canvas.drawLine(prevX, prevY, x, y, paint)
            }

            canvas.drawCircle(x, y, 5f, pointPaint)
        }
    }
}
