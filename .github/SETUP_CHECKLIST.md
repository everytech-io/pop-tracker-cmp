# Pop Tracker CI/CD Setup Checklist

## ✅ Completed Setup
- [x] iOS CI/CD pipeline configured
- [x] Android CI/CD pipeline configured
- [x] Build configurations updated
- [x] ProGuard rules added
- [x] Release notes structure created
- [x] Keystore generator script added
- [x] Documentation created

## 🔧 Required Actions

### For Android Play Store Deployment:

1. **Generate Keystore** (One-time setup)
   ```bash
   ./scripts/generate-keystore.sh
   ```

2. **Create Google Play Service Account**
   - [ ] Go to Google Play Console
   - [ ] Navigate to Settings → API access
   - [ ] Create service account with deployment permissions
   - [ ] Download JSON key file

3. **Add GitHub Secrets**
   Go to: Settings → Secrets and variables → Actions
   
   Add these secrets:
   - [ ] `ANDROID_KEYSTORE_BASE64` - Base64 encoded keystore
   - [ ] `ANDROID_KEYSTORE_PASSWORD` - Keystore password
   - [ ] `ANDROID_KEY_ALIAS` - Key alias (default: poptracker)
   - [ ] `ANDROID_KEY_PASSWORD` - Key password
   - [ ] `PLAY_STORE_SERVICE_ACCOUNT_JSON` - Service account JSON

4. **First Manual Upload**
   - [ ] Build release AAB: `./gradlew :composeApp:bundleRelease`
   - [ ] Upload to Play Store manually (establishes app presence)

### For iOS App Store Deployment (Already configured):

Required secrets (should already be set):
- [ ] `APPSTORE_ISSUER_ID`
- [ ] `APPSTORE_API_KEY_ID`
- [ ] `APPSTORE_API_PRIVATE_KEY`

## 📱 Testing the Pipelines

### Android
1. Push to `main` branch → Triggers build and test
2. Create GitHub release → Triggers full deployment
3. Manual trigger → Choose deployment track

### iOS
1. Push to `main` branch → Triggers build and test
2. Create GitHub release → Deploys to TestFlight

## 🚀 Deployment Workflow

### For Production Release:
1. Update version in `composeApp/build.gradle.kts`:
   - Increment `versionCode`
   - Update `versionName`
2. Commit and push changes
3. Create GitHub release
4. CI/CD handles the rest!

### For Beta Testing:
1. Create pre-release on GitHub
2. Android → Deploys to beta track
3. iOS → Deploys to TestFlight

## 📝 Quick Commands

```bash
# Build Android release locally
./gradlew :composeApp:bundleRelease

# Build iOS release locally
cd iosApp && xcodebuild -project iosApp.xcodeproj -scheme iosApp -configuration Release

# Run Android tests
./gradlew :composeApp:testDebugUnitTest

# Run iOS tests
./gradlew :composeApp:iosSimulatorArm64Test
```

## 🔍 Monitoring

- Check GitHub Actions tab for build status
- Monitor Google Play Console for Android deployments
- Check App Store Connect for iOS deployments

## 📚 Documentation

- [Android Setup Guide](.github/ANDROID_CI_SETUP.md)
- [GitHub Actions Workflows](.github/workflows/)
- [Release Notes](.github/release-notes/)