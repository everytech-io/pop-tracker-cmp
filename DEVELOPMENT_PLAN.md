# Development Plan: Complete CRUD Operations for Products

## Overview
This plan outlines the remaining work needed to complete the product management system for the Pop Tracker KMP application. The goal is to implement full Create, Read, Update, and Delete operations with proper UI screens and navigation.

## ✅ Completed (Current State)
- ✅ **Create**: AddProductScreen with comprehensive form UI
- ✅ **Read**: Product listing in TrackerScreen with Firestore integration
- ✅ **Country-specific collections**: products_us, products_ph, products_my, products_sg
- ✅ **Repository layer**: ProductRepository with addProduct() method
- ✅ **Data models**: Serializable Product, MarketplaceLink, and related models
- ✅ **Marketplace constants**: Predefined marketplaces with country filtering
- ✅ **Navigation**: Basic screen routing between Tracker and AddProduct

## 🎯 Tomorrow's Tasks

### 1. Product Detail Screen (Read - Enhanced)
**Priority: High**
- [ ] Create `ProductDetailScreen.kt` 
- [ ] Display full product information with Material 3 cards
- [ ] Show product image, name, description, pricing
- [ ] Display official store URL as clickable link
- [ ] List all available marketplaces with availability status
- [ ] Add floating action buttons for Edit and Delete
- [ ] Implement navigation from TrackerScreen product cards

**Files to create/modify:**
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/ui/screens/ProductDetailScreen.kt`
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/navigation/Screen.kt` (add ProductDetail route)
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/App.kt` (add navigation case)

### 2. Edit Product Screen (Update)
**Priority: High**
- [ ] Create `EditProductScreen.kt` based on AddProductScreen
- [ ] Pre-populate form fields with existing product data
- [ ] Create `EditProductViewModel.kt` 
- [ ] Implement `updateProduct()` method in ProductRepository
- [ ] Add validation for required fields
- [ ] Handle country switching (warn about collection migration)
- [ ] Success/error handling with proper user feedback

**Files to create/modify:**
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/ui/screens/EditProductScreen.kt`
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/ui/viewmodels/EditProductViewModel.kt`
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/data/ProductRepository.kt` (add updateProduct method)

### 3. Delete Functionality (Delete)
**Priority: Medium**
- [ ] Add delete confirmation dialog component
- [ ] Implement `deleteProduct()` method in ProductRepository
- [ ] Add delete action to ProductDetailScreen
- [ ] Add swipe-to-delete gesture in TrackerScreen product list
- [ ] Handle error states (network issues, permissions)
- [ ] Add undo functionality with Snackbar

**Files to create/modify:**
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/ui/components/DeleteConfirmationDialog.kt`
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/data/ProductRepository.kt` (add deleteProduct method)

### 4. Enhanced Navigation System
**Priority: Medium**
- [ ] Implement proper navigation with arguments (product ID)
- [ ] Add navigation animations/transitions
- [ ] Handle back navigation state management
- [ ] Implement deep linking support for product details
- [ ] Add navigation breadcrumbs for better UX

**Files to modify:**
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/navigation/Screen.kt`
- `composeApp/src/commonMain/kotlin/io/everytech/poptracker/App.kt`

### 5. Advanced Features
**Priority: Low (stretch goals)**
- [ ] Implement search and filtering in TrackerScreen
- [ ] Add sorting options (price, name, date added, country)
- [ ] Implement pull-to-refresh functionality
- [ ] Add offline support with local caching
- [ ] Implement batch operations (bulk delete, bulk edit)
- [ ] Add export functionality (CSV, JSON)

### 6. Error Handling & Loading States
**Priority: High**
- [ ] Implement comprehensive error handling for all operations
- [ ] Add retry mechanisms for failed operations
- [ ] Create loading states for all async operations
- [ ] Add empty states with proper illustrations
- [ ] Implement proper error messaging with actionable solutions

### 7. Testing & Quality Assurance
**Priority: Medium**
- [ ] Write unit tests for ViewModels
- [ ] Write integration tests for Repository operations
- [ ] Add UI tests for critical user flows
- [ ] Test across all supported platforms (Android, iOS, Desktop)
- [ ] Performance testing with large datasets

## Implementation Order (Recommended)

### Day 1 (Tomorrow)
1. **Morning**: ProductDetailScreen (3-4 hours)
2. **Afternoon**: EditProductScreen & ViewModel (3-4 hours)

### Day 2
1. **Morning**: Delete functionality + confirmation dialogs (2-3 hours)
2. **Afternoon**: Enhanced navigation system (3-4 hours)

### Day 3
1. **Morning**: Error handling & loading states (3-4 hours)
2. **Afternoon**: Testing & bug fixes (3-4 hours)

## Technical Considerations

### Repository Methods to Add
```kotlin
// In ProductRepository.kt
suspend fun getProduct(productId: String): Product?
suspend fun updateProduct(product: Product)
suspend fun deleteProduct(productId: String)
suspend fun getProductsByIds(productIds: List<String>): List<Product>
```

### Navigation Structure
```
TrackerScreen -> ProductDetailScreen -> EditProductScreen
             |                    |
             -> AddProductScreen   -> Delete (with confirmation)
```

### Error Handling Strategy
- Network errors: Retry button with exponential backoff
- Validation errors: Inline field validation with clear messaging
- Permission errors: Guide user to fix Firestore rules
- Data conflicts: Optimistic updates with conflict resolution

## Success Criteria
- [ ] Users can view detailed product information
- [ ] Users can edit existing products seamlessly
- [ ] Users can delete products with proper confirmation
- [ ] All operations work reliably across platforms
- [ ] Proper error handling and user feedback
- [ ] Smooth navigation experience
- [ ] No data loss or corruption during operations

## Notes for Tomorrow
- Start with ProductDetailScreen as it's the foundation for Edit and Delete
- Reuse as much UI code as possible from AddProductScreen for EditProductScreen
- Pay attention to Material 3 design consistency
- Test on actual devices early and often
- Consider edge cases (empty states, network issues, large datasets)