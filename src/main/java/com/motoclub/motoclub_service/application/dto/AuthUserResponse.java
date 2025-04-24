package com.motoclub.motoclub_service.application.dto;

import java.util.List;

public record AuthUserResponse(String username, List<String> roles) {}
