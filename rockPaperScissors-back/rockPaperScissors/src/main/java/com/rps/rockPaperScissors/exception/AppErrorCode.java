package com.rps.rockPaperScissors.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum AppErrorCode implements Serializable {
	BUSI_SQL("Business SQL exception");

	private final String reasonPhrase;
}
