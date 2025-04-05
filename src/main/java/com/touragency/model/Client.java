package com.touragency.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private int id;
    private String name;
    private String email;
    private boolean isRegular;
}
