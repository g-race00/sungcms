package sungcms.grocery;

import java.util.List;
import java.util.Optional;
import sungcms.category.Category;
import sungcms.supplier.Supplier;

/** Models grocery. */
public final class Grocery implements java.io.Serializable {
    
    private String id;
    private String name;
    private String image;
    private String description;
    private double price;
    private int quantity;
    private String categoryId;
    private String supplierId;

    public Grocery(){
        this.id = "-1";
        this.name = "";
        this.image = "";
        this.description = "";
        this.price = 0.0;
        this.quantity = 0;
        this.categoryId = "";
        this.supplierId = "";
    }

    public Grocery(
        String id, 
        String name,
        String image,
        String description,
        double price,
        int quantity, 
        String categoryId, 
        String supplierId
    ){
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public Grocery(
        String name,
        String image,
        String description,
        double price,
        int quantity, 
        String categoryId, 
        String supplierId
    ){
        this.id = "-1";
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getCategoryName(List<Category> categories){
        String categoryName = "";
        for (final Category c : categories) {
            if (c.getId().equals(this.categoryId)){
                categoryName = c.getName();
            }
        }
        return categoryName;
    }

    public String getSupplierName(List<Supplier> suppliers){
        String supplierName = "";
        for (final Supplier s : suppliers) {
            if (s.getId().equals(this.supplierId)){
                supplierName = s.getName();
            }
        }
        return supplierName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setCategoryId(String categoryId){
        this.categoryId = categoryId;
    }

    public void setSupplierId(String supplierId){
        this.supplierId = supplierId;
    }
}
