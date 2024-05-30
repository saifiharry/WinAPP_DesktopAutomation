package com.WinAppDemo.XML;

import com.WinAppDemo.BaseClass;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorXML extends BaseClass {

    public void readXML(){
        String filePath = "src\\test\\java\\com\\WinAppDemo\\XML\\TestData.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();
            NodeList Capabilities = document.getElementsByTagName("Capabilities");
            Node Capability = Capabilities.item(0);
            NodeList CapabilityDetails =  Capability.getChildNodes();
            for(int j = 0; j < CapabilityDetails.getLength(); j++){
                Node detail = CapabilityDetails.item(j);
                if(detail.getNodeType() == Node.ELEMENT_NODE) {
                    Element detailElement = (Element) detail;
                    System.out.println(detailElement.getTagName() + ": " + detailElement.getAttribute("value"));
                    if(detailElement.getTagName().equals("platformVersion")){
                        platformVersion = detailElement.getAttribute("value");
                    }
                    else if(detailElement.getTagName().equals("platformName")){
                        platformName = detailElement.getAttribute("value");
                    }
                    else if(detailElement.getTagName().equals("WAPPath")){
                        WAPPath = detailElement.getAttribute("value");
                    }
                    else if(detailElement.getTagName().equals("WAPUrl")){
                        WAPUrl = detailElement.getAttribute("value");
                    }
                    else if(detailElement.getTagName().equals("AUMID")){
                        AUMID = detailElement.getAttribute("value");
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public void setup() throws MalformedURLException {
        readXML();
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
        System.out.println("XML: "+result.getText());
        System.out.println("XML - Result"+result.getText().replaceAll("[^0-9]", ""));
    }
}
