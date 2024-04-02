package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.aspects.logging.logAspect;
import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.*;
import egeumut.customerOrder.business.abstracts.CategoryService;
import egeumut.customerOrder.business.requests.category.CreateCategoryRequest;
import egeumut.customerOrder.business.requests.category.UpdateCategoryRequest;
import egeumut.customerOrder.business.responses.category.GetAllCategoryResponse;
import egeumut.customerOrder.business.responses.category.GetCategoryResponse;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;
import egeumut.customerOrder.business.rules.CategoryBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.CategoryRepository;
import egeumut.customerOrder.entities.concretes.Category;
import egeumut.customerOrder.entities.concretes.Order;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryManager implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final CategoryBusinessRules categoryBusinessRules;
    Logger log;

    public CategoryManager(CategoryRepository categoryRepository, ModelMapperService modelMapperService, CategoryBusinessRules categoryBusinessRules) {
        this.categoryRepository = categoryRepository;
        this.modelMapperService = modelMapperService;
        this.categoryBusinessRules = categoryBusinessRules;
        log = LoggerFactory.getLogger(logAspect.class);
    }

    /**
     * Adds a new category with the provided details.
     *
     * @param createCategoryRequest   The request containing category details.
     * @return Result indicating the outcome of the operation.
     */
    @Override
    public Result addCategory(CreateCategoryRequest createCategoryRequest) {
        try {
            categoryBusinessRules.checkIfCategoryNameExists(createCategoryRequest.getName());

            Category newCategory = this.modelMapperService.forRequest().map(createCategoryRequest , Category.class);
            newCategory.setCreatedDate(LocalDateTime.now());    //date time now
            categoryRepository.save(newCategory);
            return new SuccessResult("Added Successfully");
        }
        catch (Exception e){
            log.warn("add Category Exception! Message: " + e.getMessage());
            return new ErrorResult();
        }
    }

    @Override
    public DataResult<List<GetAllCategoryResponse>> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        List<GetAllCategoryResponse> orderResponses = categories.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetAllCategoryResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllCategoryResponse>>(orderResponses,"Listed Successfully");
    }

    @Override
    public DataResult<GetCategoryResponse> getCategoryById(int categoryId) {
        categoryBusinessRules.checkIfCategoryExists(categoryId);

        Category category = this.categoryRepository.findById(categoryId).orElseThrow();
        GetCategoryResponse response = modelMapperService.forResponse().map(category,GetCategoryResponse.class);

        return new SuccessDataResult<GetCategoryResponse>(response,"Category Found Successfully");
    }

    @Override
    public Result deleteCategoryById(int categoryId) {
        categoryBusinessRules.checkIfCategoryExists(categoryId);

        categoryRepository.deleteById(categoryId);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetCategoryResponse> updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        categoryBusinessRules.checkIfCategoryExists(updateCategoryRequest.getId());

        Category category = this.categoryRepository.findById(updateCategoryRequest.getId()).orElseThrow();
        LocalDateTime createdDate = category.getCreatedDate();  //get created date
        category = modelMapperService.forRequest().map(updateCategoryRequest,Category.class);
        category.setCreatedDate(createdDate);   //set created date tmp fix
        category.setUpdatedDate(LocalDateTime.now());
        categoryRepository.save(category);

        GetCategoryResponse response = modelMapperService.forResponse().map(category,GetCategoryResponse.class);
        return new SuccessDataResult<GetCategoryResponse>(response,"updated Successfully");
    }
}
