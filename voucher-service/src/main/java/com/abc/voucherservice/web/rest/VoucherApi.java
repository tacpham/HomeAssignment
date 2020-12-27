package com.abc.voucherservice.web.rest;

import com.abc.voucherservice.infrastructure.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherApi {

    private VoucherService voucherService;

    public VoucherApi(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @GetMapping(value = { "/getVoucher/{phoneNumber}"})
    public ResponseEntity getVoucher(@PathVariable String phoneNumber){
        return ResponseEntity.ok().body(voucherService.getVoucher(phoneNumber));
    }

    @GetMapping(value = { "/getVouchers/{phoneNumber}"})
    public ResponseEntity getVouchers(@PathVariable String phoneNumber){
        return ResponseEntity.ok().body(voucherService.getVouchers(phoneNumber));
    }
}
