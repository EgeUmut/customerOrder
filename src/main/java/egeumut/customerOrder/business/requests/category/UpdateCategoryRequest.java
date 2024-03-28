package egeumut.customerOrder.business.requests.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCategoryRequest {

    @NotEmpty(message="Category must be selected!")
    private int id;
    @NotEmpty(message="Category name cannot be empty!")
    private String name;
}
