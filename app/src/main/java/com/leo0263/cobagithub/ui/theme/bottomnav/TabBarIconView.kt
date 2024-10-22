package com.leo0263.cobagithub.ui.theme.bottomnav

import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    normalIcon: ImageVector,
    selectedIcon: ImageVector,
    title: String,
) {
    Icon(
        imageVector = if (isSelected) {selectedIcon} else {normalIcon},
        contentDescription = title
    )
}
