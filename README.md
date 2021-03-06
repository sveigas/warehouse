# Code Assignment

-
#+# Intro
This assignment will be used as a discussion during a technical interview.
The primary values for the code we look for are: simplicity, readability, maintainability, testability. It should be easy to scan the code, and rather quickly understand what it’s doing. Pay attention to naming.
 
You may choose any coding language, and we look forward to discussing your choice.

## The Task
The assignment is to implement a warehouse software. This software should hold articles, and the articles should contain an identification number, a name and available stock. It should be possible to load articles into the software from a file, see the attached inventory.json.
The warehouse software should also have products, products are made of different articles. Products should have a name, price and a list of articles of which they are made from with a quantity. The products should also be loaded from a file, see the attached products.json. 
 
The warehouse should have at least the following functionality;
* Get all products and quantity of each that is an available with the current inventory
* Remove(Sell) a product and update the inventory accordingly

## Solution

This assignment has been implemented using springboot and java. PostMan can be used to access the below APIs

1. http://localhost:8080/upload/inventory                  (functionality to upload inventory.json)

2. http://localhost:8080/upload/products                   (functionality to upload products.json)

3. http://localhost:8080/view/inventory                    (functionality to view the current inventory)

4. http://localhost:8080/view/products/                    (functionality to view the current list of products)

5. http://localhost:8080/delete/products/{product-name}    (functionality to delete/sell the product and update inventory)
