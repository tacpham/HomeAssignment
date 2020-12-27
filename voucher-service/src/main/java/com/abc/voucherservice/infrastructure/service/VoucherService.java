package com.abc.voucherservice.infrastructure.service;

import org.springframework.http.ResponseEntity;

public interface VoucherService {
    ResponseEntity getVoucher(String phoneNumber);

    ResponseEntity getVouchers(String phoneNumber);
}
