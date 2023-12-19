package com.shadow_shift_studio.travelagency_frontend.view.ImageGallery

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class Photo(val id: Int, val imageUrl: String)
@SuppressLint("SuspiciousIndentation")
@Composable
fun ImageGallery(photos: List<Photo>, onPhotoClick: (Photo) -> Unit){
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(photos.size) { i ->
            PhotoItem(photo = photos[i], onPhotoClick = onPhotoClick)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onPhotoClick: (Photo) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable { onPhotoClick(photo) }
    ) {
        AsyncImage(
            model = photo.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(400.dp)
                .height(300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 17.dp,
                        topEnd = 17.dp,
                        bottomStart = 17.dp,
                        bottomEnd = 17.dp
                    )
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullScreenGallery(selectedPhoto: Photo, photos: List<Photo>, onClose: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = {
        photos.size
    })

    Box(
        modifier = Modifier
            .background(Color.Black)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            AsyncImage(
                model = photos[page].imageUrl,
                contentDescription = null,
                // contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
        }
    }
}