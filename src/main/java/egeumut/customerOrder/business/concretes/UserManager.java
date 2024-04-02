package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.Core.utilities.results.SuccessDataResult;
import egeumut.customerOrder.Core.utilities.results.SuccessResult;
import egeumut.customerOrder.business.abstracts.UserService;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
import egeumut.customerOrder.business.rules.UserBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import egeumut.customerOrder.entities.concretes.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final UserBusinessRules userBusinessRules;

    public UserManager(UserRepository userRepository, ModelMapperService modelMapperService, UserBusinessRules userBusinessRules) {
        this.userRepository = userRepository;
        this.modelMapperService = modelMapperService;
        this.userBusinessRules = userBusinessRules;
    }

    @Override
    public Result addUser(CreateUserRequest createUserRequest) {
        userBusinessRules.CheckIfUserNameExist(createUserRequest.getUserName());
        userBusinessRules.CheckIfEmailExist(createUserRequest.getEmail());

        User newUser = modelMapperService.forRequest().map(createUserRequest, User.class);
        newUser.setCreatedDate(LocalDateTime.now());    //date time now
        userRepository.save(newUser);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllUserResponse>> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<GetAllUserResponse> usersResponse = users.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user, GetAllUserResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllUserResponse>>(usersResponse, "Listed Successfully");
    }


    @Override
    public DataResult<GetUserResponse> getUserById(int userId) {
        userBusinessRules.existById(userId);

        User user = this.userRepository.findById(userId).orElseThrow();
        GetUserResponse response = modelMapperService.forResponse().map(user, GetUserResponse.class);

        return new SuccessDataResult<GetUserResponse>(response, "User Found Successfully");
    }

    @Override
    public Result deleteUserById(int userId) {
        userBusinessRules.existById(userId);

        userRepository.deleteById(userId);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetUserResponse> updateUser(UpdateUserRequest updateUserRequest) {
        userBusinessRules.existById(updateUserRequest.getId());
        userBusinessRules.CheckIfUserNameExist(updateUserRequest.getUserName(), updateUserRequest.getId());
        userBusinessRules.CheckIfEmailExist(updateUserRequest.getEmail(), updateUserRequest.getId());

        User user = this.userRepository.findById(updateUserRequest.getId()).orElseThrow();
        LocalDateTime createdDate = user.getCreatedDate();  //get created date
        user = modelMapperService.forRequest().map(updateUserRequest, User.class);
        user.setCreatedDate(createdDate);   //set created date tmp fix
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);

        GetUserResponse response = modelMapperService.forResponse().map(user, GetUserResponse.class);
        return new SuccessDataResult<GetUserResponse>(response, "updated Successfully");
    }
}
