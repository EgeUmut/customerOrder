package egeumut.customerOrder.business.requests.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCategoryRequest {
    @NotEmpty(message="Category name cannot be empty!")
    private String name;
}
