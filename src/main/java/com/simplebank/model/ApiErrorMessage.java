package com.simplebank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorMessage {

    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;
    private String path;

    public ApiErrorMessage(String message, String path) {
        this.message = message;
        this.time = LocalDateTime.now();
        this.path = path;
    }

}
