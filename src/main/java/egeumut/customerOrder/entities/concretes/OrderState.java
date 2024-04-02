package egeumut.customerOrder.entities.concretes;

import egeumut.customerOrder.Core.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orderStates")
@EqualsAndHashCode(callSuper = true)
@Builder
public class OrderState extends BaseEntity<Integer> {
    private String name;
}
