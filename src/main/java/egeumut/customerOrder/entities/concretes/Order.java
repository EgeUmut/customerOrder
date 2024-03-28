package egeumut.customerOrder.entities.concretes;

import egeumut.customerOrder.Core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Order extends BaseEntity<Integer> {

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    @Column(name="productCount")
    private int productCount;

    @Column(name="orderSumPrice")
    private double orderSumPrice;

    //@Column(name="state")
    //private int state; //1-Received 2-Shipping 3-Cancelled

    @ManyToOne
    @JoinColumn(name = "orderStateId")
    private OrderState orderState;


}
