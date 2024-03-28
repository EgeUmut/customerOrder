package egeumut.customerOrder.entities.concretes;

import egeumut.customerOrder.Core.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Category extends BaseEntity<Integer> {
    @Column(name="name")
    private String name;
}
