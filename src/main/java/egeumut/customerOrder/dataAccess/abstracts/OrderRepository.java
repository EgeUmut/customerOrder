package egeumut.customerOrder.dataAccess.abstracts;

import egeumut.customerOrder.entities.concretes.Order;
import egeumut.customerOrder.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    boolean existsById(int id);
    List<Order> findByUserId(int id);
}
