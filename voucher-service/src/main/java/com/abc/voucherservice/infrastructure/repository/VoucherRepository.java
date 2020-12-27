package com.abc.voucherservice.infrastructure.repository;

import com.abc.voucherservice.domain.dto.VoucherProjection;
import com.abc.voucherservice.domain.entity.Voucher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends CrudRepository<Voucher, String> {
    List<VoucherProjection> findAllBy(String phoneNumber);
}
