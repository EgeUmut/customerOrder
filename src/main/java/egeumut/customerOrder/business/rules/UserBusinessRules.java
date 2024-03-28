package egeumut.customerOrder.business.rules;

import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserBusinessRules {
    private UserRepository userRepository;

    public void CheckIfEmailExist(String email){
        if(userRepository.existsByEmail(email)){
            throw new BusinessException("This email address is already in use");
        }
    }

    public void CheckIfUserNameExist(String userName){
        if(userRepository.existsByUserName(userName)){
            throw new BusinessException("This User Name is already in use");
        }
    }

    public void CheckIfEmailExist(String email, int id){
        if(userRepository.existsByEmailAndIdNot(email,id)){
            throw new BusinessException("This email address is already in use");
        }
    }

    public void CheckIfUserNameExist(String userName, int id){
        if (userRepository.existsByUserNameAndIdNot(userName, id)) {
            throw new BusinessException("This User Name is already in use");
        }
    }
    public void existById(int id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("There is no such user");
        }
    }
}
