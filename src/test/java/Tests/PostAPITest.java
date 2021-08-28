package Tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.TestBase;
import client.RestClient;
import data.Users;

public class PostAPITest extends TestBase{
	
	TestBase testBase;
	String mainURL;
	String apiURL;
	String URL;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		mainURL = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");

		URL = mainURL + apiURL;
	}
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		restClient = new RestClient();

		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//Jackson API:
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");
		
		//Obj to Json file:
		mapper.writeValue(new File("C:\\Users\\prem.rathore\\eclipse-workspace\\HTTPClient\\src\\main\\java\\data\\users.json"), users);
		
		// Obj to Json in String
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		
		closeableHttpResponse = restClient.post(URL, usersJsonString, headerMap);
		
		// Status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
		
		// Json String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is-->"+responseJson);
		
		// Json to Java Obj:
		Users usersResObj = mapper.readValue(responseString, Users.class);
		System.out.println(usersResObj);
		
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
		
		
		
		
	}
	

}
