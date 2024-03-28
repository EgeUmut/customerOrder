package egeumut.customerOrder.business.responses.orderState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderStateResponse {
    private int id;
    private String name;
}
