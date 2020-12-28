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
import java.util.concurrent.*;

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
        Callable<VoucherProjection> task = ()->{
            ResponseEntity<VoucherProjection> response = restTemplate.exchange(uri, HttpMethod.GET, null, VoucherProjection.class);
            VoucherProjection voucherProjection = response.getBody();
            voucherProjection.setPhoneNumber(phoneNumber);

            Voucher voucher = new Voucher();
            voucher.setId(UUID.randomUUID().toString());
            voucher.setCode(voucherProjection.getCode());
            voucher.setPhoneNumber(phoneNumber);
            log.info(String.format("Save voucher %s for phone number %s", voucher.getCode(), phoneNumber));
            voucherRepository.save(voucher);
            return voucherProjection;
        };

        Future<VoucherProjection> future = executor.submit(task);
        try {
            VoucherProjection voucherProjection = future.get(120, TimeUnit.SECONDS);
            return ResponseEntity.ok(voucherProjection);
        } catch (InterruptedException e) {
            if(!future.isDone()){
                // because it already running, then set mayInterruptIfRunning will continue the task, so we call just 1 time
                future.cancel(false);
                log.info(String.format("Continue get voucher for phone number %s", phoneNumber));
            }
        } catch (ExecutionException e) {
            log.error(String.format("Exception when get voucher for phone number %s, error: %s ", phoneNumber, e.getMessage()));
        } catch (TimeoutException e) {
            log.error(String.format("More than 120s but can't get any voucher for phone number %s", phoneNumber));
        }
        return buildFallbackVoucher(phoneNumber);
    }

    public ResponseEntity buildFallbackVoucher(String phoneNumber) {
        VoucherProjection voucher = new VoucherProjection();
        voucher.setPhoneNumber(phoneNumber);
        voucher.setCode("The request is being processed within 30 seconds.");
        return ResponseEntity.ok(voucher);
    }

    @Override
    public ResponseEntity getVouchers(String phoneNumber) {
        List<VoucherProjection> vouchers = voucherRepository.findAllBy(phoneNumber);
        return ResponseEntity.ok(vouchers);
    }
}
