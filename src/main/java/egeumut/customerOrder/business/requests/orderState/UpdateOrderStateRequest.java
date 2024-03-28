package egeumut.customerOrder.business.requests.orderState;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderStateRequest {
    @NotEmpty(message="OrderState must be selected!")
    private int id;
    @NotEmpty(message="OrderState name cannot be empty!")
    private String name;
}
