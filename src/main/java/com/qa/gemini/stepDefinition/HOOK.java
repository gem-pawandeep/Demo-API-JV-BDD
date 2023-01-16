package com.qa.gemini.stepDefinition;

import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.ProjectSampleJson;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qa.gemini.commonUtils.GlobalVariables;
import io.cucumber.java.BeforeAll;

import java.util.HashMap;
import java.util.Map;

public class HOOK {
    @BeforeAll(order = 1000)
    public static void before_all() throws Exception {
        String urlss = ProjectConfigData.getProperty("Login");
        String payload = ProjectSampleJson.getSampleDataString("Login_sampleJson");
        Request request = new Request();
        request.setURL(urlss);
        request.setMethod("Post");
        request.setRequestPayload(payload);
        Response response = ApiInvocation.handleRequest(request);
        String Body = response.getResponseBody();
        JsonParser parser = new JsonParser();
        JsonObject Boddy = (JsonObject) parser.parse(Body);
        JsonObject to = (JsonObject) Boddy.get("data");
        GlobalVariables.Bearer_Token = String.valueOf(to.get("token"));
        String url = ProjectConfigData.getProperty("Get_token");
        String j = GlobalVariables.Bearer_Token;
        Response res;
        Map<String, String> headers = new HashMap<>();
        assert j != null;
        String jnew = j.replaceAll("^\"|\"$", "");
        headers.put("Authorization", "Bearer " + jnew);
        request = new Request();
        request.setURL(url);
        request.setMethod("get");
        request.setHeaders(headers);
        res = ApiInvocation.handleRequest(request);
        assert res != null;
        int status = res.getStatus();
        if (status == 200) {
            String bodi = res.getResponseBody();
            parser = new JsonParser();
            JsonObject body = (JsonObject) parser.parse(bodi);
            JsonObject data = body.get("data").getAsJsonObject();
            GlobalVariables.Bridge_Token = data.get("bridgeToken").getAsString();
            System.out.println(GlobalVariables.Bridge_Token);
            System.out.println(GlobalVariables.Bearer_Token);
        }
    }
}
