package egeumut.customerOrder.business.requests.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderRequest {

    @Positive
    private int userId;

    @Positive
    private int productId;

    @Positive
    private int productCount;
}
