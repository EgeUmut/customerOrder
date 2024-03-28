package egeumut.customerOrder.business.responses.order;

import egeumut.customerOrder.entities.concretes.Product;
import egeumut.customerOrder.entities.concretes.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponse {

    private int id;
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;

    private int productId;
    private String productName;
    private int productCount;
    private int orderSumPrice;

    private int orderStateId; //Received Shipping Cancelled Completed... etc
    private String orderStateName;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
}
