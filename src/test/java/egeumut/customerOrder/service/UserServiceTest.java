package egeumut.customerOrder.service;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.concretes.UserManager;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserBusinessRules userBusinessRules;

    @InjectMocks
    private UserManager userManager;
    private User user;
    private User user2;

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

        user2 = User.builder()
                .firstName("John2")
                .lastName("Doe2")
                .userName("johndoe2")
                .email("john.doe@example.com2")
                .password("password2")
                .address("123 Main St2")
                .nationalIdentityNumber("123456789")
                .role(Role.USER)
                .build();
        user2.setId(2);
    }

    @Test
    @DisplayName("Given a user object, When saveUser method is called, Then saved user should be returned")
    public void givenUserObject_WhenSaveUser_ThenUserObject() {
        // Given
        CreateUserRequest request = new CreateUserRequest("John", "doe", "johndoe", "john.doe@example.com", "John", "John");

        //when(modelMapperService.forRequest().map(request, User.class)).thenReturn(user);
        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(any(CreateUserRequest.class), eq(User.class))).thenReturn(user);
        Result result = userManager.addUser(request);

        // Then
        assertThat(result.getMessage()).isEqualTo("Added Successfully");
        verify(userBusinessRules).CheckIfUserNameExist(request.getUserName());
        verify(userBusinessRules).CheckIfEmailExist(request.getEmail());
        verify(userRepository).save(user);
    }


    @Test
    @DisplayName("Given a user ID, When getById method is called, Then optional of the user should be returned")
    public void givenUserId_WhenGetById_ThenOptionalOfUser() {
        // Given
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(User.class), eq(GetUserResponse.class))).thenReturn(new GetUserResponse(1,"john","Doe","johndoe","john.doe@example.com","123 Main St","123456789",null,null,null));
        DataResult<GetUserResponse> result = userManager.getUserById(userId);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().getId()).isEqualTo(userId);
        assertThat(result.getMessage()).isEqualTo("User Found Successfully");
        verify(userBusinessRules).existById(userId);
    }

    @Test
    @DisplayName("Given users in the database, When getAllUsers method is called, Then list of all users should be returned")
    public void givenUsersInDatabase_WhenGetAllUsers_ThenListOfAllUsers() {
        // Given
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        List<GetAllUserResponse> responseList = new ArrayList<>();
        responseList.add(new GetAllUserResponse(1,"john","Doe","johndoe","john.doe@example.com","123 Main St","123456789",null,null,null));
        responseList.add(new GetAllUserResponse(2,"john2","Doe2","johndoe2","john.doe@example.com2","123 Main St2","123456789",null,null,null));

        // UserRepository mock'una uygun bir liste dönmesi söyleniyor
        when(userRepository.findAll()).thenReturn(userList);

        // ModelMapperService mock'una uygun bir dönüş sağlanıyor
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(User.class), eq(GetAllUserResponse.class)))
                .thenReturn(responseList.get(0))
                .thenReturn(responseList.get(1));

        // When
        DataResult<List<GetAllUserResponse>> result = userManager.getAllUsers();

        // Then
        assertThat(result.getData()).isEqualTo(responseList);
        assertThat(result.getMessage()).isEqualTo("Listed Successfully");
    }

    @Test
    @DisplayName("Given a user ID, When deleteUserById method is called, Then user should be deleted successfully")
    public void givenUserId_WhenDeleteUserById_ThenUserShouldBeDeletedSuccessfully() {
        // Given
        int userId = 1;

        // When
        Result result = userManager.deleteUserById(userId);

        // Then
        assertThat(result.getMessage()).isEqualTo("Deleted Successfully");
        verify(userRepository).deleteById(userId);
        verify(userBusinessRules).existById(userId);
    }

    @Test
    @DisplayName("Given an UpdateUserRequest object, When updateUser method is called, Then user should be updated successfully")
    public void givenUpdateUserRequest_WhenUpdateUser_ThenUserShouldBeUpdatedSuccessfully() {
        // Given
        int userId = 1;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, "John", "Doe", "johndoe", "john.doe@example.com", "123 Main St","123456789");

        User existingUser = User.builder()
                .userName("existingUsername")
                .email("existingEmail@example.com")
                .build();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(UpdateUserRequest.class), eq(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(GetUserResponse.class))).thenReturn(new GetUserResponse(1,"john","Doe","johndoe","john.doe@example.com","123 Main St","123456789",null,null,null));
        DataResult<GetUserResponse> result = userManager.updateUser(updateUserRequest);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getMessage()).isEqualTo("updated Successfully");
        assertThat(result.getData().getId()).isEqualTo(userId);
        assertThat(result.getData().getUserName()).isEqualTo(updateUserRequest.getUserName());
        assertThat(result.getData().getEmail()).isEqualTo(updateUserRequest.getEmail());
        verify(userBusinessRules).existById(userId);
        verify(userBusinessRules).CheckIfUserNameExist(updateUserRequest.getUserName(), userId);
        verify(userBusinessRules).CheckIfEmailExist(updateUserRequest.getEmail(), userId);
        verify(userRepository).save(any(User.class));
    }

    // JUnit test for get all user method for negative scenario (empty list)
    @DisplayName("JUnit test for get all Users method for negative scenario")
    @Test
    public void givenEmptyUserList_whenGetAllUsers_thenReturnEmptyUsersList() {

        //given - precondition or setup
        User user1 = new User("John", "Doe", "johndoe", "john.doe@example.com", "12345", "asd", "asd", Role.USER, null);
        user1.setId(2);
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour we are going to test
        DataResult<List<GetAllUserResponse>> response = userManager.getAllUsers();

        //then - verify the output
        assertEquals(0, response.getData().size());
        assertThat(response.getData()).isEmpty();
    }
}
