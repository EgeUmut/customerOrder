package egeumut.customerOrder.business.requests.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequest {

    @NotEmpty(message="Product name cannot be empty!")
    private String name;

    @NotEmpty(message="Distributor name cannot be empty!")
    private String distributorName;

    @Positive
    private int stockCount;

    @Positive
    private double unitPrice;

    @Positive
    private int categoryId;
}
