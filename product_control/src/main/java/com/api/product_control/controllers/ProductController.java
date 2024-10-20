package com.api.product_control.controllers;

import com.api.product_control.dtos.ProductRecordDto;
import com.api.product_control.models.ProductModel;
import com.api.product_control.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        if(productService.existsByName(productRecordDto.name())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict: the name of this product is already in use!");
        }
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productsList = productService.findAll();
        if(!productsList.isEmpty()){
            for(ProductModel product : productsList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id){
        Optional<ProductModel> productOpt = productService.findById(id);
        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productOpt.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productOpt.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> productOpt = productService.findById(id);
        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        var productModel = productOpt.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productOpt = productService.findById(id);
        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productService.delete(productOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}
