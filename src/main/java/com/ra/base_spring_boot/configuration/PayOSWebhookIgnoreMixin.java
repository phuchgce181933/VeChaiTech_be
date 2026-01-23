package com.ra.base_spring_boot.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"transactionDateTime"})
public class PayOSWebhookIgnoreMixin {
}
