package com.rps.rockPaperScissors.exception.handler;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class ApiError {

    private int status;

    private String error;

    private String message;
}
