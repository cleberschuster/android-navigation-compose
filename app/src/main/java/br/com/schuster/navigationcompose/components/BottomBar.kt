package br.com.schuster.navigationcompose.components

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.schuster.navigationcompose.R
import br.com.schuster.navigationcompose.navigation.AppGraph

// 3) DEFINIR BOTTOM BAR / NAVIGATION BAR
// ++++++++++++++++++++++++++++++++++++++++++++++++++
// | Configuração dos itens da barra de navegação   |
// ++++++++++++++++++++++++++++++++++++++++++++++++++
sealed class BottomBarItemConfig(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    data object Home : BottomBarItemConfig(
        route = AppGraph.home.HOME,
        title = R.string.bottom_bar_home,
        icon = Icons.Default.Home
    )

    data object Profile : BottomBarItemConfig(
        route = AppGraph.home.PROFILE,
        title = R.string.bottom_bar_profile,
        icon = Icons.Default.Person
    )

    data object Settings : BottomBarItemConfig(
        route = AppGraph.home.SETTINGS,
        title = R.string.bottom_bar_settings,
        icon = Icons.Default.Settings
    )
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// | Lista de todas a configurações de rotas da barra de navegação  |
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
object BottomBarConfigList {
    val all = listOf(
        BottomBarItemConfig.Home,
        BottomBarItemConfig.Profile,
        BottomBarItemConfig.Settings,
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    // identificar destinação corrente
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showNavigationItems = BottomBarConfigList.all.any { it.route == currentDestination?.route }
    if (showNavigationItems) {
        NavigationBar {
            // ++++++++++++++++++++++++++++++++++++++++++
            // | Exiba os itens da barra de navegação   |
            // ++++++++++++++++++++++++++++++++++++++++++
            BottomBarConfigList.all.forEach { itemConfig ->
                AddItem(
                    itemConfig = itemConfig,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    itemConfig: BottomBarItemConfig,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    // esses 3 parametros sao necessarios para mudar a cor quando o componente esta pressionado
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val colorPressed = if (isPressed) Color.Green else Color.Unspecified

    NavigationBarItem(
        label = {
            Text(
                text = stringResource(itemConfig.title),
                color = colorPressed)
        },
        // Precisa deste interactionSource para funcionar, e chama a variavel colorPressed no tint do icon
        // a mudança de cor quando o componente esta pressionado
        interactionSource = interactionSource,
        icon = {
            Icon(
                imageVector = itemConfig.icon,
                contentDescription = "Navigation Icon",
                tint = colorPressed
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == itemConfig.route } == true,
        onClick = {
            navController.navigate(itemConfig.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}



