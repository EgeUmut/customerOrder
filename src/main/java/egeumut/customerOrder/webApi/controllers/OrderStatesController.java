package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.OrderStateService;
import egeumut.customerOrder.business.requests.orderState.CreateOrderStateRequest;
import egeumut.customerOrder.business.requests.orderState.UpdateOrderStateRequest;
import egeumut.customerOrder.business.responses.orderState.GetAllOrderStateResponse;
import egeumut.customerOrder.business.responses.orderState.GetOrderStateResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderStates")
@AllArgsConstructor
public class OrderStatesController {
    private OrderStateService orderStateService;

    @Loggable
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)  //201
    public Result Add(CreateOrderStateRequest request){
        return orderStateService.add(request);
    }

    @GetMapping()
    public DataResult<List<GetAllOrderStateResponse>> getAll(){
        return orderStateService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetOrderStateResponse> getById(@PathVariable("id") int request){
        return orderStateService.getById(request);
    }

    //@DeleteMapping()
    //public Result deleteById(int request){
    //    return orderStateService.deleteById(request);
    //}

    @Loggable
    @PutMapping()
    public DataResult<GetOrderStateResponse> update(UpdateOrderStateRequest request){
        return orderStateService.update(request);
    }

}
