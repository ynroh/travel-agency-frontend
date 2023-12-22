package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.travelagency_frontend.ForUToursText
import com.shadow_shift_studio.travelagency_frontend.HotToursText
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.RomanticToursText
import com.shadow_shift_studio.travelagency_frontend.WeekendToursText
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_onSecondaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_secondary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_tertiary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_error
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_onPrimary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_onPrimaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_surfaceTint
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_tertiary
import com.shadow_shift_studio.travelagency_frontend.view.cards.HomeTourPreviewCard
import com.shadow_shift_studio.travelagency_frontend.view.cards.TourPreviewCard

@Composable
fun HomeScreen(navController: NavController)
{
    val navControllerHomeScreen = rememberNavController()
    val listState = rememberScrollState()

    Column() {
        NavHost(
            navController = navControllerHomeScreen,
            startDestination = "main"
        ) {
            composable("main") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(listState)
                        .padding(Padding.dp)
                ) {
                    Row() {
                        ToursCompilation(
                            columnColor = md_theme_dark_primary,
                            text = HotToursText,
                            navControllerHomeScreen
                        )
                    }
                    Spacer(modifier = Modifier.height(Padding.dp))
                    Row() {
                        ToursCompilation(
                            columnColor = md_theme_dark_onSecondaryContainer,
                            text = ForUToursText,
                            navControllerHomeScreen
                        )
                    }
                    Spacer(modifier = Modifier.height(Padding.dp))
                    Row() {
                        ToursCompilation(
                            columnColor = md_theme_light_tertiary,
                            text = RomanticToursText,
                            navControllerHomeScreen
                        )
                    }
                    Spacer(modifier = Modifier.height(Padding.dp))
                    Row() {
                        ToursCompilation(
                            columnColor = md_theme_dark_tertiary,
                            text = WeekendToursText,
                            navControllerHomeScreen
                        )
                    }
                }
            }
            composable("TourPageScreen") {
                TourPage(navControllerHomeScreen)
            }
        }
    }
}

@Composable
fun ToursCompilation(
    columnColor: Color,
    text: String,
    navController: NavController) {
    Column(
        modifier = Modifier
            .height(280.dp)
            .background(color = columnColor, shape = RoundedCornerShape(20.dp))
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 11.dp, start = 11.dp, end = 11.dp)
                .height(50.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text,
                color = md_theme_light_primary,
                modifier = Modifier
                    .padding(start = 10.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace
            )
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/
                },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(60.dp)
                    .height(40.dp)
                    .padding(end = 11.dp),
                containerColor = md_theme_light_primary,
                contentColor = md_theme_light_surfaceVariant
            ) {
                Icon(
                    Icons.Default.ArrowForwardIos,
                    "more",
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }

        }
        Row(modifier = Modifier.padding(top = 11.dp, start = 11.dp ,bottom = 11.dp, end = 11.dp)){
            HorisontalTourCardList(navController)
        }
    }
}
@Composable
fun HorisontalTourCardList(navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(count = 10, key = null) { i ->
            HomeTourPreviewCard(navController)
        }
    }
}