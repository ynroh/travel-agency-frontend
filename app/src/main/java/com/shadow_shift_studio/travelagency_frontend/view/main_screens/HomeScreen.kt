package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController)
{
    val navControllerHomeScreen = rememberNavController()
    Column() {
        NavHost(
            navController = navControllerHomeScreen,
            startDestination = "main"
        ) {
            composable("main") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text("мяу")
                }
            }
        }
    }
}