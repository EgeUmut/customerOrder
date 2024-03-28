package egeumut.customerOrder.dataAccess.abstracts;

import egeumut.customerOrder.entities.concretes.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    boolean existsById(int id);
}
