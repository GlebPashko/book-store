package org.example.bookstore.dto.category;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @Size(min = 2, max = 50)
    private String name;
    @Size(min = 5, max = 200)
    private String description;
}
