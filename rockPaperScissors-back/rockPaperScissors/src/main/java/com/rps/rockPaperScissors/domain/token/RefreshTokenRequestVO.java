package com.rps.rockPaperScissors.domain.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class RefreshTokenRequestVO {

    @NotBlank(message = "RefreshApiToken is required")
    private String refreshApiToken;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

}
