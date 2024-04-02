package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.OrderStateService;
import egeumut.customerOrder.business.requests.orderState.CreateOrderStateRequest;
import egeumut.customerOrder.business.requests.orderState.UpdateOrderStateRequest;
import egeumut.customerOrder.business.responses.orderState.GetAllOrderStateResponse;
import egeumut.customerOrder.business.responses.orderState.GetOrderStateResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderStates")
public class OrderStatesController {
    private final OrderStateService orderStateService;

    public OrderStatesController(OrderStateService orderStateService) {
        this.orderStateService = orderStateService;
    }

    /**
     * Adds a new order state.
     *
     * @param createOrderStateRequest The request body containing the order state details.
     * @return The result of the operation.
     */
    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Adds a new order state")
    public Result AddOrderState(@Valid  @RequestBody CreateOrderStateRequest createOrderStateRequest){
        return orderStateService.addOrderState(createOrderStateRequest);
    }

    /**
     * Retrieves all order states.
     *
     * @return A list of all order states.
     */
    @GetMapping()
    @Operation(summary = "Retrieves all order states")
    public DataResult<List<GetAllOrderStateResponse>> getAllOrderStates(){
        return orderStateService.getAllOrderStates();
    }

    /**
     * Retrieves an order state by its ID.
     *
     * @param OrderStateId The ID of the order state.
     * @return The order state details.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Retrieves an order state by its ID")
    public DataResult<GetOrderStateResponse> getOrderStateById(@Valid @PathVariable("id") int OrderStateId){
        return orderStateService.getOrderStateById(OrderStateId);
    }

    /**
     * Updates an order state.
     *
     * @param updateOrderStateRequest The updated order state details.
     * @return The updated order state details.
     */
    @Loggable
    @PutMapping()
    @Operation(summary = "Updates an order state")
    public DataResult<GetOrderStateResponse> updateOrderState(@Valid @RequestBody UpdateOrderStateRequest updateOrderStateRequest){
        return orderStateService.updateOrderState(updateOrderStateRequest);
    }

}
