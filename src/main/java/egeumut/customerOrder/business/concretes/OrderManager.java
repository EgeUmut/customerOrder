package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.Core.utilities.results.SuccessDataResult;
import egeumut.customerOrder.Core.utilities.results.SuccessResult;
import egeumut.customerOrder.business.abstracts.OrderService;
import egeumut.customerOrder.business.abstracts.OrderStateService;
import egeumut.customerOrder.business.abstracts.ProductService;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;
import egeumut.customerOrder.business.responses.product.GetAllProductResponse;
import egeumut.customerOrder.business.responses.product.GetProductResponse;
import egeumut.customerOrder.business.rules.OrderBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.OrderRepository;
import egeumut.customerOrder.entities.concretes.Order;
import egeumut.customerOrder.entities.concretes.OrderState;
import egeumut.customerOrder.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderManager implements OrderService {
    private OrderRepository orderRepository;
    private ModelMapperService modelMapperService;
    private ProductService productService;
    private OrderBusinessRules orderBusinessRules;
    private OrderStateService orderStateService;

    @Override
    public Result add(CreateOrderRequest request) {
        Order newOrder = this.modelMapperService.forRequest().map(request , Order.class);   //mapping

        newOrder.setCreatedDate(LocalDateTime.now());    //set date time now

        newOrder.setOrderSumPrice(productService.getById(request.getProductId()).getData().getUnitPrice() * newOrder.getProductCount());  //Set Sum Price for order
        productService.lowerProductCount(newOrder.getProduct().getId() , newOrder.getProductCount());   // Lower Product Stock

        OrderState orderState = modelMapperService.forResponse().map(orderStateService.getById(1).getData(),OrderState.class);  //Get Default Order State for Received
        newOrder.setOrderState(orderState);

        orderRepository.save(newOrder);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllOrderResponse>> getAll() {
        List<Order> orders =  orderRepository.findAll();
        List<GetAllOrderResponse> orderResponses = orders.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetAllOrderResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllOrderResponse>>(orderResponses,"Listed Successfully");
    }

    @Override
    public DataResult<GetOrderResponse> getById(int request) {
        orderBusinessRules.checkIfOrderExists(request);

        Order order = this.orderRepository.findById(request).orElseThrow();
        GetOrderResponse response = modelMapperService.forResponse().map(order,GetOrderResponse.class);

        return new SuccessDataResult<GetOrderResponse>(response,"Order Found Successfully");
    }

    @Override
    public Result deleteById(int request) {
        orderBusinessRules.checkIfOrderExists(request);

        orderRepository.deleteById(request);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetOrderResponse> update(UpdateOrderRequest request) {
        orderBusinessRules.checkIfOrderExists(request.getId());

        Order order = this.orderRepository.findById(request.getId()).orElseThrow();
        LocalDateTime createdDate = order.getCreatedDate();  //get created date
        order = modelMapperService.forRequest().map(request,Order.class);
        order.setCreatedDate(createdDate);   //set created date tmp fix
        order.setUpdatedDate(LocalDateTime.now());
        orderRepository.save(order);

        GetOrderResponse response = modelMapperService.forResponse().map(order,GetOrderResponse.class);
        return new SuccessDataResult<GetOrderResponse>(response,"updated Successfully");
    }

    @Override
    public Result cancelOrder(int request) {
        orderBusinessRules.checkIfOrderExists(request);

        Order order = orderRepository.findById(request).orElseThrow();
        OrderState orderState = modelMapperService.forResponse().map(orderStateService.getById(4).getData(),OrderState.class);  //Get Default Order State for Cancelled
        order.setOrderState(orderState); //Default Cancel Id=4
        System.out.println(orderState.getId());
        productService.increaseProductCount(order.getProduct().getId() , order.getProductCount());  //Reset ProductStock Count
        orderRepository.save(order);
        return new SuccessResult("Order Cancelled");
    }
}
