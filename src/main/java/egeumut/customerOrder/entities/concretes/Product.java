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
@Table(name="products")
@EqualsAndHashCode(callSuper = true)
@Builder
public class Product extends BaseEntity<Integer>{

    @Column(name="name")
    private String name;

    @Column(name="distributor")
    private String distributorName;

    @Column(name="stockCount")
    private int stockCount;

    @Column(name="unitPrice")
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;
    //cascade = CascadeType.ALL, orphanRemoval = true ,
    @OneToMany( mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Order> orders;

}
