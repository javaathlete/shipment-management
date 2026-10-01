package com.bhgroup.shipment.trackingservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bhgroup.shipment.trackingservice.entity.ShipmentTracking;
import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;
import com.bhgroup.shipment.trackingservice.repository.ShipmentTrackingRepository;

@Service
public class TrackingService {

	private final ShipmentTrackingRepository shipmentTrackingRepository;

	public TrackingService(ShipmentTrackingRepository shipmentTrackingRepository) {
		this.shipmentTrackingRepository = shipmentTrackingRepository;
	}

	public void createTracking(ShipmentCreatedEvent createdEvent) {
		
		Optional<ShipmentTracking> existingTracking = shipmentTrackingRepository.findByShipmentId(createdEvent.getShipmentId());
		
		if(existingTracking.isPresent()) {
			return;
		}
		
		
		ShipmentTracking shipmentTracking = mapToEntity(createdEvent);
		System.out.println(shipmentTracking.toString());
		shipmentTrackingRepository.save(shipmentTracking);

	}

	private ShipmentTracking mapToEntity(ShipmentCreatedEvent createdEvent) {
		ShipmentTracking shipmentTracking = new ShipmentTracking();
		
		shipmentTracking.setCustomerId(createdEvent.getCustomerId());
		shipmentTracking.setShipmentId(createdEvent.getShipmentId());
		shipmentTracking.setTrackingNumber(createdEvent.getTrackingNumber());
		shipmentTracking.setStatus(createdEvent.getStatus());
		shipmentTracking.setCreatedAt(createdEvent.getCreatedAt());
		System.out.println(createdEvent.toString());

		return shipmentTracking;
	}
}
