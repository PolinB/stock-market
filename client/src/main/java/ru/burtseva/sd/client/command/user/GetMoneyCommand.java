package ru.burtseva.sd.client.command.user;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class GetMoneyCommand extends UserCommand {

    public GetMoneyCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return String.format("/money/%s", getLogin());
    }

    @SneakyThrows
    public Long execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .GET()
                .build();
        var response = StaticResources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        validate(response);
        return StaticResources.getObjectMapper().readerFor(Long.class).readValue(response.body());
    }
}
