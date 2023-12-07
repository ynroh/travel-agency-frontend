package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LastPage
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shadow_shift_studio.travelagency_frontend.view.cards.TourPreviewCard
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.travelagency_frontend.CatalogFilterButtonText
import com.shadow_shift_studio.travelagency_frontend.CatalogSearchBarText
import com.shadow_shift_studio.travelagency_frontend.CatalogSortingButton
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.Navbar
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_surfaceVariant

@Composable
fun CatalogScreen(navController: NavController){

    val navControllerCatalogScreen = rememberNavController()
    var sortingBottomSheetVisible by remember { mutableStateOf(false) }
    var filterBottomSheetVisible by remember { mutableStateOf(false) }

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
                    CardList(navControllerCatalogScreen)
                }
            }
            composable("TourPageScreen") {
                TourPage(navControllerCatalogScreen)
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
                .width(220.dp)
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

@Composable
fun CardList(navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(count = 10, key = null) { i ->
            TourPreviewCard(navController)
        }
    }
}