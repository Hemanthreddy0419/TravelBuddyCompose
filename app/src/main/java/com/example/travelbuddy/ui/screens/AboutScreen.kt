package com.example.travelbuddy.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.R
import com.example.travelbuddy.ui.components.AppTopBar
import com.example.travelbuddy.ui.components.EmojiAvatar
import com.example.travelbuddy.ui.startSafely

/** Screen 3: a simple About page with two more implicit Intents (dial and email). */
@Composable
fun AboutScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_about),
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmojiAvatar(emoji = "✈️", size = 96.dp)

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = stringResource(R.string.about_version),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.about_body),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            Text(
                text = stringResource(R.string.about_support_heading),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp)
            )

            // Implicit Intent: ACTION_DIAL opens the dialer (no permission needed)
            Button(
                onClick = {
                    context.startSafely(
                        Intent(Intent.ACTION_DIAL, Uri.parse(context.getString(R.string.support_phone)))
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(
                    text = stringResource(R.string.action_call_support),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Implicit Intent: ACTION_SENDTO with mailto: only matches email apps
            OutlinedButton(
                onClick = {
                    val emailIntent = Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse(context.getString(R.string.support_email))
                    ).apply {
                        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.feedback_subject))
                    }
                    context.startSafely(emailIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(
                    text = stringResource(R.string.action_email_feedback),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
