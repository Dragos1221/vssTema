package inventory.repository;

import inventory.model.Product;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class InventoryRepositoryMockTest {

    private InventoryRepository inventoryRepository;
    private Product mockProduct;

    @BeforeEach
    void setUp() {
        this.inventoryRepository = new InventoryRepository(true);
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
    void addProductTest(){
        assertEquals(inventoryRepository.getAllProducts().size(),0);
        inventoryRepository.addProduct(mockProduct);
        assertEquals(inventoryRepository.getAllProducts().size(),1);
        Product addedProduct = inventoryRepository.getAllProducts().get(0);
        //asserting the product
        assertEquals(addedProduct.getProductId(), 1);
        assertEquals(addedProduct.getName(), "product name test 1");
        assertEquals(addedProduct.getPrice(),12.5);
        assertEquals(addedProduct.getInStock(), 10);
        assertEquals(addedProduct.getMin(), 1);
        assertEquals(addedProduct.getMax(), 100);
        assertEquals(addedProduct.getAssociatedParts(), FXCollections.observableArrayList());
    }

    @Test
    void deleteProductTest(){
        assertEquals(inventoryRepository.getAllProducts().size(),0);
        inventoryRepository.addProduct(mockProduct);
        assertEquals(inventoryRepository.getAllProducts().size(),1);
        inventoryRepository.deleteProduct(mockProduct);
        assertEquals(inventoryRepository.getAllProducts().size(),0);
    }

    @Test
    void lookupProductTest(){
        assertEquals(inventoryRepository.getAllProducts().size(),0);
        inventoryRepository.addProduct(mockProduct);
        assertEquals(inventoryRepository.getAllProducts().size(),1);
        Product addedProduct = inventoryRepository.getAllProducts().get(0);
        Product searchedProduct = inventoryRepository.lookupProduct("product name test 1").get(0);
        assertEquals(addedProduct.getProductId(), searchedProduct.getProductId());
        assertEquals(addedProduct.getName(), searchedProduct.getName());
        assertEquals(addedProduct.getPrice(),searchedProduct.getPrice());
        assertEquals(addedProduct.getInStock(), searchedProduct.getInStock());
        assertEquals(addedProduct.getMin(), searchedProduct.getMin());
        assertEquals(addedProduct.getMax(), searchedProduct.getMax());
        assertEquals(addedProduct.getAssociatedParts(), searchedProduct.getAssociatedParts());
    }

}