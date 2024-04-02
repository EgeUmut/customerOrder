package egeumut.customerOrder.entities.concretes;

import egeumut.customerOrder.Core.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Builder
public class Category extends BaseEntity<Integer> {
    @Column(name="name")
    private String name;
    //cascade = CascadeType.ALL, orphanRemoval = true ,
    @OneToMany(mappedBy = "category")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Product> products;
}
