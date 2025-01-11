package com.api.product_control.controllers;

import com.api.product_control.dtos.ProductDto;
import com.api.product_control.exceptions.ProductAlreadyExistsException;
import com.api.product_control.exceptions.ProductNotFoundException;
import com.api.product_control.models.ProductModel;
import com.api.product_control.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/products")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDto productDto){
        if(productService.existsByName(productDto.name())){
            throw new ProductAlreadyExistsException("Conflict: the name of this product is already in use!");
        }
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        return new ResponseEntity<>(productService.save(productModel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAllProducts(@PageableDefault (sort = "idProduct", direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        Page<ProductModel> productsPage = productService.findAll(pageable);
        if(!productsPage.isEmpty()){
            for(ProductModel product : productsPage){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.ok(productsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getOneProduct(@PathVariable(value = "id")UUID id){
        ProductModel productModel = productService.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found."));
        productModel.add(linkTo(methodOn(ProductController.class).getAllProducts(Pageable.unpaged())).withRel("Products List"));
        return ResponseEntity.ok(productModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductDto productDto){
        ProductModel productModel = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
        BeanUtils.copyProperties(productDto,productModel);
        return ResponseEntity.ok(productService.save(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        ProductModel productModel = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
        productService.delete(productModel);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}