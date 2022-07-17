package com.rongxiaoli.backend.Network;

import com.rongxiaoli.backend.Log;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.*;

public class HttpDownload {

    private boolean isSuccess = false;

    /**
     * Target url (including file name);
     */
    public String targetUrl;
    public String localFilePath;
    public String localFileName;

    public URL urlHandle;
    public boolean Status() {
        return isSuccess;
    }

    public void Download(String PluginName) throws IllegalArgumentException, IOException {
        try {
            urlHandle = new URL(targetUrl);
            File file = new File(localFilePath , localFileName);
            file.getParentFile().mkdirs();
            ReadableByteChannel rbc = Channels.newChannel(urlHandle.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            FileChannel fc = fos.getChannel();
            fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);
            fos.flush();
            Log.WriteLog(Log.Level.Debug,
                    "File downloaded: "+ localFilePath + localFileName,
                    Log.Module.File,
                    PluginName);
            fos.close();
            isSuccess = true;
        } catch (MalformedURLException MUE) {
            Log.Exception(MUE, "URL incorrect. ", Log.Module.Network, PluginName);
            throw MUE;
        } catch (FileNotFoundException FNFE) {
            Log.Exception(FNFE, "Server file not found. ", Log.Module.Network, PluginName);
            throw FNFE;
        } catch (IllegalArgumentException IAE) {
            Log.Exception(IAE, "Illegal parameters. ", Log.Module.Network, PluginName);
            throw IAE;
        } catch (ClosedChannelException CCE) {
            Log.Exception(CCE, "Channel closed. ", Log.Module.Network, PluginName);
            throw CCE;
        } catch (IOException IOE) {
            Log.Exception(IOE, "IOException occurred. ", Log.Module.Network, PluginName);
            throw IOE;
        }
    }
}
