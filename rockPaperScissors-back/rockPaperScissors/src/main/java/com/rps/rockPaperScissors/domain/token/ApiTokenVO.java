package com.rps.rockPaperScissors.domain.token;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class ApiTokenVO {

    private String apiToken;

    private String refreshApiToken;
}
