package com.bhgroup.shipment.shipmentservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SHIPMENT")
@Setter
@Getter

public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long shipmentId;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private Long customerId;

	@Column(name = "TRACKING_NUMBER", nullable = false, unique = true)
	private String trackingNumber;

	@Column(name = "SENDER_NAME", nullable = false)
	private String senderName;

	@Column(name = "SENDER_PHONE", nullable = false)
	private String senderPhone;

	@Column(name = "RECEIVER_NAME", nullable = false)
	private String receiverName;

	@Column(name = "RECEIVER_PHONE", nullable = false)
	private String receiverPhone;

	@Column(name = "PICKUP_ADDRESS", nullable = false)
	private String pickupAddress;

	@Column(name = "DELIVERY_ADDRESS", nullable = false)
	private String deliveryAddress;

	@Column(name = "PACKAGE_WEIGHT", nullable = false)
	private Double packageWeight;

	@Column(name = "PACKAGE_DESCRIPTION", nullable = false)
	private String packageDescription;

	@Enumerated(EnumType.STRING)
	@Column(name = "SHIPMENT_STATUS", nullable = false)
	private ShipmentStatus shipmentStatus;

	@Column(name = "CREATED_AT", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "UPDATED_AT", nullable = false)
	private LocalDateTime updatedAt;

}
