package egeumut.customerOrder.repository;


import egeumut.customerOrder.dataAccess.abstracts.OrderStateRepository;
import egeumut.customerOrder.entities.concretes.OrderState;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderStateRepositoryTest {
    @Autowired
    private OrderStateRepository orderStateRepository;

    @Test
    @DisplayName("Given an order state object, When save method is called, Then saved order state should be returned")
    public void givenOrderStateObject_WhenSave_ThenSavedOrderState() {
        // Given
        OrderState orderState = new OrderState();
        orderState.setName("Test Order State");

        // When
        OrderState savedOrderState = orderStateRepository.save(orderState);

        // Then
        assertThat(savedOrderState.getId()).isNotNull();
        assertThat(savedOrderState.getName()).isEqualTo("Test Order State");
    }

    @Test
    @DisplayName("Given order states in the database, When findAll method is called, Then list of all order states should be returned")
    public void givenOrderStatesInDatabase_WhenFindAll_ThenListOfAllOrderStates() {
        // Given

        // When
        List<OrderState> orderStates = orderStateRepository.findAll();

        // Then
        assertThat(orderStates).isNotEmpty();
    }

    @Test
    @DisplayName("Given an order state ID, When findById method is called, Then optional of the order state should be returned")
    public void givenOrderStateId_WhenFindById_ThenOptionalOfOrderState() {
        // Given
        OrderState orderState = new OrderState();
        orderState.setName("Test Order State");
        OrderState savedOrderState = orderStateRepository.save(orderState);
        Integer orderStateId = savedOrderState.getId();

        // When
        Optional<OrderState> optionalOrderState = orderStateRepository.findById(orderStateId);

        // Then
        assertThat(optionalOrderState).isPresent();
        assertThat(optionalOrderState.get().getName()).isEqualTo("Test Order State");
    }
}
