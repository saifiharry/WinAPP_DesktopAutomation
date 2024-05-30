package com.WinAppDemo;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BaseClass {
    public static String platformVersion = null;
    public static String platformName = null;
    public static String WAPPath = null;
    public static String WAPUrl = null;
    public static String AUMID = null;
    public WindowsDriver<WindowsElement> WAPDriver = null;

    @BeforeSuite
    public void start() {

        File WAP_Server_PATH = new File("C:\\Program Files\\Windows Application Driver\\WinAppDriver.exe");
        Desktop desktop = Desktop.getDesktop();
        try {
            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                return;
            }

            if (WAP_Server_PATH.exists()) {
                desktop.open(WAP_Server_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void teardown(){
        if (WAPDriver != null)
        {
            WAPDriver.quit();
        }
    }

    @AfterSuite
    public static void stop() {
        try
        {
            ProcessBuilder processBuilder =new ProcessBuilder("taskkill ","/f","/IM","WinAppDriver.exe");
            processBuilder.start();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
