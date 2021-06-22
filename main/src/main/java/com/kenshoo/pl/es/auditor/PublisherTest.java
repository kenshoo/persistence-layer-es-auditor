package com.kenshoo.pl.es.auditor;

import com.google.common.collect.Lists;
import com.kenshoo.pl.entity.ChangeOperation;
import com.kenshoo.pl.entity.audit.AuditRecord;
import org.junit.Test;

import java.util.stream.Stream;

public class PublisherTest {

private static final ElasticSearchConfiguration configuration = new ElasticSearchConfiguration.Builder()
        .withUrl("http://localhost:9200/audit")
        .build();

    @Test
    public void test1(){
        final Publisher publisher = new Publisher(configuration);
        publisher.publish(Stream.of(new AuditRecord.Builder()
                .withOperator(ChangeOperation.CREATE)
                .withEntityId("10")
                .withEntityType("campaign")
                .withChildRecords(Lists.newArrayList(new AuditRecord.Builder()
                        .withOperator(ChangeOperation.CREATE)
                        .withEntityId("1")
                        .withEntityType("keyword")
                        .build()))
                .build(),
                new AuditRecord.Builder()
                        .withOperator(ChangeOperation.CREATE)
                        .withEntityId("20")
                        .withEntityType("campaign")
                        .build()
        ));
    }

}
