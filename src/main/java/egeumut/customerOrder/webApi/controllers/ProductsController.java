package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.ProductService;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Adds a new product")
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @Loggable
    public Result addProduct(CreateProductRequest createProductRequest){
        return productService.addProduct(createProductRequest);
    }

    @Operation(summary = "Retrieves all products")
    @GetMapping()
    public DataResult<List<GetAllProductResponse>> getAllProducts(){
        return productService.getAllProducts();
    }

    @Operation(summary = "Retrieves a product by its ID")
    @GetMapping("/{productId}")
    public DataResult<GetProductResponse> getProductById(@Valid @PathVariable("productId") int productId){
        return productService.getProductById(productId);
    }

    @Operation(summary = "Deletes a product by its ID")
    @DeleteMapping("/{productId}")
    @Loggable
    public Result deleteProductById(@Valid @PathVariable("productId") int productId){
        return productService.deleteProductById(productId);
    }

    @Operation(summary = "Updates a product")
    @PutMapping()
    @Loggable
    public DataResult<GetProductResponse> updateProduct(@Valid @RequestBody @Parameter(description = "The updated details of the product")
                                                            UpdateProductRequest updateProductRequest){
        return productService.updateProduct(updateProductRequest);
    }

    @Operation(summary = "Retrieves products by category name")
    @GetMapping("/getByCategorySearch")
    public DataResult<List<GetProductResponse>> getProductsByCategoryName(@Valid @RequestParam("categoryName") String categoryName){
        return productService.getProductByCategoryName(categoryName);
    }

    @Operation(summary = "Retrieves products by unit price range")
    @GetMapping("/getByUnitPriceBetween")
    public DataResult<List<GetProductResponse>> getProductsByUnitPriceBetween(
            @RequestParam(required = false, defaultValue = "0.0") @Parameter(description = "The minimum price") double minPrice,
            @RequestParam(required = false, defaultValue = "Infinity") @Parameter(description = "The maximum price") double maxPrice)
    {
        return productService.getProductByUnitPriceBetween(minPrice, maxPrice);
    }
}
