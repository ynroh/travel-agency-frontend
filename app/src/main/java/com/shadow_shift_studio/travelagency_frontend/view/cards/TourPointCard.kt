package com.shadow_shift_studio.travelagency_frontend.view.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_surfaceTint

@Composable
fun TourPointCard(tour: TourPreview) {
   /* val title: String = "Призрачный Гамсутль"
    val city: String = "Гамсутль"
    val stayDuration : Double = 2.5
    val excursion = Excursion("Тайны заброшенного города", "Эта экскурсия предлагает вам уникальную возможность исследовать древний заброшенный город в Дагестане, где каждое здание, каждая улица – свидетели ушедших эпох. На протяжении этой экскурсии вы окунетесь в атмосферу загадочности и увлекательных рассказов о прошлом города. Вы пройдете по забытым улочкам, освеживая свои воспоминания о временах, когда здесь звучали голоса жителей и раздавались гам и смех детей.")
    val hotel = Hotel("Гостиница Эльбрус", 3)*/

    val title = tour.routePoints?.get(0)?.title
    val city = tour.routePoints?.get(0)?.city
    val stayDuration = tour.routePoints?.get(0)?.stayDuration
    val excursion = tour.routePoints?.get(0)?.excursions
    val hotel = tour.routePoints?.get(0)?.hotels
    val tourPrewievInfo = city!!.name + ", " + stayDuration.toString() + " дн."

    Column(modifier = Modifier.padding(Padding.dp).width(260.dp) )
    {
        Text("Куда отправимся?",
            fontSize = 24.sp,
            color = md_theme_light_primary,)
        Spacer(modifier = Modifier.height(11.dp))
        Text(text = title.toString(),
            fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(tourPrewievInfo,
            fontSize = 15.sp)
        Spacer(modifier = Modifier.height(11.dp))


        Text("Что посмотрим?",
            fontSize = 24.sp,
            color = md_theme_light_primary,)
        Spacer(modifier = Modifier.height(11.dp))
        if (excursion != null) {
            Text(text = excursion.title.toString(),
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        ExpandableText(text = excursion!!.description)
        Spacer(modifier = Modifier.height(11.dp))

        Text("Где будем жить?",
            fontSize = 24.sp,
            color = md_theme_light_primary,)
        Spacer(modifier = Modifier.height(11.dp))
        Text(text = hotel!!.title,
            fontSize = 20.sp)
        MultipleImagesRow(hotel!!.raiting)
        Spacer(modifier = Modifier.height(11.dp))
    }
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 5,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value
    var textAlignJustify by remember { mutableStateOf(TextAlign.Justify) }
    var textAlignLeft by remember { mutableStateOf(TextAlign.Left) }


    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = textAlignLeft,
            onTextLayout = { textLayoutResultState.value = it },
            color = Color.Black,
            fontSize = 16.sp
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(
                "... Раскрыть",
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else
                            Modifier
                    )
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f),
                color = md_theme_light_surfaceTint,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun MultipleImagesRow(imageCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(imageCount) {
            AsyncImage(
                model = "https://www.pngplay.com/wp-content/uploads/1/Silver-Star-Transparent-Background.png",
                contentDescription = null, // Provide a content description
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )
        }
    }
}
