package egeumut.customerOrder.dataAccess.abstracts;

import egeumut.customerOrder.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByEmailAndIdNot(String email, int id);  //for update
    boolean existsByUserNameAndIdNot(String userName, int id);   //for update
    boolean existsById(int id);
}
