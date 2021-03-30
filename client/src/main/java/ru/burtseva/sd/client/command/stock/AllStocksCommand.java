package ru.burtseva.sd.client.command.stock;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.model.Stock;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class AllStocksCommand extends StockCommand {

    public AllStocksCommand() {
        super(null);
    }

    @Override
    public String getAddress() {
        return "";
    }

    @SneakyThrows
    public List<Stock> execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .GET()
                .build();
        var response = StaticResources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        validate(response);
        return Arrays.asList(StaticResources.getObjectMapper().readerFor(Stock[].class).readValue(response.body()));
    }
}
