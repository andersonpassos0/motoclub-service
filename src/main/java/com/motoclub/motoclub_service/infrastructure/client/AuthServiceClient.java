package com.motoclub.motoclub_service.infrastructure.client;

import com.motoclub.motoclub_service.dto.AuthUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "${auth-service.url}")
public interface AuthServiceClient {

    @GetMapping("/auth/validate")
    ResponseEntity<AuthUserResponse> validateToken(@RequestHeader("Authorization") String token);
}
