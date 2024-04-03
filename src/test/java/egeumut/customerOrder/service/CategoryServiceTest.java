package egeumut.customerOrder.service;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.concretes.CategoryManager;
import egeumut.customerOrder.business.requests.category.CreateCategoryRequest;
import egeumut.customerOrder.business.requests.category.UpdateCategoryRequest;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.category.GetAllCategoryResponse;
import egeumut.customerOrder.business.responses.category.GetCategoryResponse;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
import egeumut.customerOrder.business.rules.CategoryBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.CategoryRepository;
import egeumut.customerOrder.entities.concretes.Category;
import egeumut.customerOrder.entities.concretes.Role;
import egeumut.customerOrder.entities.concretes.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryBusinessRules categoryBusinessRules;

    @InjectMocks
    private CategoryManager categoryManager;

    private Category category;
    private Category category1;

    @BeforeEach
    public void setup() {

        category = Category.builder()
                .name("test")
                .build();
        category.setId(1);

        category1 = Category.builder()
                .name("test1")
                .build();
        category1.setId(2);
    }

    @Test
    @DisplayName("Given a category object, When saveCategory method is called, Then saved category should be returned")
    public void givenCategoryObject_WhenSaveCategory_ThenCategoryObject() {
        // Given
        CreateCategoryRequest request = new CreateCategoryRequest("test");

        //when(modelMapperService.forRequest().map(request, User.class)).thenReturn(user);
        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(any(CreateCategoryRequest.class), eq(Category.class))).thenReturn(category);
        Result result = categoryManager.addCategory(request);

        // Then
        assertThat(result.getMessage()).isEqualTo("Added Successfully");
        verify(categoryBusinessRules).checkIfCategoryNameExists(request.getName());
        verify(categoryRepository).save(category);
    }


    @Test
    @DisplayName("Given a category ID, When getById method is called, Then optional of the category should be returned")
    public void givenCategoryId_WhenGetById_ThenOptionalOfCategory() {
        // Given
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(Category.class), eq(GetCategoryResponse.class))).thenReturn(new GetCategoryResponse(1,"test",null,null,null));
        DataResult<GetCategoryResponse> result = categoryManager.getCategoryById(categoryId);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().getId()).isEqualTo(categoryId);
        assertThat(result.getMessage()).isEqualTo("Category Found Successfully");
        verify(categoryBusinessRules).checkIfCategoryExists(categoryId);
    }

    @Test
    @DisplayName("Given categories in the database, When getAllCategories method is called, Then list of all categories should be returned")
    public void givenCategoriesInDatabase_WhenGetAllCategories_ThenListOfAllCategories() {
        // Given
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);

        List<GetAllCategoryResponse> responseList = new ArrayList<>();
        responseList.add(new GetAllCategoryResponse(1,"test",null,null,null));
        responseList.add(new GetAllCategoryResponse(2,"test1",null,null,null));

        // When
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(Category.class), eq(GetAllCategoryResponse.class)))
                .thenReturn(responseList.get(0))
                .thenReturn(responseList.get(1));


        DataResult<List<GetAllCategoryResponse>> result = categoryManager.getAllCategories();

        // Then
        assertThat(result.getData()).isEqualTo(responseList);
        assertThat(result.getMessage()).isEqualTo("Listed Successfully");
    }

    @Test
    @DisplayName("Given a category ID, When deleteCategoryById method is called, Then category should be deleted successfully")
    public void givenCategoryId_WhenDeleteCategoryById_ThenCategoryShouldBeDeletedSuccessfully() {
        // Given
        int categoryId = 1;

        // When
        Result result = categoryManager.deleteCategoryById(categoryId);

        // Then
        assertThat(result.getMessage()).isEqualTo("Deleted Successfully");
        verify(categoryRepository).deleteById(categoryId);
        verify(categoryBusinessRules).checkIfCategoryExists(categoryId);
    }

    @Test
    @DisplayName("Given an UpdateCategoryRequest object, When updateCategory method is called, Then Category should be updated successfully")
    public void givenUpdateCategoryRequest_WhenUpdateCategory_ThenCategoryShouldBeUpdatedSuccessfully() {
        // Given
        int categoryId = 1;
        UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(categoryId,"test");

        Category existingCategory = Category.builder()
                .name("ChangedName")
                .build();
        existingCategory.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(UpdateCategoryRequest.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(GetCategoryResponse.class))).thenReturn(new GetCategoryResponse(1,"test",null,null,null));
        DataResult<GetCategoryResponse> result = categoryManager.updateCategory(updateCategoryRequest);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getMessage()).isEqualTo("updated Successfully");
        assertThat(result.getData().getId()).isEqualTo(categoryId);
        assertThat(result.getData().getName()).isEqualTo(updateCategoryRequest.getName());
        verify(categoryBusinessRules).checkIfCategoryExists(categoryId);
        verify(categoryRepository).save(any(Category.class));
    }

    // JUnit test for get all category method for negative scenario (empty list)
    @DisplayName("JUnit test for get all Categories method for negative scenario")
    @Test
    public void givenEmptyCategoryList_whenGetAllCategories_thenReturnEmptyCategoryList() {

        //given - precondition or setup
//        Category category2 = new Category("test3");
//        category2.setId(3);
        given(categoryRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour we are going to test
        DataResult<List<GetAllCategoryResponse>> response = categoryManager.getAllCategories();

        //then - verify the output
        assertEquals(0, response.getData().size());
        assertThat(response.getData()).isEmpty();
    }
}
