package com.konecta.middlewareweb.negocio.util;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Utils {
	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
	
	private Utils() {
		throw new UnsupportedOperationException("This class is an utility class");
	}
	
	
	public static boolean isMoreThanMinutesAgo(Date date,int minutes) {
		if (date == null) {
			return true;
		}
		
		Date now = new Date();
		return now.getTime() - date.getTime() >= minutes*60*1000;
	}
	
	
	public static LinkedMultiValueMap<String, Object> toMapFormData(Object... args) {
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		if (args.length > 1 && args.length % 2 == 0) {
			for (int i = 0; i < args.length; i = i + 2)
				if(args[i + 1] != null)
					map.add(String.valueOf(args[i]), args[i + 1]);
		}
		return map;

	}
	
	public static Document stringToDocument(String xml) {

		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
	        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
	        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
	        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	        factory.setXIncludeAware(false);
	        factory.setExpandEntityReferences(false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader strReader = new StringReader(xml);
			InputSource is = new InputSource(strReader);
			doc = builder.parse(is);
		} catch (Exception e) {
			LOG.error("Error parseando contenido XML");
		}
	
		return doc;

	}
	
	public static Map<String, Object> toMapString(Object... args) {
		Map<String, Object> map = new HashMap<>();
		if (args.length > 1 && args.length % 2 == 0) {
			for (int i = 0; i < args.length; i = i + 2)
				if(args[i + 1] != null)
					map.put(String.valueOf(args[i]), String.valueOf(args[i + 1]));
		}
		return map;

	}
	
	public static String generateTempCode(String format) {
		SimpleDateFormat oSDF = new SimpleDateFormat(format);
		return oSDF.format(new Date());
	}
	
	 
	

}
