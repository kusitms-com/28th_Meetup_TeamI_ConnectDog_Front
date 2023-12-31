package com.kusitms.connectdog.feature.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.management.ManagementRoute

fun NavController.navigateManagement(navOptions: NavOptions) {
    navigate(ManagementRoute.route, navOptions)
}

fun NavGraphBuilder.managementNavGraph(
    onBackClick: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit
) {
    composable(route = ManagementRoute.route) {
        ManagementRoute(
            onBackClick,
            onShowErrorSnackbar
        )
    }
}

object ManagementRoute {
    const val route = "management"
}
