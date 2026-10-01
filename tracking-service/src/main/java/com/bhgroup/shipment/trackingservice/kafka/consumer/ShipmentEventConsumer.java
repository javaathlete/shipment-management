package com.bhgroup.shipment.trackingservice.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;
import com.bhgroup.shipment.trackingservice.service.TrackingService;

@Component
public class ShipmentEventConsumer {

	private final TrackingService trackingService;
	
	public ShipmentEventConsumer(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@KafkaListener(
		topics = "shipment-created",
	   groupId = "tracking-service-group",
	   containerFactory = "kafkaListenerContainerFactory"
	)
	
	public void consumeShipmentCreatedEvent(ShipmentCreatedEvent createdEvent) {
		trackingService.createTracking(createdEvent);
	}
}
