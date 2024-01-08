package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.travelagency_frontend.ForVacationToursText
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.RomanticToursText
import com.shadow_shift_studio.travelagency_frontend.WeekendToursText
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.Filter
import com.shadow_shift_studio.travelagency_frontend.model.entity.CompilationFilter
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.model.enum.DurationCategory
import com.shadow_shift_studio.travelagency_frontend.model.enum.toFormattedDouble
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_onSecondaryContainer
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_tertiary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_tertiary
import com.shadow_shift_studio.travelagency_frontend.view.cards.HomeTourPreviewCard
import com.shadow_shift_studio.travelagency_frontend.view_model.CatalogViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, catalogViewModel: CatalogViewModel)
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
                            columnColor = md_theme_dark_onSecondaryContainer,
                            text = ForVacationToursText,
                            catalogViewModel = catalogViewModel,
                            Countries = emptyList<String>(),
                            Duration = listOf(DurationCategory.THREE_TO_FIVE_DAYS),
                            navControllerHomeScreen
                        )
                    }
                    Spacer(modifier = Modifier.height(Padding.dp))
                    Row() {
                        ToursCompilation(
                            columnColor = md_theme_light_tertiary,
                            text = RomanticToursText,
                            catalogViewModel = catalogViewModel,
                            Countries = listOf("Россия", "Италия", "Франция"),
                            Duration = emptyList<DurationCategory>(),
                            navControllerHomeScreen
                        )
                    }
                    Spacer(modifier = Modifier.height(Padding.dp))
                    Row() {
                        ToursCompilation(
                            columnColor = md_theme_dark_tertiary,
                            text = WeekendToursText,
                            catalogViewModel = catalogViewModel,
                            Countries = emptyList<String>(),
                            Duration = listOf(DurationCategory.ONE_TO_TWO_DAYS),
                            navControllerHomeScreen
                        )
                    }
                }
            }
            composable("TourPageScreen") {
                //TourPage(navControllerHomeScreen)
            }
        }
    }
}

@Composable
fun ToursCompilation(
    columnColor: Color,
    text: String,
    catalogViewModel: CatalogViewModel,
    Countries: List<String>, Duration: List<DurationCategory>,
    navController: NavController
    ) {
    var idValue by remember { mutableLongStateOf(0) }

    Column(
        modifier = Modifier
            .height(280.dp)
            .background(color = columnColor, shape = RoundedCornerShape(20.dp))
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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

        }
        Row(modifier = Modifier.padding(top = 11.dp, start = 11.dp ,bottom = 11.dp, end = 11.dp)){
            HorisontalTourCardList(navController, catalogViewModel, Countries, Duration){ id: Long ->
            idValue = id}
        }
    }
}
@Composable
fun HorisontalTourCardList(navController: NavController, catalogViewModel: CatalogViewModel, Countries: List<String>, Duration: List<DurationCategory>, onId: (id : Long) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var complationTours by remember {mutableStateOf<List<TourPreview>>(listOf<TourPreview>())}

    var compilationFilter: CompilationFilter = CompilationFilter()
    for (country in Countries)
        compilationFilter.selectedCountries.add(country)
    for (dur in Duration)
        compilationFilter.selectedDuration.add(dur)
    for(duration in compilationFilter.selectedDuration) {
        for (double in duration.toFormattedDouble())
            compilationFilter.selectedDurationToDouble.add(double)
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        coroutineScope.launch {
            complationTours = catalogViewModel.getCatalogCompilation(compilationFilter)
        }

        items(count = complationTours.size) { index ->
            HomeTourPreviewCard(navController, complationTours[index], onId)
        }
    }
}