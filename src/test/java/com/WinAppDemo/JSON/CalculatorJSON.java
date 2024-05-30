package com.WinAppDemo.JSON;

import com.WinAppDemo.BaseClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorJSON extends BaseClass {
    public void readJson(){
        try {
            String filePath = "src\\test\\java\\com\\WinAppDemo\\JSON\\TestData.json";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            platformVersion = jsonNode.get("platformVersion").asText();
            platformName = jsonNode.get("platformName").asText();
            WAPPath = jsonNode.get("WAPPath").asText();
            WAPUrl = jsonNode.get("WAPUrl").asText();
            AUMID = jsonNode.get("AUMID").asText();

        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @BeforeClass
    public void setup() throws MalformedURLException {
        readJson();
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("ms:experimental-webdriver", true);
        capability.setCapability("app",AUMID);
        capability.setCapability(CapabilityType.PLATFORM_NAME, platformName);
        capability.setCapability(CapabilityType.VERSION, platformVersion);
        WAPDriver = new WindowsDriver<WindowsElement>(new URL(WAPUrl), capability);
        WAPDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }

    @Test
    public void launchCalculator(){
        WindowsElement one = WAPDriver.findElementByName("One");
        one.click();
        WindowsElement result = WAPDriver.findElementByAccessibilityId("CalculatorResults");
        System.out.println("JSON: "+result.getText());
        System.out.println("JSON - Result."+result.getText().replaceAll("[^0-9]", ""));


    }
}
