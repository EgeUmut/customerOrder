package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.ProductService;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductsController {
    private ProductService productService;

    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)  //201
    public Result Add(CreateProductRequest request){
        return productService.add(request);
    }

    @GetMapping()
    public DataResult<List<GetAllProductResponse>> getAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetProductResponse> getById(@PathVariable("id") int request){
        return productService.getById(request);
    }

    @Loggable
    @DeleteMapping()
    public Result deleteById(int request){
        return productService.deleteById(request);
    }

    @Loggable
    @PutMapping()
    public DataResult<GetProductResponse> update(UpdateProductRequest request){
        return productService.update(request);
    }
    @GetMapping("/getByCategorySearch")
    public DataResult<List<GetProductResponse>> getByCategorySearch(String request){
        return productService.getProductByCategoryName(request);
    }
    @GetMapping("/getByUnitPriceBetween")
    public DataResult<List<GetProductResponse>> getByUnitPriceBetween(
            @RequestParam(required = false, defaultValue = "0.0") double minPrice,
            @RequestParam(required = false, defaultValue = "Infinity") double maxPrice)
    {
        return productService.getProductByUnitPriceBetween(minPrice, maxPrice);
    }
}
