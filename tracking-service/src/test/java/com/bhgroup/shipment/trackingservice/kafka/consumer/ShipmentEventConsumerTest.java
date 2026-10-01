package com.bhgroup.shipment.trackingservice.kafka.consumer;

import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;
import com.bhgroup.shipment.trackingservice.service.TrackingService;

@ExtendWith(MockitoExtension.class)
public class ShipmentEventConsumerTest {

	@Mock
	private TrackingService trackingService;
	
	private ShipmentEventConsumer shipmentEventConsumer;
	
	@BeforeEach
	void setUp() {
		shipmentEventConsumer = new ShipmentEventConsumer(trackingService);
	}
	
	@Test
	void testconsumeShipmentCreatedEvent() {
		//Arrange
		
		LocalDateTime createdAt = LocalDateTime.of(2026, 9, 30, 5, 30);
		ShipmentCreatedEvent event = new ShipmentCreatedEvent();

		event.setShipmentId(10302L);
		event.setCustomerId(911L);
		event.setTrackingNumber("SHP20260929000023");
		event.setStatus("CREATED");
		event.setCreatedAt(createdAt);
		
		shipmentEventConsumer.consumeShipmentCreatedEvent(event);
		
		verify(trackingService).createTracking(event);
		
		
	}
}
