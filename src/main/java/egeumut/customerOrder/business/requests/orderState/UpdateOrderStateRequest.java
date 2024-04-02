package egeumut.customerOrder.business.requests.orderState;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderStateRequest {
    //@NotEmpty(message="OrderState must be selected!")
    @Positive
    private int id;
    //@NotEmpty(message="OrderState name cannot be empty!")
    @Positive
    private String name;
}
