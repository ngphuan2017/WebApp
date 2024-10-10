package com.annp.utils;

import com.annp.dto.GeoIPDto;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GeoIPUtil {

    public static GeoIPDto getGeoIP(String ip) {
        String apiUrl = "http://ip-api.com/json/" + ip + "?fields=status,country,regionName,isp,query";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Đọc kết quả trả về
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Phân tích JSON bằng Gson
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            String status = jsonResponse.get("status").getAsString();

            if ("success".equals(status)) {
                String country = jsonResponse.has("country") ? jsonResponse.get("country").getAsString() : "";
                String region = jsonResponse.has("regionName") ? jsonResponse.get("regionName").getAsString() : "";
                String isp = jsonResponse.has("isp") ? jsonResponse.get("isp").getAsString() : "";

                String location = "";

                if (!region.isEmpty()) {
                    location += region + " - ";
                }
                if (!country.isEmpty()) {
                    location += country;
                }

                // Tạo đối tượng GeoIP
                GeoIPDto geoIP = new GeoIPDto(location, isp);

                return geoIP;
            } else {
                return new GeoIPDto("Unknown Location", "Unknown ISP");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new GeoIPDto("Unknown Location", "Unknown ISP");
        }
    }
}
