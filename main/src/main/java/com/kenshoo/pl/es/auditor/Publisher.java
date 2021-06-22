package com.kenshoo.pl.es.auditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenshoo.pl.entity.audit.AuditRecord;
import com.kenshoo.pl.entity.spi.audit.AuditRecordPublisher;

import java.awt.image.DataBuffer;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Publisher implements AuditRecordPublisher {

    private final ElasticSearchConfiguration elasticSearchConfiguration;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Publisher(ElasticSearchConfiguration configuration) {
        this.elasticSearchConfiguration = configuration;

    }

    @Override
    public void publish(Stream<? extends AuditRecord> auditRecords) {
        auditRecords.forEach(record -> {
            sendAuditRecord(record, null, null);
            sendRecord(record.getChildRecords(), record.getEntityId(), record.getEntityType());
        });

    }

    private void sendRecord(Collection<? extends AuditRecord> auditRecords, String parentEntityId, String parentEntityType) {
        auditRecords.forEach(record -> {
            sendAuditRecord(record, parentEntityId, parentEntityType);
            sendRecord(record.getChildRecords(), record.getEntityId(), record.getEntityType());
        });

    }

    private void sendAuditRecord(AuditRecord auditRecord, String parentEntityId, String parentEntityType) {
        try {
            final URL url = new URL(this.elasticSearchConfiguration.getUrl() + "/_doc/");
            final HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            final String jsonRecord = mapper.writeValueAsString(createRequest(auditRecord, parentEntityId, parentEntityType));
            final OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(jsonRecord);
            out.close();
            httpCon.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AuditRecordDto createRequest(AuditRecord auditRecord, String parentEntityId, String parentEntityType) {
        return new AuditRecordDto.Builder()
                .withParentEntityId(parentEntityId)
                .withParentEntityType(parentEntityType)
                .withAuditRecord(auditRecord)
                .withTimestamp(new Timestamp(new Date().getTime()))
                .build();
    }
}

