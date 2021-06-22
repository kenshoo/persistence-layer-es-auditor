package com.kenshoo.pl.es.auditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kenshoo.pl.entity.ChangeOperation;
import com.kenshoo.pl.entity.FieldValue;
import com.kenshoo.pl.entity.audit.AuditRecord;
import com.kenshoo.pl.entity.audit.FieldAuditRecord;
import com.kenshoo.pl.entity.spi.audit.AuditRecordPublisher;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Stream;

class AuditRecordDto {

    private final String parentEntityType;
    private final String parentEntityId;
    private final String entityType;
    private final String entityId;
    private final Collection<? extends FieldValue> mandatoryFieldValues;
    private final ChangeOperation operator;
    private final Collection<? extends FieldAuditRecord> fieldRecords;
    private final Timestamp timestamp;

    public AuditRecordDto(String parentEntityType,
                          String parentEntityId,
                          String entityType,
                          String entityId,
                          Collection<? extends FieldValue> mandatoryFieldValues,
                          ChangeOperation operator,
                          Collection<? extends FieldAuditRecord> fieldRecords,
                          Timestamp timestamp) {
        this.parentEntityType = parentEntityType;
        this.parentEntityId = parentEntityId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.mandatoryFieldValues = mandatoryFieldValues;
        this.operator = operator;
        this.fieldRecords = fieldRecords;
        this.timestamp = timestamp;
    }

    public String getParentEntityType() {
        return parentEntityType;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public Collection<? extends FieldValue> getMandatoryFieldValues() {
        return mandatoryFieldValues;
    }

    public ChangeOperation getOperator() {
        return operator;
    }

    public Collection<? extends FieldAuditRecord> getFieldRecords() {
        return fieldRecords;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    static class Builder{
        private String parentEntityType;
        private String parentEntityId;
        private String entityType;
        private String entityId;
        private Collection<? extends FieldValue> mandatoryFieldValues;
        private ChangeOperation operator;
        private Collection<? extends FieldAuditRecord> fieldRecords;
        private Timestamp timestamp;

        public Builder withParentEntityType(String parentEntityType) {
            this.parentEntityType = parentEntityType;
            return this;
        }

        public Builder withParentEntityId(String parentEntityId) {
            this.parentEntityId = parentEntityId;
            return this;
        }

        public Builder withEntityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        public Builder withEntityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder withMandatoryFieldValues(Collection<? extends FieldValue> mandatoryFieldValues) {
            this.mandatoryFieldValues = mandatoryFieldValues;
            return this;
        }

        public Builder withOperator(ChangeOperation operator) {
            this.operator = operator;
            return this;
        }

        public Builder withFieldRecords(Collection<? extends FieldAuditRecord> fieldRecords) {
            this.fieldRecords = fieldRecords;
            return this;
        }

        public Builder withTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withAuditRecord(AuditRecord auditRecord) {
            this.entityId= auditRecord.getEntityId();
            this.entityType= auditRecord.getEntityType();
            this.operator= auditRecord.getOperator();
            this.mandatoryFieldValues= auditRecord.getMandatoryFieldValues();
            this.fieldRecords= auditRecord.getFieldRecords();
            return this;
        }

        public AuditRecordDto build(){
            return new AuditRecordDto(parentEntityType, parentEntityId, entityType, entityId, mandatoryFieldValues, operator, fieldRecords, timestamp);
        }
    }
}
