package com.example.composejoyride.ui.theme.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.theme.liquid.LiquidBottomBar
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.kyant.backdrop.Backdrop

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    visibility: MutableState<Boolean>,
    backdrop: Backdrop?,
    useLiquid: Boolean = LiquidGlassSupport.enabled,
) {
    val liquidEnabled = useLiquid && backdrop != null && LiquidGlassSupport.enabled
    val isAdmin = remember { mutableStateOf(false) }
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if (uid != null) {
        Firebase.firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                isAdmin.value = document.getBoolean("isAdmin") ?: false
            }
            .addOnFailureListener {
                isAdmin.value = false
            }
    }

    val navItems = Constants.BottomNavItems.filter {
        if (isAdmin.value) {
            it.route != NoteGraph.PROFILE_SCREEN
        } else {
            it.route != NoteGraph.ADMIN_SCREEN
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedIndex = navItems.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    AnimatedVisibility(
        visible = visibility.value,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        if (liquidEnabled) {
            LiquidBottomBar(
                backdrop = backdrop!!,
                selectedIndex = selectedIndex,
                onTabSelected = { index ->
                    navController.navigate(navItems[index].route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                tabCount = navItems.size,
                modifier = Modifier.fillMaxWidth(),
            ) { index ->
                Icon(
                    imageVector = navItems[index].icon,
                    contentDescription = navItems[index].label,
                    tint = colorScheme.primary,
                )
            }
        } else {
            Surface(
                tonalElevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                shadowElevation = 8.dp,
                color = colorScheme.surface,
                modifier = Modifier
                    .background(color = colorScheme.background)
                    .navigationBarsPadding()
            ) {
                BottomNavigation(
                    elevation = 5.dp,
                    backgroundColor = colorScheme.secondary,
                ) {
                    navItems.forEach { navItem ->
                        BottomNavigationItem(
                            selected = currentRoute == navItem.route,
                            onClick = { navController.navigate(navItem.route) },
                            icon = { Icon(navItem.icon, contentDescription = navItem.label) },
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        }
    }
}
