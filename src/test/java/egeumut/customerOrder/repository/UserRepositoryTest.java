package egeumut.customerOrder.repository;

import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import egeumut.customerOrder.entities.concretes.OrderState;
import egeumut.customerOrder.entities.concretes.Role;
import egeumut.customerOrder.entities.concretes.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .email("john.doe@example.com")
                .password("password")
                .address("123 Main St")
                .nationalIdentityNumber("123456789")
                .role(Role.USER)
                .build();
    }

    @Test
    @DisplayName("Given an User object, When save method is called, Then saved User should be returned")
    public void givenUserObject_WhenSave_ThenSavedUser() {
        // Given


        // When
        User addedUser = userRepository.save(user);

        // Then
        assertThat(addedUser.getId()).isNotNull();
        assertThat(addedUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    @DisplayName("Given Users in the database, When findAll method is called, Then list of all Users should be returned")
    public void givenUsersInDatabase_WhenFindAll_ThenListOfAllUsers() {
        // Given

        userRepository.save(user);
        // When
        List<User> users = userRepository.findAll();

        // Then
        assertThat(users).isNotEmpty();
    }

    @Test
    @DisplayName("Given an User ID, When findById method is called, Then optional of the User should be returned")
    public void givenOrderStateId_WhenFindById_ThenOptionalOfOrderState() {
        // Given

        User addedUser = userRepository.save(user);
        Integer userId = addedUser.getId();

        // When
        Optional<User> optionalUser = userRepository.findById(userId);

        // Then
        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getEmail()).isEqualTo("john.doe@example.com");
    }
}
