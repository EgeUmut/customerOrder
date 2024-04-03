package egeumut.customerOrder.service;

import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.Core.utilities.results.DataResult;
import egeumut.customerOrder.Core.utilities.results.Result;
import egeumut.customerOrder.business.concretes.OrderStateManager;
import egeumut.customerOrder.business.requests.orderState.CreateOrderStateRequest;
import egeumut.customerOrder.business.responses.orderState.GetAllOrderStateResponse;
import egeumut.customerOrder.business.responses.orderState.GetOrderStateResponse;
import egeumut.customerOrder.business.rules.OrderStateBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.OrderStateRepository;
import egeumut.customerOrder.entities.concretes.OrderState;
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

@ExtendWith(MockitoExtension.class)
public class OrderStateServiceTest {
    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private OrderStateRepository orderStateRepository;

    @Mock
    private OrderStateBusinessRules orderStateBusinessRules;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderStateManager orderStateManager;
    private OrderState orderState1;
    private OrderState orderState2;

    @BeforeEach
    public void setup() {

        orderState1 = OrderState.builder()
                .name("test")
                .build();
        orderState1.setId(1);

        orderState2 = OrderState.builder()
                .name("test")
                .build();
        orderState2.setId(1);

    }

    @Test
    @DisplayName("Given an OrderState object, When saveOrderState method is called, Then saved order state should be returned")
    public void givenOrderStateObject_WhenSaveOrderState_ThenOrderStateObject() {
        // Given
        String stateName = "New";
        OrderState orderState = new OrderState(stateName);

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(any(CreateOrderStateRequest.class), eq(OrderState.class))).thenReturn(orderState);
        Result result = orderStateManager.addOrderState(new CreateOrderStateRequest(stateName));

        // Then
        assertThat(result.getMessage()).isEqualTo("Added Successfully");
        verify(orderStateBusinessRules).checkIfOrderStateNameExists(stateName);
        verify(orderStateRepository).save(orderState);
    }

    @Test
    @DisplayName("Given an order state ID, When getOrderStateById method is called, Then optional of the order state should be returned")
    public void givenOrderStateId_WhenGetOrderStateById_ThenOptionalOfOrderState() {
        // Given
        int orderStateId = 1;
        String stateName = "New";
        OrderState orderState = new OrderState(stateName);
        when(orderStateRepository.findById(orderStateId)).thenReturn(Optional.of(orderState));

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(OrderState.class), eq(GetOrderStateResponse.class)))
                .thenReturn(new GetOrderStateResponse(orderStateId, stateName));
        DataResult<GetOrderStateResponse> result = orderStateManager.getOrderStateById(orderStateId);

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().getId()).isEqualTo(orderStateId);
        assertThat(result.getData().getName()).isEqualTo(stateName);
        assertThat(result.getMessage()).isEqualTo("Order State Found Successfully");
        verify(orderStateBusinessRules).checkIfOrderStateExists(orderStateId);
    }

    @Test
    @DisplayName("Given order states in the database, When getAllOrderStates method is called, Then list of all order states should be returned")
    public void givenOrderStatesInDatabase_WhenGetAllOrderStates_ThenListOfAllOrderStates() {
        // Given
        List<OrderState> orderStateList = new ArrayList<>();
        orderStateList.add(orderState1);
        orderStateList.add(orderState2);

        List<GetAllOrderStateResponse> responseList = new ArrayList<>();
        responseList.add(new GetAllOrderStateResponse(1, "New"));
        responseList.add(new GetAllOrderStateResponse(2, "Processing"));

        when(orderStateRepository.findAll()).thenReturn(orderStateList);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(any(OrderState.class), eq(GetAllOrderStateResponse.class)))
                .thenReturn(responseList.get(0))
                .thenReturn(responseList.get(1));

        // When
        DataResult<List<GetAllOrderStateResponse>> result = orderStateManager.getAllOrderStates();

        // Then
        assertThat(result.getData()).isEqualTo(responseList);
        assertThat(result.getMessage()).isEqualTo("Listed Successfully");
    }

    @Test
    @DisplayName("Given an order state ID, When deleteOrderStateById method is called, Then order state should be deleted successfully")
    public void givenOrderStateId_WhenDeleteOrderStateById_ThenOrderStateShouldBeDeletedSuccessfully() {
        // Given
        int orderStateId = 1;

        // When
        Result result = orderStateManager.deleteOrderStateById(orderStateId);

        // Then
        assertThat(result.getMessage()).isEqualTo("Deleted Successfully");
        verify(orderStateRepository).deleteById(orderStateId);
        verify(orderStateBusinessRules).checkIfOrderStateExists(orderStateId);
    }

    @Test
    @DisplayName("JUnit test for get all OrderStates method for negative scenario")
    public void givenEmptyOrderStateList_whenGetAllOrderStates_thenReturnEmptyOrderStatesList() {
        // Given - precondition or setup
        given(orderStateRepository.findAll()).willReturn(Collections.emptyList());

        // When - action or the behaviour we are going to test
        DataResult<List<GetAllOrderStateResponse>> response = orderStateManager.getAllOrderStates();

        // Then - verify the output
        assertEquals(0, response.getData().size());
        assertThat(response.getData()).isEmpty();
    }
}
