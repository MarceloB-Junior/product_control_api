package com.api.product_control.services;

import com.api.product_control.models.ProductModel;
import com.api.product_control.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void saveProductReturnsProductModel(){
        var productModel = new ProductModel();
        productModel.setIdProduct(UUID.randomUUID());
        productModel.setName("Smartphone Xiaomi Redmi Note 11");
        productModel.setValue(new BigDecimal("2000.00"));

        when(productRepository.save(productModel)).thenReturn(productModel);
        var savedProduct = productService.save(productModel);

        assertEquals(productModel.getIdProduct(),savedProduct.getIdProduct());
        verify(productRepository,(times(1))).save(productModel);
    }

    @Test
    public void findAllReturnsPageOfProducts(){
        var pageable = Pageable.ofSize(10);
        var products = List.of(new ProductModel(), new ProductModel());
        Page<ProductModel> page = new PageImpl<>(products);

        when(productRepository.findAll(pageable)).thenReturn(page);
        Page<ProductModel> productModelPage = productService.findAll(pageable);

        assertEquals(products.size(),productModelPage.getContent().size());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void existsByNameReturnsTrue(){
        String productName = "Smartphone Xiaomi Redmi Note 11";

        when(productRepository.existsByName(productName)).thenReturn(true);
        boolean productExists = productService.existsByName(productName);

        assertTrue(productExists);
        verify(productRepository, times(1)).existsByName(productName);
    }

    @Test
    public void existsByNameReturnsFalse(){
        String productName = "Smartphone Xiaomi Redmi Note 11";

        when(productRepository.existsByName(productName)).thenReturn(false);
        boolean productExists = productService.existsByName(productName);

        assertFalse(productExists);
        verify(productRepository, times(1)).existsByName(productName);
    }

    @Test
    public void findByIdReturnsProductModelOptional(){
        var productModel = new ProductModel();
        var id = UUID.randomUUID();
        productModel.setIdProduct(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(productModel));
        Optional<ProductModel> productModelOptional = productService.findById(id);

        assertTrue(productModelOptional.isPresent());
        assertEquals(id, productModelOptional.get().getIdProduct());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdReturnsEmptyOptional(){
        var id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        Optional<ProductModel> productModelOptional = productService.findById(id);

        assertTrue(productModelOptional.isEmpty());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void deleteRemovesProduct(){
        var productModel = new ProductModel();

        doNothing().when(productRepository).delete(productModel);
        productService.delete(productModel);

        verify(productRepository,times(1)).delete(productModel);
    }
}
