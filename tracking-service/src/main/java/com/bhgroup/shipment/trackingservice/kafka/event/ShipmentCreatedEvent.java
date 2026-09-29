package com.bhgroup.shipment.trackingservice.kafka.event;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ShipmentCreatedEvent {
	
	private Long shipmentId;
    private Long customerId;
    private String trackingNumber;
    private String status;
    private LocalDateTime createdAt;
}
