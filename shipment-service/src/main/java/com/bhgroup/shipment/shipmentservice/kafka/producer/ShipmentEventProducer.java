package com.bhgroup.shipment.shipmentservice.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.bhgroup.shipment.shipmentservice.kafka.event.ShipmentCreatedEvent;

@Component
public class ShipmentEventProducer {
	private final KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate;

	public ShipmentEventProducer(KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendShipmentCreatedEvents(ShipmentCreatedEvent createdEvent) {
		kafkaTemplate.send("shipment-created",createdEvent.getShipmentId().toString(),createdEvent);
	}

}
