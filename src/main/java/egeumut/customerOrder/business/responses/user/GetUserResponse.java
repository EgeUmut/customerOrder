package egeumut.customerOrder.business.responses.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String address;
    private String nationalIdentityNumber;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
}
