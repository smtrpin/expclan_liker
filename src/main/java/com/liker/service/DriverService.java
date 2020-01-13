package com.liker.service;

import org.openqa.selenium.WebDriver;

public interface DriverService {

    DriverService setHost(String hostName);
    DriverService setUserAgent(String userAgent);
    DriverService setJavaScriptEnable(Boolean javaScriptEnable);
    WebDriver build();
}