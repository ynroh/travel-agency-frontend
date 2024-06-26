package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun TourPreviewCard(navController: NavController, tour: TourPreview, onId: (tour: TourPreview) -> Unit){
    val title: String? = tour.title
    val price: String? = tour.cost.toString()
    val countrie: String? = tour.country?.name
    val duration: Double? = tour.stayDuration
    val tourPrewievInfo = countrie + ", " + duration + " дн."
    Card(
        modifier = Modifier
            .height(320.dp)
            .clickable {
                tour?.let { onId(it) }
                navController.navigate("TourPageScreen")}
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
                    AsyncImage(
                        model = tour.photosUrl?.get(0),
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
                            .height(200.dp)
                            .width(420.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 200.dp)
                ) {
                    Text(
                        text = title.toString(),
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
                        text = price.toString(),
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
