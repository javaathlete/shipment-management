package com.bhgroup.shipment.shipmentservice.kafka.event;

import java.time.LocalDateTime;

import com.bhgroup.shipment.shipmentservice.entity.ShipmentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
//why NoArgs Constructor
//Later, when another service consumes this JSON event, 
//Jackson may need a no-argument constructor to create the Java object.
@NoArgsConstructor
public class ShipmentCreatedEvent {

	private Long shipmentId;
    private Long customerId;
    private String trackingNumber;
    private ShipmentStatus status;
    private LocalDateTime createdAt;

}
