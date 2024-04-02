package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.category.CreateCategoryRequest;
import egeumut.customerOrder.business.requests.category.UpdateCategoryRequest;
import egeumut.customerOrder.business.responses.category.GetAllCategoryResponse;
import egeumut.customerOrder.business.responses.category.GetCategoryResponse;

import java.util.List;

public interface CategoryService {
    Result addCategory(CreateCategoryRequest request);
    DataResult<List<GetAllCategoryResponse>> getAllCategories();
    DataResult<GetCategoryResponse> getCategoryById(int categoryId);
    Result deleteCategoryById(int request);
    DataResult<GetCategoryResponse> updateCategory(UpdateCategoryRequest updateCategoryRequest);
}
