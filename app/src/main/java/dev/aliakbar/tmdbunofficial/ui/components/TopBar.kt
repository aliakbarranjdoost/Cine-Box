@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aliakbar.tmdbunofficial.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TopBar(title: String, onNavigateBack: () -> Unit)
{
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack)
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun TopBar(
    title: String,
    isBookmarkAlready: Boolean,
    onNavigateBack: () -> Unit,
    onShare: () -> Unit,
    addToBookmark: () -> Unit,
    removeFromBookmark: () -> Unit)
{
    var isBookmark by remember { mutableStateOf(isBookmarkAlready) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onShare() }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null
                )
            }

            IconButton(onClick =
            {
                if (isBookmark)
                {
                    removeFromBookmark()
                }
                else
                {
                    addToBookmark()
                }
                isBookmark = !isBookmark
            }
            ) {
                Icon(
                    imageVector = if (isBookmark) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    tint = if (isBookmark) Color.Yellow else LocalContentColor.current,
                    contentDescription = null
                )
            }
        }
    )
}