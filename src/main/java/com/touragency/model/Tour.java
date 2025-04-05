package com.touragency.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Tour {
    private int id;
    private String name;
    private TourType type;
    private BigDecimal price;
    private boolean isHot;
}
