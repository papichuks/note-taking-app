package com.example.notetakingapp.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction) }
}


fun NavController.safeNavigateUp() {
    currentDestination?.run { navigateUp() }
}