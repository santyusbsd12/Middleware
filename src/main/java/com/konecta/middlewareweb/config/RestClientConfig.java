package com.konecta.middlewareweb.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.konecta.middlewareweb.negocio.response.dto.ValkiryaDTO;

@Configuration
public class RestClientConfig {

	private static final Logger LOG = LoggerFactory.getLogger(RestClientConfig.class);


	@Value("${application.enable.proxy:false}")
	private boolean enableProxy;
	@Value("${application.selfSignedCertificate.enabled:true}")
	private boolean enableSelfSignedCertificate;

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException {
		RestTemplate restTemplate = new RestTemplate(getHttpComponentsClientHttpRequestFactory());
		restTemplate.getMessageConverters().add(createXmlHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.setErrorHandler(getResponseErrorHandler());
		return restTemplate;
	}
	
	
    public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

        final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xstreamMarshaller.setAnnotatedClasses(ValkiryaDTO.class);
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);
        
   

        return xmlConverter;
    }


	public HttpComponentsClientHttpRequestFactory getHttpComponentsClientHttpRequestFactory() throws KeyManagementException, NoSuchAlgorithmException {
		HttpClientBuilder httpClientBuilder = HttpClients.custom()
				.setSSLSocketFactory(enableSelfSignedCertificate ? getSSLFactoryAccepSelfSigned() : getSSLFactoryDefault());
		if (this.enableProxy) {
			httpClientBuilder.setProxy(new HttpHost("piwssbcn.ind.local", 8080, "http"));
		}

		return new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());

	}

	public SSLConnectionSocketFactory getSSLFactoryAccepSelfSigned() {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLConnectionSocketFactory factory = null;
		try {
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy).build();
			factory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			LOG.error(e.getMessage(), e);
		}
		return factory;
	}

	public SSLConnectionSocketFactory getSSLFactoryDefault() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext context;
		context = SSLContext.getInstance("TLSv1.2");
		context.init(null, null, null);
		return new SSLConnectionSocketFactory(context);
	}
	
	public ResponseErrorHandler getResponseErrorHandler() {
    	return new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return !response.getStatusCode().is2xxSuccessful();
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				LOG.error("Error en RestTemplate. StatusCode: {}", response.getStatusCode());
			}
    	};
    }
}
