package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductManager implements ProductService {
    private final ModelMapperService modelMapperService;
    private final ProductRepository productRepository;
    private final ProductBusinessRules productBusinessRules;

    public ProductManager(ModelMapperService modelMapperService, ProductRepository productRepository, ProductBusinessRules productBusinessRules) {
        this.modelMapperService = modelMapperService;
        this.productRepository = productRepository;
        this.productBusinessRules = productBusinessRules;
    }

    @Override
    public Result addProduct(CreateProductRequest createProductRequest) {
        productBusinessRules.existsByCategoryId(createProductRequest.getCategoryId());
        Product newProduct = new Product();
        newProduct = this.modelMapperService.forRequest().map(createProductRequest , Product.class);
        newProduct.setCreatedDate(LocalDateTime.now());    //date time now
        productRepository.save(newProduct);

        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllProductResponse>> getAllProducts() {
        List<Product> products =  productRepository.findAll();

        List<GetAllProductResponse> productResponses = products.stream()
                .map(product -> this.modelMapperService.forResponse().
                        map(product,GetAllProductResponse.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<GetAllProductResponse>>(productResponses,"Listed Successfully");
    }

    @Override
    public DataResult<GetProductResponse> getProductById(int productId) {
        productBusinessRules.existById(productId);

        Product product = this.productRepository.findById(productId).orElseThrow();
        GetProductResponse response = modelMapperService.forResponse().map(product,GetProductResponse.class);

        return new SuccessDataResult<GetProductResponse>(response,"Product Found Successfully");
    }

    @Override
    public Result deleteProductById(int productId) {
        productBusinessRules.existById(productId);

        productRepository.deleteById(productId);

        return new SuccessResult("Deleted Successfully");
    }
    @Loggable
    @Override
    public void lowerProductCount(int productId, int productCount) {
        productBusinessRules.existById(productId);
        productBusinessRules.checkProductStock(productId,productCount);

        Product product = this.productRepository.findById(productId).orElseThrow();
        product.setStockCount(product.getStockCount() - productCount);

        productRepository.save(product);
    }
    @Loggable
    @Override
    public void increaseProductCount(int productId, int productCount) {
        productBusinessRules.existById(productId);

        Product product = this.productRepository.findById(productId).orElseThrow();
        product.setStockCount(product.getStockCount() + productCount);

        productRepository.save(product);
    }

    @Override
    public DataResult<GetProductResponse> updateProduct(UpdateProductRequest updateProductRequest) {
        productBusinessRules.existById(updateProductRequest.getId());

        Product product = this.productRepository.findById(updateProductRequest.getId()).orElseThrow();
        LocalDateTime createdDate = product.getCreatedDate();  //get created date
        product = modelMapperService.forRequest().map(updateProductRequest,Product.class);
        product.setCreatedDate(createdDate);   //set created date tmp fix
        product.setUpdatedDate(LocalDateTime.now());
        productRepository.save(product);

        GetProductResponse response = modelMapperService.forResponse().map(product,GetProductResponse.class);
        return new SuccessDataResult<GetProductResponse>(response,"updated Successfully");
    }

    @Override
    public DataResult<List<GetProductResponse>> getProductByCategoryName(String categoryName) {
        List<Product> productList = productRepository.findByCategoryNameContaining(categoryName);

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
