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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private UserRepository userRepository;
    private ModelMapperService modelMapperService;
    private UserBusinessRules userBusinessRules;

    @Override
    public Result add(CreateUserRequest request) {
        this.userBusinessRules.CheckIfUserNameExist(request.getUserName());
        this.userBusinessRules.CheckIfEmailExist(request.getEmail());

        User newUser = this.modelMapperService.forRequest().map(request , User.class);
        newUser.setCreatedDate(LocalDateTime.now());    //date time now
        userRepository.save(newUser);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllUserResponse>> getAll() {

        List<User> users =  userRepository.findAll();

        List<GetAllUserResponse> usersReponse = users.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetAllUserResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllUserResponse>>(usersReponse,"Listed Successfully");
    }

    @Override
    public DataResult<GetUserResponse> getById(int request) {
        userBusinessRules.existById(request);

        User user = this.userRepository.findById(request).orElseThrow();
        GetUserResponse response = modelMapperService.forResponse().map(user,GetUserResponse.class);

        return new SuccessDataResult<GetUserResponse>(response,"User Found Successfully");
    }

    @Override
    public Result deleteById(int request) {
        userBusinessRules.existById(request);

        userRepository.deleteById(request);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetUserResponse> update(UpdateUserRequest request) {
        userBusinessRules.existById(request.getId());
        userBusinessRules.CheckIfUserNameExist(request.getUserName() , request.getId());
        userBusinessRules.CheckIfEmailExist(request.getEmail() , request.getId());

        User user = this.userRepository.findById(request.getId()).orElseThrow();
        LocalDateTime createdDate = user.getCreatedDate();  //get created date
        user = modelMapperService.forRequest().map(request,User.class);
        user.setCreatedDate(createdDate);   //set created date tmp fix
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);

        GetUserResponse response = modelMapperService.forResponse().map(user,GetUserResponse.class);
        return new SuccessDataResult<GetUserResponse>(response,"updated Successfully");
    }
}
