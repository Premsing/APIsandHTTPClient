package Tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.TestBase;
import client.RestClient;
import util.TestUtil;

public class GetAPITest extends TestBase {

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

	@Test(priority=1)
	public void getTestWithoutHeaders() throws ClientProtocolException, IOException {

		restClient = new RestClient();
		closeableHttpResponse = restClient.get(URL);
		
		// 1. Status Code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code-->" + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// 2. Json String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response Json from GET API-->" + responseJson);
		
		// Single value validation:
		String perPageValue = TestUtil.getValueByJPath(responseJson, "per_page");
		System.out.println("Per Page value is-->"+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		String totalPagesValue = TestUtil.getValueByJPath(responseJson, "total_pages");
		System.out.println("Total Pages value is-->"+totalPagesValue);
		Assert.assertEquals(Integer.parseInt(totalPagesValue), 2);
		
		// Json array validation
		String firstName = TestUtil.getValueByJPath(responseJson, "data[0]/first_name");
		System.out.println("First Name is-->"+firstName);
		Assert.assertEquals(firstName, "George");
		
		String LastName = TestUtil.getValueByJPath(responseJson, "data[0]/last_name");
		System.out.println("Last Name is-->"+LastName);
		Assert.assertEquals(LastName, "Bluth");
		
		String email = TestUtil.getValueByJPath(responseJson, "data[0]/email");
		System.out.println("Email is-->"+email);
		Assert.assertEquals(email, "george.bluth@reqres.in");
		
		String avatar = TestUtil.getValueByJPath(responseJson, "data[0]/avatar");
		System.out.println("Avatar value is-->"+avatar);
		Assert.assertEquals(avatar, "https://reqres.in/img/faces/1-image.jpg");

		// 3. All Headers:
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}

		System.out.println("Headers Array: " + allHeaders);
	}
	
	@Test(priority=2)
	public void getTestWithHeaders() throws ClientProtocolException, IOException {

		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		closeableHttpResponse = restClient.get(URL, headerMap);
		
		// 1. Status Code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code-->" + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// 2. Json String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response Json from GET API-->" + responseJson);
		
		// Single value validation:
		String perPageValue = TestUtil.getValueByJPath(responseJson, "per_page");
		System.out.println("Per Page value is-->"+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		String totalPagesValue = TestUtil.getValueByJPath(responseJson, "total_pages");
		System.out.println("Total Pages value is-->"+totalPagesValue);
		Assert.assertEquals(Integer.parseInt(totalPagesValue), 2);
		
		// Json array validation
		String firstName = TestUtil.getValueByJPath(responseJson, "data[0]/first_name");
		System.out.println("First Name is-->"+firstName);
		Assert.assertEquals(firstName, "George");
		
		String LastName = TestUtil.getValueByJPath(responseJson, "data[0]/last_name");
		System.out.println("Last Name is-->"+LastName);
		Assert.assertEquals(LastName, "Bluth");
		
		String email = TestUtil.getValueByJPath(responseJson, "data[0]/email");
		System.out.println("Email is-->"+email);
		Assert.assertEquals(email, "george.bluth@reqres.in");
		
		String avatar = TestUtil.getValueByJPath(responseJson, "data[0]/avatar");
		System.out.println("Avatar value is-->"+avatar);
		Assert.assertEquals(avatar, "https://reqres.in/img/faces/1-image.jpg");

		// 3. All Headers:
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}

		System.out.println("Headers Array: " + allHeaders);
	}

}
