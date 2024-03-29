package egeumut.customerOrder.entities.concretes;

import egeumut.customerOrder.Core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
public class Category extends BaseEntity<Integer> {
    @Column(name="name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true , mappedBy = "category")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Product> products;
}
