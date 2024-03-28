package egeumut.customerOrder.business.requests.orderState;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderStateRequest {
    @NotEmpty(message="OrderState name cannot be empty!")
    private String name;
}
