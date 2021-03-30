package ru.burtseva.sd.client.command.user;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class BuyStocksCommand extends UserCommand {
    private final String company;
    private final Long count;

    public BuyStocksCommand(String login, String company, Long count) {
        super(login);
        this.company = company;
        this.count = count;
    }

    @Override
    public String getAddress() {
        return String.format("/buy/%s?company=%s&count=%d", getLogin(), company, count);
    }

    @SneakyThrows
    public Boolean execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = StaticResources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        validate(response);
        return true;
    }
}
