package test.application.grafic.data.point

import androidx.annotation.Keep

@Keep
data class PointResponse(
    val points: ArrayList<Point>
)

@Keep
data class Point(
    val x: Float,
    val y: Float
)
