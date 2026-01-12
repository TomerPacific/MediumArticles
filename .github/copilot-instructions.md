# Copilot Instructions for MediumArticles Repository

## Repository Overview

This repository contains code examples and sample projects accompanying Medium articles written by the repository owner. It is a **collection of 18+ independent, standalone demo projects** across multiple platforms and technologies. Each project demonstrates a specific concept covered in a Medium article.

**Key Characteristics:**
- **Size:** ~8.2MB (source code only)
- **Project Count:** 18+ independent projects
- **Purpose:** Educational demos and code samples for Medium articles
- **Languages:** Kotlin, Java, Dart, JavaScript, HTML, Swift
- **Platforms:** Android, Flutter/Dart, Chrome Extensions, iOS

## Project Types and Structure

### 1. Android Projects (10+ projects)
**Language:** Kotlin 2.0.0, Java  
**Build Tool:** Gradle 8.11.1  
**Android Gradle Plugin:** 8.9.1 (configured, but may not be available)  
**JDK Version:** Java 17 (OpenJDK 17.0.17)

**Android Projects:**
- `JetpackComposeTabs/` - Jetpack Compose tabs implementation
- `NavigationComponent/` - Android Navigation Component demo
- `AlertDialog/` - AlertDialog usage examples
- `EditText/` - EditText input handling
- `ViewVisibility/` - View visibility listeners
- `Camrea2API/` - Camera2 API implementation
- `LocationPermissions/` - Location permissions in Compose
- `TooltipExample/` - Tooltips in Jetpack Compose
- `AndroidtoJSNativeCommunicator/` - Android to JavaScript bridge
- `AndroidStorage/External/` - External storage handling
- `AndroidStorage/Internal/` - Internal storage handling
- `BroadcastReceivers/CustomBroadcast/` - Custom broadcast receivers
- `BroadcastReceivers/RegisteringBroadcast/` - Registering broadcast receivers

**Common Configuration:**
- `compileSdk`: 36
- `targetSdk`: 36
- `minSdk`: 21 or 23
- Android SDK installed at: `/usr/local/lib/android/sdk`
- Available platforms: android-34, android-35, android-36
- Build tools: 34.0.0, 35.0.0, 35.0.1, 36.0.0, 36.1.0

### 2. Flutter Projects (4 projects)
**Language:** Dart  
**Framework:** Flutter SDK 3.0.0+  
**Build Tool:** Flutter CLI

**Flutter Projects:**
- `flutter_communication_bridge/` - Native to JS communication bridge
- `alarm_manager_example/` - AlarmManager usage
- `serialization_example/` - Object serialization in Flutter
- `flutter_location/` - User location in Flutter

**Note:** Flutter is NOT installed in the default environment. Flutter projects cannot be built without first installing Flutter.

### 3. Chrome Extensions (1 project)
**Language:** JavaScript, HTML  
**Manifest Version:** 2

**Projects:**
- `Chrome Extension/NewTab/` - New tab override extension
- `Chrome Extension/SameTab/` - Same tab extension

### 4. iOS Projects (1 project)
**Language:** Swift  
**Build Tool:** Xcode

**Projects:**
- `iOStoJSNativeCommunicator/` - iOS to JavaScript communication bridge

**Note:** Xcode is NOT available in the default Linux environment.

## Build Instructions

### Prerequisites
- **Java 17** is installed and configured
- **Android SDK** is installed at `/usr/local/lib/android/sdk`
- **Gradle** wrapper (gradlew) is included in each Android project
- **Flutter** must be installed separately for Flutter projects
- **Xcode** required for iOS projects (macOS only)

### Android Projects

**CRITICAL BUILD ISSUE:** All Android projects reference Android Gradle Plugin version `8.9.1`, which does not exist or is not publicly available. This causes all Gradle builds to fail with network errors.

**Current Behavior:**
```bash
cd <android-project-directory>
chmod +x gradlew  # ALWAYS make gradlew executable first
./gradlew build
# FAILS: Could not resolve com.android.tools.build:gradle:8.9.1
```

**Workarounds:**
1. **Lower the Android Gradle Plugin version** in `build.gradle` to an available version (e.g., 8.7.x or 8.8.x)
2. **OR** Use a compatible Gradle version that supports offline/cached builds
3. **OR** Wait for version 8.9.1 to become available

**Build Commands (after fixing version):**
```bash
cd <project-directory>
chmod +x gradlew              # Required: Make gradlew executable
./gradlew clean               # Clean build artifacts
./gradlew assembleDebug       # Build debug APK (60-120 seconds)
./gradlew assembleRelease     # Build release APK (60-120 seconds)
./gradlew test                # Run unit tests (30-60 seconds)
./gradlew connectedAndroidTest # Run instrumented tests (requires emulator)
```

**Common Gradle Issues:**
- **Permission denied:** Run `chmod +x gradlew` before executing
- **Network errors:** Check Android Gradle Plugin version in `build.gradle`
- **Out of memory:** Increase heap in `gradle.properties`: `org.gradle.jvmargs=-Xmx2048m`

**Project Structure (typical Android project):**
```
ProjectName/
├── app/
│   ├── build.gradle or build.gradle.kts  # App-level build config
│   ├── src/main/
│   │   ├── java/ or kotlin/              # Source code
│   │   ├── res/                          # Resources (layouts, strings, etc.)
│   │   └── AndroidManifest.xml           # App manifest
│   └── proguard-rules.pro                # ProGuard config
├── build.gradle or build.gradle.kts      # Root build config (AGP version here!)
├── settings.gradle or settings.gradle.kts
├── gradle.properties                     # Gradle settings
├── gradlew                               # Gradle wrapper (Unix)
├── gradlew.bat                           # Gradle wrapper (Windows)
└── README.md                             # Project documentation
```

**Key Files:**
- **Root `build.gradle`**: Contains Android Gradle Plugin version (`classpath 'com.android.tools.build:gradle:8.9.1'`)
- **`gradle/libs.versions.toml`** (for projects using version catalogs): Defines plugin versions
- **`app/build.gradle`**: Contains `compileSdk`, `targetSdk`, `minSdk`, and dependencies
- **`gradle.properties`**: JVM settings, AndroidX flags, Kotlin settings

### Flutter Projects

**Prerequisites:** Flutter SDK must be installed and added to PATH.

**Installation (if needed):**
```bash
# Flutter is NOT pre-installed
# Download and install Flutter from https://flutter.dev/docs/get-started/install
```

**Build Commands:**
```bash
cd <flutter-project-directory>
flutter pub get                # Download dependencies (30-60 seconds)
flutter analyze                # Run static analysis (10-20 seconds)
flutter test                   # Run unit tests (20-40 seconds)
flutter build apk              # Build Android APK (120-180 seconds)
flutter build ios              # Build iOS (macOS only, 120-240 seconds)
flutter run                    # Run on connected device/emulator
```

**Project Structure (typical Flutter project):**
```
project_name/
├── lib/
│   └── main.dart              # Main application entry point
├── android/                   # Android platform code
├── ios/                       # iOS platform code
├── test/                      # Unit tests
├── assets/                    # Images, HTML, etc.
├── pubspec.yaml               # Dependencies and configuration
└── README.md
```

**Key Files:**
- **`pubspec.yaml`**: Dependencies, assets, SDK version constraints
- **`lib/main.dart`**: Application entry point
- **`android/app/build.gradle`**: Android-specific build config (compileSdk, minSdk)

### Chrome Extensions

**No build process required.** Chrome extensions in this repo are simple JavaScript/HTML projects.

**Testing:**
1. Open Chrome/Chromium browser
2. Navigate to `chrome://extensions/`
3. Enable "Developer mode"
4. Click "Load unpacked"
5. Select the extension directory (e.g., `Chrome Extension/NewTab/`)

**Key Files:**
- **`manifest.json`**: Extension configuration (permissions, scripts, version)
- **`popup.html`**: Extension popup UI
- **`background.js`**: Background script

### iOS Projects

**Prerequisites:** macOS with Xcode installed.

**Note:** iOS projects cannot be built in Linux environments. Skip these projects if not on macOS.

**Build Commands (macOS only):**
```bash
cd <ios-project-directory>
open *.xcodeproj               # Open in Xcode
# Build via Xcode GUI or xcodebuild CLI
```

## Validation and Testing

### No CI/CD Configured
**Important:** This repository has **NO GitHub Actions workflows, CI pipelines, or automated tests** configured at the repository root level. Each project is independent and self-contained.

**Validation Steps:**
1. **For Android projects:** Run `./gradlew build` after fixing AGP version
2. **For Flutter projects:** Run `flutter pub get && flutter analyze && flutter test`
3. **For Chrome extensions:** Load unpacked and test in browser
4. **For iOS projects:** Build in Xcode (macOS only)

### Manual Testing
Since projects are demos, manual testing is recommended:
- **Android:** Use Android Emulator or physical device
- **Flutter:** Use `flutter run` on emulator/device
- **Chrome Extensions:** Test in Chrome browser
- **iOS:** Use iOS Simulator or physical device

## Known Issues and Workarounds

### 1. Android Gradle Plugin Version 8.9.1 Not Available
**Problem:** All Android projects reference AGP 8.9.1, which doesn't exist.  
**Impact:** All `./gradlew` commands fail with network errors.  
**Workaround:** Edit `build.gradle` and change AGP version to 8.7.x or 8.8.x.  
**Example:**
```gradle
dependencies {
    classpath 'com.android.tools.build:gradle:8.7.2'  // Changed from 8.9.1
}
```

### 2. Permission Denied on gradlew
**Problem:** `./gradlew: Permission denied`  
**Solution:** Run `chmod +x gradlew` before executing Gradle commands.

### 3. Flutter Not Installed
**Problem:** `flutter: command not found`  
**Solution:** Flutter is not pre-installed. Install Flutter SDK from https://flutter.dev or skip Flutter projects.

### 4. Navigation Safe Args Plugin Version
**Problem:** Some projects use `androidx.navigation:navigation-safe-args-gradle-plugin:2.9.4` which may not be available.  
**Workaround:** Lower version to 2.8.x or 2.7.x in `build.gradle`.

### 5. Network Access May Be Limited
**Problem:** `dl.google.com` and other domains may be blocked in some environments.  
**Impact:** Cannot download Gradle dependencies.  
**Solution:** Use cached dependencies or configure offline mode.

## Repository Layout Summary

**Root Directory Files:**
- `README.md` - Repository overview with links to all Medium articles
- `.gitignore` - Ignores build artifacts, IDE files, OS files

**Top-Level Directories (by type):**
- **Android:** `AlertDialog/`, `AndroidStorage/`, `AndroidtoJSNativeCommunicator/`, `BroadcastReceivers/`, `Camrea2API/`, `EditText/`, `JetpackComposeTabs/`, `LocationPermissions/`, `NavigationComponent/`, `TooltipExample/`, `ViewVisibility/`
- **Flutter:** `alarm_manager_example/`, `flutter_communication_bridge/`, `flutter_location/`, `serialization_example/`
- **Chrome:** `Chrome Extension/`
- **iOS:** `iOStoJSNativeCommunicator/`

**Each project is self-contained** with its own README, build files, and source code.

## Quick Reference

### Common Tasks
| Task | Command | Notes |
|------|---------|-------|
| Build Android project | `chmod +x gradlew && ./gradlew assembleDebug` | Fix AGP version first |
| Clean Android build | `./gradlew clean` | Removes build/ directory |
| Test Android project | `./gradlew test` | Runs unit tests |
| Build Flutter project | `flutter pub get && flutter build apk` | Requires Flutter SDK |
| Test Flutter project | `flutter test` | Runs Dart unit tests |
| Load Chrome extension | Open `chrome://extensions/` → Load unpacked | No build needed |

### Environment Details
- **Java:** OpenJDK 17.0.17
- **Gradle:** 8.11.1 (via wrapper)
- **Kotlin:** 2.0.0
- **Android SDK:** `/usr/local/lib/android/sdk`
- **Compose BOM:** 2024.11.00 (JetpackComposeTabs), 2025.09.00 (TooltipExample)
- **Flutter:** Not installed by default

## Important Guidelines

1. **Always check the AGP version** before building Android projects. Version 8.9.1 will fail.
2. **Make gradlew executable** with `chmod +x gradlew` before running Gradle commands.
3. **Each project is independent.** Don't expect shared dependencies or configurations.
4. **No repository-wide tests or builds.** Validate each project individually.
5. **Read project-specific READMEs** for context on what each demo does.
6. **Trust these instructions.** Only search if information is incomplete or incorrect.
7. **Build times:** Android builds take 60-120 seconds, Flutter builds take 120-180 seconds.
8. **Android projects use Jetpack Compose** (modern) or XML layouts (older).
9. **Network access may be limited** - be prepared for dependency download failures.
10. **No CI/CD workflows** - manual validation required.

## Additional Notes

- Projects range from 2018-2024, so expect varying conventions and SDK versions
- Some projects use Kotlin DSL (`.gradle.kts`), others use Groovy (`.gradle`)
- AndroidX is enabled in all Android projects (`android.useAndroidX=true`)
- Material Design 3 is used in newer Compose projects
- Older projects may use deprecated APIs or patterns
- Each project README links to the corresponding Medium article

---

**Last Updated:** 2026-01-12  
**Repository:** https://github.com/TomerPacific/MediumArticles
