package com.liker.service.impl;

import com.liker.service.DriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    private String host;
    private String userAgent;
    private Boolean javaScriptEnable = false;

    @Override
    public DriverService setHost(String hostName) {
        this.host = hostName;
        return this;
    }

    @Override
    public DriverService setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public DriverService setJavaScriptEnable(Boolean javaScriptEnable) {
        this.javaScriptEnable = javaScriptEnable;
        return this;
    }

    @Override
    public WebDriver build() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("firefox");
        caps.setPlatform(Platform.WIN10);
        caps.setJavascriptEnabled(javaScriptEnable);
        if (host != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Host", host);
        }
        if (userAgent != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
        }
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs.exe");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
        return new PhantomJSDriver(caps);
    }
}
