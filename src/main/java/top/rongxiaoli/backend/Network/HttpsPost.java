package top.rongxiaoli.backend.Network;

import top.rongxiaoli.backend.Log;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

public class HttpsPost {
    private HashMap<String, String> headers = new HashMap<>();
    private String targetURL;
    public Param Par = new Param();
    public StringBuilder Output = new StringBuilder();
    public HttpsPost(String PluginName) {
        HttpsURLConnection conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null,
                    new TrustManager[]{new HttpsTrustManager.TrustAnyTrustManager()},
                    new SecureRandom());
            URL url = new URL(targetURL);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            if (headers.isEmpty()) {
                headers.put("Application", "application/json");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            }
            out = new PrintWriter(conn.getOutputStream());
            out.print(Par.BuildPost());
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                Output.append(line);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.Exception(e,
                    null,
                    Log.LogClass.Network,
                    PluginName);
        } catch (KeyManagementException e) {
            Log.Exception(e,
                    "Unexpected exception.",
                    Log.LogClass.Network,
                    PluginName);
        } catch (MalformedURLException e) {
            Log.Exception(e,
                    "URL invalid.",
                    Log.LogClass.Network,
                    PluginName);
        } catch (IOException e) {
            Log.Exception(e,
                    null,
                    Log.LogClass.Network,
                    PluginName);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                conn.disconnect();
            } catch (NullPointerException e) {
                Log.WriteLog(Log.Level.Warning,
                        "Unexpected NullPointException occurred while disconnecting. ",
                        Log.LogClass.Network,
                        PluginName);
            } catch (IOException e) {
                Log.Exception(e,
                        "Unexpected IOException occurred while closing incoming BufferedReader. ",
                        Log.LogClass.Network,
                        PluginName);
            }
        }
    }
}
