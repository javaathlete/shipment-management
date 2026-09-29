package com.bhgroup.shipment.trackingservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhgroup.shipment.trackingservice.entity.ShipmentTracking;

public interface ShipmentTrackingRepository
extends JpaRepository<ShipmentTracking, Long> {

Optional<ShipmentTracking> findByShipmentId(Long shipmentId);

Optional<ShipmentTracking> findByTrackingNumber(String trackingNumber);
}
