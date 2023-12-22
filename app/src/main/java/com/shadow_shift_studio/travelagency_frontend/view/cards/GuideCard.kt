package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary

@Composable
fun GuideCard(){
    var name: String = "Марк Яснов"
    var description: String = "В моем \"рюкзаке знаний\" собраны самые интересные факты, забавные истории и несколько секретных тропинок, чтобы каждая ваша поездка стала неповторимым приключением. С меня - веселые рассказы, смешные анекдоты и немного вдохновения для того, чтобы ваш отдых стал по-настоящему незабываемым."
    Column( modifier = Modifier.padding(Padding.dp).fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://img06.teamo.ru/v2/crop?height=300&url=http://img06.teamo.ru:8080/d/9/30388945.jpg&width=300&sign=LP4gA5Nz52dShr6kEOp4%2B4IP2rgz0pYeThODXCxhn%2Bk%3D",
                contentDescription = "",
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .clip(
                        CircleShape
                    )
            )
            Text(text = name,
                color = md_theme_light_primary,
                fontSize = 23.sp,
                textAlign = TextAlign.Right,
                lineHeight =35.sp
                )
        }
        Spacer(modifier = Modifier.height(Padding.dp))
        Text(text = description,)
    }
}