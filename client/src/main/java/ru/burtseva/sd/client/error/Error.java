package ru.burtseva.sd.client.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private int status;
    private String error;
    private String message;
}
