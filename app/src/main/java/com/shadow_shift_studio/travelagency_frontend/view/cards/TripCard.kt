package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_inverseSurface
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_onPrimaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primaryContainer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun TripCard(){
    val title: String = "Мяу мур"
    val countrie: String = "Италия"
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    var startDate = LocalDate.parse("21-02-2003", formatter)
    var endDate = LocalDate.parse("15-12-2003", formatter)

    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(start = Padding.dp, bottom = Padding.dp, end = Padding.dp),
        colors = CardColors(
            Color.White,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface
        )
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Text(
                text = title,
                color = md_theme_light_onPrimaryContainer,
                modifier = Modifier
                    .padding(start = 10.dp),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = countrie,
                color = md_theme_light_onPrimaryContainer,
                modifier = Modifier
                    .padding(start = 10.dp),
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$startDate - $endDate",
                color = md_theme_light_onPrimaryContainer,
                modifier = Modifier
                    .padding(start = 10.dp),
                fontSize = 10.sp
            )
        }
    }
}