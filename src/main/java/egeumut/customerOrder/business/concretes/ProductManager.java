package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.Core.utilities.results.SuccessDataResult;
import egeumut.customerOrder.Core.utilities.results.SuccessResult;
import egeumut.customerOrder.business.abstracts.ProductService;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;
import egeumut.customerOrder.business.rules.ProductBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.ProductRepository;
import egeumut.customerOrder.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {
    private ModelMapperService modelMapperService;
    private ProductRepository productRepository;
    private ProductBusinessRules productBusinessRules;

    @Override
    public Result add(CreateProductRequest request) {
        Product newProduct = this.modelMapperService.forRequest().map(request , Product.class);
        newProduct.setCreatedDate(LocalDateTime.now());    //date time now
        productRepository.save(newProduct);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllProductResponse>> getAll() {
        List<Product> products =  productRepository.findAll();
        List<GetAllProductResponse> productResponses = products.stream()
                .map(product -> this.modelMapperService.forResponse().
                        map(product,GetAllProductResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllProductResponse>>(productResponses,"Listed Successfully");
    }

    @Override
    public DataResult<GetProductResponse> getById(int request) {
        productBusinessRules.existById(request);

        Product product = this.productRepository.findById(request).orElseThrow();
        GetProductResponse response = modelMapperService.forResponse().map(product,GetProductResponse.class);

        return new SuccessDataResult<GetProductResponse>(response,"Product Found Successfully");
    }

    @Override
    public Result deleteById(int request) {
        productBusinessRules.existById(request);

        productRepository.deleteById(request);
        return new SuccessResult("Deleted Successfully");
    }
    @Override
    public Result lowerProductCount(int productId, int productCount) {
        productBusinessRules.existById(productId);
        productBusinessRules.checkProductStock(productId,productCount);

        Product product = this.productRepository.findById(productId).orElseThrow();
        product.setStockCount(product.getStockCount() - productCount);
        productRepository.save(product);
        return new SuccessResult("Count Lowered Successfully");
    }
    @Override
    public Result increaseProductCount(int productId, int productCount) {
        productBusinessRules.existById(productId);

        Product product = this.productRepository.findById(productId).orElseThrow();
        product.setStockCount(product.getStockCount() + productCount);
        productRepository.save(product);
        return new SuccessResult("Count Increased Successfully");
    }

    @Override
    public DataResult<GetProductResponse> update(UpdateProductRequest request) {
        productBusinessRules.existById(request.getId());

        Product product = this.productRepository.findById(request.getId()).orElseThrow();
        LocalDateTime createdDate = product.getCreatedDate();  //get created date
        product = modelMapperService.forRequest().map(request,Product.class);
        product.setCreatedDate(createdDate);   //set created date tmp fix
        product.setUpdatedDate(LocalDateTime.now());
        productRepository.save(product);

        GetProductResponse response = modelMapperService.forResponse().map(product,GetProductResponse.class);
        return new SuccessDataResult<GetProductResponse>(response,"updated Successfully");
    }

    @Override
    public DataResult<List<GetProductResponse>> getProductByCategoryName(String request) {
        List<Product> productList = productRepository.findByCategoryNameContaining(request);
        List<GetProductResponse> productResponses = productList.stream()
                .map(product -> this.modelMapperService.forResponse().
                        map(product , GetProductResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetProductResponse>>(productResponses,"Listed Successfully");
    }

    @Override
    public DataResult<List<GetProductResponse>> getProductByUnitPriceBetween(double minPrice, double maxPrice) {
        List<Product> productList = productRepository.findByUnitPriceBetween(minPrice, maxPrice);
        List<GetProductResponse> productResponses = productList.stream()
                .map(product -> this.modelMapperService.forResponse().
                        map(product , GetProductResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetProductResponse>>(productResponses,"Listed Successfully");
    }
}
