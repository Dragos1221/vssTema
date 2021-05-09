package inventory.model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    public void ProductInvalid() {
        int id = 999;
        String name = "test product name";
        double price = 32.5;
        int inStock = 20;
        int minStock = 10;
        int maxStock = 30;
        ObservableList<Part> parts = FXCollections.observableArrayList();
        Product p = new Product(id, name, price, inStock, minStock, maxStock, parts);

        //asserts
        assertEquals(id, p.getProductId());
        assertEquals(name, p.getName());
        assertEquals(price, p.getPrice());
        assertEquals(inStock, p.getInStock());
        assertEquals(minStock, p.getMin());
        assertEquals(maxStock, p.getMax());
        assertEquals(parts, p.getAssociatedParts());

        String error = "Product must contain at least 1 part. ";
        assertEquals(error,Product.isValidProduct(name, price, inStock, minStock, maxStock, parts, ""));

    }

    @Test
    public void ProductValid() {
        int id = 1000;
        String name = "test valid product name";
        double price = 31.5;
        int inStock = 10;
        int minStock = 2;
        int maxStock = 50;
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1,"part1",10,2,1,10,2));
        Product p = new Product(id, name, price, inStock, minStock, maxStock, parts);

        //asserts
        assertEquals(id, p.getProductId());
        assertEquals(name, p.getName());
        assertEquals(price, p.getPrice());
        assertEquals(inStock, p.getInStock());
        assertEquals(minStock, p.getMin());
        assertEquals(maxStock, p.getMax());
        assertEquals(parts, p.getAssociatedParts());

        String error = "";
        assertEquals(error,Product.isValidProduct(name, price, inStock, minStock, maxStock, parts, ""));

    }
}