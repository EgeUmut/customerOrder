package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.orderState.CreateOrderStateRequest;
import egeumut.customerOrder.business.requests.orderState.UpdateOrderStateRequest;
import egeumut.customerOrder.business.responses.orderState.GetAllOrderStateResponse;
import egeumut.customerOrder.business.responses.orderState.GetOrderStateResponse;

import java.util.List;

public interface OrderStateService {
    Result addOrderState(CreateOrderStateRequest createOrderStateRequest);
    DataResult<List<GetAllOrderStateResponse>> getAllOrderStates();
    DataResult<GetOrderStateResponse> getOrderStateById(int orderStateId);
    Result deleteOrderStateById(int request);
    DataResult<GetOrderStateResponse> updateOrderState(UpdateOrderStateRequest updateOrderStateRequest);
}
