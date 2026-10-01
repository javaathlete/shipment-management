package com.bhgroup.shipment.trackingservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SHIPMENT_TRACKING")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ShipmentTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_tracking_seq")
    @SequenceGenerator(
            name = "shipment_tracking_seq",
            sequenceName = "SHIPMENT_TRACKING_SEQ",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "SHIPMENT_ID", nullable = false, unique = true)
    private Long shipmentId;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "TRACKING_NUMBER", nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}