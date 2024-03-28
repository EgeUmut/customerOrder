package egeumut.customerOrder.business.rules;

import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import egeumut.customerOrder.dataAccess.abstracts.OrderStateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderStateBusinessRules {
    private OrderStateRepository orderStateRepository;


    public void checkIfOrderStateExists(int id) {
        if (!orderStateRepository.existsById(id)) {
            throw new BusinessException("There is no such Order State");
        }
    }

    public void checkIfOrderStateNameExists(String name) {
        if (orderStateRepository.existsByName(name)) {
            throw new BusinessException("Order State name already exists!");
        }
    }

    public void checkNotToUpdateStartingData(int id) {
        if(id == 1 || id == 2 || id == 3 || id == 4){
            throw new BusinessException("Please do not update initial data!");
        }
    }
}
