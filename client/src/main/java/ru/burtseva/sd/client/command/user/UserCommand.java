package ru.burtseva.sd.client.command.user;

import lombok.Data;

import java.net.URI;

@Data
public abstract class UserCommand {
    private final String login;

    public abstract String getAddress();

    public URI getUri() {
        return URI.create(String.format("http://localhost:8080/api/user%s", getAddress()));
    }
}
