package egeumut.customerOrder.business.rules;

import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import egeumut.customerOrder.dataAccess.abstracts.ProductRepository;
import egeumut.customerOrder.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductBusinessRules {
    private ProductRepository productRepository;

    public void existById(int id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException("There is no such product");
        }
    }

    public void checkProductStock(int productId , int productCountToLower){
        Product product = productRepository.findById(productId).orElseThrow();
        if (product.getStockCount() < productCountToLower){
            throw new BusinessException("Product(s) in stock is not enough for this order");
        }
    }
}
