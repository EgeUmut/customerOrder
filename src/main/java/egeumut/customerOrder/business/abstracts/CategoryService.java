package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.category.CreateCategoryRequest;
import egeumut.customerOrder.business.requests.category.UpdateCategoryRequest;
import egeumut.customerOrder.business.responses.category.GetAllCategoryResponse;
import egeumut.customerOrder.business.responses.category.GetCategoryResponse;

import java.util.List;

public interface CategoryService {
    public Result add(CreateCategoryRequest request);
    public DataResult<List<GetAllCategoryResponse>> getAll();
    public DataResult<GetCategoryResponse> getById(int request);
    public Result deleteById(int request);
    public DataResult<GetCategoryResponse> update(UpdateCategoryRequest request);
}
