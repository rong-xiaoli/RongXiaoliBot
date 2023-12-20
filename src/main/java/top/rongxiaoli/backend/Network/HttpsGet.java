package top.rongxiaoli.backend.Network;

import top.rongxiaoli.backend.Log;
import top.rongxiaoli.backend.Network.HttpsTrustManager.TrustAnyHostnameVerifier;
import top.rongxiaoli.backend.Network.HttpsTrustManager.TrustAnyTrustManager;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class HttpsGet {
    public String targetUrl;
    public Param Par = new Param();
    public StringBuilder Output = new StringBuilder();
    /**
     * A hashmap storing the headers of the request.
     */
    private HashMap<String, String> headers = new HashMap<>();

    /**
     * This function is to put a header on the request.
     *
     * @param key   Variable name.
     * @param value Variable value.
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getFinalURL() {
        return targetUrl + Par.BuildGet();
    }

    /**
     * Https GET request.
     *
     * @return Output string.
     */
    public String GET(String PluginName) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection conn = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                    new java.security.SecureRandom());
            URL console = new URL(getFinalURL());
            conn = (HttpsURLConnection) console.openConnection();
            // This is for adding headers.
            if (headers.isEmpty()) {
                headers.put("Application", "application/json");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            }
            for (String key :
                    headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    StandardCharsets.UTF_8));
            String ret = "";
            while (ret != null) {
                ret = br.readLine();
                if (ret != null && !ret.trim().equals("")) {
                    Output.append(ret);
                }
            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException IOE) {
            Log.Exception(IOE,
                    null,
                    Log.LogClass.Network,
                    PluginName);
            throw IOE;
        } catch (Exception e) {
            Log.Exception(e,
                    "Unknown reason. ",
                    Log.LogClass.Network,
                    PluginName);
            throw e;
        } finally {
            conn.disconnect();
        }
        return Output.toString();
    }
}