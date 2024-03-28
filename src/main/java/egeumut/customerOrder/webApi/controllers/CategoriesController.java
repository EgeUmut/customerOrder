package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.CategoryService;
import egeumut.customerOrder.business.requests.category.CreateCategoryRequest;
import egeumut.customerOrder.business.requests.category.UpdateCategoryRequest;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.responses.category.GetAllCategoryResponse;
import egeumut.customerOrder.business.responses.category.GetCategoryResponse;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {
    private CategoryService categoryService;

    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)  //201
    public Result Add(CreateCategoryRequest request){
        return categoryService.add(request);
    }

    @GetMapping()
    public DataResult<List<GetAllCategoryResponse>> getAll(){
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetCategoryResponse> getById(@PathVariable("id") int request){
        return categoryService.getById(request);
    }

    @Loggable
    @DeleteMapping()
    public Result deleteById(int request){
        return categoryService.deleteById(request);
    }

    @Loggable
    @PutMapping()
    public DataResult<GetCategoryResponse> update(UpdateCategoryRequest request){
        return categoryService.update(request);
    }
}
