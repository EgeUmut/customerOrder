package egeumut.customerOrder.business.rules;

import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import egeumut.customerOrder.dataAccess.abstracts.OrderRepository;
import egeumut.customerOrder.dataAccess.abstracts.OrderStateRepository;
import egeumut.customerOrder.dataAccess.abstracts.ProductRepository;
import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderBusinessRules {
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private OrderStateRepository orderStateRepository;
    private OrderRepository orderRepository;

    public void checkIfOrderExists(int id) {
        if (!orderRepository.existsById(id)) {
            throw new BusinessException("There is no such order");
        }
    }

    public void checkIfUserExists(int id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("There is no such user");
        }
    }

    public void checkIfProductExists(int id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException("There is no such product");
        }
    }
}
