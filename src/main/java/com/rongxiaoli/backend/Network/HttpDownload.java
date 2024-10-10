package com.rongxiaoli.backend.Network;

import com.rongxiaoli.backend.Log;

import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class HttpDownload {

    /**
     * Target url (including file name);
     */
    public String targetUrl;
    public String localFilePath;
    public String localFileName;
    public URL urlHandle;
    private boolean isSuccess = false;

    public boolean Status() {
        return isSuccess;
    }

    public void Download(String PluginName) throws IllegalArgumentException, IOException {
        try {
            urlHandle = new URL(targetUrl);
            File file = new File(localFilePath, localFileName);
            file.getParentFile().mkdirs();
            ReadableByteChannel rbc = Channels.newChannel(urlHandle.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            FileChannel fc = fos.getChannel();
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.flush();
            Log.WriteLog(Log.Level.Debug,
                    "File downloaded: " + localFilePath + localFileName,
                    Log.LogClass.File,
                    PluginName);
            fos.close();
            isSuccess = true;
        } catch (MalformedURLException MUE) {
            Log.Exception(MUE, "URL incorrect. ", Log.LogClass.Network, PluginName);
            throw MUE;
        } catch (FileNotFoundException FNFE) {
            Log.Exception(FNFE, "Server file not found. ", Log.LogClass.Network, PluginName);
            throw FNFE;
        } catch (IllegalArgumentException IAE) {
            Log.Exception(IAE, "Illegal parameters. ", Log.LogClass.Network, PluginName);
            throw IAE;
        } catch (ClosedChannelException CCE) {
            Log.Exception(CCE, "Channel closed. ", Log.LogClass.Network, PluginName);
            throw CCE;
        } catch (SSLHandshakeException SSLHE) {
            Log.Exception(SSLHE, "SSL Handshake error. ", Log.LogClass.Network, PluginName);
            throw SSLHE;
        } catch (IOException IOE) {
            Log.Exception(IOE, "IOException occurred. ", Log.LogClass.Network, PluginName);
            throw IOE;
        }
    }
}
