package com.flipkart.daily.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditLogger {
    private static final Logger logger = LoggerFactory.getLogger("auditLogger");

    public static void log(AuditLogEntry entry) {
        try {
            logger.info(entry.toString());
        } catch (Exception e) {
            System.err.println("Audit logging failed: " + e.getMessage());
        }
    }
}

