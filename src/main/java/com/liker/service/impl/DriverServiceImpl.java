package com.liker.service.impl;

import com.liker.service.DriverService;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DriverServiceImpl implements DriverService {

    private String host;
    private String userAgent;
    private Boolean javaScriptEnable = false;

    private final ResourceLoader resourceLoader;

    @Autowired
    public DriverServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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
        setCaps(caps);
        File phantomJsDriver = null;
        try {
            phantomJsDriver = File.createTempFile("phjsdriver", ".dat");
            FileUtils.copyInputStreamToFile(
                    resourceLoader.getResource("classpath:/static/phantomjs.exe").getInputStream(),
                    phantomJsDriver
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        PhantomJSDriverService service = new PhantomJSDriverService.Builder()
                .usingPort(8081)
                .usingPhantomJSExecutable(phantomJsDriver)
                .withLogFile(null)
                .build();
        return new PhantomJSDriver(service, caps);
    }

    private void setCaps(DesiredCapabilities caps) {
        caps.setBrowserName("firefox");
        caps.setPlatform(Platform.WIN10);
        caps.setJavascriptEnabled(javaScriptEnable);
        if (host != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Host", host);
        }
        if (userAgent != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
        }
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--webdriver-loglevel=NONE"});
    }
}
