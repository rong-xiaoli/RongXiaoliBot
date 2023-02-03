package com.rongxiaoli.backend.Network;
import com.rongxiaoli.backend.Log;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
public class HttpsGet {
    public static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
    public static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }
    public String targetUrl;
    public String getFinalURL() {
        return targetUrl + Par.Build();
    }
    public Param Par = new Param();
    public static class Param {
        public StringBuilder Par;
        /**
         * Append a param to params.
         * @param name Param name.
         * @param value Param value.
         */
        public void Append(String name,String value) {
            if (Par.length()==0){
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
                    encodedParByte.append("%").append((Integer.toHexString(( singleByte & 0x000000ff) | 0xffffff00)).substring(6));
                }
                finalValue = encodedParByte.toString();
            }
            Append(name, finalValue);
        }
        /**
         * Get params.
         * @return The param added to the end of Url.
         */
        public String Build() {
            if (Par==null){
                return "";
            }
            return Par.toString();
        }
        public Param() {
            this.Par=new StringBuilder();
        }
    }
    public StringBuilder Output = new StringBuilder();
    /**
     * Https GET request.
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
        }  catch (IOException | NoSuchAlgorithmException | KeyManagementException IOE) {
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