package com.ceyhun.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoUpdate {
    private Integer id;

    @NotBlank(message = "Book name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Author name is required")
    private String author;

    @NotNull(message = "Price is required")
    @Range(min = 0, message = "Price can not be smaller than 0")
    private BigDecimal price;

    private String description;
}
