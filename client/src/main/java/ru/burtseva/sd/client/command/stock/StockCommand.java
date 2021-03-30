package ru.burtseva.sd.client.command.stock;

import lombok.Data;

import java.net.URI;

@Data
public abstract class StockCommand {
    private final String company;


    public abstract String getAddress();


    public URI getUri() {
        return URI.create(String.format("http://localhost:8080/api/stock%s", getAddress()));
    }
}
