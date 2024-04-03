package egeumut.customerOrder.service;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.concretes.ProductManager;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;
import egeumut.customerOrder.business.rules.ProductBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.CategoryRepository;
import egeumut.customerOrder.dataAccess.abstracts.ProductRepository;
import egeumut.customerOrder.entities.concretes.Category;
import egeumut.customerOrder.entities.concretes.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductBusinessRules productBusinessRules;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductManager productManager;
    private Product product1;
    private Product product2;
    private Category category;

    @BeforeEach
    public void setup() {

        category = Category.builder()
                .name("test")
                .build();
        category.setId(1);
        product1 = Product.builder()
                .name("testProduct")
                .distributorName("testdist")
                .unitPrice(10.0)
                .stockCount(10)
                .category(category)
                .build();
        product1.setId(1);

        product2 = Product.builder()
                .name("testProduct2")
                .distributorName("testdist2")
                .unitPrice(10.0)
                .stockCount(10)
                .category(category)
                .build();
        product2.setId(2);
    }

//    @Test
//    @DisplayName("Given a product object, When addProduct method is called, Then saved product should be returned")
//    public void givenProductObject_WhenAddProduct_ThenSavedProductShouldBeReturned() {
//        // Given
//        CreateProductRequest request = new CreateProductRequest("testProduct", "testdist",10, 10.0, 1);
//
//        // When
//        when(modelMapperService.forRequest()).thenReturn(modelMapper);
//        when(modelMapper.map(any(CreateProductRequest.class), eq(Product.class))).thenReturn(product1);
//        Result result = productManager.addProduct(request);
//
//        // Then
//        assertThat(result.getMessage()).isEqualTo("Added Successfully");
//        verify(productBusinessRules).existsByCategoryId(request.getCategoryId());
//        //verify(productRepository).save(product1);
//    }

    @Test
    @DisplayName("Given a product ID, When getById method is called, Then optional of the product should be returned")
    public void givenProductId_WhenGetById_ThenOptionalOfProduct() {
        // Given
        int productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(Product.class), eq(GetProductResponse.class)))
                .thenReturn(new GetProductResponse(1, "testProduct", "testdist",10, 10.0, 1,"test",null,null,null));

        DataResult<GetProductResponse> result = productManager.getProductById(productId);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().getId()).isEqualTo(productId);
        assertThat(result.getMessage()).isEqualTo("Product Found Successfully");
        verify(productBusinessRules).existById(productId);
    }

    @Test
    @DisplayName("Given products in the database, When getAllProducts method is called, Then list of all products should be returned")
    public void givenProductsInDatabase_WhenGetAllProducts_ThenListOfAllProducts() {
        // Given
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        List<GetAllProductResponse> responseList = new ArrayList<>();
        responseList.add(new GetAllProductResponse(1, "testProduct", "testdist",10, 10.0, 1,"test",null,null,null));
        responseList.add(new GetAllProductResponse(2, "testProduct2", "testdist2",10, 10.0, 1,"test",null,null,null));

        // ProductRepository mock'una uygun bir liste dönmesi sağlanıyor
        when(productRepository.findAll()).thenReturn(productList);

        // ModelMapperService mock'una uygun bir dönüş sağlanıyor
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(Product.class), eq(GetAllProductResponse.class)))
                .thenReturn(responseList.get(0))
                .thenReturn(responseList.get(1));

        // When
        DataResult<List<GetAllProductResponse>> result = productManager.getAllProducts();

        // Then
        assertThat(result.getData()).isEqualTo(responseList);
        assertThat(result.getMessage()).isEqualTo("Listed Successfully");
    }

    @Test
    @DisplayName("Given a product ID, When deleteProductById method is called, Then product should be deleted successfully")
    public void givenProductId_WhenDeleteProductById_ThenProductShouldBeDeletedSuccessfully() {
        // Given
        int productId = 1;

        // When
        Result result = productManager.deleteProductById(productId);

        // Then
        assertThat(result.getMessage()).isEqualTo("Deleted Successfully");
        verify(productRepository).deleteById(productId);
        verify(productBusinessRules).existById(productId);
    }

    @Test
    @DisplayName("Given an UpdateProductRequest object, When updateProduct method is called, Then product should be updated successfully")
    public void givenUpdateProductRequest_WhenUpdateProduct_ThenProductShouldBeUpdatedSuccessfully() {
        // Given
        int productId = 1;
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(productId, "testProduct", "testdist",10, 10.0, 1);

        Product existingProduct = Product.builder()
                .name("updatedProduct")
                .unitPrice(50.0)
                .distributorName("updatedDist")
                .stockCount(10)
                .build();
        existingProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(UpdateProductRequest.class), eq(Product.class))).thenReturn(product1);
        when(modelMapper.map(any(Product.class), eq(GetProductResponse.class)))
                .thenReturn(new GetProductResponse(1, "testProduct", "testdist",10, 10.0, 1,"test",null,null,null));
        DataResult<GetProductResponse> result = productManager.updateProduct(updateProductRequest);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getMessage()).isEqualTo("updated Successfully");
        assertThat(result.getData().getId()).isEqualTo(productId);
        assertThat(result.getData().getName()).isEqualTo(updateProductRequest.getName());
        assertThat(result.getData().getUnitPrice()).isEqualTo(updateProductRequest.getUnitPrice());
        assertThat(result.getData().getStockCount()).isEqualTo(updateProductRequest.getStockCount());
        verify(productBusinessRules).existById(productId);
        verify(productRepository).save(any(Product.class));
    }

    // JUnit test for get all products method for negative scenario (empty list)
    @DisplayName("JUnit test for get all Products method for negative scenario")
    @Test
    public void givenEmptyProductList_whenGetAllProducts_thenReturnEmptyProductsList() {
        // Given - setup
        given(productRepository.findAll()).willReturn(Collections.emptyList());

        // When - action
        DataResult<List<GetAllProductResponse>> response = productManager.getAllProducts();

        // Then - verify the output
        assertThat(response.getData()).isEmpty();
    }
}
