package com.bhgroup.shipment.trackingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.oracle.OracleContainer;

import com.bhgroup.shipment.trackingservice.entity.ShipmentTracking;
import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;
import com.bhgroup.shipment.trackingservice.repository.ShipmentTrackingRepository;

@Testcontainers
@SpringBootTest(properties = {
		"spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
		"spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JacksonJsonSerializer" })
public class TrackingServiceWithKafkaIntegrationTest {
//Adding oracle test container

	@Container
	@ServiceConnection
	static OracleContainer oracle = new OracleContainer("gvenzl/oracle-free:23-slim-faststart");
	// Adding kafka test container for kafka related test case
	@Container
	@ServiceConnection
	static KafkaContainer kafka = new KafkaContainer("apache/kafka:4.2.0");

	@DynamicPropertySource
	static void kafkaProperties(DynamicPropertyRegistry registry) {
	    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	// for publishing an event
	@Autowired
	private KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate;

	@Autowired
	private ShipmentTrackingRepository shipmentTrackingRepository;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Test
	void testKafkaIntegrationTest() {
		ShipmentCreatedEvent event = new ShipmentCreatedEvent();

		event.setShipmentId(10302L);
		event.setCustomerId(911L);
		event.setTrackingNumber("SSHP20260819000004-test");
		event.setStatus("CREATED");
		event.setCreatedAt(LocalDateTime.of(2026, 10, 4, 10, 30));

		kafkaTemplate.send("shipment-created", event.getShipmentId().toString(), event);

		ConsumerFactory<String, ShipmentCreatedEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(
				Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
						ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group", ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
						"earliest", ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
						ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class),
				new StringDeserializer(), new JacksonJsonDeserializer<>(ShipmentCreatedEvent.class));

//temprorary testing for local and test and dev env. but not in prod env.
		
		/*try (Consumer<String, ShipmentCreatedEvent> consumer = consumerFactory.createConsumer()) {

			consumer.subscribe(List.of("shipment-created"));

			ConsumerRecords<String, ShipmentCreatedEvent> records = consumer.poll(Duration.ofSeconds(10));

			assertFalse(records.isEmpty());
			assertFalse(records.isEmpty());

			ConsumerRecord<String, ShipmentCreatedEvent> record = records.iterator().next();

			ShipmentCreatedEvent receivedEvent = record.value();

			assertEquals(10302L, receivedEvent.getShipmentId());
			assertEquals(911L, receivedEvent.getCustomerId());
			assertEquals("SSHP20260819000004-test", receivedEvent.getTrackingNumber());
			assertEquals("CREATED", receivedEvent.getStatus());
			assertEquals(LocalDateTime.of(2026, 10, 4, 10, 30), receivedEvent.getCreatedAt());

			await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {

				Optional<ShipmentTracking> tracking = shipmentTrackingRepository.findByShipmentId(10302L);

				assertTrue(tracking.isPresent());
			});
		}*/
		
		await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {

			Optional<ShipmentTracking> tracking = shipmentTrackingRepository.findByShipmentId(10302L);

			assertTrue(tracking.isPresent());
			
			ShipmentTracking savedTracking = tracking.get();

	        assertEquals(10302L, savedTracking.getShipmentId());
	        assertEquals(911L, savedTracking.getCustomerId());
	        assertEquals(
	                "SSHP20260819000004-test",
	                savedTracking.getTrackingNumber()
	        );
	        assertEquals("CREATED", savedTracking.getStatus());
	        assertEquals(
	                LocalDateTime.of(2026, 10, 4, 10, 30),
	                savedTracking.getCreatedAt()
	        );

		});
	}

// If the same ShipmentCreatedEvent is delivered twice by Kafka, the Tracking Service should not create two tracking records.
//	That is important because Kafka consumers can receive/reprocess messages.
	@Test
	void shouldNotCreateDuplicateTrackingForSameShipmentEvent() throws Exception {

	    ShipmentCreatedEvent event = new ShipmentCreatedEvent();
	    event.setShipmentId(10303L);
	    event.setCustomerId(912L);
	    event.setTrackingNumber("SHP20260819000025");
	    event.setStatus("CREATED");
	    event.setCreatedAt(LocalDateTime.of(2026, 10, 5, 10, 30));

	    // Send the same event twice
	    kafkaTemplate.send("shipment-created",event.getShipmentId().toString(),event).get();

	    kafkaTemplate.send("shipment-created",event.getShipmentId().toString(),event).get();

	    // Wait until the first event is processed
	    await().atMost(Duration.ofSeconds(10))
	            .untilAsserted(() -> {
	                Optional<ShipmentTracking> tracking =
	                        shipmentTrackingRepository
	                                .findByShipmentId(10303L);

	                assertTrue(tracking.isPresent());

	                ShipmentTracking savedTracking = tracking.get();

	                assertEquals(10303L, savedTracking.getShipmentId());
	                assertEquals(912L, savedTracking.getCustomerId());
	                assertEquals(
	                        "SHP20260819000025",
	                        savedTracking.getTrackingNumber()
	                );
	                assertEquals("CREATED", savedTracking.getStatus());
	            });

	    // Verify that only ONE tracking record exists
	    long count = shipmentTrackingRepository.count();

	    assertEquals(1L,
	            shipmentTrackingRepository
	                    .findAll()
	                    .stream()
	                    .filter(t -> t.getShipmentId().equals(10303L))
	                    .count()
	                    
	    );
	}
	
}
