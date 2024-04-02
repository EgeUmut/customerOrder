package egeumut.customerOrder.business.requests.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequest {

    //@NotEmpty(message="User cannot be empty!")
    @Positive
    private int id;
    @NotEmpty(message="first name cannot be empty!")
    private String firstName;

    @NotEmpty(message="last name cannot be empty!")
    private String lastName;

    @NotEmpty(message="User Name cannot be empty!")
    private String userName;

    @NotEmpty(message="Email cannot be empty!")
    private String email;

    @NotEmpty(message="Address cannot be empty!")
    private String address;

    @NotEmpty(message="national Identity Number cannot be empty!")
    private String nationalIdentityNumber;
}
