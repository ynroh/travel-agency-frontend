package com.shadow_shift_studio.travelagency_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.Navbar
import com.shadow_shift_studio.travelagency_frontend.ui.theme.TravelAgencyTheme
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_background
import com.shadow_shift_studio.travelagency_frontend.view.authentication_screens.Authorization
import com.shadow_shift_studio.travelagency_frontend.view.main_screens.CatalogScreen
import com.shadow_shift_studio.travelagency_frontend.view.main_screens.HomeScreen
import com.shadow_shift_studio.travelagency_frontend.view.main_screens.ProfileScreen
import com.shadow_shift_studio.travelagency_frontend.view.navBar.Constants
import com.shadow_shift_studio.travelagency_frontend.view_model.CatalogViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isAuthorization by remember { mutableStateOf(false) }
            TravelAgencyTheme() {
                val navController = rememberNavController()
                Surface {
                    if(isAuthorization) {
                        Scaffold(
                            bottomBar = {
                                if (Navbar.getNavbarVisible())
                                    BottomNavigationBar(navController = navController)
                            },
                            content = { padding ->
                                NavHostContainer(
                                    navController = navController,
                                    padding = padding
                                )
                            }
                        )
                    }
                    else
                        Authorization(navController) { isAuthorization = true }
                }
            }
        }
    }
}


@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    val context = LocalContext.current
    val viewModelCatalog = CatalogViewModel(context)

    NavHost(
        navController = navController,

        startDestination = "home",

        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            composable("home") {
                HomeScreen(navController, viewModelCatalog);
            }

            composable("constructor") {
            }

            composable("catalog") {
                CatalogScreen(navController, viewModelCatalog)
            }

            composable("profile") {
                ProfileScreen(navController)
            }
        })

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(containerColor = md_theme_light_background) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->

            NavigationBarItem(
                selected = currentRoute == navItem.route,

                onClick = {
                    navController.navigate(navItem.route)
                },

                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },

                label = {
                    Text(text = navItem.title,
                        fontSize = 11.sp)
                },

                alwaysShowLabel = true
            )

        }
    }
}