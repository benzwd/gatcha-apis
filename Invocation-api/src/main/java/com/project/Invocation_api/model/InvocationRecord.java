package com.project.Invocation_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "invocation_records")
public class InvocationRecord {
    @Id
    private String id;
    private String username;
    private String baseMonsterId;
    private String generatedMonsterId;
    private LocalDateTime invocationTime;

}
