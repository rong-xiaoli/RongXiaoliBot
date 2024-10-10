package com.rongxiaoli.backend.Network;

import com.rongxiaoli.backend.Log;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
        return targetUrl + Par.Build();
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

    public static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    public static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }

    public static class Param {
        public StringBuilder Par;

        public Param() {
            this.Par = new StringBuilder();
        }

        /**
         * Append a param to params.
         *
         * @param name  Param name.
         * @param value Param value.
         */
        public void Append(String name, String value) {
            if (Par.length() == 0) {
                Par.append('?').append(name).append('=').append(value);
            } else {
                Par.append('&').append(name).append('=').append(value);
            }
        }

        public void Append(String name, String value, boolean unicodeEncodeMode) {
            String finalValue = value;
            if (unicodeEncodeMode) {
                byte[] ParByte = value.getBytes(StandardCharsets.UTF_8);
                StringBuilder encodedParByte = new StringBuilder();
                for (byte singleByte :
                        ParByte) {
                    encodedParByte.append("%").append((Integer.toHexString((singleByte & 0x000000ff) | 0xffffff00)).substring(6));
                }
                finalValue = encodedParByte.toString();
            }
            Append(name, finalValue);
        }

        /**
         * Get params.
         *
         * @return The param added to the end of Url.
         */
        public String Build() {
            if (Par == null) {
                return "";
            }
            return Par.toString();
        }
    }
}