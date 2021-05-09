package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InventoryServiceIntegrationTest_SR {

    private Product mockProduct;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp(){
        this.inventoryService = new InventoryService(new InventoryRepository(true));
        this.mockProduct = mock(Product.class);
        Mockito.when(mockProduct.getProductId()).thenReturn(1);
        Mockito.when(mockProduct.getName()).thenReturn("product name test 1");
        Mockito.when(mockProduct.getPrice()).thenReturn(12.5);
        Mockito.when(mockProduct.getInStock()).thenReturn(10);
        Mockito.when(mockProduct.getMin()).thenReturn(1);
        Mockito.when(mockProduct.getMax()).thenReturn(100);
        Mockito.when(mockProduct.getAssociatedParts()).thenReturn(FXCollections.observableArrayList());
    }

    @Test
    void addProductTest() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1,"part1",10,2,1,10,2));
        try{
            inventoryService.addProduct("product name test 1", 12.5, 10,1,100,parts);
            Product addedProduct = inventoryService.getAllProducts().get(0);
            //asserting the product
            assertEquals(addedProduct.getProductId(), mockProduct.getProductId());
            assertEquals(addedProduct.getName(), mockProduct.getName());
            assertEquals(addedProduct.getPrice(),mockProduct.getPrice());
            assertEquals(addedProduct.getInStock(), mockProduct.getInStock());
            assertEquals(addedProduct.getMin(), mockProduct.getMin());
            assertEquals(addedProduct.getMax(), mockProduct.getMax());
            assertEquals(addedProduct.getAssociatedParts(), parts);
        }
        catch (Exception e){
            assert false;
        }
    }

    @Test
    void deleteProductTest() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1,"part1",10,2,1,10,2));
        assertEquals(inventoryService.getAllProducts().size(),0);
        try{
            inventoryService.addProduct("product name test 1", 12.5, 10,1,100,parts);
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