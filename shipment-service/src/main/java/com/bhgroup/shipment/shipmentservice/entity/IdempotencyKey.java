package com.bhgroup.shipment.shipmentservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name="IDEMPOTENCYKEY",
		uniqueConstraints = {
				@UniqueConstraint
				               (columnNames = "IDEMPOTENCY_KEY",
				               name="UK_IDEMPOTENCY_KEY"
				               )
				            }
			 )
@Entity
public class IdempotencyKey {

	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="IDEMPOTENCY_KEY" ,unique = true, nullable = false)
	private String idempotencyKey;
	
	@Column(name="CREATED_AT" , nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name="REQUEST_HASH" , nullable = false)
	private String requestHash;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipment_id", nullable = false)
	private Shipment shipment;
	
}
