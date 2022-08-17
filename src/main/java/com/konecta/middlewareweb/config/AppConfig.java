package com.konecta.middlewareweb.config;

import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitorjbl.json.JsonViewModule;
import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;

@Configuration
@EnableCaching
public class AppConfig {
	
	@Bean
	public ObjectMapper mapperJsonViewModule() {
		return new ObjectMapper().registerModule(new JsonViewModule())
				.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
	}
	
	@Bean
	public ScriptEngine engineJs() {

		Map<String, String> options = new HashMap<>();

		options.put("js.ecmascript-version", "2021");
		
		return GraalJSScriptEngine.create(null,
		        Context.newBuilder("js")
		        .allowHostAccess(HostAccess.ALL)
		        .allowHostClassLookup(s -> true)
		        .options(options));
	}
	
	@Bean
	public Invocable invocable() {
		 return (Invocable)engineJs();
	}
	
}
