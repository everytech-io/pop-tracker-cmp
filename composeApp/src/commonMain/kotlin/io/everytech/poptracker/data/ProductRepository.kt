package io.everytech.poptracker.data

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.everytech.poptracker.ui.components.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository {
    private val firestore = Firebase.firestore
    private val productsCollection = firestore.collection("PRODUCTS")
    
    /**
     * Fetches all products from Firestore
     * Returns a Flow that emits the list of products
     */
    fun getProducts(): Flow<List<Product>> = flow {
        try {
            val snapshot = productsCollection.get()
            val products = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.data<Product>()
                } catch (e: Exception) {
                    println("Error parsing product document ${doc.id}: ${e.message}")
                    null
                }
            }
            emit(products)
        } catch (e: Exception) {
            println("Error fetching products from Firestore: ${e.message}")
            emit(emptyList())
        }
    }
    
    /**
     * Fetches a single product by ID
     */
    suspend fun getProduct(productId: String): Product? {
        return try {
            val doc = productsCollection.document(productId).get()
            doc.data<Product>()
        } catch (e: Exception) {
            println("Error fetching product $productId: ${e.message}")
            null
        }
    }
    
    /**
     * Listens to real-time updates of products
     */
    fun observeProducts(): Flow<List<Product>> = flow {
        try {
            Logger.i { "Starting to observe products from Firestore" }
            productsCollection.snapshots.collect { snapshot ->
                val products = snapshot.documents.mapNotNull { doc ->
                    try {
                        // Parse to Product
                        doc.data<Product>()
                    } catch (e: Exception) {
                        Logger.e(e) { "Error parsing product document ${doc.id}" }
                        null
                    }
                }
                Logger.i { "Emitting ${products.size} products from Firestore" }
                emit(products)
            }
        } catch (e: Exception) {
            Logger.e(e) { "Error observing products from Firestore: ${e.message}" }
            // Emit empty list to trigger fallback in ViewModel
            emit(emptyList())
        }
    }
}