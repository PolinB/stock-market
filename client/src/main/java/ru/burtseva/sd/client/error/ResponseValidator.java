package ru.burtseva.sd.client.error;

import lombok.SneakyThrows;
import ru.burtseva.sd.client.StaticResources;

import java.net.http.HttpResponse;

public class ResponseValidator {
    @SneakyThrows
    public static void validate(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            return;
        }
        var error = StaticResources.getObjectMapper().readValue(response.body(), Error.class);
        throw new RuntimeException(String.format("Error: %s, message: %s, code: %d", error.getError(), error.getMessage(), error.getStatus()));
    }
}
