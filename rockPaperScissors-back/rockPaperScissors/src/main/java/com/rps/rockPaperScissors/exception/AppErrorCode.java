package com.rps.rockPaperScissors.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum AppErrorCode implements Serializable {
	BUSI_SQL("Business SQL exception"),
	BUSI_USERNAME("Username already exists"),
	BUSI_EMAIL("Email already exists"),
	BUSI_USER("Invalid username"),
	BUSI_REFRESH_TOKEN("Invalid refresh token");

	private final String reasonPhrase;
}
