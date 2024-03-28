package egeumut.customerOrder.business.responses.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {

    private int id;
    private String name;
    private String distributorName;
    private int stockCount;
    private double unitPrice;
    private int categoryId;
    private String categoryName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
}
