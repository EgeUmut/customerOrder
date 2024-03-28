package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.requests.user.UpdateUserRequest;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;

import java.util.List;

public interface OrderService {
    public Result add(CreateOrderRequest request);
    public DataResult<List<GetAllOrderResponse>> getAll();
    public DataResult<GetOrderResponse> getById(int request);
    public Result deleteById(int request);
    public DataResult<GetOrderResponse> update(UpdateOrderRequest request);
    public Result cancelOrder(int request);
}
