package com.annp.utils;

import ua_parser.Client;
import ua_parser.OS;
import ua_parser.Parser;
import ua_parser.UserAgent;

public class UserAgentUtil {

    public static String getDeviceInfo(String userAgentString) {
        Parser uaParser = new Parser();
        Client client = uaParser.parse(userAgentString);

        UserAgent userAgent = client.userAgent;
        OS os = client.os;

        String browser = userAgent.family != null ? userAgent.family : "Unknown Browser";
        String browserVersion = userAgent.major != null ? userAgent.major : "Unknown Version";
        String osName = os.family != null ? os.family : "Unknown OS";

        String deviceInfo = browser + " " + browserVersion + " on " + osName;

        return deviceInfo;
    }
}
