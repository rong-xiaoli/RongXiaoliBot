package com.rongxiaoli.backend.Network;

import com.rongxiaoli.backend.Log;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Basic network helper.
 */
public class HttpGet {
    /**
     * URL instance.
     */
    public URL urlHandle;
    /**
     * HttpUrlConnection instance.
     */
    public HttpURLConnection httpConn;
    /**
     * InputStream instance.
     */
    public InputStream inStream;
    /**
     * BufferedReader instance.
     */
    public BufferedReader bufferedReader;
    /**
     * Url to be connected.
     */
    public String targetUrl;
    /**
     * Http param.
     */
    public Param Par = new Param();
    /**
     * Is connect response code 200.
     */
    public boolean isSuccess = false;
    public boolean isDisconnected = true;
    /**
     * Output.
     */
    public StringBuilder Output = new StringBuilder();
    /**
     * A hashmap storing the headers of the request.
     */
    private HashMap<String, String> headers = new HashMap<>();
    private String FinalURL;

    /**
     * Get final URL. Used for debug.
     *
     * @return Final URL.
     */
    public String getFinalURL() {
        return FinalURL;
    }

    /**
     * This function is to put a header on the request.
     *
     * @param key   Variable name.
     * @param value Variable value.
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Encode the Url.
     *
     * @param source Source Url.
     * @return Encoded Url.
     * @throws UnsupportedEncodingException Throws when encoding is not supported.
     */
    private String encode(String source) throws UnsupportedEncodingException {
        String zhPattern = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(zhPattern);
        Matcher m = p.matcher(source);
        StringBuffer b = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(b, URLEncoder.encode(m.group(0), "UTF-8"));
        }
        m.appendTail(b);
        return b.toString();
    }

    /**
     * Send a GET request.
     *
     * @param PluginName Plugin name.
     * @return Respond content.
     * @throws SocketTimeoutException       Thrown if connection times out.
     * @throws UnsupportedEncodingException Thrown if encoding fails.
     * @throws FileNotFoundException        Thrown if Server 404.
     * @throws IOException                  Thrown because of lots of reasons.
     */
    public String GET(String PluginName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String FinalUrl = targetUrl + Par.Build();
        FinalURL = FinalUrl;
        try {
            urlHandle = new URL(encode(FinalUrl));
            httpConn = (HttpURLConnection) urlHandle.openConnection();
            httpConn.setConnectTimeout(300000);
            httpConn.setReadTimeout(1000000);
            // This is for adding headers.
            if (headers.isEmpty()) {
                headers.put("Application", "application/json");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            }
            for (String key :
                    headers.keySet()) {
                httpConn.setRequestProperty(key, headers.get(key));
            }
            isDisconnected = false;
            httpConn.connect();
            if (httpConn.getResponseCode() == 200) {
                isSuccess = true;
                inStream = httpConn.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Output.append(line);
                    Log.WriteLog(Log.Level.Verbose,
                            "Received data: " + "\n" +
                                    line,
                            Log.LogClass.Network,
                            PluginName);
                }
            } else {
                Log.WriteLog(Log.Level.Warning,
                        "Unexpected code received: " + httpConn.getResponseCode(),
                        Log.LogClass.Network,
                        PluginName);
            }
        } catch (MalformedURLException MUE) {
            Log.Exception(MUE, "URL incorrect. URL: " + FinalUrl, Log.LogClass.Network, PluginName);
            throw MUE;
        } catch (FileNotFoundException FNFE) {
            Log.Exception(FNFE, "Server file not found. ", Log.LogClass.Network, PluginName);
            throw FNFE;
        } catch (UnsupportedEncodingException UEE) {
            Log.Exception(UEE, "Unsupported encoding. ", Log.LogClass.Network, PluginName);
            throw UEE;
        } catch (IOException IOE) {
            Log.Exception(IOE, "IOException. ", Log.LogClass.Network, PluginName);
            throw IOE;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException IOE) {
                Log.Exception(IOE, "Unexpected IO operation after close. ", Log.LogClass.Network, PluginName);
            }
            httpConn.disconnect();
            isDisconnected = true;
        }
        return Output.toString();
    }

    /**
     * Param class. Call Build() to get params.
     */
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
    }
}
