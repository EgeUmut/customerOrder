package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.user.CreateUserRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.user.GetAllUserResponse;
import egeumut.customerOrder.business.responses.user.GetUserResponse;
import egeumut.customerOrder.entities.concretes.User;

import java.util.List;

public interface UserService {

    public Result add(CreateUserRequest request);
    public DataResult<List<GetAllUserResponse>> getAll();
    public DataResult<GetUserResponse> getById(int request);
    public Result deleteById(int request);
    public DataResult<GetUserResponse> update(UpdateUserRequest request);
}
