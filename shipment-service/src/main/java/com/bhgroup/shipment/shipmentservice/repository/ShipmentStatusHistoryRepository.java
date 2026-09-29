package com.bhgroup.shipment.shipmentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhgroup.shipment.shipmentservice.entity.ShipmentStatusHistory;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long>{

	List<ShipmentStatusHistory> findByShipmentShipmentIdOrderByChangedAtAsc(Long shipmentId);
	//List<ShipmentStatusHistory>  deleteChildEntity(List<ShipmentStatusHistory> deletedEntity);
}
