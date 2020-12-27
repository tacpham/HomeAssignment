package com.thirdparty.voucher.web.rest;

import com.thirdparty.voucher.service.VoucherService;
import com.thirdparty.voucher.domain.dto.Voucher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class VoucherRest {

    private VoucherService voucherService;
    public VoucherRest(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @GetMapping(value = "/getVoucher")
    public ResponseEntity<Voucher> getVoucher() {
        return ResponseEntity.ok().body(voucherService.getVoucherCode());
    }
}
