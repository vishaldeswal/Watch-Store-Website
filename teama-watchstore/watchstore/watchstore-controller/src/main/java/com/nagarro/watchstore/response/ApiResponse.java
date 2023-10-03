package com.nagarro.watchstore.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The ApiResponse class represents a generic API response.
 * It contains a message field indicating the response message.
 */
@Data
public class ApiResponse {
	private final String message;
}
