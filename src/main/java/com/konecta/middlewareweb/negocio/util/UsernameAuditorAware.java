package com.konecta.middlewareweb.negocio.util;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class UsernameAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		 return Optional.ofNullable("anonymous");
	}

}
