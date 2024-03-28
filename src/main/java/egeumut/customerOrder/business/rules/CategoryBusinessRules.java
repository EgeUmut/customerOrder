package egeumut.customerOrder.business.rules;

import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import egeumut.customerOrder.dataAccess.abstracts.CategoryRepository;
import egeumut.customerOrder.dataAccess.abstracts.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryBusinessRules {
    private CategoryRepository categoryRepository;

    public void checkIfCategoryExists(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("There is no such category");
        }
    }

    public void checkIfCategoryNameExists(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new BusinessException("Category name already exists!");
        }
    }
}
