package com.rps.rockPaperScissors.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum AppErrorCode implements Serializable {
	BUSI_SQL("Business SQL exception"),
	BUSI_USERNAME("Username already exists"),
	BUSI_EMAIL("Email aready exists");

	private final String reasonPhrase;
}
