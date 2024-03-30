package egeumut.customerOrder.repository;

import egeumut.customerOrder.dataAccess.abstracts.*;
import egeumut.customerOrder.entities.concretes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStateRepository orderStateRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Order order;
    private User user;
    private Product product;
    private OrderState orderState;
    private Category category;

    @BeforeEach
    public void setup(){
//        orderRepository.deleteAll();
//        orderStateRepository.deleteAll();
//        categoryRepository.deleteAll();
//        productRepository.deleteAll();
//        userRepository.deleteAll();

        orderState = OrderState.builder()
                .name("Test Order State")
                .build();
        orderStateRepository.save(orderState);
        category = Category.builder()
                .name("Test Category")
                .build();
        categoryRepository.save(category);
        product = Product.builder()
                .name("Test Product")
                .unitPrice(20.0)
                .stockCount(100)
                .category(category)
                .build();
        productRepository.save(product);
        user = User.builder()
                .email("Test User")
                .build();
        userRepository.save(user);
        order = Order.builder()
                .orderState(orderState)
                .product(product)
                .user(user)
                .productCount(1)
                .orderSumPrice(100.0)
                .build();
    }
    @Test
    @DisplayName("Given an order object, When save method is called, Then saved order should be returned")
    public void givenOrderObject_WhenSave_ThenSavedOrder() {
        // Given

        // When
        Order savedOrder = orderRepository.save(order);

        // Then
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getUser().getEmail()).isEqualTo("Test User");
        assertThat(savedOrder.getProduct().getName()).isEqualTo("Test Product");
        assertThat(savedOrder.getProductCount()).isEqualTo(1);
        assertThat(savedOrder.getOrderSumPrice()).isEqualTo(100.0);
        assertThat(savedOrder.getOrderState().getName()).isEqualTo("Test Order State");
    }

    @Test
    @DisplayName("Given orders in the database, When findAll method is called, Then list of all orders should be returned")
    public void givenOrdersInDatabase_WhenFindAll_ThenListOfAllOrders() {
        // Given
//        Order order1 = new Order();
//
//        User user1 = new User();
//        user1.setEmail("Test User");
//
//        Product product1 = new Product();
//        product1.setName("Test Product");
//
//        OrderState orderState1 = new OrderState();
//        orderState1.setName("Test Order State");
//
//        order1.setUser(user1);
//        order1.setProduct(product1);
//        order1.setProductCount(1);
//        order1.setOrderSumPrice(100.0);
//        order1.setOrderState(orderState1);

        //orderRepository.save(order1);
        orderRepository.save(order);
        // When
        List<Order> orders = orderRepository.findAll();

        // Then
        assertThat(orders).isNotEmpty();
    }

    @Test
    @DisplayName("Given an order ID, When findById method is called, Then optional of the order should be returned")
    public void givenOrderId_WhenFindById_ThenOptionalOfOrder() {
        // Given

        Order savedOrder = orderRepository.save(order);
        Integer orderId = savedOrder.getId();

        // When
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        // Then
        assertThat(optionalOrder).isPresent();
        assertThat(optionalOrder.get().getUser().getEmail()).isEqualTo("Test User");
        assertThat(optionalOrder.get().getProduct().getName()).isEqualTo("Test Product");
        assertThat(optionalOrder.get().getProductCount()).isEqualTo(1);
        assertThat(optionalOrder.get().getOrderSumPrice()).isEqualTo(100.0);
        assertThat(optionalOrder.get().getOrderState().getName()).isEqualTo("Test Order State");
    }
}
