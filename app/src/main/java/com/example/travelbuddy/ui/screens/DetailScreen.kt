package com.example.travelbuddy.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.R
import com.example.travelbuddy.data.DestinationRepository
import com.example.travelbuddy.ui.components.AppTopBar
import com.example.travelbuddy.ui.components.EmojiAvatar
import com.example.travelbuddy.ui.components.InfoBlock
import com.example.travelbuddy.ui.components.RatingText
import com.example.travelbuddy.ui.startSafely

/**
 * Screen 2: full details of one destination.
 *
 * Only the [destinationId] travels through the navigation route. The full object is looked up
 * again here, so nothing complex has to be passed between screens.
 */
@Composable
fun DetailScreen(
    destinationId: Int,
    onBack: () -> Unit
) {
    val destination = DestinationRepository.findById(destinationId)
    val context = LocalContext.current

    // Saved across rotation. Keyed by id so each destination has its own value.
    var isFavourite by rememberSaveable(destinationId) { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = destination?.name ?: stringResource(R.string.title_detail_fallback),
                onBack = onBack
            )
        }
    ) { innerPadding ->

        if (destination == null) {
            // Unknown id: show a message instead of crashing
            Text(
                text = stringResource(R.string.error_no_destination),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmojiAvatar(emoji = destination.emoji, size = 96.dp)

            Text(
                text = destination.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = destination.country,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            RatingText(
                rating = destination.rating,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = destination.tagline,
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoBlock(
                    label = stringResource(R.string.label_best_season),
                    value = destination.bestSeason,
                    modifier = Modifier.weight(2f)
                )
                InfoBlock(
                    label = stringResource(R.string.label_budget),
                    value = destination.budget,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = stringResource(R.string.label_about_place),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            Text(
                text = destination.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )

            // Favourite toggle
            OutlinedButton(
                onClick = { isFavourite = !isFavourite },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = stringResource(
                        if (isFavourite) R.string.favourite_remove else R.string.favourite_add
                    )
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Implicit Intent #1: ACTION_SEND opens the system share sheet
                Button(
                    onClick = {
                        val message = context.getString(
                            R.string.share_text_format,
                            destination.name,
                            destination.country,
                            destination.tagline,
                            destination.webUrl
                        )
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, destination.name)
                            putExtra(Intent.EXTRA_TEXT, message)
                        }
                        context.startSafely(
                            Intent.createChooser(
                                sendIntent,
                                context.getString(R.string.share_chooser_title)
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(stringResource(R.string.action_share))
                }

                // Implicit Intent #2: ACTION_VIEW opens the web page in a browser
                Button(
                    onClick = {
                        context.startSafely(
                            Intent(Intent.ACTION_VIEW, Uri.parse(destination.webUrl))
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.action_open_web))
                }
            }
        }
    }
}
