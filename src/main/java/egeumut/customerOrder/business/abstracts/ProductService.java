package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;

import java.util.List;

public interface ProductService {
    public Result add(CreateProductRequest request);
    public DataResult<List<GetAllProductResponse>> getAll();
    public DataResult<GetProductResponse> getById(int request);
    public Result deleteById(int request);
    public Result lowerProductCount(int productId , int productCount);
    public Result increaseProductCount(int productId , int productCount);
    public DataResult<GetProductResponse> update(UpdateProductRequest request);
    public DataResult<List<GetProductResponse>> getProductByCategoryName(String request);
    public DataResult<List<GetProductResponse>> getProductByUnitPriceBetween(double minPrice, double maxPrice);
}
