package egeumut.customerOrder.repository;

import egeumut.customerOrder.dataAccess.abstracts.CategoryRepository;
import egeumut.customerOrder.entities.concretes.Category;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository; // Test edilecek repository
    private Category category = new Category();
    @BeforeEach
    public void setup(){
        categoryRepository.deleteAll();

        category.setName("Test Category");
    }



    @Test
    @DisplayName("Given a category object, When save method is called, Then saved category should be returned")
    public void givenCategoryObject_WhenSave_ThenSavedCategory() {
        // Given


        // When
        Category savedCategory = categoryRepository.save(category);

        // Then
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Test Category");
    }

    @Test
    @DisplayName("Given categories in the database, When findAll method is called, Then list of all categories should be returned")
    public void givenCategoriesInDatabase_WhenFindAll_ThenListOfAllCategories() {
        // Given
        Category category1 = new Category("test 2",null);
        categoryRepository.save(category1);
        categoryRepository.save(category);
        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertThat(categories).isNotEmpty();
    }

    @Test
    @DisplayName("Given a category ID, When findById method is called, Then optional of the category should be returned")
    public void givenCategoryId_WhenFindById_ThenOptionalOfCategory() {
        // Given

        Category savedCategory = categoryRepository.save(category);
        Integer categoryId = savedCategory.getId();

        // When
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        // Then
        assertThat(optionalCategory).isPresent();
        assertThat(optionalCategory.get().getName()).isEqualTo("Test Category");
    }

    @Test
    @DisplayName("Given a category object with updated name, When save method is called, Then updated category should be returned")
    public void givenCategoryObjectWithUpdatedName_WhenSave_ThenUpdatedCategory() {
        // Given

        Category savedCategory = categoryRepository.save(category);
        Integer categoryId = savedCategory.getId();

        // When
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category retrievedCategory = optionalCategory.get();
            retrievedCategory.setName("Updated Test Category");
            Category updatedCategory = categoryRepository.save(retrievedCategory);

            // Then
            assertThat(updatedCategory.getName()).isEqualTo("Updated Test Category");
        } else {
            throw new RuntimeException("Category not found!");
        }
    }
}
