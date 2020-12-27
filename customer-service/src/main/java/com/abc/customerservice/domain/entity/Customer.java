package com.abc.customerservice.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "customers")
@EqualsAndHashCode(callSuper=false)
public class Customer implements Serializable {
    private String id;
    private String username;
    private String password;
}
