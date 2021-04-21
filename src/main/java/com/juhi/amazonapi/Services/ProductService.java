package com.juhi.amazonapi.Services;

import com.juhi.amazonapi.Models.ProductModel;
import com.juhi.amazonapi.Models.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addproduct(ProductModel product) throws Exception
    {
        if(product.getName().isEmpty() || product.getName()==""){
            throw new Exception("Product Name Required!");
        }else if(product.getCategory().isEmpty() || product.getCategory()==""){
            throw  new Exception("Product Category Required!");
        }
        repository.insert(product);
    }

    public List<ProductModel> allproducts(){
        return  repository.findAll();
    }

    public List<ProductModel> productsByCategory(String category) throws Exception{
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        List<ProductModel> products= mongoTemplate.find(query, ProductModel.class);
        if(products.isEmpty()){
            throw new Exception("No product Found");
        }
        return products;
    }

    public List allcategories(){
        return mongoTemplate.query(ProductModel.class).distinct("category").as(String.class).all();
    }

    public List<ProductModel> bestsellerproducts(){
        Query query = new Query();
        query.addCriteria(Criteria.where("bestseller").is("true"));
        return mongoTemplate.find(query, ProductModel.class);
    }

    public Optional<ProductModel> getproduct(String id){
        return repository.findById(id);
    }

    public void updateproduct(String id, ProductModel newproduct) throws  Exception{
        Optional<ProductModel> product;
        product = repository.findById(id);
        if(product.isPresent()){
            ProductModel oldproduct = product.get();
            oldproduct.setName(newproduct.getName());
            oldproduct.setBestseller(newproduct.getBestseller());
            oldproduct.setCategory(newproduct.getCategory());
            oldproduct.setDescription(newproduct.getDescription());
            oldproduct.setImg(newproduct.getImg());
            oldproduct.setPrice(newproduct.getPrice());
            oldproduct.setQuantity(newproduct.getQuantity());
            repository.save(oldproduct);
        }else{
            throw new Exception("Invalid" +id+ " Product Id");
        }

    }

    public void deleteproduct(String id) throws  Exception{
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
        }else{
            throw new Exception("Invalid " +id+ " Product Id!");
        }
    }

}
