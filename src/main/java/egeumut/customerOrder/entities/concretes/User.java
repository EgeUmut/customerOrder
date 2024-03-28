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
@Table(name="users")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class User extends BaseEntity<Integer> {
    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="userName")
    private String userName;

    @Column(name="email")
    private String email;

    @Column(name="address")
    private String address;

    @Column(name="nationalIdentityNumber")
    private String nationalIdentityNumber;
}
