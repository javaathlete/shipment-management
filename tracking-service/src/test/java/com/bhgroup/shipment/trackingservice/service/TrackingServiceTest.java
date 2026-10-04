package com.bhgroup.shipment.trackingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.bhgroup.shipment.trackingservice.entity.ShipmentTracking;
import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;
import com.bhgroup.shipment.trackingservice.repository.ShipmentTrackingRepository;

@ExtendWith(MockitoExtension.class)
public class TrackingServiceTest {

	@Mock
	private ShipmentTrackingRepository shipmentTrackingRepository;

	private TrackingService trackingService;

	@BeforeEach
	void setUp() {
		trackingService = new TrackingService(shipmentTrackingRepository);
	}

	@Test
	void shouldCreateTrackingFromShipmentCreatedEvent() {
		LocalDateTime createdAt = LocalDateTime.of(2026, 9, 30, 5, 30);
		ShipmentCreatedEvent event = new ShipmentCreatedEvent();

		event.setShipmentId(10302L);
		event.setCustomerId(911L);
		event.setTrackingNumber("SHP20260929000023");
		event.setStatus("CREATED");
		event.setCreatedAt(createdAt);

		trackingService.createTracking(event);

		ArgumentCaptor<ShipmentTracking> captor = ArgumentCaptor.forClass(ShipmentTracking.class);

		verify(shipmentTrackingRepository).save(captor.capture());

		verify(shipmentTrackingRepository).findByShipmentId(10302L);

		verify(shipmentTrackingRepository).save(captor.capture());

		ShipmentTracking savedTracking = captor.getValue();

		assertEquals(10302L, savedTracking.getShipmentId());
		assertEquals(911L, savedTracking.getCustomerId());
		assertEquals("SHP20260929000023", savedTracking.getTrackingNumber());
		assertEquals("CREATED", savedTracking.getStatus());
		assertEquals(createdAt, savedTracking.getCreatedAt());
	}

	@Test
	void shouldNoCreateDuplicateTracking() {

		// Arrange
		ShipmentCreatedEvent event = new ShipmentCreatedEvent();

		event.setShipmentId(10302L);
		event.setCustomerId(911L);
		event.setTrackingNumber("SHP20260929000023");
		event.setStatus("CREATED");
		event.setCreatedAt(LocalDateTime.of(2026, 10, 01, 18, 31));

		ShipmentTracking existingShipmentTracking = new ShipmentTracking();
		existingShipmentTracking.setTrackingNumber("SHP20260929000023");

		when(shipmentTrackingRepository.findByShipmentId(10302L)).thenReturn(Optional.of(existingShipmentTracking));

		// Act
		trackingService.createTracking(event);

		// Assert
		verify(shipmentTrackingRepository, never()).save(any(ShipmentTracking.class));
	}

	@Test
	void testPropgateExceptionWhenTrackingSaveFail() {
		// Arrange
		ShipmentCreatedEvent event = new ShipmentCreatedEvent();

		event.setShipmentId(10302L);
		event.setCustomerId(911L);
		event.setTrackingNumber("SHP20260929000023");
		event.setStatus("CREATED");
		event.setCreatedAt(LocalDateTime.of(2026, 10, 01, 18, 31));

		when(shipmentTrackingRepository.findByShipmentId(10302L)).thenReturn(Optional.empty());

		DataAccessException databaseException = new DataAccessException("Database unavailable") {};

		when(shipmentTrackingRepository.save(any(ShipmentTracking.class))).thenThrow(databaseException);

		assertThrows(DataAccessException.class, () -> trackingService.createTracking(event));

	}

}
