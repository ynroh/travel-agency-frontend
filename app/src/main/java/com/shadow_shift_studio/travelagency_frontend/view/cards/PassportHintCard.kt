package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.layout.Column
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
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_outline
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_outlineVariant
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_secondary

@Composable
fun PassportHintCard()
{
    val series = "5017"
    val number = "776905"
    val surname = "Кунина"
    val name = "Александра"
    val middleName = "Олеговна"

    Card(
        modifier = Modifier
            .padding(end = 11.dp),
        colors = CardColors(
            md_theme_dark_outline,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface,
            md_theme_dark_inverseSurface
        )
    ){
        Column(Modifier.padding(Padding.dp)){
            Text(text = "$series $number",
                color = md_theme_dark_outlineVariant,
                fontSize = 10.sp)
            Text(text = "$surname $name $middleName",
                color = md_theme_dark_outlineVariant,
                fontSize = 10.sp)
        }
    }
}
