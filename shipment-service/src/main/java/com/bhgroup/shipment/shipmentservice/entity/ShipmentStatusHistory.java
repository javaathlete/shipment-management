package com.bhgroup.shipment.shipmentservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SHIPMENT_STATUS_HISTORY")

@Setter
@Getter
public class ShipmentStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long historyId;

	@Enumerated(EnumType.STRING)
	@Column(name = "OLD_STATUS")
	private ShipmentStatus oldStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "NEW_STATUS", nullable = false)
	private ShipmentStatus newStatus;

	@Column(name = "CHANGED_AT", nullable = false)
	private LocalDateTime changedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPMENT_ID", nullable = false)
	private Shipment shipment;

}
