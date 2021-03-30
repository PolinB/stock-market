package ru.burtseva.sd.client.command.user;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.model.User;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.burtseva.sd.client.error.ResponseValidator.validate;

public class RegisterUserCommand extends UserCommand {

    public RegisterUserCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return String.format("?login=%s", getLogin());
    }

    @SneakyThrows
    public User execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = StaticResources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        validate(response);
        return StaticResources.getObjectMapper().readerFor(User.class).readValue(response.body());
    }
}
