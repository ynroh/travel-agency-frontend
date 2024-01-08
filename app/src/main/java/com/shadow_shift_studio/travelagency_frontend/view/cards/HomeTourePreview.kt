package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_inverseSurface
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_onPrimaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_secondary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_secondaryContainer

@Composable
fun HomeTourPreviewCard(navController: NavController, tour: TourPreview, onId: (id : Long) -> Unit){
    val title: String = tour.title
    val price: String = tour.cost.toString()
    val countrie: String = tour.country.name
    val duration: String = tour.stayDuration.toString()
    val tourPrewievInfo = countrie + ", " + duration;
    val photo = tour.photosUrl[0]
    Card(
        modifier = Modifier
            .height(200.dp)
            .clickable {
                tour.id?.let { onId(it) }
                navController.navigate("TourPageScreen")
            }
            .padding(end = 11.dp),
        colors = CardColors(
            Color.White,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface
        )
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Box() {
                Row(modifier = Modifier .width(200.dp)) {
                    AsyncImage(
                        model = photo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 17.dp,
                                    bottomEnd = 17.dp
                                )
                            )

                            ,
                        contentDescription = "")
                }
                Row(
                    modifier = Modifier
                        .padding(top = 135.dp)
                ) {
                    Text(
                        text = title,
                        color = md_theme_light_secondary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 15.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 155.dp)
                ) {
                    Text(
                        text = price,
                        color = md_theme_light_primary,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 170.dp)
                ) {
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