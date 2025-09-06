# Android CI/CD Setup Guide

This guide will help you set up the Android CI/CD pipeline for automatic deployment to Google Play Store.

## Prerequisites

1. A Google Play Developer account
2. An app created in Google Play Console
3. A signing keystore for your app

## Step 1: Generate a Signing Keystore

If you don't have a keystore yet, create one:

```bash
keytool -genkey -v -keystore poptracker-release.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias poptracker
```

Keep this file safe and never commit it to the repository!

## Step 2: Encode Your Keystore

Convert your keystore to base64 for secure storage in GitHub Secrets:

```bash
base64 -i poptracker-release.jks -o keystore-base64.txt
```

On Windows:
```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("poptracker-release.jks")) | Out-File keystore-base64.txt
```

## Step 3: Set up Google Play Service Account

1. Go to [Google Play Console](https://play.google.com/console)
2. Navigate to **Settings** → **API access**
3. Click **Create new service account**
4. Follow the link to Google Cloud Console
5. In Google Cloud Console:
   - Click **Create Service Account**
   - Name: `github-actions-deploy`
   - Description: `Service account for GitHub Actions deployment`
   - Click **Create and Continue**
   - Grant the role: **Service Account User**
   - Click **Done**
6. Back in the service accounts list, click on your new service account
7. Go to **Keys** tab → **Add Key** → **Create new key**
8. Choose **JSON** format and download the key file
9. Return to Google Play Console
10. Click **Grant Access** for the new service account
11. Grant these permissions:
    - **Release apps to testing tracks**
    - **Release apps to production**
    - **View app information**
    - **Manage production releases**
    - **Manage testing track releases**

## Step 4: Add GitHub Secrets

Go to your GitHub repository → **Settings** → **Secrets and variables** → **Actions**

Add the following repository secrets:

### Required Secrets:

1. **ANDROID_KEYSTORE_BASE64**
   - The base64-encoded content from Step 2
   - Copy the entire content of `keystore-base64.txt`

2. **ANDROID_KEYSTORE_PASSWORD**
   - The password you used when creating the keystore

3. **ANDROID_KEY_ALIAS**
   - The alias you used (e.g., `poptracker`)

4. **ANDROID_KEY_PASSWORD**
   - The key password (often the same as keystore password)

5. **PLAY_STORE_SERVICE_ACCOUNT_JSON**
   - The entire content of the JSON key file from Step 3
   - Copy and paste the complete JSON content

## Step 5: First Manual Upload

Before the CI/CD can work, you need to:

1. Build your first AAB manually:
   ```bash
   ./gradlew :composeApp:bundleRelease
   ```

2. Upload it manually to Google Play Console:
   - Go to **Production** → **Create new release**
   - Upload your AAB file
   - Fill in release notes
   - Save and review
   - Start rollout

This establishes the app in the Play Store so subsequent automated uploads will work.

## Step 6: Test the Pipeline

### Manual Deployment Test

1. Go to **Actions** tab in your GitHub repository
2. Select **Android CI/CD - Play Store**
3. Click **Run workflow**
4. Choose:
   - Branch: `main`
   - Track: `internal` (for testing)
5. Click **Run workflow**

### Automatic Deployment

The pipeline will automatically deploy when:
- You create a new release on GitHub (production track)
- You create a pre-release on GitHub (beta track)

## Workflow Triggers

- **Push to main/develop**: Builds and tests only
- **Pull Requests**: Builds and tests only
- **GitHub Release**: Full build, test, and deploy to Play Store
- **Manual Trigger**: Choose specific track for deployment

## Deployment Tracks

- **internal**: For internal testing (smallest group)
- **alpha**: For alpha testers
- **beta**: For beta testers (pre-release)
- **production**: For all users (full release)

## Release Notes

Update release notes by adding files to:
- `.github/release-notes/en-US/default.txt` - Default release notes
- `.github/release-notes/[language-code]/default.txt` - For other languages

## Troubleshooting

### "Package not found" error
- Ensure you've done the first manual upload
- Verify the package name matches exactly: `io.everytech.poptracker`

### "Invalid credentials" error
- Double-check all secrets are set correctly
- Ensure service account has proper permissions
- Verify JSON key is complete and valid

### "Version code already exists" error
- Increment `versionCode` in `composeApp/build.gradle.kts`
- Each upload must have a unique, higher version code

### Build fails with signing error
- Verify keystore base64 encoding is correct
- Check passwords match exactly
- Ensure alias is correct

## Security Notes

- Never commit keystores or credentials to the repository
- Rotate service account keys periodically
- Use GitHub's secret scanning to prevent accidental exposure
- Keep keystore backups in secure location

## Additional Resources

- [Google Play Console Help](https://support.google.com/googleplay/android-developer)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android App Bundle Guide](https://developer.android.com/guide/app-bundle)