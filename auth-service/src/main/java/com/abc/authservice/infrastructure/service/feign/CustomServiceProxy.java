package com.abc.authservice.infrastructure.service.feign;

import com.abc.authservice.domain.dto.CustomerProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "http://localhost:8082",name = "customer-service")
@Service
public interface CustomServiceProxy {
    @RequestMapping(value = "/customer-service/findByUsername/{username}")
    CustomerProjection findByUsername(@PathVariable("username") String username);
}
