Feature: Test_API

  Scenario Outline: Get Company API
    Given Set endpoint and method "<endpoint>" and "<Method>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint    | Method | Expected_status |
      | Get_Company | get    | 200             |

  Scenario Outline: Login User
    Given Set endpoint and method and SampleName "<endpoint>" and "<Method>" and "<SampleName>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint | Method | Expected_status | SampleName       |
      | Login    | Post   | 200             | Login_sampleJson |


  Scenario Outline: Update the suite using Put API (suitexe case)
    Given Update Suite using endpoint and method and SampleName "<endpoint>" and "<Method>" and "<SampleName>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint   | Method | Expected_status | SampleName       |
      | Post_Suite | put    | 200             | put_1_sampleJson |

  Scenario Outline: Insert Test-Suite Using Post APIs
    Given Set Suite-API endpoint and method and SampleName "<endpoint>" and "<Method>" and "<SampleName>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint   | Method | Expected_status | SampleName        |
      | Post_Suite | Post   | 201             | psuite_sampleJson |


  Scenario Outline: Get data of suite s-run id not present
    Given Authenticate endpoint and method "<endpoint>" and "<Method>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint  | Method | Expected_status |
      | Get_Suite | get    | 400             |

  Scenario Outline: Get data of suite s-run id not present
    Given Authenticate endpoint and method "<endpoint>" and "<Method>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint  | Method | Expected_status |
      | Get_Suite | get    | 400             |

  Scenario Outline: Login User with wrong credentials
    Given Set credentials endpoint and method and SampleName "<endpoint>" and "<Method>" and "<SampleName>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint | Method | Expected_status | SampleName          |
      | Login    | Post   | 400             | loginInvalid_sample |

  Scenario Outline: Change Token When Header is not Given
    Given Set Empty token endpoint and method "<endpoint>" and "<Method>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint   | Method | Expected_status |
      | post_token | Post   | 403             |

  Scenario Outline: Update the suite When S-run-id is not present in Database
    Given Update Suite when S-run-id not present using endpoint and method and SampleName "<endpoint>" and "<Method>" and "<SampleName>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint   | Method | Expected_status | SampleName       |
      | Post_Suite | put    | 400             | put_2_sampleJson |

  Scenario Outline: Update the suite Using Wrong Authentication
    Given Update Suite using wrong Authentication using endpoint and method and SampleName "<endpoint>" and "<Method>" and "<SampleName>"
    Then Verify Status code <Expected_status>
    Examples:
      | endpoint   | Method | Expected_status | SampleName       |
      | Post_Suite | put    | 403             | put_2_sampleJson |
