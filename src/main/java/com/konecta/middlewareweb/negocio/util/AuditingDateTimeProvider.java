package com.konecta.middlewareweb.negocio.util;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;

public class AuditingDateTimeProvider implements DateTimeProvider{
	@Override
    public Optional<TemporalAccessor> getNow() {
    	return Optional.of(LocalDateTime.now());
   	}
}
