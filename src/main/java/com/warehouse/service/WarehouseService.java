package com.warehouse.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.domain.*;
import com.warehouse.repository.InventoryRepository;
import com.warehouse.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WarehouseService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * This service parses the inventory JSON and saves to INVENTORY table
     * @param file
     */
    public void uploadInventory(MultipartFile file) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeReference<List<Inventory>> mapType = new TypeReference<List<Inventory>>() {};
        try {
            String JsonAsString = new String (file.getBytes());
            InputStream is = file.getInputStream();
            InventoryRoot inventoryList = mapper.readValue(JsonAsString, InventoryRoot.class);
            inventoryRepository.saveAll(inventoryList.getInventoryList());
            log.info("Inventory saved successfully");
        } catch (IOException e) {
            log.error(e.getMessage(),e);

        }
    }

    /**
     * This service parses the products JSON and saves to PRODUCT table
     * @param file
     */
    public void uploadProducts(MultipartFile file) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeReference<List<Product>> mapType = new TypeReference<List<Product>>() {};
        try {
            String JsonAsString = new String (file.getBytes());
            InputStream is = file.getInputStream();
            ProductRoot productList = mapper.readValue(JsonAsString, ProductRoot.class);
            productRepository.saveAll(productList.getProductList());
            log.info("Products saved successfully");
        } catch (IOException e) {
            log.error(e.getMessage(),e);

        }
    }

    /**
     * This service method fetches the current rows from INVENTORY table
     *
     */
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    /**
     * This service method fetches the current rows from PRODUCT table
     *
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * The logic in this service method is to retrieve the product from the PRODUCT table and iterate
     * through all its containing articles, fetch their current stock and then update it
     * to the new Stock Value
     */
    public void findProductAndUpdateInventory(String productName){
        Optional<Product> prod = productRepository.findById(productName);
        Product product = prod.get();
        List<ContainArticle> articlesInProd = product.getContain_articles();
        for(ContainArticle art :articlesInProd){
            Optional<Inventory> article =inventoryRepository.findById(art.getArt_id());
            Inventory articleDetail = article.get();
            int newStock = articleDetail.getStock() - art.getAmount_of();//Substract the current stock value with the sold article quantity
            inventoryRepository.updateStock(art.getArt_id(),newStock);
        }
        productRepository.deleteById(productName);//Finally delete/sell the product
        log.info("Products sold and inventory updated successfully");
    }

}
