package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryServiceIntegrationTest_SRE {
    private Product product;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp(){
        this.inventoryService = new InventoryService(new InventoryRepository(true));
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1,"part1",10,2,1,10,2));
        this.product = new Product(1, "product name test 1", 12.5, 10,1, 100, parts);
    }

    @Test
    void addProductTest() {
        try{
            assertEquals(inventoryService.getAllProducts().size(),0);
            inventoryService.addProduct(product.getName(), product.getPrice(), product.getInStock(), product.getMin(), product.getMax(), product.getAssociatedParts());
            Product addedProduct = inventoryService.getAllProducts().get(0);
            //asserting the product
            assertEquals(addedProduct.getProductId(), 1);
            assertEquals(addedProduct.getName(), "product name test 1");
            assertEquals(addedProduct.getPrice(),12.5);
            assertEquals(inventoryService.getAllProducts().size(),1);
        }
        catch (Exception e){
            assert false;
        }

        assertThrows(Exception.class, () -> inventoryService.addProduct(product.getName(), product.getPrice(), product.getInStock(), product.getMin(), product.getMax(), FXCollections.observableArrayList()));
    }

    @Test
    void deleteProductTest() {
        try{
            assertEquals(inventoryService.getAllProducts().size(),0);
            inventoryService.addProduct(product.getName(), product.getPrice(), product.getInStock(), product.getMin(), product.getMax(), product.getAssociatedParts());
            assertEquals(inventoryService.getAllProducts().size(),1);
            Product toDelete = inventoryService.getAllProducts().get(0);
            inventoryService.deleteProduct(toDelete);
            assertEquals(inventoryService.getAllProducts().size(),0);
        }
        catch (Exception e){
            assert false;
        }
    }
}