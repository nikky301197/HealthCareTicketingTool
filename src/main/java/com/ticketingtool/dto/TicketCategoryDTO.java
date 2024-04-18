package com.ticketingtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCategoryDTO {
    private Integer categoryId;
    private String categoryName;
    // Add other fields as needed
}
