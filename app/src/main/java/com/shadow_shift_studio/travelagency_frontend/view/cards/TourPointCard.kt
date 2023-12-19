package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.shadow_shift_studio.travelagency_frontend.view.ImageGallery.FullScreenGallery
import com.shadow_shift_studio.travelagency_frontend.view.ImageGallery.ImageGallery
import com.shadow_shift_studio.travelagency_frontend.view.ImageGallery.Photo

@Composable
fun TourPointCard() {
    val listState = rememberScrollState()
    var photo1 = Photo(
        0,
        "https://mykaleidoscope.ru/x/uploads/posts/2022-09/1663166898_7-mykaleidoscope-ru-p-amalfi-italiya-dostoprimechatelnosti-7.jpg"
    )
    var photo2 = Photo(
        1,
        "https://mykaleidoscope.ru/x/uploads/posts/2022-09/1663166811_3-mykaleidoscope-ru-p-gunib-dagestan-dostoprimechatelnosti-krasi-4.jpg"
    )
    var photo3 = Photo(
        2,
        "https://mykaleidoscope.ru/x/uploads/posts/2022-10/1666194742_50-mykaleidoscope-ru-p-v-dagestane-krasivo-51.jpg"
    )
    var photo4 = Photo(3, "https://a.d-cd.net/tAYNg7WJuxU1M0N-Xa4WmOms-V0-1920.jpg")

    val photos = listOf(photo1, photo2, photo3, photo4)

    var selectedPhoto by remember { mutableStateOf(photos.first()) }
    var isFullScreenGalleryVisible by remember { mutableStateOf(false) }

    if (isFullScreenGalleryVisible) {
        FullScreenGallery(selectedPhoto = selectedPhoto, photos = photos) {
            isFullScreenGalleryVisible = false
        }
    } else {
        Column()
        {
            ImageGallery(photos = photos) { selectedPhoto = it; isFullScreenGalleryVisible = true }
            Text("хуй")
        }
    }
}