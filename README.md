# TravelBuddy (Jetpack Compose)

This is TravelBuddy rebuilt from scratch in **Jetpack Compose** with **Material 3**. The first version used XML layouts, three Activities and Intents. This version has one Activity, three composable screens and Navigation Compose.

It shows a list of travel destinations, a detail page for the one you tap, and an About page.

---

## What changed from the XML version

| Before (AND-1) | Now (AND-2) |
|---|---|
| XML layouts + ViewBinding | Composable functions |
| `RecyclerView` + Adapter + ViewHolder | `LazyColumn` + a reusable `DestinationCard` |
| 3 Activities and explicit Intents | 1 `ComponentActivity` + `NavHost` / `NavController` |
| Parcelable object in an Intent extra | Only the item `id` in the route (`detail/{id}`), the screen looks the item up again |
| Material components in XML | Material3 `Scaffold`, `TopAppBar`, `Card`, `Button`, `DropdownMenu` |
| XML theme | `MaterialTheme` with explicit light and dark colour schemes |

---

## How it meets the project requirements

| Requirement | Where |
|---|---|
| All three screens as composables, no XML layouts | `ListScreen.kt`, `DetailScreen.kt`, `AboutScreen.kt` (there is no `res/layout` folder) |
| Material3 components | `Scaffold`, `TopAppBar`, `Card`, `Button`, `OutlinedButton`, `DropdownMenu`, `Icon` |
| Navigation Compose | `navigation/AppNavHost.kt` with `NavHost` and `NavController` |
| List with `LazyColumn` | `ListScreen.kt` (12 destinations) |
| Reusable composables with parameters | `DestinationCard`, `EmojiAvatar`, `InfoBlock`, `RatingText`, `AppTopBar` in `ui/components` |
| Light and dark theme | `ui/theme/Theme.kt` defines `LightColorScheme` and `DarkColorScheme`, chosen with `isSystemInDarkTheme()` |

The app also keeps the features from the first version: share (`ACTION_SEND`), open link (`ACTION_VIEW`), call support (`ACTION_DIAL`), send feedback (`ACTION_SENDTO`), a favourite button, and a "last viewed" line. State is kept across rotation with `rememberSaveable`.

---

## Project structure

```
app/src/main/java/com/example/travelbuddy/
├── MainActivity.kt                 The only Activity, hosts the NavHost
├── navigation/
│   └── AppNavHost.kt               Routes + navigation graph
├── data/
│   ├── Destination.kt              Data class
│   └── DestinationRepository.kt    12 hardcoded destinations
└── ui/
    ├── IntentHelpers.kt            Safe startActivity for implicit Intents
    ├── theme/
    │   ├── Color.kt                Colour palette
    │   └── Theme.kt                Light and dark MaterialTheme
    ├── components/
    │   ├── AppTopBar.kt            Shared Material3 top bar
    │   └── Components.kt           DestinationCard, EmojiAvatar, InfoBlock, RatingText
    └── screens/
        ├── ListScreen.kt           Screen 1
        ├── DetailScreen.kt         Screen 2
        └── AboutScreen.kt          Screen 3
```

---

## How to run it

1. Install a recent version of [Android Studio](https://developer.android.com/studio).
2. Clone this repository, then choose **File > Open** and select the project folder.
3. Wait for Gradle to finish syncing.
4. Choose an emulator or a phone and press **Run**.

**Requirements:** minSdk 24, compileSdk and targetSdk 35, Kotlin 2.0.21, Gradle 8.9.

---

## Try it yourself

1. Tap a destination. The detail page shows that place.
2. Press back (arrow or system button) to return to the list. "Last viewed" is updated.
3. Open the three-dot menu and choose **About**.
4. Switch the phone to dark mode (Settings > Display > Dark theme) and check all three screens.
5. Rotate the device. The same screen and destination stay in place.
