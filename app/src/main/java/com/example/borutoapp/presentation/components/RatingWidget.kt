package com.example.borutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.borutoapp.R
import com.example.borutoapp.ui.theme.StarColor


@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spacedBy: Dp = 1.dp
) {
    val result = calculateStars(rating = rating)
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacedBy)) {
        result["filledStars"]?.let {
            repeat(it){
                FilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["halfStars"]?.let {
            repeat(it){
                HalfFilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["emptyStars"]?.let {
            repeat(it){
                EmptyStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float

) {
    Canvas(modifier = Modifier.size(30.dp)) {
        val canvasSize = this.size
        scale(scale = scaleFactor) {
            val pathHeight = starPathBounds.height
            val pathWidth = starPathBounds.width
            val left = (canvasSize.width / 2) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(top = top, left = left) {
                drawPath(path = starPath, color = StarColor)

            }
        }

    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float

) {
    Canvas(modifier = Modifier.size(30.dp)) {
        val canvasSize = this.size
        scale(scale = scaleFactor) {
            val pathHeight = starPathBounds.height
            val pathWidth = starPathBounds.width
            val left = (canvasSize.width / 2) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(top = top, left = left) {
                drawPath(path = starPath, color = Color.LightGray.copy(alpha = 0.5f))

            }
        }

    }
}

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars = 5
    var filledStars by remember {
        mutableIntStateOf(0)
    }
    var emptyStars by remember {
        mutableIntStateOf(0)
    }
    var halfStars by remember { mutableIntStateOf(0) }


    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString().split(".").map {
            it.toInt()
        }
        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (filledStars == 5 && lastNumber > 0) {
                filledStars = 0
                halfStars = 0
                emptyStars = 5
            }
            if (lastNumber in 1..5) {
                halfStars++
            }else if(lastNumber in 6..9) {
                filledStars++
            }
        } else {
            Log.d("RatingWidget", "Invalid rating number")
        }
    }
    emptyStars = maxStars - (filledStars + halfStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfStars" to halfStars,
        "emptyStars" to emptyStars
    )
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(30.dp)) {
        val canvasSize = this.size
        scale(scale = scaleFactor) {
            val pathHeight = starPathBounds.height
            val pathWidth = starPathBounds.width
            val left = (canvasSize.width / 2) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(top = top, left = left) {
                drawPath(path = starPath, color = Color.LightGray.copy(alpha = 0.5f))
                clipPath(path = starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }

    }
}