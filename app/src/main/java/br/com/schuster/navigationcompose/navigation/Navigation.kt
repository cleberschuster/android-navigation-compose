package br.com.schuster.navigationcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

// 4) DEFINIR ROTAS DE NAVEGAçÃO EM SI
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = RootGraph.ROOT,
        startDestination = AuthGraph.ROOT
    ) {
        authNavGraph(navController = navController)
        composable(route = HomeGraph.ROOT) {
            HomeViewContent()
        }
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = AppGraph.auth.ROOT,
        startDestination = AppGraph.auth.LOGIN
    ) {
        composable(route = AppGraph.auth.LOGIN) {
            LoginContent(
                onLoginClick = {
                    navController.popBackStack()
                    navController.navigate(AppGraph.home.ROOT)
                },
                onSignUpClick = { navController.navigate(AppGraph.auth.SIGN_UP) },
                onForgotClick = { navController.navigate(AppGraph.auth.FORGOT_PASSWORD) }
            )
        }
        composable(route = AppGraph.auth.SIGN_UP) {
            ViewContent(name = "SIGN UP") {}
        }
        composable(route = AppGraph.auth.FORGOT_PASSWORD) {
            ViewContent(name = "FORGOT PASSWORD") {}
        }
    }
}


@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = HomeGraph.ROOT,
        startDestination = HomeGraph.HOME
    ) {
        composable(route = HomeGraph.HOME) {
            ViewContent(
                name = "Homeeeee",
                onClick = {
                    navController.navigate(DetailsGraph.ROOT)
                }
            )
        }
        composable(route = HomeGraph.PROFILE) {
            ViewContent(
                name = "Profile",
                onClick = { }
            )
        }
        composable(route = HomeGraph.SETTINGS) {
            ViewContent(
                name = "Settings",
                onClick = { }
            )
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = AppGraph.details.ROOT,
        startDestination = AppGraph.details.HELP
    ) {
        composable(route = AppGraph.details.HELP) {
            ViewContent(name = "HELP") {
                navController.navigate(AppGraph.details.DISCLAIMER)
            }
        }
        composable(route = AppGraph.details.DISCLAIMER) {
            ViewContent(name = "DISCLAIMER") {
                navController.navigate(AppGraph.details.FAQ)
            }
        }
        composable(route = AppGraph.details.FAQ) {
            ViewContent(name = "FAQ") {
                navController.popBackStack(
                    route = AppGraph.details.FAQ,
                    inclusive = false
                )
            }
        }
    }
}