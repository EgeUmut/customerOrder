package egeumut.customerOrder.repository;

import egeumut.customerOrder.dataAccess.abstracts.CategoryRepository;
import egeumut.customerOrder.dataAccess.abstracts.ProductRepository;
import egeumut.customerOrder.entities.concretes.Product;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import egeumut.customerOrder.entities.concretes.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;

    @BeforeEach
    public void build(){
//        productRepository.deleteAll();
//        categoryRepository.deleteAll();

        category = Category.builder()
                .name("Test Category")
                .build();
        categoryRepository.save(category);
        product = Product.builder()
                .name("Test Product")
                .unitPrice(100.0)
                .stockCount(10)
                .category(category)
                .distributorName("Test Distributor")
                .build();
    }

    @Test
    @DisplayName("Given a product object, When save method is called, Then saved product should be returned")
    public void givenProductObject_WhenSave_ThenSavedProduct() {
        // Given


        // When
        Product savedProduct = productRepository.save(product);

        // Then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        assertThat(savedProduct.getDistributorName()).isEqualTo("Test Distributor");
        assertThat(savedProduct.getStockCount()).isEqualTo(10);
        assertThat(savedProduct.getUnitPrice()).isEqualTo(100.0);
        assertThat(savedProduct.getCategory().getName()).isEqualTo("Test Category");
    }

    @Test
    @DisplayName("Given products in the database, When findAll method is called, Then list of all products should be returned")
    public void givenProductsInDatabase_WhenFindAll_ThenListOfAllProducts() {
        // Given

        productRepository.save(product);
        // When
        List<Product> products = productRepository.findAll();

        // Then
        assertThat(products).isNotEmpty();
    }

    @Test
    @DisplayName("Given a product ID, When findById method is called, Then optional of the product should be returned")
    public void givenProductId_WhenFindById_ThenOptionalOfProduct() {
        // Given

        Product savedProduct = productRepository.save(product);
        Integer productId = savedProduct.getId();

        // When
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // Then
        assertThat(optionalProduct).isPresent();
        assertThat(optionalProduct.get().getName()).isEqualTo("Test Product");
        assertThat(optionalProduct.get().getDistributorName()).isEqualTo("Test Distributor");
        assertThat(optionalProduct.get().getStockCount()).isEqualTo(10);
        assertThat(optionalProduct.get().getUnitPrice()).isEqualTo(100.0);
        assertThat(optionalProduct.get().getCategory().getName()).isEqualTo("Test Category");
    }
}
