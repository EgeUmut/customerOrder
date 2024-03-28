package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.orderState.CreateOrderStateRequest;
import egeumut.customerOrder.business.requests.orderState.UpdateOrderStateRequest;
import egeumut.customerOrder.business.responses.orderState.GetAllOrderStateResponse;
import egeumut.customerOrder.business.responses.orderState.GetOrderStateResponse;

import java.util.List;

public interface OrderStateService {
    public Result add(CreateOrderStateRequest request);
    public DataResult<List<GetAllOrderStateResponse>> getAll();
    public DataResult<GetOrderStateResponse> getById(int request);
    public Result deleteById(int request);
    public DataResult<GetOrderStateResponse> update(UpdateOrderStateRequest request);
}
