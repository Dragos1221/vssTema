package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {
    private static final List<Product> l= new ArrayList<>();
    private static final List<Part> partList = new ArrayList<>();
    private static final InventoryRepository repo = new InventoryRepository();
    private static final InventoryService serv = new InventoryService(repo);
    private static final ObservableList<Part> addParts= FXCollections.observableArrayList();

    @BeforeAll
    public static void beforeAll(){
        Part p1 = new InhousePart(repo.getAutoPartId(),"abc",2.0,7,1,9,12);
        Part p2 = new InhousePart(repo.getAutoPartId(),"def",5.0,4,2,10,12);
        addParts.add(p1);
        addParts.add(p2);
        partList.add(p1);
        partList.add(p2);
    }
    @AfterAll
    public static void afterAll() {
        for (Product product : l) {
            repo.deleteProduct(product);
        }
        for (Part part : partList) {
            repo.deletePart(part);
        }
    }

    @DisplayName("Test AddProduct inStock ECP Valid")
    @ParameterizedTest
    @ValueSource(ints = {2, 6})
    void addProduct_inStockECPV(int inStock) {
        ////Arrange
        String name = "Component";
        int min=1;
        int max=8;
        int sizeOfProducts = repo.getAllProducts().size();
        //Act
        try{
            Product product = serv.addProduct(name,60.0,inStock,min,max,addParts);
            l.add(product);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+1 );
        } catch (Exception e) {
            assert(false);
        }
    }

    @DisplayName("Test AddProduct inStock ECP Invalid")
    @ParameterizedTest
    @ValueSource(ints = {-1, 10})
    void addProduct_inStockECPI(int inStock) {
        ////Arrange
        String name = "Component";
        int min=1;
        int max=8;

        //Act && Assert
        assertThrows(Exception.class, () -> serv.addProduct(name,5.0,inStock,min,max,addParts));
    }

    @DisplayName("Test AddProduct inStock BVA Valid")
    @ParameterizedTest
    @CsvSource({"1, 2", "5, 7", "8, 100"})
    void addProduct_inStockBVAV(int minStock, int maxStock) {
        ////Arrange
        String name = "Component";
        int sizeOfProducts = repo.getAllProducts().size();
        //Act
        try{
            Product product =serv.addProduct(name,160.0,minStock,minStock,maxStock,addParts);
            l.add(product);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+1 );
            Product product2 =serv.addProduct(name,160.0,minStock+1,minStock,maxStock,addParts);
            l.add(product2);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+2 );
            Product product3 =serv.addProduct(name,160.0,maxStock,minStock,maxStock,addParts);
            l.add(product3);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+3 );
            Product product4 =serv.addProduct(name,160.0,maxStock-1,minStock,maxStock,addParts);
            l.add(product4);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+4);
        } catch (Exception e) {
            //System.out.println(e);
            assert(false);
        }
    }
    @DisplayName("Test AddProduct inStock BVA Invalid")
    @ParameterizedTest
    @CsvSource({"1, 2", "5, 7", "8, 100"})
    void addProduct_inStockBVAI(int minStock, int maxStock) {
        ////Arrange
        String name = "Component";
        //Act && Assert
        assertThrows(Exception.class, () -> serv.addProduct(name,160.0,minStock-1,minStock,maxStock,addParts));
        assertThrows(Exception.class, () -> serv.addProduct(name,160.0,maxStock+1,minStock,maxStock,addParts));
    }

    @DisplayName("Test AddProduct addParts ECP Valid")
    @Test
    void addProduct_addPartsECPV() {
        ////Arrange
        String name = "Component";
        int inStock = 10, min=5, max = 30;
        ObservableList<Part> allParts = repo.getAllParts();
        ObservableList<Part> newParts = FXCollections.observableArrayList(allParts);
        double newPrice = 0.0;
        for(int i=0; i<newParts.size()/2-1; i+=2){
            newParts.remove(newParts.get(i));
        }
        for (Part newPart : newParts) {
            newPrice += newPart.getPrice();
        }
        int sizeOfProducts = repo.getAllProducts().size();
        //Act
        try{
            Product product = serv.addProduct(name,newPrice+1, inStock, min,max,newParts);
            l.add(product);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+1 );
        } catch (Exception e) {
            assert(true);
        }
    }
    @DisplayName("Test AddProduct addParts ECP Invalid")
    @Test
    void addProduct_addPartsECPI() {
        ////Arrange
        String name = "Component";
        int inStock = 10, min=5, max = 30;
        ObservableList<Part> allParts = repo.getAllParts();
        ObservableList<Part> newParts = FXCollections.observableArrayList(allParts);
        for(int i=0; i<newParts.size(); i++){
            newParts.remove(newParts.get(i));
        }
        //Act && Assert
        assertThrows(Exception.class, () -> serv.addProduct(name,0,inStock, min, max, newParts));
    }
    @DisplayName("Test AddProduct addParts BVA Valid")
    @Test
    void addProduct_addPartsBVAV() {
        ////Arrange
        String name = "Component";
        int inStock = 10, min=5, max = 30;
        ObservableList<Part> allParts = repo.getAllParts();
        ObservableList<Part> newParts = FXCollections.observableArrayList(allParts);
        double newPrice = 0.0;
        for (Part newPart : newParts) {
            newPrice += newPart.getPrice();
        }
        int sizeOfProducts = repo.getAllProducts().size();
        //Act
        try{
            Product product = serv.addProduct(name,newPrice+1, inStock, min,max,newParts);
            l.add(product);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+1 );
        } catch (Exception e) {
            assert(true);
        }
        newPrice=0.0;
        sizeOfProducts = repo.getAllProducts().size();
        for(int i=1; i<newParts.size(); i++){
            newParts.remove(newParts.get(i));
        }
        for (Part newPart : newParts) {
            newPrice += newPart.getPrice();
        }
        try{
            Product product = serv.addProduct(name,newPrice+1, inStock, min,max,newParts);
            l.add(product);
            //Assert
            assertEquals(repo.getAllProducts().size() , sizeOfProducts+1 );
        } catch (Exception e) {
            assert(true);
        }
    }

    @DisplayName("Test AddProduct addParts BVA Invalid")
    @Test
    void addProduct_addPartsBVAI() {
        ////Arrange
        String name = "Component";
        int inStock = 10, min=5, max = 30;
        ObservableList<Part> newParts = FXCollections.observableArrayList();
        assertThrows(Exception.class, () -> serv.addProduct(name,10.0,inStock, min, max, newParts));
    }

}