#!/bin/bash

# Script to generate Android release keystore for Pop Tracker app

echo "================================================"
echo "Pop Tracker Android Keystore Generator"
echo "================================================"
echo

# Default values
KEYSTORE_NAME="poptracker-release.jks"
KEY_ALIAS="poptracker"
VALIDITY_DAYS=10000

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if keystore already exists
if [ -f "$KEYSTORE_NAME" ]; then
    echo -e "${YELLOW}Warning: Keystore '$KEYSTORE_NAME' already exists!${NC}"
    read -p "Do you want to overwrite it? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "Exiting without changes."
        exit 0
    fi
    rm "$KEYSTORE_NAME"
fi

echo "Creating new keystore..."
echo "Please provide the following information:"
echo

# Get user input
read -p "Your Name (CN): " CN_NAME
read -p "Organizational Unit (OU): " OU_NAME
read -p "Organization (O): " O_NAME
read -p "City/Locality (L): " L_NAME
read -p "State/Province (ST): " ST_NAME
read -p "Country Code (C) [e.g., US]: " C_CODE

# Build the distinguished name
DNAME="CN=$CN_NAME, OU=$OU_NAME, O=$O_NAME, L=$L_NAME, ST=$ST_NAME, C=$C_CODE"

echo
echo "Generating keystore with the following details:"
echo "  Keystore: $KEYSTORE_NAME"
echo "  Alias: $KEY_ALIAS"
echo "  Validity: $VALIDITY_DAYS days"
echo "  DN: $DNAME"
echo

# Generate the keystore
keytool -genkey -v \
    -keystore "$KEYSTORE_NAME" \
    -alias "$KEY_ALIAS" \
    -keyalg RSA \
    -keysize 2048 \
    -validity $VALIDITY_DAYS \
    -dname "$DNAME"

if [ $? -eq 0 ]; then
    echo
    echo -e "${GREEN}✓ Keystore generated successfully!${NC}"
    echo
    
    # Generate base64 encoded version
    echo "Generating base64 encoded version for GitHub Secrets..."
    base64 -i "$KEYSTORE_NAME" -o "${KEYSTORE_NAME}.base64"
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Base64 file generated: ${KEYSTORE_NAME}.base64${NC}"
        echo
        echo "================================================"
        echo "IMPORTANT - Save the following information:"
        echo "================================================"
        echo "1. Keystore file: $KEYSTORE_NAME"
        echo "2. Key alias: $KEY_ALIAS"
        echo "3. Store password: [what you entered]"
        echo "4. Key password: [what you entered]"
        echo
        echo "GitHub Secrets to set:"
        echo "  ANDROID_KEYSTORE_BASE64: Content of ${KEYSTORE_NAME}.base64"
        echo "  ANDROID_KEYSTORE_PASSWORD: Your store password"
        echo "  ANDROID_KEY_ALIAS: $KEY_ALIAS"
        echo "  ANDROID_KEY_PASSWORD: Your key password"
        echo
        echo -e "${YELLOW}⚠ WARNING: Keep the keystore file secure and never commit it to git!${NC}"
        echo
        
        # Add to .gitignore if not already there
        if ! grep -q "$KEYSTORE_NAME" .gitignore 2>/dev/null; then
            echo "# Android keystore files" >> .gitignore
            echo "*.jks" >> .gitignore
            echo "*.keystore" >> .gitignore
            echo "${KEYSTORE_NAME}.base64" >> .gitignore
            echo -e "${GREEN}✓ Added keystore patterns to .gitignore${NC}"
        fi
    else
        echo -e "${RED}✗ Failed to generate base64 file${NC}"
    fi
else
    echo -e "${RED}✗ Failed to generate keystore${NC}"
    exit 1
fi