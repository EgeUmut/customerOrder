package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;

import java.util.List;

public interface ProductService {
    Result addProduct(CreateProductRequest createProductRequest);
    DataResult<List<GetAllProductResponse>> getAllProducts();
    DataResult<GetProductResponse> getProductById(int ProductId);
    Result deleteProductById(int productId);
    void lowerProductCount(int productId, int productCount);
    void increaseProductCount(int productId, int productCount);
    DataResult<GetProductResponse> updateProduct(UpdateProductRequest updateProductRequest);
    DataResult<List<GetProductResponse>> getProductByCategoryName(String categoryName);
    DataResult<List<GetProductResponse>> getProductByUnitPriceBetween(double minPrice, double maxPrice);
}
