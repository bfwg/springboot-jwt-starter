package com.bfwg.config;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.stereotype.Component;

/**
 * Created by fanjin on 2017-10-07.
 */
@Component
public class DeviceDummy implements Device {
    private boolean normal;
    private boolean mobile;
    private boolean tablet;

    @Override
    public boolean isNormal() {
        return normal;
    }

    @Override
    public boolean isMobile() {
        return mobile;
    }

    @Override
    public boolean isTablet() {
        return tablet;
    }

    @Override
    public DevicePlatform getDevicePlatform() {
        return null;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public void setTablet(boolean tablet) {
        this.tablet = tablet;
    }
}
