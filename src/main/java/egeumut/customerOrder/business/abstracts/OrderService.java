package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;

import java.util.List;

public interface OrderService {
    Result addOrder(CreateOrderRequest createOrderRequest);
    DataResult<List<GetAllOrderResponse>> getAllOrders();
    DataResult<GetOrderResponse> getOrderById(int orderId);
    Result deleteOrderById(int orderId);
    DataResult<GetOrderResponse> updateOrder(UpdateOrderRequest updateOrderRequest);
    Result cancelOrderById(int orderId);
    DataResult<List<GetOrderResponse>> getOrdersByUserId(int UserId);
}
