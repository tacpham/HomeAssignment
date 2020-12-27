package com.abc.voucherservice.infrastructure.service.impl;

import com.abc.voucherservice.domain.dto.VoucherProjection;
import com.abc.voucherservice.domain.entity.Voucher;
import com.abc.voucherservice.infrastructure.repository.VoucherRepository;
import com.abc.voucherservice.infrastructure.service.VoucherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class VoucherServiceImpl implements VoucherService {

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    private VoucherRepository voucherRepository;
    private RestTemplate restTemplate;
    private final String uri = "http://localhost:8084/api/getVoucher/";

    public VoucherServiceImpl(VoucherRepository voucherRepository, RestTemplate restTemplate){
        this.voucherRepository = voucherRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand(fallbackMethod = "buildFallbackVoucher", commandProperties={@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")})
    public ResponseEntity getVoucher(String phoneNumber) {
        ResponseEntity<VoucherProjection> response = restTemplate.exchange(uri, HttpMethod.GET, null, VoucherProjection.class);
        if (HttpStatus.OK.equals(response.getStatusCode())){
            VoucherProjection voucher = response.getBody();
            voucher.setPhoneNumber(phoneNumber);
            return ResponseEntity.ok(voucher);
        } else {
            VoucherProjection voucher = new VoucherProjection();
            voucher.setPhoneNumber(phoneNumber);
            voucher.setCode("The request is being processed within 30 seconds.");
            saveDelayVoucher(phoneNumber);
            return ResponseEntity.ok(voucher);
        }
    }

    private void saveDelayVoucher(String phoneNumber) {
        Runnable task = () ->{
            ResponseEntity<VoucherProjection> delayResponse = restTemplate.exchange(uri, HttpMethod.GET, null, VoucherProjection.class);
            if (HttpStatus.OK.equals(delayResponse.getStatusCode())){
                VoucherProjection delayVoucherProjection = delayResponse.getBody();
                Voucher delayVoucher = new Voucher();
                delayVoucher.setId(UUID.randomUUID().toString());
                delayVoucher.setCode(delayVoucherProjection.getCode());
                delayVoucher.setPhoneNumber(phoneNumber);
                log.info(String.format("Save voucher %s for phone number %s", delayVoucherProjection.getCode(), phoneNumber));
                voucherRepository.save(delayVoucher);
            }
        };
        executor.execute(task);
    }

    public ResponseEntity buildFallbackVoucher(String phoneNumber) {
        VoucherProjection voucher = new VoucherProjection();
        voucher.setPhoneNumber(phoneNumber);
        voucher.setCode("The request is being processed within 30 seconds.");
        saveDelayVoucher(phoneNumber);
        return ResponseEntity.ok(voucher);
    }

    @Override
    public ResponseEntity getVouchers(String phoneNumber) {
        List<VoucherProjection> vouchers = voucherRepository.findAllBy(phoneNumber);
        return ResponseEntity.ok(vouchers);
    }
}
