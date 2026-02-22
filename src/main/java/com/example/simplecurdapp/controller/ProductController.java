package com.example.simplecurdapp.controller;

import com.example.simplecurdapp.model.Product;
import com.example.simplecurdapp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products in the inventory system")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products in the inventory")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID of the product to retrieve") @PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Create a new product", description = "Add a new product to the inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "409", description = "Product already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Product object to be created", required = true,
                content = @Content(schema = @Schema(implementation = Product.class)))
            @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @Operation(summary = "Update a product", description = "Update an existing product's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID of the product to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated product information", required = true)
            @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    // ...existing code...

    @Operation(summary = "Delete a product", description = "Remove a product from the inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search products", description = "Search products by keyword in name, description, or category")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @Parameter(description = "Keyword to search for") @RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get products by category", description = "Retrieve all products in a specific category")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products in category")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @Parameter(description = "Category name") @PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get available products", description = "Retrieve all products marked as available")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved available products")
    @GetMapping("/available")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products = productService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get in-stock products", description = "Retrieve all products with quantity greater than zero")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved in-stock products")
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> products = productService.getInStockProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get products by price range", description = "Retrieve products within a specified price range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products in price range")
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all unique product categories")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = productService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Update product availability", description = "Change the availability status of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product availability updated"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Product> updateProductAvailability(
            @Parameter(description = "ID of the product") @PathVariable Long id,
            @Parameter(description = "Availability status") @RequestParam Boolean isAvailable) {
        Product product = productService.updateProductAvailability(id, isAvailable);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Update product quantity", description = "Change the quantity of a product in stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product quantity updated"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Product> updateProductQuantity(
            @Parameter(description = "ID of the product") @PathVariable Long id,
            @Parameter(description = "New quantity") @RequestParam Integer quantity) {
        Product product = productService.updateProductQuantity(id, quantity);
        return ResponseEntity.ok(product);
    }
}
