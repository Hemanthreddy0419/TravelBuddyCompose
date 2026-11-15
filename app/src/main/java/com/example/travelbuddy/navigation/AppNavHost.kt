package com.example.travelbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelbuddy.ui.screens.AboutScreen
import com.example.travelbuddy.ui.screens.DetailScreen
import com.example.travelbuddy.ui.screens.ListScreen

/** All route names in one place, so nobody has to repeat route strings. */
object Routes {
    const val LIST = "list"
    const val ABOUT = "about"

    const val DETAIL_ARG_ID = "id"
    const val DETAIL = "detail/{$DETAIL_ARG_ID}"

    /** Builds the concrete route for one destination, e.g. "detail/3". */
    fun detail(id: Int) = "detail/$id"
}

/**
 * The navigation graph. This replaces the Activity-to-Activity Intents of the XML version.
 * rememberNavController() survives rotation, so the current screen is kept.
 */
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LIST,
        modifier = modifier
    ) {
        composable(Routes.LIST) {
            ListScreen(
                onDestinationClick = { id -> navController.navigate(Routes.detail(id)) },
                onAboutClick = { navController.navigate(Routes.ABOUT) }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument(Routes.DETAIL_ARG_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            // Only a primitive Int travels in the route; the screen re-fetches the full object
            val id = backStackEntry.arguments?.getInt(Routes.DETAIL_ARG_ID) ?: -1
            DetailScreen(
                destinationId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
