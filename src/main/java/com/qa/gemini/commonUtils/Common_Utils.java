package com.qa.gemini.commonUtils;

import com.gemini.generic.api.utils.*;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.utils.GemJarGlobalVar;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Common_Utils {

    public static Response HitAPI(String UrlNameFromConfig, String method, String step) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = ProjectConfigData.getProperty(UrlNameFromConfig);
            GemTestReporter.addTestStep("Url of the test case", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!step.isEmpty()) {
                request.setStep(step);
            }
            response = ApiInvocation.handleRequest(request);
//          GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        }
        return response;
    }

    public static Response HitAPI(String UrlNameFromConfig, String method) {
        return HitAPI(UrlNameFromConfig, method, "");
    }

    public static void VerifyStatusCode(int expected, int actual) {
        if (expected == actual) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status :" + expected + ",<br>Actual :" + actual, STATUS.PASS);
        } else {
            GemTestReporter.addTestStep("Status Verification", "Expected Status :" + expected + ",<br>Actual :" + actual, STATUS.FAIL);
        }
    }

    public static Response LoginUser(String UrlNameFromConfig, String method, String sampleName, String step) throws Exception {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = ProjectConfigData.getProperty(UrlNameFromConfig);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!step.isEmpty()) {
                request.setStep(step);
            }
            String payload = ProjectSampleJson.getSampleDataString(sampleName);
            request.setRequestPayload(payload);
            response = ApiInvocation.handleRequest(request);
//            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        }
        return response;
    }

    public static Response LoginUser(String UrlNameFromConfig, String method, String sampleName) throws Exception {
        return LoginUser(UrlNameFromConfig, method, sampleName, "");
    }

    public static Response HitAPIWithToken(String UrlNameFromConfig, String method, String step, Map<String, String> headers) throws Exception {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = ProjectConfigData.getProperty(UrlNameFromConfig);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!headers.isEmpty()) {
                request.setHeaders(headers);
            }
            if (!step.isEmpty()) {
                request.setStep(step);
            }
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            if (step.isEmpty() && !(response.getErrorMessage().isEmpty())) {
                GemTestReporter.addTestStep("Message", response.getErrorMessage(), STATUS.INFO);
            }
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        }
        return response;
    }

    public static Response HitAPIWithToken(String UrlNameFromConfig, String method) throws Exception {
        return HitAPIWithToken(UrlNameFromConfig, method, "", new HashMap<>());
    }

    public static Response CheckAPiWithAuth(String UrlNameFromConfig, String method, String bt, String sampleName) throws Exception {
        Response response = new Response();
        try {
            GemTestReporter.addTestStep("Test Case", "Test using " + method.toUpperCase() + "API  ", STATUS.INFO);
            String url = ProjectConfigData.getProperty(UrlNameFromConfig);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            String payloads = ProjectSampleJson.getSampleDataString(sampleName);
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));
            GemTestReporter.addTestStep("Request Body","Request Body : " +String.valueOf(payload), STATUS.INFO);
            String username = ProjectConfigData.getProperty("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);
//            GemTestReporter.addTestStep("username", "username : " + username, STATUS.INFO);
//            GemTestReporter.addTestStep("bridgeToken", "bridgeToken: " + bt, STATUS.INFO);
            response = new Response();
            Request request = new Request();
            request.setURL(url);
            request.setMethod(method);
            if (!bt.isEmpty()) {
                request.setHeaders(headers);
            }
            request.setRequestPayload(payload);
            response = ApiInvocation.handleRequest(request);
//            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        }
        return response;
    }

    public static String GetBridgeToken(){
        return GlobalVariables.Bridge_Token;
    }

    public static Response requestSuiteApi(String UrlNameFromConfig, String method, String bt, String sampleName, String srunid) throws Exception {
        Response response = new Response();
        try {
//            GemTestReporter.addTestStep("Test Case", "Test to Update the suite using Put API", STATUS.INFO);
            String url = ProjectConfigData.getProperty(UrlNameFromConfig);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            String payloads = ProjectSampleJson.getSampleDataString(sampleName);
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);
            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod") && (!srunid.isEmpty())) {
                pay.addProperty("s_run_id", srunid);
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay)); //converts to json obj.
            GemTestReporter.addTestStep("Request Body","Request Body : "+ String.valueOf(payload), STATUS.INFO);
            String username = ProjectConfigData.getProperty("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);
//            GemTestReporter.addTestStep("username", "username : " + username, STATUS.INFO);
//            GemTestReporter.addTestStep("bridgeToken", "bridgeToken: " + bt, STATUS.INFO);
            response = new Response();
            Request request = new Request();
            request.setURL(url);
            request.setMethod(method);
            if (!bt.isEmpty()) {
                request.setHeaders(headers);
            }
            request.setRequestPayload(payload);
            response = ApiInvocation.handleRequest(request);
//            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        }
        return response;
    }


}
