package egeumut.customerOrder.business.requests.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message="first name cannot be empty!")
    private String firstName;

    @NotEmpty(message="last name cannot be empty!")
    private String lastName;

    @NotEmpty(message="Email cannot be empty!")
    private String email;

    @NotEmpty(message="Password cannot be empty!")
    private String password;

    @NotEmpty(message="User Name cannot be empty!")
    private String userName;

    @NotEmpty(message="Address cannot be empty!")
    private String address;

    @NotEmpty(message="national Identity Number cannot be empty!")
    private String nationalIdentityNumber;
}
