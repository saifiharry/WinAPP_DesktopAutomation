package com.WinAppDemo.PDF;

import com.WinAppDemo.BaseClass;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorPDF extends BaseClass {
    public void readPDF() {
        try {
            PDDocument document = PDDocument.load(new File("src\\test\\java\\com\\WinAppDemo\\PDF\\TestData.pdf"));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String[] texts = pdfStripper.getText(document).split("\\r?\\n");
            document.close();
            for(int i=0; i< texts.length;i++){
                if(texts[i].contains("platformVersion")){
                    String[] text = texts[i].replaceAll(" ","").split("-");
                    platformVersion = text[1];
                }
                else if(texts[i].contains("platformName")){
                    String[] text = texts[i].replaceAll(" ","").split("-");
                    platformName = text[1];
                }
                else if(texts[i].contains("WAPPath")){
                    String[] text = texts[i].replaceAll(" ","").split("-");
                    WAPPath = text[1];
                }
                else if(texts[i].contains("WAPUrl")){
                    String[] text = texts[i].replaceAll(" ","").split("-");
                    WAPUrl = text[1];
                }
                else if(texts[i].contains("AUMID")){
                    String[] text = texts[i].replaceAll(" ","").split("-");
                    AUMID = text[1];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @BeforeClass
    public void setup() throws MalformedURLException {
        readPDF();
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
        System.out.println("PDF: "+result.getText());
        System.out.println("PDF - Result."+result.getText().replaceAll("[^0-9]", ""));
    }


}
