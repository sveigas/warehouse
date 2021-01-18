package com.warehouse.controller;

import com.warehouse.domain.Inventory;
import com.warehouse.domain.Product;
import com.warehouse.exception.FileProcessingException;
import com.warehouse.message.ResponseMessage;
import com.warehouse.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@Slf4j
public class WarehouseController {

    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(json))$)");

    @Autowired
    private WarehouseService warehouseService;


    /**
     * This method uploads the inventory.json and saves the Article details in the INVENTORY table
     * @param file
     */
    @PostMapping("/upload/inventory")
    public ResponseEntity<ResponseMessage> uploadInventoryFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (file == null) {
            throw new FileProcessingException("No file to upload.");
        }
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Check if the file's name contains invalid extension
        if (!validateFileExtn(fileName)) {
            message = "Sorry! wrong file extension " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
        fileName = StringUtils.getFilename(file.getOriginalFilename());
        log.info("Received file with name : {} and size : {} bytes", fileName, file.getSize());
        try {
            warehouseService.uploadInventory(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    /**
     * This method uploads the products.json and saves the Product details in the PRODUCT table
     * @param file
     */
    @PostMapping("/upload/products")
    public ResponseEntity<ResponseMessage> uploadProductFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (file == null) {
            throw new FileProcessingException("No file to upload.");
        }
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Check if the file's name contains invalid extension
        if (!validateFileExtn(fileName)) {
            message = "Sorry! wrong file extension " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
        fileName = StringUtils.getFilename(file.getOriginalFilename());
        log.info("Received file with name : {} and size : {} bytes", fileName, file.getSize());
        try {
            warehouseService.uploadProducts(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    /**
     * This method lists the current inventory
     */
    @GetMapping("/view/inventory")
    public List<Inventory> viewCurrentInventory() {
        return warehouseService.getAllInventory();
    }


    /**
     * This method lists the current products
     */
    @GetMapping("/view/products")
    public List<Product> viewCurrentProducts() {
        return warehouseService.getAllProducts();
    }


    /**
     * This method deletes the Article and updates the inventory
     */
    @DeleteMapping("/delete/products/{name}")
    public ResponseEntity<ResponseMessage> sellProductAndUpdateInventory(@PathVariable("name") String name) {
        warehouseService.findProductAndUpdateInventory(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Inventory deleted!"));
    }

    /**
     * This method verifies that the file has a JSON extension
     * @param fileName
     */
    private static boolean validateFileExtn(String fileName) {
        Matcher match = FILE_EXTENSION_PATTERN.matcher(fileName);
        return match.matches();
    }
}
