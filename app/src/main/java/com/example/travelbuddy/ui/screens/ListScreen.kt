package com.example.travelbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.R
import com.example.travelbuddy.data.DestinationRepository
import com.example.travelbuddy.ui.components.AppTopBar
import com.example.travelbuddy.ui.components.DestinationCard

/**
 * Screen 1: the destination list, shown with a LazyColumn inside a Material3 Scaffold.
 *
 * @param onDestinationClick called with the id of the tapped destination
 * @param onAboutClick called when "About" is chosen in the overflow menu
 */
@Composable
fun ListScreen(
    onDestinationClick: (Int) -> Unit,
    onAboutClick: () -> Unit
) {
    // rememberSaveable keeps these values across screen rotation
    var lastViewed by rememberSaveable { mutableStateOf<String?>(null) }
    var menuExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.app_name),
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu_more)
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.menu_about)) },
                            onClick = {
                                menuExpanded = false
                                onAboutClick()
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
        ) {
            item(key = "header") {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.main_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = lastViewed?.let { stringResource(R.string.last_viewed_format, it) }
                            ?: stringResource(R.string.last_viewed_none),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            items(
                items = DestinationRepository.destinations,
                key = { it.id }
            ) { destination ->
                DestinationCard(
                    destination = destination,
                    onClick = {
                        lastViewed = destination.name
                        onDestinationClick(destination.id)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
