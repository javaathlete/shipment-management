package com.bhgroup.shipment.shipmentservice.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.bhgroup.shipment.shipmentservice.dto.CreateShipmentRequest;

public class RequestHashUtil {

	 private RequestHashUtil() {
	    }

	    public static String generateHash(CreateShipmentRequest request) {

	        String requestData =
	                String.valueOf(request.getCustomerId())
	                + "|" + request.getSenderName()
	                + "|" + request.getSenderPhone()
	                + "|" + request.getReceiverName()
	                + "|" + request.getReceiverPhone()
	                + "|" + request.getPickupAddress()
	                + "|" + request.getDeliveryAddress()
	                + "|" + String.valueOf(request.getPackageWeight())
	                + "|" + String.valueOf(request.getPackageDescription());

	        try {
	            MessageDigest messageDigest =
	                    MessageDigest.getInstance("SHA-256");

	            byte[] hashBytes =
	                    messageDigest.digest(
	                            requestData.getBytes(StandardCharsets.UTF_8));

	            StringBuilder hash = new StringBuilder();

	            for (byte b : hashBytes) {
	                hash.append(String.format("%02x", b));
	            }

	            return hash.toString();

	        } catch (NoSuchAlgorithmException exception) {
	            throw new IllegalStateException(
	                    "SHA-256 algorithm is not available",
	                    exception);
	        }
	    }
}