package com.shadow_shift_studio.travelagency_frontend.view.navBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.ScatterPlot
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.shadow_shift_studio.travelagency_frontend.CatalogButtonName
import com.shadow_shift_studio.travelagency_frontend.ConstructorButtonName
import com.shadow_shift_studio.travelagency_frontend.HomeButtonName
import com.shadow_shift_studio.travelagency_frontend.ProfileButtonName

data class NavBarItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)
object Constants {
    val BottomNavItems = listOf(
        NavBarItem(
            title = HomeButtonName,
            icon = Icons.Default.Home,
            route = "home"
        ),
        NavBarItem(
            title = ConstructorButtonName,
            icon = Icons.Default.ScatterPlot,
            route = "constructor"
        ),
        NavBarItem(
            title = CatalogButtonName,
            icon = Icons.Default.Luggage,
            route = "catalog"
        ),
        NavBarItem(
            title = ProfileButtonName,
            icon = Icons.Default.Face,
            route = "profile"
        )
    )
}