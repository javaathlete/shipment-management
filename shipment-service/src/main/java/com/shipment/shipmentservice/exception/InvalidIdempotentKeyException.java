package com.shipment.shipmentservice.exception;

public class InvalidIdempotentKeyException extends RuntimeException {

	public InvalidIdempotentKeyException(String errorMessage) {
		super(errorMessage);
	}
	
}
