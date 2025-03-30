package com.touragency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ClientTour {
    private int clientId;
    private int tourId;
    private BigDecimal discount;
    private boolean paymentStatus;
}
