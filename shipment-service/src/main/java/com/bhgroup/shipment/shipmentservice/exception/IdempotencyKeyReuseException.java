package com.bhgroup.shipment.shipmentservice.exception;

import lombok.Getter;

@Getter
public class IdempotencyKeyReuseException extends RuntimeException {
	
	private String idempotentErrorMsg;

	public IdempotencyKeyReuseException(String idempotentErrorMsg) {
		super(idempotentErrorMsg);
		this.idempotentErrorMsg = idempotentErrorMsg;
	}
}
