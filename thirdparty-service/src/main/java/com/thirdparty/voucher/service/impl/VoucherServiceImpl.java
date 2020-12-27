package com.thirdparty.voucher.service.impl;

import com.thirdparty.voucher.generator.CodeConfig;
import com.thirdparty.voucher.service.VoucherService;
import com.thirdparty.voucher.generator.VoucherCodes;
import com.thirdparty.voucher.domain.dto.Voucher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class VoucherServiceImpl implements VoucherService {
    private static final Random random = new Random(System.currentTimeMillis());
    private int min =30;
    private int max =120;
    private CodeConfig defaultConfig = CodeConfig.length(10);
    private VoucherCodes VoucherCodes = new VoucherCodes();

    @Override
    public Voucher getVoucherCode() {
        try {
            // just try to presented wait time 3-120 seconds
            int waitTime = getRandomWaitTime();
            Thread.sleep(waitTime * 1000);
            log.info(String.format("Wait time: %d seconds", waitTime));
        } catch (InterruptedException e) {
            log.error(String.format("Exception when try to create wait time: %s", e.getMessage()));
        }
        Voucher voucher = new Voucher();
        voucher.setCode(VoucherCodes.generate(defaultConfig));
        return voucher;
    }
    public int getRandomWaitTime() {
        return random.nextInt(max - min) + min;
    }
}
