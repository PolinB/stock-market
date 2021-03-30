package ru.burtseva.sd.client.command.stock;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.model.Stock;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class UpdatePriceCommand extends StockCommand {
    private final Long price;

    public UpdatePriceCommand(String company, Long price) {
        super(company);
        this.price = price;
    }

    @Override
    public String getAddress() {
        return String.format("/%s?price=%d", getCompany(), price);
    }

    @SneakyThrows
    public Stock execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = StaticResources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        validate(response);
        return StaticResources.getObjectMapper().readerFor(Stock.class).readValue(response.body());
    }
}
