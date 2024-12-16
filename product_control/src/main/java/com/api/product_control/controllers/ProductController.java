package com.api.product_control.controllers;

import com.api.product_control.dtos.ProductRecordDto;
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

import java.util.Optional;
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
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        if(productService.existsByName(productRecordDto.name())){
            throw new ProductAlreadyExistsException("Conflict: the name of this product is already in use!");
        }
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
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
        return ResponseEntity.status(HttpStatus.OK).body(productsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id){
        Optional<ProductModel> productOpt = productService.findById(id);
        if(productOpt.isEmpty()){
            throw new ProductNotFoundException("Product not found.");
        }
        productOpt.get().add(linkTo(methodOn(ProductController.class).getAllProducts(Pageable.unpaged())).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productOpt.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> productOpt = productService.findById(id);
        if(productOpt.isEmpty()){
            throw new ProductNotFoundException("Product not found.");
        }
        var productModel = productOpt.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productOpt = productService.findById(id);
        if(productOpt.isEmpty()){
            throw new ProductNotFoundException("Product not found.");
        }
        productService.delete(productOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}
