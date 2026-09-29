package com.bhgroup.shipment.shipmentservice.dto;

import java.time.LocalDateTime;

import com.bhgroup.shipment.shipmentservice.entity.ShipmentStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShipmentStatusHistoryResponse {

	private ShipmentStatus oldStatus;
	private ShipmentStatus newStatus;
	private LocalDateTime changedAt;

	
}
