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
import egeumut.customerOrder.business.rules.OrderBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.OrderRepository;
import egeumut.customerOrder.entities.concretes.Order;
import egeumut.customerOrder.entities.concretes.OrderState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderManager implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapperService modelMapperService;
    private final ProductService productService;
    private final OrderBusinessRules orderBusinessRules;
    private final OrderStateService orderStateService;

    public OrderManager(OrderRepository orderRepository, ModelMapperService modelMapperService,
                        ProductService productService, OrderBusinessRules orderBusinessRules,
                        OrderStateService orderStateService) {
        this.orderRepository = orderRepository;
        this.modelMapperService = modelMapperService;
        this.productService = productService;
        this.orderBusinessRules = orderBusinessRules;
        this.orderStateService = orderStateService;
    }

    @Override
    public Result addOrder(CreateOrderRequest createOrderRequest) {
        Order newOrder = this.modelMapperService.forRequest().map(createOrderRequest , Order.class);   //mapping

        newOrder.setCreatedDate(LocalDateTime.now());    //set date time now
        newOrder.setOrderSumPrice(productService.getProductById(createOrderRequest.getProductId()).getData().getUnitPrice() * newOrder.getProductCount());  //Set Sum Price for order
        productService.lowerProductCount(newOrder.getProduct().getId() , newOrder.getProductCount());   // Lower Product Stock
        OrderState orderState = modelMapperService.forResponse().map(orderStateService.getOrderStateById(1).getData(),OrderState.class);  //Get Default Order State for Received
        newOrder.setOrderState(orderState);

        orderRepository.save(newOrder);
        return new SuccessResult("Added Successfully");
    }

    @Override
    public DataResult<List<GetAllOrderResponse>> getAllOrders() {
        List<Order> orders =  orderRepository.findAll();
        List<GetAllOrderResponse> orderResponses = orders.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetAllOrderResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllOrderResponse>>(orderResponses,"Listed Successfully");
    }

    @Override
    public DataResult<GetOrderResponse> getOrderById(int orderId) {
        orderBusinessRules.checkIfOrderExists(orderId);

        Order order = this.orderRepository.findById(orderId).orElseThrow();
        GetOrderResponse response = modelMapperService.forResponse().map(order,GetOrderResponse.class);

        return new SuccessDataResult<GetOrderResponse>(response,"Order Found Successfully");
    }

    @Override
    public Result deleteOrderById(int orderId) {
        orderBusinessRules.checkIfOrderExists(orderId);

        orderRepository.deleteById(orderId);
        return new SuccessResult("Deleted Successfully");
    }

    @Override
    public DataResult<GetOrderResponse> updateOrder(UpdateOrderRequest updateOrderRequest) {
        orderBusinessRules.checkIfOrderExists(updateOrderRequest.getId());
        orderBusinessRules.CheckIfOrderCancelled(updateOrderRequest.getId());

        Order order = this.orderRepository.findById(updateOrderRequest.getId()).orElseThrow();
        LocalDateTime createdDate = order.getCreatedDate();  //get created date
        order = modelMapperService.forRequest().map(updateOrderRequest,Order.class);
        order.setCreatedDate(createdDate);   //set created date tmp fix
        order.setUpdatedDate(LocalDateTime.now());
        orderRepository.save(order);

        GetOrderResponse response = modelMapperService.forResponse().map(order,GetOrderResponse.class);
        return new SuccessDataResult<GetOrderResponse>(response,"updated Successfully");
    }

    @Override
    public Result cancelOrderById(int orderId) {
        orderBusinessRules.checkIfOrderExists(orderId);
        orderBusinessRules.CheckIfOrderCancelled(orderId);

        Order order = orderRepository.findById(orderId).orElseThrow();
        OrderState orderState = modelMapperService.forResponse().map(orderStateService.getOrderStateById(4).getData(),OrderState.class);  //Get Default Order State for Cancelled
        order.setOrderState(orderState); //Default Cancel Id=4
        System.out.println(orderState.getId());
        productService.increaseProductCount(order.getProduct().getId() , order.getProductCount());  //Reset ProductStock Count
        orderRepository.save(order);
        return new SuccessResult("Order Cancelled");
    }

    @Override
    public DataResult<List<GetOrderResponse>> getOrdersByUserId(int userId) {
        orderBusinessRules.checkIfUserExists(userId);

        var userOrders = orderRepository.findByUserId(userId);
        List<GetOrderResponse> orderResponses = userOrders.stream()
                .map(user -> this.modelMapperService.forResponse().
                        map(user,GetOrderResponse.class)).toList();
        return new SuccessDataResult<List<GetOrderResponse>>(orderResponses , "Orders Listed for user");
    }
}
