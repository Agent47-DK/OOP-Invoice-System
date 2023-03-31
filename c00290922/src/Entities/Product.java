/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This is the data needed to save the Product into the database
 *      it will be called primarily when working with product service
 */
package Entities;

public class Product {
    private int productId;
    private String name;
    private String description;
    private double price;
    private Product.Category category;
    public enum Category {
        // only 2 categories can be defined at this point so we will use enum for it
        buy,
        sell
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
