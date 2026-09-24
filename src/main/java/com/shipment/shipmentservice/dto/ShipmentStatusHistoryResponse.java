package com.shipment.shipmentservice.dto;

import java.time.LocalDateTime;

import com.shipment.shipmentservice.entity.ShipmentStatus;

public class ShipmentStatusHistoryResponse {

	private ShipmentStatus oldStatus;
	private ShipmentStatus newStatus;
	private LocalDateTime changedAt;

	public ShipmentStatus getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(ShipmentStatus oldStatus) {
		this.oldStatus = oldStatus;
	}

	public ShipmentStatus getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(ShipmentStatus newStatus) {
		this.newStatus = newStatus;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		this.changedAt = changedAt;
	}

}
