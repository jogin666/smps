package com.zy.smps_ui.configuration;

import javax.servlet.http.HttpServletRequest;

public class IPAddressHelper {

    public static String getIPAddress(HttpServletRequest request) {
        try {
            String remoteAddress="";
            if (request != null) {
                remoteAddress = request.getHeader("X-Forwarded-For");
                if (remoteAddress == null || "".equals(remoteAddress)) {
                    remoteAddress = request.getRemoteAddr();
                }
            }
            remoteAddress=remoteAddress!=null && remoteAddress.contains(",")? remoteAddress.split(",")[0]: remoteAddress;
            return remoteAddress;
        } catch (Exception e) {
            return null;
        }
    }
}
