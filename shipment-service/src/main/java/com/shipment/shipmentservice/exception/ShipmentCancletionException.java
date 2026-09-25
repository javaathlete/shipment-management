package com.shipment.shipmentservice.exception;

import com.shipment.shipmentservice.entity.ShipmentStatus;

public class ShipmentCancletionException extends RuntimeException {

	private final String errorMessage;

	public ShipmentCancletionException(String errorMessage, ShipmentStatus shipmentStatus) {
		super(errorMessage + "  " + shipmentStatus);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage+""+ShipmentStatus.CREATED;
	}

	
	
}