package com.abc.voucherservice.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vouchers")
@EqualsAndHashCode(callSuper=false)
public class Voucher {
    private String id;
    private String phoneNumber;
    private String code;
}
