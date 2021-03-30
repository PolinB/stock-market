package ru.burtseva.sd.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String login;
    private long money;
    private List<Stock> portfolio;
}
