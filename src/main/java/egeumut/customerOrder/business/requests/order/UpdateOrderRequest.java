package egeumut.customerOrder.business.requests.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderRequest {

    @Positive
    //@NotEmpty(message="Order cannot be empty!")
    private int id;
    @Positive
    //@NotEmpty(message = "Default Values: 1-Received , 2-Shipping , 3-Completed , 4-Cancelled")
    private int orderStateId;   //you can only update state of the order. (received - cancelled - on delivery - completed etc.)
}
