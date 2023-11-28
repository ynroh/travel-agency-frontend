package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_inversePrimary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_inverseSurface
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_onPrimary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_background
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_onPrimaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_secondary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_secondaryContainer

@Composable
fun TourPreviewCard(){
    val title: String = "Ну мяу или что или гав"
    val price: String = "150 000Р"
    val countrie: String = "Италия"
    val duration: String = "5 дней"
    val tourPrewievInfo = countrie + ", " + duration;
    Card(
        modifier = Modifier
            .height(320.dp)
            .clickable {}
            .padding(start = Padding.dp, bottom = Padding.dp, end = Padding.dp),
        colors = CardColors(
            md_theme_light_primaryContainer,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface
        )
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Box() {
                Row() {
                    GradientImage(
                        startColor = Color.Transparent,
                        endColor = md_theme_light_primaryContainer
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 200.dp)
                ) {
                    Text(
                        text = title,
                        color = md_theme_light_onPrimaryContainer,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                    .padding(top = 230.dp)
                ) {
                    Text(
                        text = price,
                        color = md_theme_light_primary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 24.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 260.dp)
                ) {
                    Text(
                        text = tourPrewievInfo,
                        color = md_theme_light_secondary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun GradientImage(startColor: Color, endColor: Color) {
    val coverHeightPx = 206
    Box(modifier = Modifier.fillMaxSize()) {
       /* AsyncImage(
            model = "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 17.dp,
                        topEnd = 17.dp,
                        bottomStart = 17.dp,
                        bottomEnd = 17.dp
                    )
                )
                .height(coverHeightPx.dp)
                .width(412.dp)
        )*/
        Image(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 17.dp,
                        topEnd = 17.dp,
                        bottomStart = 17.dp,
                        bottomEnd = 17.dp
                    )
                )
                .height(200.dp)
                .width(420.dp),
            painter = ColorPainter(Color.Magenta),
            contentDescription = "Красный прямоугольник")
        Box(
            modifier = Modifier
                .drawBehind {
                    val gradientBrush = Brush.verticalGradient(
                        colors = listOf(startColor, endColor),
                        startY = size.height - size.width,
                        endY = size.height
                    )

                    drawRect(brush = gradientBrush)
                }
                .padding(top = (coverHeightPx).dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomCenter)
                .background(Color.Transparent)
        )

    }
}