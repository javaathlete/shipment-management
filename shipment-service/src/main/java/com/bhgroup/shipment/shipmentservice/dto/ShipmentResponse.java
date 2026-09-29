package com.bhgroup.shipment.shipmentservice.dto;

import java.time.LocalDateTime;

import com.bhgroup.shipment.shipmentservice.entity.ShipmentStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ShipmentResponse {
	private Long shipmentId;
	private Long customerId;
	private String trackingNumber;

	private String senderName;
	private String receiverName;

	private String pickupAddress;
	private String deliveryAddress;

	private Double packageWeight;
	private String packageDescription;

	private ShipmentStatus shipmentStatus;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
