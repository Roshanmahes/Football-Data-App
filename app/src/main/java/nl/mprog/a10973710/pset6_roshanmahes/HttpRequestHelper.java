package nl.mprog.a10973710.pset6_roshanmahes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HttpRequestHelper {

    static synchronized String downloadFromServer(String link) {
        String result = "";
        URL url = null;
        HttpURLConnection connect;

        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url != null) {
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Integer responseCode = connect.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    BufferedReader bReader =
                            new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;
                    while ((line = bReader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
