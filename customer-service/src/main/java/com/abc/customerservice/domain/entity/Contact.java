package com.abc.customerservice.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "contact")
@EqualsAndHashCode(callSuper=false)
public class Contact implements Serializable {
    private String id;
    private String customerId;
    private String phoneNumber;
}
