package egeumut.customerOrder.dataAccess.abstracts;

import egeumut.customerOrder.entities.concretes.Category;
import egeumut.customerOrder.entities.concretes.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsById(int id);
    boolean existsByName(String Name);
}
