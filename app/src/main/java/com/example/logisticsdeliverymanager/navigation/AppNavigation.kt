package com.example.logisticsdeliverymanager.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.logisticsdeliverymanager.ui.screens.AddDeliveryScreen
import com.example.logisticsdeliverymanager.ui.screens.DashboardScreen
import com.example.logisticsdeliverymanager.ui.screens.DeliveriesScreen
import com.example.logisticsdeliverymanager.ui.screens.DeliveryDetailsScreen
import com.example.logisticsdeliverymanager.viewmodel.LogisticsViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val logisticsViewModel: LogisticsViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        "dashboard",
        "deliveries",
        "add_delivery"
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "dashboard",
                        onClick = {
                            navController.navigate("dashboard") {
                                popUpTo("dashboard") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "deliveries",
                        onClick = {
                            navController.navigate("deliveries") {
                                popUpTo("dashboard")
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.LocalShipping,
                                contentDescription = "Deliveries"
                            )
                        },
                        label = { Text("Deliveries") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "add_delivery",
                        onClick = {
                            navController.navigate("add_delivery") {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add delivery"
                            )
                        },
                        label = { Text("Add") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") {
                DashboardScreen(
                    viewModel = logisticsViewModel,
                    onOpenDeliveries = {
                        navController.navigate("deliveries")
                    },
                    onAddDelivery = {
                        navController.navigate("add_delivery")
                    }
                )
            }

            composable("deliveries") {
                DeliveriesScreen(
                    viewModel = logisticsViewModel,
                    onDeliveryClick = { deliveryId ->
                        navController.navigate("delivery_details/$deliveryId")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "delivery_details/{deliveryId}",
                arguments = listOf(
                    navArgument("deliveryId") {
                        type = NavType.IntType
                    }
                )
            ) { entry ->
                val deliveryId =
                    entry.arguments?.getInt("deliveryId") ?: -1

                DeliveryDetailsScreen(
                    deliveryId = deliveryId,
                    viewModel = logisticsViewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("add_delivery") {
                AddDeliveryScreen(
                    viewModel = logisticsViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onSaved = {
                        navController.navigate("deliveries") {
                            popUpTo("dashboard")
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
