package com.example.simplecurdapp.service;

import com.example.simplecurdapp.exception.ResourceNotFoundException;
import com.example.simplecurdapp.model.Product;
import com.example.simplecurdapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProductByIdShouldReturnProductWhenPresent() {
        Product product = createProduct(1L, "Phone", "Electronics");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product actual = productService.getProductById(1L);

        assertEquals(1L, actual.getId());
        assertEquals("Phone", actual.getName());
    }

    @Test
    void getProductByIdShouldThrowWhenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    void createProductShouldSaveEntity() {
        Product input = createProduct(null, "Laptop", "Electronics");
        Product saved = createProduct(10L, "Laptop", "Electronics");
        when(productRepository.save(input)).thenReturn(saved);

        Product actual = productService.createProduct(input);

        assertEquals(10L, actual.getId());
        verify(productRepository).save(input);
    }

    @Test
    void updateProductShouldCopyFieldsAndSave() {
        Product existing = createProduct(7L, "Old", "OldCategory");
        Product updates = createProduct(null, "New", "NewCategory");
        updates.setDescription("Updated description");
        updates.setPrice(new BigDecimal("199.99"));
        updates.setQuantity(5);
        updates.setIsAvailable(false);

        when(productRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product actual = productService.updateProduct(7L, updates);

        assertEquals("New", actual.getName());
        assertEquals("Updated description", actual.getDescription());
        assertEquals(new BigDecimal("199.99"), actual.getPrice());
        assertEquals(5, actual.getQuantity());
        assertEquals("NewCategory", actual.getCategory());
        assertEquals(false, actual.getIsAvailable());
        verify(productRepository).save(existing);
    }

    @Test
    void deleteProductShouldDeleteResolvedEntity() {
        Product existing = createProduct(8L, "DeleteMe", "Misc");
        when(productRepository.findById(8L)).thenReturn(Optional.of(existing));

        productService.deleteProduct(8L);

        verify(productRepository).delete(existing);
    }

    @Test
    void getAvailableProductsShouldDelegateToRepository() {
        List<Product> expected = List.of(createProduct(1L, "P1", "Cat"));
        when(productRepository.findByIsAvailable(true)).thenReturn(expected);

        List<Product> actual = productService.getAvailableProducts();

        assertEquals(1, actual.size());
        verify(productRepository).findByIsAvailable(true);
    }

    @Test
    void getProductsByPriceRangeShouldDelegateToRepository() {
        when(productRepository.findByPriceBetween(new BigDecimal("10.00"), new BigDecimal("20.00")))
            .thenReturn(Collections.emptyList());

        List<Product> actual = productService.getProductsByPriceRange(new BigDecimal("10.00"), new BigDecimal("20.00"));

        assertEquals(0, actual.size());
        verify(productRepository).findByPriceBetween(new BigDecimal("10.00"), new BigDecimal("20.00"));
    }

    @Test
    void updateProductAvailabilityShouldSetValueAndSave() {
        Product existing = createProduct(11L, "Watch", "Wearables");
        existing.setIsAvailable(true);
        when(productRepository.findById(11L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product actual = productService.updateProductAvailability(11L, false);

        assertEquals(false, actual.getIsAvailable());
        verify(productRepository).save(existing);
    }

    @Test
    void updateProductQuantityShouldSetValueAndSave() {
        Product existing = createProduct(15L, "Keyboard", "Accessories");
        existing.setQuantity(1);
        when(productRepository.findById(15L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product actual = productService.updateProductQuantity(15L, 9);

        assertEquals(9, actual.getQuantity());
        verify(productRepository).save(existing);
    }

    private Product createProduct(Long id, String name, String category) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription("Sample description");
        product.setPrice(new BigDecimal("99.99"));
        product.setQuantity(10);
        product.setCategory(category);
        product.setIsAvailable(true);
        return product;
    }
}
