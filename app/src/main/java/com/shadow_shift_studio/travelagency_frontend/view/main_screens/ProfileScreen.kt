package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_outline
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.view.cards.PassportHintCard
import com.shadow_shift_studio.travelagency_frontend.view.cards.TripCard

@Composable
fun ProfileScreen(navController: NavController) {
    val phoneNumber: String = "89137023733"
    val listState = rememberLazyListState()
    val tripsCount: Int = 2
    Column(
        Modifier
            .fillMaxWidth()
            .padding(Padding.dp)){
        Text(
            phoneNumber,
            color = md_theme_light_primary,
            modifier = Modifier
                .padding(bottom = 15.dp),
            fontSize = 28.sp,
            fontFamily = FontFamily.Monospace)

        Spacer(modifier = Modifier.height(15.dp))
        Column (
            Modifier.fillMaxWidth()
            .background(
                color = md_theme_dark_bottom_sheet_bottoms,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(Padding.dp)
        ) {
            Text(
                "Забронированные туры",
                color = md_theme_light_primary,
                modifier = Modifier
                    .padding(bottom = 15.dp),
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(15.dp))
            if (tripsCount >= 1) {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(count = tripsCount, key = null) { i ->
                        TripCard()
                    }
                }
            } else {
                Text(
                    "У вас еще нет забронированных туров",
                    color = md_theme_light_outline,
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    fontSize = 20.sp
                )
            }
        }
    }

}