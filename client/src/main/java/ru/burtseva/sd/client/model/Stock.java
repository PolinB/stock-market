package ru.burtseva.sd.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String company;
    private long price;
    private long count;
}
