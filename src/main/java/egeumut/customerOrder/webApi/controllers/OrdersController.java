package egeumut.customerOrder.webApi.controllers;


import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.OrderService;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Services")
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Adds a new order.
     *
     * @param createOrderRequest The request body containing order details.
     * @return The result of the operation.
     */
    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new order")
    public Result addOrder(@RequestBody CreateOrderRequest createOrderRequest){
        return orderService.addOrder(createOrderRequest);
    }

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders.
     */
    @GetMapping()
    @Operation(summary = "Get All Orders")
    public DataResult<List<GetAllOrderResponse>> getAllOrders(){
        return orderService.getAllOrders();
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param OrderId The ID of the order.
     * @return The order details.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Order By OrderId")
    public DataResult<GetOrderResponse> getOrderById(@Valid @PathVariable("id") int OrderId){
        return orderService.getOrderById(OrderId);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param OrderId The ID of the order to delete.
     * @return The result of the operation.
     */
    @Loggable
    @DeleteMapping()
    @Operation(summary = "Delete Order By OrderId")
    public Result deleteOrderById(@Valid int OrderId){
        return orderService.deleteOrderById(OrderId);
    }

    /**
     * Updates an order.
     *
     * @param updateOrderRequest The updated order details.
     * @return The updated order details.
     */
    @Loggable
    @PutMapping()
    @Operation(summary = "Update Order")
    public DataResult<GetOrderResponse> updateOrder(@Valid UpdateOrderRequest updateOrderRequest){
        if(updateOrderRequest.getOrderStateId() == 4){
            orderService.cancelOrderById(updateOrderRequest.getId());
        }
        return orderService.updateOrder(updateOrderRequest);
    }

    /**
     * Cancels an order by its ID.
     *
     * @param OrderId The ID of the order to cancel.
     * @return The result of the operation.
     */
    @Loggable
    @PutMapping("/cancelOrder")
    @Operation(summary = "Cancel Order By OrderId")
    public Result cancelOrderByOrderId(@Valid int OrderId){
        return orderService.cancelOrderById(OrderId);
    }

    /**
     * Retrieves orders by user ID.
     *
     * @param UserId The ID of the user.
     * @return A list of orders for the specified user.
     */
    @Loggable
    @GetMapping("/getOrdersByUserId")
    @Operation(summary = "Get Orders by user id")
    public DataResult<List<GetOrderResponse>> getOrdersByUserId(@Valid int UserId){
        return orderService.getOrdersByUserId(UserId);
    }
}
