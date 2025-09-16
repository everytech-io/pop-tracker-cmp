# Firestore Setup Instructions

## Fix Permission Denied Error

The app is getting a "PERMISSION_DENIED: Missing or insufficient permissions" error when trying to read from Firestore. This is because Firestore security rules are blocking read access.

### Quick Fix (Development Only)

1. Go to the [Firebase Console](https://console.firebase.google.com)
2. Select your project
3. Navigate to **Firestore Database** in the left sidebar
4. Click on the **Rules** tab
5. Replace the existing rules with:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Allow read/write access to country-specific product collections
    // Following Firestore best practices: lowercase with underscores
    match /products_us/{document=**} {
      allow read, write: if true;
    }
    
    match /products_ph/{document=**} {
      allow read, write: if true;
    }
    
    match /products_my/{document=**} {
      allow read, write: if true;
    }
    
    match /products_sg/{document=**} {
      allow read, write: if true;
    }
    
    match /products_global/{document=**} {
      allow read, write: if true;
    }
    
    // Legacy collection (for backward compatibility if needed)
    match /products/{document=**} {
      allow read: if true;
      allow write: if false;
    }
    
    // Default deny for other collections
    match /{document=**} {
      allow read, write: if false;
    }
  }
}
```

6. Click **Publish** to save the rules

### Production-Ready Rules (Recommended)

For production, you should implement more secure rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Country-specific product collections are publicly readable
    // but only writable by authenticated users (or admins)
    match /PRODUCTS_{country}/{productId} {
      allow read: if true;
      allow write: if request.auth != null;  // Require authentication for writes
      // For admin-only writes, use: request.auth.token.admin == true
    }
    
    // Legacy lowercase collection (for backward compatibility)
    match /products/{productId} {
      allow read: if true;
      allow write: if request.auth != null && 
                     request.auth.token.admin == true;
    }
    
    // User-specific data (future feature)
    match /users/{userId}/{document=**} {
      allow read, write: if request.auth != null && 
                            request.auth.uid == userId;
    }
  }
}
```

## Adding Sample Data to Firestore

To add sample products to your Firestore database:

1. Go to the [Firebase Console](https://console.firebase.google.com)
2. Navigate to **Firestore Database**
3. Click **Start collection**
4. Set Collection ID as `products`
5. Add documents with the following structure:

### Sample Product Document

```json
{
  "id": "product-001",
  "title": "Labubu Halloween Keychain",
  "subtitle": "Limited Edition Collectible",
  "imageName": "labubu_demo",
  "price": {
    "amount": "15.99",
    "currency": "$",
    "range": null
  },
  "marketplaces": [
    {
      "name": "Popmart",
      "iconName": "popmart",
      "type": "Official",
      "url": "https://www.popmart.com",
      "availability": "InStock",
      "price": {
        "amount": "15.99",
        "currency": "$"
      }
    },
    {
      "name": "Shopee",
      "iconName": "shopee",
      "type": "Secondary",
      "url": "https://shopee.com",
      "availability": "InStock",
      "price": null
    }
  ]
}
```

### Important Notes

- The `type` field in marketplaces must be one of: `Official`, `Primary`, `Secondary`
- The `availability` field must be one of: `InStock`, `OutOfStock`, `ComingSoon`, `Limited`, `PreOrder`
- The `imageName` and `iconName` fields should match the drawable resource names in the app

## Troubleshooting

If you're still getting permission errors after updating the rules:

1. Check that you're connected to the internet
2. Verify your `google-services.json` (Android) and `GoogleService-Info.plist` (iOS) files are correctly configured
3. Make sure Firebase is properly initialized in your app
4. Check the Firebase Console for any error logs or quota issues

## Fallback Data

The app automatically shows fallback demo data when:
- Firestore is unreachable
- Permission is denied
- The products collection is empty

This ensures the app remains functional even without a proper Firestore connection.