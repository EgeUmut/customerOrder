package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.Core.utilities.results.SuccessDataResult;
import egeumut.customerOrder.Core.utilities.results.SuccessResult;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryManager implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapperService modelMapperService;
    private CategoryBusinessRules categoryBusinessRules;

    @Override
    public Result add(CreateCategoryRequest request) {
        categoryBusinessRules.checkIfCategoryNameExists(request.getName());

        Category newCategory = this.modelMapperService.forRequest().map(request , Category.class);
        newCategory.setCreatedDate(LocalDateTime.now());    //date time now
        categoryRepository.save(newCategory);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllCategoryResponse>> getAll() {
        List<Category> categories =  categoryRepository.findAll();
        List<GetAllCategoryResponse> orderResponses = categories.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetAllCategoryResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllCategoryResponse>>(orderResponses,"Listed Successfully");
    }

    @Override
    public DataResult<GetCategoryResponse> getById(int request) {
        categoryBusinessRules.checkIfCategoryExists(request);

        Category category = this.categoryRepository.findById(request).orElseThrow();
        GetCategoryResponse response = modelMapperService.forResponse().map(category,GetCategoryResponse.class);

        return new SuccessDataResult<GetCategoryResponse>(response,"Category Found Successfully");
    }

    @Override
    public Result deleteById(int request) {
        categoryBusinessRules.checkIfCategoryExists(request);

        categoryRepository.deleteById(request);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetCategoryResponse> update(UpdateCategoryRequest request) {
        categoryBusinessRules.checkIfCategoryExists(request.getId());

        Category category = this.categoryRepository.findById(request.getId()).orElseThrow();
        LocalDateTime createdDate = category.getCreatedDate();  //get created date
        category = modelMapperService.forRequest().map(request,Category.class);
        category.setCreatedDate(createdDate);   //set created date tmp fix
        category.setUpdatedDate(LocalDateTime.now());
        categoryRepository.save(category);

        GetCategoryResponse response = modelMapperService.forResponse().map(category,GetCategoryResponse.class);
        return new SuccessDataResult<GetCategoryResponse>(response,"updated Successfully");
    }
}
