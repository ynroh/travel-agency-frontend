package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_secondary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_tertiary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.yellow
import com.shadow_shift_studio.travelagency_frontend.view.Custom.imageGallery.FullScreenGallery
import com.shadow_shift_studio.travelagency_frontend.view.Custom.imageGallery.ImageGallery
import com.shadow_shift_studio.travelagency_frontend.view.Custom.imageGallery.Photo
import com.shadow_shift_studio.travelagency_frontend.view.cards.GuideCard
import com.shadow_shift_studio.travelagency_frontend.view.cards.TourPointCard


@Composable
fun TourPage(navController: NavController){
    val listState = rememberScrollState()
    var photo1 = Photo(0, "https://mykaleidoscope.ru/x/uploads/posts/2022-09/1663166898_7-mykaleidoscope-ru-p-amalfi-italiya-dostoprimechatelnosti-7.jpg")
    var photo2 = Photo(1, "https://mykaleidoscope.ru/x/uploads/posts/2022-09/1663166811_3-mykaleidoscope-ru-p-gunib-dagestan-dostoprimechatelnosti-krasi-4.jpg")
    var photo3 = Photo(2, "https://mykaleidoscope.ru/x/uploads/posts/2022-10/1666194742_50-mykaleidoscope-ru-p-v-dagestane-krasivo-51.jpg")
    var photo4 = Photo(3, "https://a.d-cd.net/tAYNg7WJuxU1M0N-Xa4WmOms-V0-1920.jpg")

    val navControllerTourPage = rememberNavController()

    val photos = listOf(photo1, photo2, photo3, photo4)

    var selectedPhoto by remember { mutableStateOf(photos.first()) }
    var isFullScreenGalleryVisible by remember { mutableStateOf(false) }

    NavHost(
        navController = navControllerTourPage,
        startDestination = "main"
    ) {
        composable("main") {
            if (isFullScreenGalleryVisible) {
                FullScreenGallery(selectedPhoto = selectedPhoto, photos = photos) {
                    isFullScreenGalleryVisible = false
                }
            } else {
                Box() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(listState)
                            .padding(Padding.dp)
                    ) {
                        ImageGallery(photos = photos) {
                            selectedPhoto = it; isFullScreenGalleryVisible = true
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = yellow,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            GuideCard()
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = md_theme_dark_tertiary,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            val listState = rememberLazyListState()
                            LazyRow(
                                state = listState,

                                ) {
                                items(count = 10, key = null) { i ->
                                    Row(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(
                                                color = md_theme_dark_tertiary,
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                    ) {
                                        TourPointCard()
                                    }
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { navControllerTourPage.navigate("TripScreen") },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(Padding.dp)
                    ) {
                        Icon(Icons.Default.AddShoppingCart, "")
                    }
                }
            }
        }
        composable("TripScreen") {
            TripScreen(navControllerTourPage)
        }
    }
}

