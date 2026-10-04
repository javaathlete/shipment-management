package com.bhgroup.shipment.trackingservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import com.bhgroup.shipment.trackingservice.kafka.event.ShipmentCreatedEvent;


@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Bean
	public ConsumerFactory<String, ShipmentCreatedEvent> consumerFactory() {
		Map<String, Object> properties = new HashMap<>();

		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "tracking-service-group");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
		
		
		JacksonJsonDeserializer<ShipmentCreatedEvent> deserializer = new JacksonJsonDeserializer<>(
				ShipmentCreatedEvent.class);
		
		deserializer.setUseTypeHeaders(false);

		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
	}
	@Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShipmentCreatedEvent>
            kafkaListenerContainerFactory(ConsumerFactory<String, ShipmentCreatedEvent> consumerFactory,
            @Value("${spring.kafka.listener.auto-startup:true}") boolean autoStartup) 
	{

        ConcurrentKafkaListenerContainerFactory<String, ShipmentCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setAutoStartup(autoStartup);

        return factory;
    }
}
