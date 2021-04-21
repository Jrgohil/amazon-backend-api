package com.juhi.amazonapi.Controllers;

import com.juhi.amazonapi.CustomizedResponse;
import com.juhi.amazonapi.Models.ProductModel;
import com.juhi.amazonapi.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping(value = "products/addproduct",consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })

    public ResponseEntity addproduct(@RequestBody ProductModel product){
        CustomizedResponse customizedResponse;
        try{
            service.addproduct(product);
            customizedResponse = new CustomizedResponse("Product Added!",null);
            return new ResponseEntity(customizedResponse,HttpStatus.OK);
        }catch (Exception e){
            customizedResponse = new CustomizedResponse(e.getMessage(),null);
            return new ResponseEntity(customizedResponse,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/products")
    public ResponseEntity allproducts(){
        return new ResponseEntity(service.allproducts(),HttpStatus.OK);
    }

    @GetMapping("/products/allcategories")
    public ResponseEntity allcategories(){
        return new ResponseEntity(service.allcategories(), HttpStatus.OK);
    }

    @GetMapping("products/getproducts/{category}")
    public ResponseEntity getproductsByCategory(@PathVariable("category") String category){
        CustomizedResponse customizedResponse;
        try{
            customizedResponse = new CustomizedResponse("List of products",service.productsByCategory(category));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("products/bestsellers")
    public ResponseEntity bestsellerProducts(){
        return new ResponseEntity(service.bestsellerproducts(), HttpStatus.OK);
    }

    @GetMapping("products/getproduct/{id}")
    public ResponseEntity getproductById(@PathVariable("id") String id){
        return new ResponseEntity(service.getproduct(id), HttpStatus.OK);
    }

    @PutMapping(value = "/products/updateproduct/{id}",consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity updateproduct(@PathVariable("id") String id, @RequestBody ProductModel product){

        CustomizedResponse customizedResponse;

        try{
            service.updateproduct(id,product);
            customizedResponse = new CustomizedResponse("Product Updated",null);
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/products/deleteproduct/{id}")
    public ResponseEntity deleteproduct(@PathVariable("id") String id){

        CustomizedResponse customizedResponse;

        try {
            service.deleteproduct(id);
            customizedResponse= new CustomizedResponse("Product Deleted!",null);
            return new ResponseEntity(customizedResponse, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
