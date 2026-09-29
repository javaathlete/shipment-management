package com.bhgroup.shipment.trackingservice.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;

@Component
public class ShipmentEventConsumer {

	@KafkaListener(
		topics = "shipment-created",
	   groupId = "tracking-service-group",
	   containerFactory = "kafkaListenerContainerFactory"
	)
	
	public void consumeShipmentCreatedEvent(ShipmentCreatedEvent createdEvent) {
		System.out.println("Customer Id: " +createdEvent.getCustomerId());
		System.out.println("Shipment Number: "+createdEvent.getShipmentId());
		System.out.println("Tracking Number :"+createdEvent.getTrackingNumber());
		System.out.println("Ststus: "+createdEvent.getStatus());
		System.out.println("Created At :"+createdEvent.getCreatedAt());
	}
}
