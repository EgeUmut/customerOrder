package egeumut.customerOrder.dataAccess.abstracts;

import egeumut.customerOrder.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategoryNameContaining(String categoryName);    //get products by category name
    List<Product> findByUnitPriceBetween(double minPrice, double maxPrice); //get products
    boolean existsById(int id);
}
