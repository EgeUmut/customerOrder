package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.UserService;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final  UserService userService;
    /**
     * Constructor for UsersController.
     * @param userService The UserService instance.
     */
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     * @return A list of all users.
     */
    @GetMapping()
    @Operation(summary = "Retrieves all users")
    public DataResult<List<GetAllUserResponse>> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by its ID.
     * @param userId The ID of the user.
     * @return The details of the user.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Retrieves a user by its ID")
    public DataResult<GetUserResponse> getUserById(@Valid  @PathVariable("userId") int userId){
        return userService.getUserById(userId);
    }

    /**
     * Deletes a user by its ID.
     * @param userId The ID of the user to delete.
     * @return The result of the operation.
     */
    @DeleteMapping("/{userId}")
    @Loggable
    @Operation(summary = "Deletes a user by its ID")
    public Result deleteUserById(@Valid @PathVariable("userId") int userId){
        return userService.deleteUserById(userId);
    }

    /**
     * Updates a user.
     * @param updateUserRequest The updated details of the user.
     * @return The updated details of the user.
     */
    @PutMapping()
    @Loggable
    @Operation(summary = "Updates a user")
    public DataResult<GetUserResponse> updateUser(@Valid @RequestBody @Parameter(description = "The updated details of the user")
                                                      UpdateUserRequest updateUserRequest){
        return userService.updateUser(updateUserRequest);
    }
}
