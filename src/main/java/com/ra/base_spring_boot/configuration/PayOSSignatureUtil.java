package com.ra.base_spring_boot.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;

public class PayOSSignatureUtil {

    public static boolean verify(
            JsonNode data,
            String signature,
            String checksumKey
    ) {
        try {
            // 1️⃣ Lấy tất cả field
            Iterator<String> fieldNames = data.fieldNames();
            List<String> keys = new ArrayList<>();
            fieldNames.forEachRemaining(keys::add);

            // 2️⃣ Sort alphabet
            Collections.sort(keys);

            // 3️⃣ Build canonical string
            StringBuilder rawData = new StringBuilder();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                JsonNode valueNode = data.get(key);

                String value = valueNode == null || valueNode.isNull()
                        ? ""
                        : valueNode.asText();

                rawData.append(key).append('=').append(value);
                if (i < keys.size() - 1) {
                    rawData.append('&');
                }
            }

            // 4️⃣ HMAC SHA256
            String calculatedSignature = new HmacUtils("HmacSHA256", checksumKey)
                    .hmacHex(rawData.toString());

            // DEBUG nếu cần
            // System.out.println("RAW DATA = " + rawData);
            // System.out.println("SIGNATURE = " + calculatedSignature);

            return calculatedSignature.equals(signature);

        } catch (Exception e) {
            return false;
        }
    }
}

