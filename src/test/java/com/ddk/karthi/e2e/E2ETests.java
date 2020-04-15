package com.ddk.karthi.e2e;

import com.google.gson.Gson;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class E2ETests {
    /*
     ** Initialize HttpClient and HttpAsyncClient
     */
    @BeforeClass(alwaysRun = true)
    public void setup() {
        httpClient = HttpClients.createDefault();
        baseUri = Config.INSTANCE.getBaseUri();
        try {
            testHealth();
        } catch (Exception e) {
            Assert.fail("Health Check Failed - Check if server is running");
        }
    }

    /*
     ** Close HttpClient and HttpAsyncClient
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        try {
            httpClient.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Assert.fail(e.getLocalizedMessage(), e);
        }
    }

    public void testHealth() throws Exception {
        Map<String, Object> response = new Hashtable<String, Object>();
        ClientMethods.doGet(httpClient, baseUri + "health", response);
        Assert.assertEquals(response.get("responseCode"), 200);
        Assert.assertEquals(response.get("responseBody"), "OK");
    }

    @Test(groups = "Functional", testName = "Get Version should return 1.0")
    public void testGetVersion() {
        try {
            Map<String, Object> response = new Hashtable<String, Object>();
            ClientMethods.doGet(httpClient, baseUri + "version", response);
            Assert.assertEquals(response.get("responseCode"), 200);
            Assert.assertEquals(response.get("responseBody"), "1.0");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Assert.fail(e.getLocalizedMessage(), e);
        }
    }

    @DataProvider(name = "testFractions")
    public Iterator<Object[]> dataTestFractions() {
        List<Object[]> data = new ArrayList<Object[]>();
        data.add(new Object[]{ "1/4", "3/4", "1", null });
        data.add(new Object[]{ "3/2", "6/4", "3", null });
        data.add(new Object[]{ "6/4", "3/2", "3", null });
        data.add(new Object[]{ "2/7", "3/14", "1/2", null });
        data.add(new Object[]{ "1/13", "1/3", "16/39", null });
        data.add(new Object[]{ "2/7", "4/14", "4/7", null });
        data.add(new Object[]{ "7/2", "4/7", "57/14", null });
        data.add(new Object[]{ "4/7", "8/14", "8/7", null });
        data.add(new Object[]{ "2", "1/4", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "1/4", "3", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "5/0", "5/0", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });

        return data.iterator();
    }

    @Test(groups = "Functional", testName = "Add Two Fractions {0} + {1} = {2} with {3} exception", dataProvider = "testFractions")
    public void testFractions(String f1, String f2, String result, Class expectedException) {
        try {
            Map<String, Object> response = new Hashtable<String, Object>();
            Map<String, String> post = new Hashtable<>();
            post.put("f1", f1);
            post.put("f2", f2);
            Gson gson = new Gson();
            String postBody = gson.toJson(post);
            LOGGER.log(Level.INFO, postBody);
            ClientMethods.doPost(httpClient, baseUri + "addTwoFractions", postBody, response);
            Assert.assertEquals(response.get("responseCode"), 200);
            Assert.assertEquals(response.get("responseBody"), result);
        } catch (Exception e) {
            if ( expectedException != null ) {
                Assert.assertEquals(e.getClass(), expectedException );
                Assert.assertEquals(e.getLocalizedMessage(), result);
            } else {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
                Assert.fail(e.getLocalizedMessage(), e);
            }
        }
    }

    @DataProvider(name = "testAddLinkedList")
    public Iterator<Object[]> dataTestAddLinkedList() {
        List<Object[]> data = new ArrayList<Object[]>();
        data.add(new Object[]{ "899", "901", "1800", null });
        data.add(new Object[]{ "00001", "2", "3", null });
        data.add(new Object[]{ "1", "999", "1000", null });
        data.add(new Object[]{ "0", "0", "0", null });
        data.add(new Object[]{ "890", "098", "988", null });
        data.add(new Object[]{ "111", "111", "222", null });
        data.add(new Object[]{ "999", "001", "1000", null });
        data.add(new Object[]{ "9999", "9999", "19998", null });
        data.add(new Object[]{ "-1", "-2", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class }); // It's a bug
        data.add(new Object[]{ "1", "1", "2", null });
        data.add(new Object[]{ "", "", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "1", "", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "", "1", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "-1", "", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "0", "10", "10", null });
        data.add(new Object[]{ "2147483646", "1", "2147483647", null });
        data.add(new Object[]{ "2147483647", "1", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });
        data.add(new Object[]{ "2147483648", "1", "status code: 500, reason phrase: Response Message:: Server Error", HttpResponseException.class });

        return data.iterator();
    }

    @Test(groups = "Functional", testName = "Add Two Fractions {0} + {1} = {2} with {3} exception", dataProvider = "testAddLinkedList")
    public void testAddLinkedList(String f1, String f2, String result, Class expectedException) {
        try {
            Map<String, Object> response = new Hashtable<String, Object>();
            Map<String, String> post = new Hashtable<>();
            post.put("num1", f1);
            post.put("num2", f2);
            Gson gson = new Gson();
            String postBody = gson.toJson(post);
            LOGGER.log(Level.INFO, postBody);
            ClientMethods.doPost(httpClient, baseUri + "addtwolinkedlist", postBody, response);
            Assert.assertEquals(response.get("responseCode"), 200);
            Assert.assertEquals(response.get("responseBody"), result);
        } catch (Exception e) {
            if ( expectedException != null ) {
                Assert.assertEquals(e.getClass(), expectedException );
                Assert.assertEquals(e.getLocalizedMessage(), result);
            } else {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
                Assert.fail(e.getLocalizedMessage(), e);
            }
        }
    }

    @DataProvider(name = "testValidateCreditCard")
    public Iterator<Object[]> dataValidateCreditCard() {
        List<Object[]> data = new ArrayList<Object[]>();
        data.add(new Object[]{"374245455400126", true, null });
        data.add(new Object[]{"6011000991300009", true, null });
        data.add(new Object[]{"60115564485789458", false, null });
        data.add(new Object[]{"5425233430109903", true, null });
        data.add(new Object[]{"4263982640269299", true, null });
        data.add(new Object[]{"3263982640269299", false, null });
        data.add(new Object[]{"174245455400126", false, null });
        data.add(new Object[]{"100", false, null });
        data.add(new Object[]{"4111111111111112", false, null });

        return data.iterator();
    }

    @Test(groups = "Functional", testName = "Add Two Fractions {0} + {1} = {2} with {3} exception", dataProvider = "testValidateCreditCard")
    public void testValidateCreditCard(String creditCardStr, Boolean result, Class expectedException) {
        try {
            Map<String, Object> response = new Hashtable<String, Object>();
            Map<String, String> post = new Hashtable<>();
            post.put("creditCardStr", creditCardStr);
            Gson gson = new Gson();
            String postBody = gson.toJson(post);
            LOGGER.log(Level.INFO, postBody);
            ClientMethods.doPost(httpClient, baseUri + "validateCreditCard", postBody, response);
            Assert.assertEquals(response.get("responseCode"), 200);
            Assert.assertEquals(Boolean.valueOf(response.get("responseBody").toString()), result);
        } catch (Exception e) {
            if ( expectedException != null ) {
                Assert.assertEquals(e.getClass(), expectedException);
                Assert.assertEquals(e.getLocalizedMessage(), "status code: 500, reason phrase: Response Message:: Server Error");
            } else {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
                Assert.fail(e.getLocalizedMessage(), e);
            }
        }
    }

    @DataProvider(name = "testSortGrades")
    public Iterator<Object[]> dataSortGrades() {
        List<Object[]> data = new ArrayList<Object[]>();
        data.add(new Object[]{Arrays.asList("F", "D-", "D", "D+"), Arrays.asList("D+", "D", "D-", "F"), null });
        data.add(new Object[]{Arrays.asList("A", "A-", "A+", "B+"), Arrays.asList("A+", "A", "A-", "B+"), null });
        data.add(new Object[]{Arrays.asList("E", "E-", "E+", "G+"), new ArrayList<>(), null });
        data.add(new Object[]{null, new ArrayList<>(), null });

        return data.iterator();
    }

    @Test(groups = "Functional", testName = "Add Two Fractions {0} + {1} = {2} with {3} exception", dataProvider = "testSortGrades")
    public void testSortGrades(List<String> unsortedList, List<String> sortedList, Class expectedException) {
        try {
            Map<String, Object> response = new Hashtable<String, Object>();
            Map<String, List<String>> post = new HashMap<>();
            post.put("unsortedGrades", unsortedList);
            Gson gson = new Gson();
            String postBody = gson.toJson(post);
            String expected = gson.toJson(sortedList);
            LOGGER.log(Level.INFO, postBody);
            ClientMethods.doPost(httpClient, baseUri + "sortGrades", postBody, response);
            Assert.assertEquals(response.get("responseCode"), 200);
            Assert.assertEquals(response.get("responseBody"), expected);
        } catch (Exception e) {
            if ( expectedException != null ) {
                Assert.assertEquals(e.getClass(), expectedException);
                Assert.assertEquals(e.getLocalizedMessage(), "status code: 500, reason phrase: Response Message:: Server Error");
            } else {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
                Assert.fail(e.getLocalizedMessage(), e);
            }
        }
    }

    static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    CloseableHttpClient httpClient;
    String baseUri;
}
