package com.example.borutoapp.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.borutoapp.ui.theme.topAppBarBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Explore")
        }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topAppBarBackgroundColor),
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            }
        },

        )
}