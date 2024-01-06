@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package dev.aliakbar.tmdbunofficial.ui.components

import android.media.Image
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import dev.aliakbar.tmdbunofficial.R

@Composable
fun Image(url: String,modifier: Modifier = Modifier)
{
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
    {
        val state = painter.state

        if (state is AsyncImagePainter.State.Loading)
        {
            ImageLoadingAnimation()
        }
        else
        {
            SubcomposeAsyncImageContent()
        }
    }

}
@Composable
fun ImageLoadingAnimation(modifier: Modifier = Modifier)
{
    Box(modifier = Modifier.fillMaxSize())
    {
        var atEnd by rememberSaveable { mutableStateOf(false) }

        Image(
            painter = rememberAnimatedVectorPainter(
                animatedImageVector = AnimatedImageVector.animatedVectorResource(
                    R.drawable.ic_hourglass_animated
                ), atEnd = atEnd
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .size(50.dp, 50.dp)
                .align(Alignment.Center)
        )
        atEnd = true
    }
}