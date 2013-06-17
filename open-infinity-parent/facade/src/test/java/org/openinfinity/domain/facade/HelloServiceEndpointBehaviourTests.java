package org.openinfinity.domain.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openinfinity.domain.entity.HelloName;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HelloServiceEndpointBehaviourTests {
	
	private RestTemplate restTemplate;
	private static URI RESOURCE_URI;
	
	private static final String BASE_URL = "http://localhost:8080/domainservices/services/helloservice/";
	
	private static Long RESOURCE_ID = null;
	
	@Before
	public void setup() {
		restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new MappingJacksonHttpMessageConverter()); 
		restTemplate.setMessageConverters(converters);
	}
	
	@Test
	public void givenHelloInformationWhenCreatingNewHelloThenResourceIdentifierMustBeExposedForPublic(){
		HelloName expected = createHelloName();
		String location = BASE_URL + "create";
		RESOURCE_URI = restTemplate.postForLocation(location, expected);
		HelloName actual = restTemplate.getForObject(RESOURCE_URI, HelloName.class);
		RESOURCE_ID = actual.getId();
		assertEquals(expected.getName(), actual.getName());
		assertNotNull(actual.getId());	
		assertNotNull(RESOURCE_URI);		
	}
		
	@Test
	public void givenIdentifierWhenCallingResourceThenResourceMetadataMustBeReturned(){
		HelloName helloName = restTemplate.getForEntity(RESOURCE_URI, HelloName.class).getBody();
		assertNotNull(helloName);
	}
	
	
	@Test @Ignore
	public void givenResourceIdentifierWhichDoesNotExistWhenCallingResourceThenLocalizedExceptionMessageMustBeThrown(){
		try {
			URI resource = new URI(BASE_URL + "load/" + 12345);
			ExceptionMessage exceptionMessage = restTemplate.getForObject(resource, ExceptionMessage.class);
			Collection<String> exceptionDetails = exceptionMessage.getInformativesDetails();
			assertEquals(1, exceptionDetails.size());
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
	
	private HelloName createHelloName() {
		HelloName expected = new HelloName();
		expected.setName("testname");
		return expected;
	}
	
	@After
	public void tearDown() {
		restTemplate = null;
	}
	
}
