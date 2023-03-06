package com.qa.gemini.stepDefinition;

import com.qa.gemini.commonUtils.Common_Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.gemini.generic.utils.GemJarLambda;


public class Step_Definition {
    int status;

    @Given("^Set endpoint and method \"(.*)\" and \"(.*)\"$")
    public void hitApiWithStep(String url, String method) throws Exception {
        status = Common_Utils.HitAPI(url, method, "Test for " + method.toUpperCase()).getStatus();
    }

    @Then("Verify Status code {int}")
    public void check_status_code(int Expected) {
        Common_Utils.VerifyStatusCode(Expected, status);
    }

    @Given("^Authenticate endpoint and method \"(.*)\" and \"(.*)\"$")
    public void hitApiWithoutStep(String url, String method) throws Exception {
        status = Common_Utils.HitAPI(url, method).getStatus();
    }

    @Given("^Set endpoint and method and SampleName \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void Login(String url, String method, String SampleName) throws Exception {
        status = Common_Utils.LoginUser(url, method, SampleName, "Login user").getStatus();
    }

    @Given("^Set credentials endpoint and method and SampleName \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void Loginwrong(String url, String method, String SampleName) throws Exception {
        status = Common_Utils.LoginUser(url, method, SampleName).getStatus();
    }

    @Given("^Set Empty token endpoint and method \"(.*)\" and \"(.*)\"$")
    public void hitApiTokenEmpty(String url, String method) throws Exception {
        status = Common_Utils.HitAPIWithToken(url, method).getStatus();
    }

    @Given("^Set Suite-API endpoint and method and SampleName \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void Suite_Api(String url, String method, String SampleName) throws Exception {
        status = Common_Utils.CheckAPiWithAuth(url, method, Common_Utils.GetBridgeToken(), SampleName).getStatus(); //returns bridge token
    }

    @Given("^Update Suite using endpoint and method and SampleName \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void UpdateSuite(String url, String method, String SampleName) throws Exception {
        status = Common_Utils.requestSuiteApi(url, method, Common_Utils.GetBridgeToken(), SampleName, "GEMPYP_TEST_PROD_63700467-4D93-46AB-A46E-727B2E85DC3F").getStatus();
    }

    @Given("^Update Suite when S-run-id not present using endpoint and method and SampleName \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void UpdateSuite2(String url, String method, String SampleName) throws Exception {
        status = Common_Utils.requestSuiteApi(url, method, Common_Utils.GetBridgeToken(), SampleName, "b2f779e7-a4f2-44d8-a557-b3426ea520c14").getStatus();
    }

    @Given("^Update Suite using wrong Authentication using endpoint and method and SampleName \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void UpdateSuite3(String url, String method, String SampleName) throws Exception {
        status = Common_Utils.requestSuiteApi(url, method, Common_Utils.GetBridgeToken() + "maulick", SampleName, "b2f779e7-a4f2-44d8-a557-b3426ea520c14").getStatus();
    }
}
