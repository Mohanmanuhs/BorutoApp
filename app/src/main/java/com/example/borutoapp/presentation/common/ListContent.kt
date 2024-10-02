package com.example.borutoapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.wear.compose.material.ContentAlpha
import coil.compose.rememberAsyncImagePainter
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.components.RatingWidget
import com.example.borutoapp.presentation.components.handlePagingResult
import com.example.borutoapp.ui.theme.HERO_ITEM_HEIGHT
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.borutoapp.ui.theme.topAppBarContentColor
import com.example.borutoapp.util.Constants.BASE_URL

@Composable
fun ListContent(
    padding:PaddingValues,heroes: LazyPagingItems<Hero>,navController: NavHostController
) {
    val result = handlePagingResult(heroes = heroes)
    if (result) {
        LazyColumn(
            modifier = Modifier.padding(top=TOP_APP_BAR_HEIGHT+8.dp),
            contentPadding = PaddingValues(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(count = heroes.itemCount,
                key = { heroes[it]?.id ?: it },
                contentType = heroes.itemContentType { }) { index ->
                val hero = heroes[index]
                hero?.let {
                    HeroItem(hero = it, navController = navController)
                }
            }
        }
    }
}

@Composable
fun HeroItem(
    hero: Hero,navController: NavHostController
) {
    val painter = rememberAsyncImagePainter(
        model = "$BASE_URL${hero.image}",
        placeholder = painterResource(id = R.drawable.ic_placeholder),
        error = painterResource(id = R.drawable.ic_placeholder)
    )

    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            }, contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    maxLines = 1,
                    color = topAppBarContentColor,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 3,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(modifier = Modifier.padding(end = SMALL_PADDING), rating = hero.rating)
                    Text(
                        text = "(${hero.rating})",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}