package egeumut.customerOrder.webApi.controllers;


import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.OrderService;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.requests.product.CreateProductRequest;
import egeumut.customerOrder.business.requests.product.UpdateProductRequest;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrdersController {
    private OrderService orderService;

    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)  //201
    public Result Add(CreateOrderRequest request){
        return orderService.add(request);
    }

    @GetMapping()
    public DataResult<List<GetAllOrderResponse>> getAll(){
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetOrderResponse> getById(@PathVariable("id") int request){
        return orderService.getById(request);
    }

    @Loggable
    @DeleteMapping()
    public Result deleteById(int request){
        return orderService.deleteById(request);
    }

    @Loggable
    @PutMapping()
    public DataResult<GetOrderResponse> update(UpdateOrderRequest request){
        if(request.getOrderStateId() == 4){ //default cancel value
            orderService.cancelOrder(request.getId());
        }
        return orderService.update(request);
    }

    @Loggable
    @PutMapping("/cancelOrder")
    public Result cancelOrder(int request){
        return orderService.cancelOrder(request);
    }
}
