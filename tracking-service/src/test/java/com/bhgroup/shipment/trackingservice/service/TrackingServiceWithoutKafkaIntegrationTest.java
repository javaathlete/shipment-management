package com.bhgroup.shipment.trackingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

import com.bhgroup.shipment.trackingservice.entity.ShipmentTracking;
import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;
import com.bhgroup.shipment.trackingservice.repository.ShipmentTrackingRepository;

@Testcontainers
@SpringBootTest
(
properties = {
	    "spring.kafka.listener.auto-startup=false"
	})

public class TrackingServiceWithoutKafkaIntegrationTest {

	@Container
	@ServiceConnection
	static OracleContainer oracle = new OracleContainer("gvenzl/oracle-free:23-slim-faststart");

	@Autowired
	private TrackingService trackingService;

	@Autowired
	private ShipmentTrackingRepository shipmentTrackingRepository;

	@Test
	void testShouldPersistShipmentTracking() {

		ShipmentCreatedEvent event = new ShipmentCreatedEvent();

		event.setShipmentId(50001L);
		event.setCustomerId(9001L);
		event.setTrackingNumber("SHP-TEST-50001");
		event.setStatus("CREATED");
		event.setCreatedAt(LocalDateTime.of(2026, 10, 3, 10, 30));

		trackingService.createTracking(event);

		Optional<ShipmentTracking> result = shipmentTrackingRepository.findByShipmentId(50001L);

		assertNotNull(result);
		assertEquals(true, result.isPresent());

		ShipmentTracking tracking = result.get();

		assertEquals(50001L, tracking.getShipmentId());
		assertEquals(9001L, tracking.getCustomerId());
		assertEquals("SHP-TEST-50001", tracking.getTrackingNumber());
		assertEquals("CREATED", tracking.getStatus());
		assertEquals(event.getCreatedAt(), tracking.getCreatedAt());
	}

}
