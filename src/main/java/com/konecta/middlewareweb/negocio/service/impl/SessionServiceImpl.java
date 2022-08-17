package com.konecta.middlewareweb.negocio.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFunction;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.stub.SessionsStubSettings;
import com.konecta.middlewareweb.model.ServiceAccount;
import com.konecta.middlewareweb.negocio.service.SessionService;

import io.grpc.HttpConnectProxiedSocketAddress;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ProxiedSocketAddress;
import io.grpc.ProxyDetector;

@Service
public class SessionServiceImpl implements SessionService {
	
	@Value("${enable.dialogflowclient.proxy:false}")
	private Boolean useProxy;

	@SuppressWarnings("rawtypes")
	@Cacheable(value = "sesionCache", key = "#id", condition = "#id != null")
	public SessionsClient getSessionClient(ServiceAccount serviceAccount) throws IOException {
		TransportChannelProvider transportChannelProvider ;
		
		
		if (useProxy) {
			transportChannelProvider = SessionsStubSettings.defaultGrpcTransportProviderBuilder()
					.setChannelConfigurator(new ApiFunction<ManagedChannelBuilder, ManagedChannelBuilder>() {
						@Override
						public ManagedChannelBuilder apply(ManagedChannelBuilder managedChannelBuilder) {
							return managedChannelBuilder.proxyDetector(new ProxyDetector() {
								@Nullable
								@Override
								public ProxiedSocketAddress proxyFor(SocketAddress socketAddress) {
									return HttpConnectProxiedSocketAddress.newBuilder()
											.setProxyAddress(new InetSocketAddress("piwss.ind.local", 8080))
											.setTargetAddress((InetSocketAddress) socketAddress).build();
								}
							});
						}
					}).build();

		} else {
			transportChannelProvider = SessionsStubSettings.defaultGrpcTransportProviderBuilder().build();
		}
		
		
		
		InputStream is = new ByteArrayInputStream(serviceAccount.getJson().getBytes());
		SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
				.setTransportChannelProvider(transportChannelProvider)
				.setCredentialsProvider(FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(is)))
				.build();
		return SessionsClient.create(sessionsSettings);
	}

	
}
