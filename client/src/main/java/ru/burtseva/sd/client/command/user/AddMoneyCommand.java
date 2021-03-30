package ru.burtseva.sd.client.command.user;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class AddMoneyCommand extends UserCommand {

    private final Long money;

    public AddMoneyCommand(String login, Long money) {
        super(login);
        this.money = money;
    }

    @Override
    public String getAddress() {
        return String.format("/money/add/%s?value=%d", getLogin(), money);
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
