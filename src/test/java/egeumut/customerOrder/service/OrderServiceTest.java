package egeumut.customerOrder.service;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.abstracts.ProductService;
import egeumut.customerOrder.business.concretes.OrderManager;
import egeumut.customerOrder.business.concretes.ProductManager;
import egeumut.customerOrder.business.requests.order.CreateOrderRequest;
import egeumut.customerOrder.business.requests.order.UpdateOrderRequest;
import egeumut.customerOrder.business.responses.order.GetAllOrderResponse;
import egeumut.customerOrder.business.responses.order.GetOrderResponse;
import egeumut.customerOrder.business.rules.OrderBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.OrderRepository;
import egeumut.customerOrder.dataAccess.abstracts.ProductRepository;
import egeumut.customerOrder.entities.concretes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;
    @Mock
    private OrderBusinessRules orderBusinessRules;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderManager orderManager;
    private Category category;
    private OrderState orderState;
    private Product product;
    private User user;

    private Order order1;
    private Order order2;

    @BeforeEach
    public void setup() {

        orderState = OrderState.builder()
                .name("test")
                .build();
        orderState.setId(1);

        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .email("john.doe@example.com")
                .password("password")
                .address("123 Main St")
                .nationalIdentityNumber("123456789")
                .role(Role.USER)
                .build();
        user.setId(1);

        category = Category.builder()
                .name("test")
                .build();
        category.setId(1);

        product = Product.builder()
                .name("testProduct")
                .distributorName("testdist")
                .unitPrice(10.0)
                .stockCount(10)
                .category(category)
                .build();
        product.setId(1);

        order1 = Order.builder()
                .orderState(orderState)
                .orderSumPrice(10.0)
                .productCount(10)
                .product(product)
                .user(user)
                .build();
        order1.setId(1);

        order2 = Order.builder()
                .orderState(orderState)
                .orderSumPrice(10.0)
                .productCount(10)
                .product(product)
                .user(user)
                .build();
        order2.setId(2);
    }

    @Test
    @DisplayName("Given an order ID, When getOrderById method is called, Then optional of the order should be returned")
    public void givenOrderId_WhenGetOrderById_ThenOptionalOfOrder() {
        // Given
        int orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order1));

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(Order.class), eq(GetOrderResponse.class))).thenReturn(new GetOrderResponse(1,1,"asd","asd","asd",1,"asd",1,1,1,"asd",null,null,null));
        DataResult<GetOrderResponse> result = orderManager.getOrderById(orderId);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().getId()).isEqualTo(orderId);
        assertThat(result.getMessage()).isEqualTo("Order Found Successfully");
    }

    @Test
    @DisplayName("Given orders in the database, When getAllOrders method is called, Then list of all orders should be returned")
    public void givenOrdersInDatabase_WhenGetAllOrders_ThenListOfAllOrders() {
        // Given
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);

        List<GetAllOrderResponse> responseList = new ArrayList<>();
        responseList.add(new GetAllOrderResponse(1,1,"asd","asd","asd",1,"asd",1,1,1,"asd",null,null,null));
        responseList.add(new GetAllOrderResponse(2,1,"asd","asd","asd",1,"asd",1,1,1,"asd",null,null,null));

        when(orderRepository.findAll()).thenReturn(orderList);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(Order.class), eq(GetAllOrderResponse.class)))
                .thenReturn(responseList.get(0))
                .thenReturn(responseList.get(1));

        // When
        DataResult<List<GetAllOrderResponse>> result = orderManager.getAllOrders();

        // Then
        assertThat(result.getData()).isEqualTo(responseList);
        assertThat(result.getMessage()).isEqualTo("Listed Successfully");
    }

    @Test
    @DisplayName("Given an order ID, When deleteOrderById method is called, Then order should be deleted successfully")
    public void givenOrderId_WhenDeleteOrderById_ThenOrderShouldBeDeletedSuccessfully() {
        // Given
        int orderId = 1;

        // When
        Result result = orderManager.deleteOrderById(orderId);

        // Then
        assertThat(result.getMessage()).isEqualTo("Deleted Successfully");
        verify(orderRepository).deleteById(orderId);
        verify(orderBusinessRules).checkIfOrderExists(orderId);
    }

    @Test
    @DisplayName("Given an UpdateOrderRequest object, When updateOrder method is called, Then order should be updated successfully")
    public void givenUpdateOrderRequest_WhenUpdateOrder_ThenOrderShouldBeUpdatedSuccessfully() {
        // Given
        int orderId = 1;
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(orderId, 1);

        Order existingOrder = Order.builder()
                        .orderState(orderState)
                        .build();
        existingOrder.setId(1);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(UpdateOrderRequest.class), eq(Order.class))).thenReturn(order1);
        when(modelMapper.map(any(Order.class), eq(GetOrderResponse.class))).thenReturn(new GetOrderResponse(1,1,"asd","asd","asd",1,"asd",1,1,1,"asd",null,null,null));
        DataResult<GetOrderResponse> result = orderManager.updateOrder(updateOrderRequest);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getMessage()).isEqualTo("updated Successfully");
        assertThat(result.getData().getId()).isEqualTo(orderId);
        verify(orderBusinessRules).checkIfOrderExists(orderId);
        verify(orderRepository).save(any(Order.class));
    }

    // JUnit test for get all orders method for negative scenario (empty list)
    @DisplayName("JUnit test for get all Orders method for negative scenario")
    @Test
    public void givenEmptyOrderList_whenGetAllOrders_thenReturnEmptyOrderList() {
        //given - precondition or setup
        Order order1 = new Order(/* order properties */);
        order1.setId(2);
        given(orderRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour we are going to test
        DataResult<List<GetAllOrderResponse>> response = orderManager.getAllOrders();

        //then - verify the output
        assertEquals(0, response.getData().size());
        assertThat(response.getData()).isEmpty();
    }
}
