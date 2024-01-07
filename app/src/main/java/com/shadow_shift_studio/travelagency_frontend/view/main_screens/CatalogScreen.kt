package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.shadow_shift_studio.travelagency_frontend.view.cards.TourPreviewCard
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.travelagency_frontend.CatalogSearchBarText
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.Filter
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.Navbar
import com.shadow_shift_studio.travelagency_frontend.model.entity.Country
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.model.enum.DurationCategory
import com.shadow_shift_studio.travelagency_frontend.model.enum.toFormattedDouble
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_onPrimary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.travelagency_frontend.view_model.CatalogViewModel
import kotlinx.coroutines.launch

@Composable
fun CatalogScreen(navController: NavController, viewModel: CatalogViewModel){

    val navControllerCatalogScreen = rememberNavController()
    var sortingBottomSheetVisible by remember { mutableStateOf(false) }
    var filterBottomSheetVisible by remember { mutableStateOf(false) }
    var idValue by remember { mutableLongStateOf(0) }

    LaunchedEffect(viewModel) {
        viewModel.getCatalog()
    }

    Column() {
        NavHost(
            navController = navControllerCatalogScreen,
            startDestination = "main"
        ) {
            composable("main") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SearchBar()
                    CatalogButtons(
                        changeButtonSheetSortVisible = { sortingBottomSheetVisible = true },
                        changeButtonSheetFilterVisible = { filterBottomSheetVisible = true }
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                    CardList(navControllerCatalogScreen, viewModel){ id: Long ->
                    idValue = id}
                }
            }
            composable("TourPageScreen") {
                TourPage(navControllerCatalogScreen)
            }
        }
    }
    AnimatedVisibility(
        visible = filterBottomSheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            FilterButtonSheet(onClose = { filterBottomSheetVisible = false }, viewModel = viewModel)
        }
    )
    AnimatedVisibility(
        visible = sortingBottomSheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            SortingBottomSheet(onClose = { sortingBottomSheetVisible = false })
        }
    )
}

@Composable
fun CardList(navController: NavController, viewModel:CatalogViewModel, onId: (id : Long) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val listState = rememberLazyListState()
    val catalogState = remember { mutableStateOf<List<TourPreview>?>(null) }
    val catalogObserver = Observer<List<TourPreview>> { newCatalog ->
        catalogState.value = newCatalog
    }

    DisposableEffect(viewModel) {
        viewModel.catalogTours.observeForever(catalogObserver)

        onDispose {
            viewModel.catalogTours.removeObserver(catalogObserver)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            catalogState.value?.let {
                items(count = it.size, key = null) { index ->
                    TourPreviewCard(navController, it[index], onId)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val horizontalPadding = animateDpAsState(if (expanded) 0.dp else Padding.dp).value
    val verticalPadding = animateDpAsState(if (expanded) 0.dp else 11.dp).value
    Navbar.setNavbarVisible(!active)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        androidx.compose.material3.SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { },
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
                active = false
                expanded = false
                Navbar.setNavbarVisible(index = false)
            },
            active = active,
            onActiveChange = {
                active = it
                expanded = it
            },
            placeholder = {
                Text(text = CatalogSearchBarText)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            searchText = ""
                            active = false
                            expanded = false
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            },
        ) {
        }
    }
}

@Composable
fun CatalogButtons(
    changeButtonSheetSortVisible: () -> Unit,
    changeButtonSheetFilterVisible: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = ColorPainter(md_theme_light_primary), "",
            modifier = Modifier
                .width(170.dp)
                .height(2.dp)
        )
        Spacer(modifier = Modifier.width(Padding.dp))
        ExtendedFloatingActionButton(
            onClick = {
                changeButtonSheetFilterVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(60.dp)
                .height(40.dp),
            containerColor = md_theme_light_primary,
            contentColor = md_theme_light_surfaceVariant
        ) {
            Icon(
                Icons.Default.Tune,
                "filter icon",
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }
        Spacer(modifier = Modifier.width(Padding.dp))
        ExtendedFloatingActionButton(
            onClick = { /*TODO*/
                changeButtonSheetSortVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(60.dp)
                .height(40.dp),
            containerColor = md_theme_light_primary,
            contentColor = md_theme_light_surfaceVariant
        ) {
            Icon(
                Icons.Default.SwapVert,
                "Sort icon",
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingBottomSheet(onClose: () -> Unit) {
    val scaffoldState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = scaffoldState,
        modifier = Modifier.height(400.dp),
        containerColor = md_theme_dark_onPrimary
    ) {
        ButtonsForSorting(onClose = { onClose() })
    }
}

@Composable
fun ButtonsForSorting(onClose: () -> Unit) {
    Column {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = "По цене ↑",
                color = md_theme_dark_onPrimary)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = "По цене ↓",
                color = md_theme_dark_onPrimary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FilterButtonSheet(onClose: () -> Unit, viewModel: CatalogViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val countriesState = remember { mutableStateOf<List<Country>?>(null) }

    val countriesObserver = Observer<List<Country>> { newCountries ->
        countriesState.value = newCountries
    }

    LaunchedEffect(viewModel) {
        viewModel.getCountries()
    }


    DisposableEffect(viewModel) {
        viewModel.countriesLiveData.observeForever(countriesObserver)

        onDispose {
            viewModel.countriesLiveData.removeObserver(countriesObserver)
        }
    }

    val scaffoldState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
                coroutineScope.launch {
                    viewModel.getCatalog()
                }
            onClose()
        },
        sheetState = scaffoldState,
        modifier = Modifier
            .heightIn(400.dp, 800.dp),
        containerColor = md_theme_dark_onPrimary
    ) {
        countriesState.value?.let { countriesState.value?.let { it1 -> FilterButtons(it1, viewModel) { onClose() } } }
        //it1 -> FilterButtons(it1, viewModel) { onClose() }
    }
}

@Composable
fun FilterButtons(countries: List<Country>, viewModel: CatalogViewModel, onClose:() -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        ButtonPrice()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsCountry(countries, viewModel)
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsDuration()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.getCatalog()
                    }
                    onClose()
                }
            ) {
                Text(text = "Применить")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = {
                    Filter.resetAllSelected()
                    coroutineScope.launch {
                        viewModel.getCatalog()
                    }
                    onClose()
                }
            ) {
                Text(text = "Сбросить")
            }
        }
    }
}

@Composable
fun ButtonPrice()
{
    val min = remember{mutableStateOf("")}
    val max = remember{mutableStateOf("150000")}

    Text(text = "Цена")
    Row(horizontalArrangement = Arrangement.SpaceBetween)
    {
        TextField(
            value = min.value,
            onValueChange = {newText -> min.value = newText},
            placeholder = {Text("От")}
        )
        TextField(
            value = max.value,
            onValueChange = {newText -> max.value = newText},
            placeholder = {Text("До")}
        )
    }

    Filter.selectedMinPrice.value = min.value.toInt()
    Filter.selectedMaxPrice.value = max.value.toInt()

}

@Composable
fun ButtonsDuration() {
    val isTitleStatusExpanded = remember { mutableStateOf(false) }
    var isOneTwoSelected = remember { mutableStateOf(false) }
    var isThreeFiveSelected = remember { mutableStateOf(false) }
    var isSevenTenSelected = remember { mutableStateOf(false) }
    var isElevenFourteenSelected = remember { mutableStateOf(false) }

    for (duration in Filter.selectedDuration) {
        if (duration == DurationCategory.ONE_TO_TWO_DAYS) isOneTwoSelected.value = true
        if (duration == DurationCategory.THREE_TO_FIVE_DAYS) isThreeFiveSelected.value = true
        if (duration == DurationCategory.SEVEN_TO_TEN_DAYS) isSevenTenSelected.value = true
        if (duration == DurationCategory.ELEVEN_FOURTEEN_DAYS) isElevenFourteenSelected.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isTitleStatusExpanded.value = !isTitleStatusExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Длительность")
                if (!isTitleStatusExpanded.value) Icon(
                    Icons.Default.ArrowRight,
                    contentDescription = ""
                )
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isTitleStatusExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "1-2 дня")
                            Checkbox(
                                checked = isOneTwoSelected.value,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        Filter.selectedDuration.add(DurationCategory.ONE_TO_TWO_DAYS)
                                    } else {
                                        Filter.selectedDuration.remove(DurationCategory.ONE_TO_TWO_DAYS)
                                        isOneTwoSelected.value = false
                                    }
                                }
                            )
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "3-5 дней")
                            Checkbox(
                                checked = isThreeFiveSelected.value,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        Filter.selectedDuration.add(DurationCategory.THREE_TO_FIVE_DAYS)
                                    } else {
                                        Filter.selectedDuration.remove(DurationCategory.THREE_TO_FIVE_DAYS)
                                        isThreeFiveSelected.value = false
                                    }

                                }
                            )
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "7-10 дней")
                            Checkbox(
                                checked = isSevenTenSelected.value,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        Filter.selectedDuration.add(DurationCategory.SEVEN_TO_TEN_DAYS)
                                    } else {
                                        Filter.selectedDuration.remove(DurationCategory.SEVEN_TO_TEN_DAYS)
                                        isSevenTenSelected.value = false
                                    }
                                }
                            )
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "11-14 дней")
                            Checkbox(
                                checked = isElevenFourteenSelected.value,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        Filter.selectedDuration.add(DurationCategory.ELEVEN_FOURTEEN_DAYS)
                                    } else {
                                        Filter.selectedDuration.remove(DurationCategory.ELEVEN_FOURTEEN_DAYS)
                                        isElevenFourteenSelected.value = false
                                    }
                                }
                            )
                        }
                    }
                }
            }
        )
    }
    for(duration in Filter.selectedDuration) {
        for (double in duration.toFormattedDouble())
            Filter.selectedDurationToDouble.add(double)
    }
}

@Composable
fun ButtonsCountry(countries: List<Country>, viewModel: CatalogViewModel) {
    val isCategoryExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isCategoryExpanded.value = !isCategoryExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Страна")
                if (!isCategoryExpanded.value) Icon(
                    Icons.Default.ArrowRight,
                    contentDescription = ""
                )
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isCategoryExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(start = 23.dp, end = 23.dp)
                ) {
                    for (country in countries) {
                        var checkStatus: Boolean = false
                        for(selectedCountry in Filter.selectedCountries) {
                            if(selectedCountry == country.name)
                                checkStatus = true
                        }
                        CounrtyButton(country = country, checkStatus, viewModel) { isChecked ->
                            if (isChecked) {
                                Filter.selectedCountries.add(country.name)
                            } else {
                                Filter.selectedCountries.remove(country.name)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun CounrtyButton(country: Country, checkStatus: Boolean, viewModel: CatalogViewModel, onCheckedChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(checkStatus) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = country.name)
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = !isChecked
            onCheckedChange(isChecked)
        })
    }
}