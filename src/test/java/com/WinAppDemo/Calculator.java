package com.WinAppDemo;

import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.windows.WindowsDriver;


public class Calculator extends BaseClass{
    public final String AUMID = "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App";
    @BeforeClass
    public void setup() throws MalformedURLException {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("ms:experimental-webdriver", true);
        capability.setCapability("app",AUMID);
        capability.setCapability(CapabilityType.PLATFORM_NAME, "Windows");
        capability.setCapability(CapabilityType.VERSION, "10");

        WAPDriver = new WindowsDriver<WindowsElement>(new URL("http://localhost:4723/"), capability);
        WAPDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }

    @Test
    public void launchCalculator(){
        WindowsElement one = WAPDriver.findElementByName("One");
        one.click();
        WindowsElement result = WAPDriver.findElementByAccessibilityId("CalculatorResults");
        System.out.println("No Test Data: "+result.getText());
        System.out.println("No Test Data - Result"+result.getText().replaceAll("[^0-9]", ""));
    }

}
