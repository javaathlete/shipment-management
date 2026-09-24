package com.shipment.shipmentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shipment.shipmentservice.dto.ShipmentStatusHistoryResponse;
import com.shipment.shipmentservice.entity.ShipmentStatusHistory;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long>{

	List<ShipmentStatusHistory> findByShipmentShipmentIdOrderByChangedAtAsc(Long shipmentId);
	//List<ShipmentStatusHistory>  deleteChildEntity(List<ShipmentStatusHistory> deletedEntity);
}
