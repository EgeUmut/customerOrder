package egeumut.customerOrder.entities.concretes;
import egeumut.customerOrder.Core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
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

}
