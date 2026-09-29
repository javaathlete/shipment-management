package com.bhgroup.shipment.shipmentservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhgroup.shipment.shipmentservice.entity.IdempotencyKey;


public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey, Long>{

	Optional<IdempotencyKey> findByIdempotencyKey(String idempotenntKey);
}
