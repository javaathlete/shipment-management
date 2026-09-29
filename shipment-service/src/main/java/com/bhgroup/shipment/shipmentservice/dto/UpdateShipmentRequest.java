package com.bhgroup.shipment.shipmentservice.dto;

import java.time.LocalDateTime;

import com.bhgroup.shipment.shipmentservice.entity.ShipmentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateShipmentRequest {
	@NotBlank(message = "Sender Name is Required")
	private String senderName;
	@NotBlank(message = "Sender Phone is Required")
	private String senderPhone;
	@NotBlank(message = "Receiver Name is Required")
	private String receiverName;
	@NotBlank(message = "Receiver Phone is Required")
	private String receiverPhone;
	@NotBlank(message = "Pickup Address is Required")
	private String pickupAddress;
	@NotBlank(message = "Delivery Address is Required")
	private String deliveryAddress;
	@NotNull(message = "Weight of package should not be 0 or (-)ve ")
	@Positive
	private Double packageWeight;
	private String packageDescription;
}
