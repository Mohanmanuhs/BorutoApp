package com.example.borutoapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.borutoapp.util.Constants.BASE_URL
import com.example.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.example.borutoapp.util.PaletteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(), navHostController: NavHostController
) {

    val selectedHero by detailsViewModel.selectedHero.collectAsState()
    val colorPalette by detailsViewModel.colorPalette

    if (colorPalette.isNotEmpty()) {
        DetailsContent(
            navController = navHostController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsViewModel.generateColorPalette()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        detailsViewModel.setColorPalette(
                            colors = extractColorsFromBitmap(
                                bitmap = bitmap
                            )
                        )
                    }
                }
            }
        }
    }

}