package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.Core.utilities.results.SuccessResult;
import egeumut.customerOrder.business.abstracts.UserService;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
import egeumut.customerOrder.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

    private UserService userService;

//    public UsersController(UserService userService) {
//        this.userService = userService;
//    }

    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)  //201
    public Result Add(CreateUserRequest request){
        return userService.add(request);
    }

    @GetMapping()
    public DataResult<List<GetAllUserResponse>> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetUserResponse> getById(@PathVariable("id") int request){
        return userService.getById(request);
    }

    @Loggable
    @DeleteMapping()
    public Result deleteById(int request){
        return userService.deleteById(request);
    }

    @Loggable
    @PutMapping()
    public DataResult<GetUserResponse> update(UpdateUserRequest request){
        return userService.update(request);
    }
}
