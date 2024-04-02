package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.CategoryService;
import egeumut.customerOrder.business.requests.category.CreateCategoryRequest;
import egeumut.customerOrder.business.requests.category.UpdateCategoryRequest;
import egeumut.customerOrder.business.responses.category.GetAllCategoryResponse;
import egeumut.customerOrder.business.responses.category.GetCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private final CategoryService categoryService;

    /**
     * Constructor for CategoriesController.
     * @param categoryService The CategoryService instance.
     */
    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Adds a new category.
     * @param createCategoryRequest The request body containing the details of the category to be added.
     * @return The result of the operation.
     */
    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Adds a new category")
    public Result addCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return categoryService.addCategory(createCategoryRequest);
    }

    /**
     * Retrieves all categories.
     * @return A list of all categories.
     */
    @GetMapping()
    @Operation(summary = "Retrieves all categories")
    public DataResult<List<GetAllCategoryResponse>> getAllCategories(){
        return categoryService.getAllCategories();
    }

    /**
     * Retrieves a category by its ID.
     * @param categoryId The ID of the category.
     * @return The details of the category.
     */
    @GetMapping("/{categoryId}")
    @Operation(summary = "Retrieves a category by its ID")
    public DataResult<GetCategoryResponse> getCategoryById(@Valid @PathVariable("categoryId") int categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    /**
     * Deletes a category by its ID.
     * @param categoryId The ID of the category to delete.
     * @return The result of the operation.
     */
    @DeleteMapping("/{categoryId}")
    @Loggable
    @Operation(summary = "Deletes a category by its ID")
    public Result deleteCategoryById(@Valid @PathVariable("categoryId") int categoryId){
        return categoryService.deleteCategoryById(categoryId);
    }

    /**
     * Updates a category.
     * @param updateCategoryRequest The updated details of the category.
     * @return The updated details of the category.
     */
    @PutMapping()
    @Loggable
    @Operation(summary = "Updates a category")
    public DataResult<GetCategoryResponse> updateCategory(@Valid @RequestBody @Parameter(description = "The updated details of the category")
                                                              UpdateCategoryRequest updateCategoryRequest){
        return categoryService.updateCategory(updateCategoryRequest);
    }
}
