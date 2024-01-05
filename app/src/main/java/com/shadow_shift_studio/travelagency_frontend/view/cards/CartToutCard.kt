package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_inverseSurface
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_secondary

@Composable
fun CartTour(){
    val title: String = "Ну мяу или что или гав"
    val price: String = "150 000Р"
    val countrie: String = "Италия"
    val duration: String = "5 дней"
    val tourPrewievInfo = countrie + ", " + duration;
    val photo = "https://ic.pics.livejournal.com/mg5642/66429722/2348596/2348596_original.jpg"
    Card(
        modifier = Modifier
            .padding(end = 11.dp),
        colors = CardColors(
            Color.White,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface
        )
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column() {
                AsyncImage(
                    model = photo,
                    modifier = Modifier
                        .width(140.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 17.dp,
                                bottomEnd = 17.dp
                            )
                        ),
                    contentDescription = ""
                )
            }
            Column() {
                Row() {
                    Text(
                        text = title,
                        color = md_theme_light_secondary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 15.sp
                    )
                }

                Row() {
                    Text(
                        text = price,
                        color = md_theme_light_primary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                }
                Row() {
                    Text(
                        text = tourPrewievInfo,
                        color = md_theme_light_secondary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}