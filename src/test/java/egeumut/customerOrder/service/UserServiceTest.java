package egeumut.customerOrder.service;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.business.concretes.UserManager;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.rules.UserBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import egeumut.customerOrder.entities.concretes.Role;
import egeumut.customerOrder.entities.concretes.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Collections;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserManager userManager;

    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private UserBusinessRules userBusinessRules;


    private User user;

    @BeforeEach
    public void setup() {

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
        user.setId(1);
    }

//    @Test
//    @DisplayName("Given a user object, When saveUser method is called, Then saved user should be returned")
//    public void givenUserObject_WhenSaveUser_ThenUserObject() {
//        // Given
//        CreateUserRequest request = new CreateUserRequest("John","doe","johndoe","john.doe@example.com","John","John");
//        User newUser = user;
//        //when(modelMapperService.forRequest().map(request, User.class)).thenReturn(newUser);
//
//        // When
//        Result result = userManager.add(request);
//
//        // Then
//        assertThat(result.getMessage()).isEqualTo("Added Successfully");
//        verify(userBusinessRules).CheckIfUserNameExist(request.getUserName());
//        verify(userBusinessRules).CheckIfEmailExist(request.getEmail());
//        verify(userRepository).save(newUser);
//    }
//
//    @Test
//    @DisplayName("Given users in the database, When getAllUsers method is called, Then list of all users should be returned")
//    public void givenUsersInDatabase_WhenGetAllUsers_ThenListOfAllUsers() {
//        // Given
//        List<User> users = new ArrayList<User>();
//        users.add(user);
//        when(userRepository.findAll()).thenReturn(users);
//
//        // When
//        DataResult<List<GetAllUserResponse>> result = userManager.getAll();
//
//        // Then
//        assertThat(result.getData()).hasSize(1);
//        assertThat(result.getMessage()).isEqualTo("Listed Successfully");
//    }
//
//    @Test
//    @DisplayName("Given a user ID, When getById method is called, Then optional of the user should be returned")
//    public void givenUserId_WhenGetById_ThenOptionalOfUser() {
//        // Given
//        int userId = 1;
//        User newUser = user;
//        newUser.setId(userId);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));
//
//        // When
//        DataResult<GetUserResponse> result = userManager.getById(userId);
//
//        // Then
//        assertThat(result.getData()).isNotNull();
//        assertThat(result.getData().getId()).isEqualTo(userId);
//        assertThat(result.getMessage()).isEqualTo("User Found Successfully");
//    }

    // JUnit test for get all user method for negative scenario (empty list)
    @DisplayName("JUnit test for get all Users method for negative scenario")
    @Test
    public void givenEmptyUserList_whenGetAllUsers_thenReturnEmptyUsersList() {

        //given - precondition or setup
        User user1 = new User("John", "Doe","johndoe", "john.doe@example.com","12345","asd","asd",Role.USER,null);
        user1.setId(2);
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour we are going to test
        DataResult<List<GetAllUserResponse>> response = userManager.getAll();

        //then - verify the output
        org.junit.jupiter.api.Assertions.assertEquals(0, response.getData().size());
        assertThat(response.getData()).isEmpty();
    }


}
