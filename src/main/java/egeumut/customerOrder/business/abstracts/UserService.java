package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;

import java.util.List;

public interface UserService {

    Result addUser(CreateUserRequest createUserRequest);
    DataResult<List<GetAllUserResponse>> getAllUsers();
    DataResult<GetUserResponse> getUserById(int userId);
    Result deleteUserById(int userId);
    DataResult<GetUserResponse> updateUser(UpdateUserRequest updateUserRequest);
}
