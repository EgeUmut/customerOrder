package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.Core.utilities.results.SuccessDataResult;
import egeumut.customerOrder.Core.utilities.results.SuccessResult;
import egeumut.customerOrder.business.abstracts.OrderStateService;
import egeumut.customerOrder.business.requests.orderState.CreateOrderStateRequest;
import egeumut.customerOrder.business.requests.orderState.UpdateOrderStateRequest;
import egeumut.customerOrder.business.responses.orderState.GetAllOrderStateResponse;
import egeumut.customerOrder.business.responses.orderState.GetOrderStateResponse;
import egeumut.customerOrder.business.rules.OrderStateBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.OrderStateRepository;
import egeumut.customerOrder.entities.concretes.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderStateManager implements OrderStateService {
    private OrderStateRepository orderStateRepository;
    private ModelMapperService modelMapperService;
    private OrderStateBusinessRules orderStateBusinessRules;

    public OrderStateManager(OrderStateRepository orderStateRepository, ModelMapperService modelMapperService, OrderStateBusinessRules orderStateBusinessRules) {
        this.orderStateRepository = orderStateRepository;
        this.modelMapperService = modelMapperService;
        this.orderStateBusinessRules = orderStateBusinessRules;
        //Initial orderState data
        if(orderStateRepository.findAll().isEmpty()){
            OrderState orderState1 = new OrderState("Received");
            OrderState orderState2 = new OrderState("Shipping");
            OrderState orderState3 = new OrderState("Completed");
            OrderState orderState4 = new OrderState("Cancelled");
            orderState1.setId(1);
            orderState2.setId(2);
            orderState3.setId(3);
            orderState4.setId(4);
            orderStateRepository.save(orderState1);
            orderStateRepository.save(orderState2);
            orderStateRepository.save(orderState3);
            orderStateRepository.save(orderState4);
        }
    }

    @Override
    public Result add(CreateOrderStateRequest request) {
        orderStateBusinessRules.checkIfOrderStateNameExists(request.getName());

        OrderState newOrderState = this.modelMapperService.forRequest().map(request , OrderState.class);
        newOrderState.setCreatedDate(LocalDateTime.now());    //date time now
        orderStateRepository.save(newOrderState);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllOrderStateResponse>> getAll() {
        List<OrderState> orderStates =  orderStateRepository.findAll();
        List<GetAllOrderStateResponse> orderStateResponses = orderStates.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetAllOrderStateResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllOrderStateResponse>>(orderStateResponses,"Listed Successfully");

    }

    @Override
    public DataResult<GetOrderStateResponse> getById(int request) {
        orderStateBusinessRules.checkIfOrderStateExists(request);

        OrderState orderState = this.orderStateRepository.findById(request).orElseThrow();
        GetOrderStateResponse response = modelMapperService.forResponse().map(orderState,GetOrderStateResponse.class);
        return new SuccessDataResult<GetOrderStateResponse>(response,"Order Found Successfully");
    }

    @Override
    public Result deleteById(int request) {
        orderStateBusinessRules.checkIfOrderStateExists(request);

        orderStateRepository.deleteById(request);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetOrderStateResponse> update(UpdateOrderStateRequest request) {
        orderStateBusinessRules.checkIfOrderStateExists(request.getId());   //check id
        orderStateBusinessRules.checkNotToUpdateStartingData(request.getId());  //for initial data

        OrderState orderState = this.orderStateRepository.findById(request.getId()).orElseThrow();
        LocalDateTime createdDate = orderState.getCreatedDate();  //get created date
        orderState = modelMapperService.forRequest().map(request,OrderState.class);
        orderState.setCreatedDate(createdDate);   //set created date tmp fix
        orderState.setUpdatedDate(LocalDateTime.now());
        orderStateRepository.save(orderState);

        GetOrderStateResponse response = modelMapperService.forResponse().map(orderState,GetOrderStateResponse.class);
        return new SuccessDataResult<GetOrderStateResponse>(response,"updated Successfully");
    }
}
