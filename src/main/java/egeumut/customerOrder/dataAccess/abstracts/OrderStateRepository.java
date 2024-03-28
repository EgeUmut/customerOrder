package egeumut.customerOrder.dataAccess.abstracts;

import egeumut.customerOrder.entities.concretes.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStateRepository extends JpaRepository<OrderState,Integer> {
    boolean existsById(int id);
    boolean existsByName(String Name);
}
