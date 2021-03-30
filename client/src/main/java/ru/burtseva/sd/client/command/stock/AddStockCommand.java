package ru.burtseva.sd.client.command.stock;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.model.Stock;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class AddStockCommand extends StockCommand {

    private final Long price;
    private final Long count;

    public AddStockCommand(String company, Long price, Long count) {
        super(company);
        this.price = price;
        this.count = count;
    }

    @Override
    public String getAddress() {
        return "";
    }

    @SneakyThrows
    public Stock execute() {
        var stock = new Stock(getCompany(), price, count);
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(StaticResources.getObjectMapper().writeValueAsString(stock)))
                .build();
        var response = StaticResources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        validate(response);
        return StaticResources.getObjectMapper().readerFor(Stock.class).readValue(response.body());
    }
}
