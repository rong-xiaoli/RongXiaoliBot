package com.rongxiaoli.module.setu;

import com.alibaba.fastjson2.JSON;
import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.Network.HttpDownload;
import com.rongxiaoli.backend.Network.HttpGet;
import com.rongxiaoli.backend.Network.HttpsGet;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;

import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.nio.channels.ClosedChannelException;
import java.security.KeyManagementException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PicturePlugin extends Module {

    private static final boolean DebugMode = false;
    /**
     * Cooling thread.
     */
    public static CoolingThread CThread = new CoolingThread();
    /**
     * Is plugin running.
     */
    private static boolean isRunning = true;
    /**
     * Used as process lock.
     */
    private static boolean isProcessing = false;
    /**
     * Is plugin enabled.
     */
    private static boolean IsEnabled = false;
    /**
     * Plugin name.
     */
    private final String PluginName = "setu";
    /**
     * The prefix of the plugin.
     */
    private final String CommandPrefix = "setu";
    private final String PictureProxy = "i.pixiv.re";
    private final String HelpContent =
            "setu [Keyword] [Keyword] ...\n" +
                    //"暂时不能使用中文关键词\n" +
                    //"(一些特殊原因，该插件已弃用)\n" +
                    "获取一张涩图\n" +
                    "参数: \n" +
                    "Keyword: 要查询的关键字";

    /**
     * Plugin main method for groups.
     *
     * @param arrCommand     Command array.
     * @param Friend         QQID.
     * @param Group          Group ID.
     * @param SubjectContact Contact of the sender.
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact) {
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        if (message.length == 0) {
            return;
        }

        //Declare variables.

        long FriendID;
        long GroupID;
        short FreezeTime;
        short RemainingTime;

        //Start process.
        if (!Objects.equals(message[0], CommandPrefix)) {
            return;
        }
        //Judge if the plugin is enabled.
        if (!IsEnabled) {
            SubjectContact.sendMessage("当前图片插件未启用");
            return;
        }
        FriendID = Friend;
        GroupID = Group;
        Log.WriteLog(Log.Level.Verbose,
                "Received group command from: " + "\n" +
                        "Group: " + GroupID + "\n" +
                        "Member: " + FriendID + "\n" +
                        "Content: " + Arrays.toString(message),
                Log.LogClass.ModuleMain,
                PluginName);

        // Check lock.
        if (isProcessing) {
            SubjectContact.sendMessage("其他消息正在处理，请稍后");
            return;
        }
        if (FriendID != RongXiaoliBot.Owner) {
            FreezeTime = 60;
            RemainingTime = CoolingObjectList.Add(FriendID, FreezeTime);
            if (RemainingTime != -1) {
                SubjectContact.sendMessage("冷却还剩" + RemainingTime + "秒，请耐心等待");
                return;
            }
        }
        // Lock.
        isProcessing = true;
        process(message, SubjectContact, true, true);
    }

    /**
     * Plugin main method for friends.
     *
     * @param arrCommand     Command array.
     * @param Friend         QQID.
     * @param SubjectContact Contact of the sender.
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        //0-length array.
        if (message.length == 0) {
            return;
        }

        long FriendID;
        short FreezeTime;
        short RemainingTime;

        //Start process.
        if (!Objects.equals(message[0], CommandPrefix)) {
            return;
        }
        //Forcibly unlock this plugin.
        if (message.length > 1) {
            if (Friend == RongXiaoliBot.Owner && Objects.equals(message[1], "unlock")) {
                isProcessing = false;
                Log.WriteLog(Log.Level.Info,
                        "setu unlocked. ",
                        Log.LogClass.ModuleMain,
                        PluginName);
                SubjectContact.sendMessage("setu unlocked. ");
                return;
            }
        }
        //Judge if the plugin is enabled.
        if (!IsEnabled) {
            SubjectContact.sendMessage("当前图片插件未启用");
            return;
        }
        FriendID = Friend;
        Log.WriteLog(Log.Level.Verbose,
                "Received friend command from: " + "\n" +
                        "Friend: " + FriendID + "\n" +
                        "Content: " + Arrays.toString(message),
                Log.LogClass.ModuleMain,
                PluginName);


        //Version 0.1.0 add:
        //Reason: After being banned for many times, this function is banned forever for others.
        //Version 0.2.0 delete:
        //Reason: Now we don't need to worry.
//        if (FriendID != RongXiaoliBot.Owner) {
//            return;
//        }

        //Lock.
        if (isProcessing) {
            SubjectContact.sendMessage("其他消息正在处理，请稍后");
            return;
        }
        isProcessing = true;
        if (FriendID != RongXiaoliBot.Owner) {
            FreezeTime = 90;
            RemainingTime = CoolingObjectList.Add(FriendID, FreezeTime);
            if (RemainingTime != -1) {
                SubjectContact.sendMessage("冷却还剩" + RemainingTime + "秒，请耐心等待");
                isProcessing = false;
                return;
            }
        }
        process(message, SubjectContact, true, false);
    }

    /**
     * Plugin name. Use in logs.
     */
    public String getPluginName() {
        return PluginName;
    }

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public String getHelpContent() {
        return HelpContent;
    }

    /**
     * True if enabled.
     */
    public boolean isEnabled() {
        return IsEnabled;
    }

    /**
     * Set status.
     *
     * @param status Status
     */
    public void setEnabled(boolean status) {
        IsEnabled = status;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return DebugMode;
    }

    /**
     * Module init.
     */
    public void Init() {
        CThread.start();
        IsEnabled = true;
        Log.WriteLog(Log.Level.Debug,
                "setu initiated! ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    public void Shutdown() {
        PicturePlugin.isRunning = false;
        PicturePlugin.CThread.interrupt();
        Log.WriteLog(Log.Level.Debug,
                "setu Plugin shutting down. ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    private void process(String[] message, Contact SubjectContact, boolean sendPicture, boolean isGroup) {

        String[] Keywords;
        String ApiUrlString = "https://api.lolicon.app/setu/v2";
        String ApiReturnString = null;
        String PictureAuthor;
        String PictureFilePath;
        String PictureSavingPath = RongXiaoliBot.DataPath.toString() + "/setu/Image/";
        //String PictureTags;
        String PictureTitle;
        String PictureUrlString;

        int PicturePid;

        File PictureLocalFile;

        MessageChainBuilder PictureInfoMessage = new MessageChainBuilder();
        MessageChainBuilder PictureMessage = new MessageChainBuilder();

        //Process tags.
        Keywords = null;
        if (message.length >= 2) {
            Keywords = new String[message.length - 1];
            System.arraycopy(message, 1, Keywords, 0, Keywords.length - 1 + 1);
            Log.WriteLog(Log.Level.Verbose,
                    "Command received (raw): " + Arrays.toString(message),
                    Log.LogClass.ModuleMain,
                    PluginName);
        }

        //Construct API Request.
        HttpsGet APIHttpsGet = new HttpsGet();
        APIHttpsGet.targetUrl = ApiUrlString;
        if (Keywords != null) {
            for (String tag :
                    Keywords) {
                APIHttpsGet.Par.Append("tag", tag, true);
            }
        }
        APIHttpsGet.Par.Append("size", "regular");
        APIHttpsGet.Par.Append("proxy", PictureProxy);
        try {
            ApiReturnString = APIHttpsGet.GET(PluginName);
            Log.WriteLog(Log.Level.Verbose,
                    "API connect succeeded. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
        } catch (ConnectException CE) {
            SubjectContact.sendMessage("API连接失败，请重试，多次失败请联系主人维修");
            isProcessing = false;
            return;
        } catch (IOException IOE) {
            Log.WriteLog(Log.Level.Warning, "Unexpected IOException got. Try to get in http protocol. ", Log.LogClass.ModuleMain, PluginName);
            HttpGet httpGet = new HttpGet();
            httpGet.targetUrl = "http://api.lolicon.app/setu/v2";
            if (Keywords != null) {
                for (String tag :
                        Keywords) {
                    httpGet.Par.Append("tag", tag, true);
                }
            }
            httpGet.Par.Append("size", "regular");
            httpGet.Par.Append("proxy", PictureProxy);
            try {
                ApiReturnString = httpGet.GET(PluginName);
            } catch (IOException e) {
                SubjectContact.sendMessage("网络出错，请重试，多次失败请联系主人维修");
                isProcessing = false;
                throw new RuntimeException(e);
            }
        } catch (KeyManagementException KME) {
            SubjectContact.sendMessage("URL验证失败，请重试，多次失败请联系主人维修");
            isProcessing = false;
            return;
        } catch (Exception e) {
            SubjectContact.sendMessage("API获取失败，多次失败请联系主人维修");
            isProcessing = false;
            return;
        }

        //JSON resolve.
        if (Objects.equals(ApiReturnString, "")) {
            SubjectContact.sendMessage("图片获取失败，请重试，多次失败请联系主人维修");
            Log.WriteLog(Log.Level.Warning,
                    "API returned empty string. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            isProcessing = false;
            return;
        }
        if (Objects.equals(ApiReturnString, "{\"error\":\"\",\"data\":[]}")) {
            SubjectContact.sendMessage("找不到相关图片，换一个关键词试试");
            Log.WriteLog(Log.Level.Info,
                    "Picture not found. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            isProcessing = false;
            return;
        }
        if (!JSON.isValid(ApiReturnString)) {
            SubjectContact.sendMessage("错误：JSON未能正确转换");
            Log.WriteLog(Log.Level.Warning,
                    "JSON cannot be resolved. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            Log.WriteLog(Log.Level.Verbose,
                    "JSON: " + ApiReturnString,
                    Log.LogClass.ModuleMain,
                    PluginName);
            isProcessing = false;
            return;
        }
        LoliconAPIRespond APIRespond = JSON.parseObject(ApiReturnString, LoliconAPIRespond.class);
        LoliconAPIRespond.Data PictData = APIRespond.getData().get(0);
        PictureUrlString = PictData.getUrls().getOriginal();
        if (PictureUrlString == null) {
            PictureUrlString = PictData.getUrls().getRegular();
        }
        if (PictureUrlString == null) {
            SubjectContact.sendMessage("图片链接获取失败，请联系主人维修");
            isProcessing = false;
            return;
        }
        // This part is to filter R-18 tags.
        if (isGroup) {
            ArrayList<String> r18Tags = new ArrayList<>();
            r18Tags.add("R-18");
            r18Tags.add("R18");
            r18Tags.add("r18");
            r18Tags.add("r-18");
            r18Tags.add("nsfw");
            r18Tags.add("NSFW");
            for (String singleTag :
                    PictData.getTags()) {
                if (!sendPicture) {
                    break;
                }
                for (String r18Tag :
                        r18Tags) {
                    if (singleTag.equals(r18Tag)) {
                        sendPicture = false;
                        break;
                    }
                }
            }
        }
        if (sendPicture) {
            //Download picture.
            String[] UrlSplit = PictureUrlString.split("/");
            HttpDownload PictureDownload = new HttpDownload();
            PictureDownload.targetUrl = PictureUrlString;
            PictureDownload.localFileName = UrlSplit[UrlSplit.length - 1];
            PictureDownload.localFilePath = PictureSavingPath;
            PictureFilePath = PictureDownload.localFilePath + PictureDownload.localFileName;
            PictureLocalFile = new File(PictureFilePath);
            SubjectContact.sendMessage("图片加载中");
            if (PictureLocalFile.exists()) {
                Log.WriteLog(Log.Level.Verbose,
                        "File: " + PictureLocalFile + " exists. Using local file instead. ",
                        Log.LogClass.ModuleMain,
                        PluginName);
            } else {
                try {
                    PictureDownload.Download(PluginName);
                } catch (MalformedURLException MUE) {
                    SubjectContact.sendMessage("API返回URL错误，请重试");
                    isProcessing = false;
                    return;
                } catch (ConnectException CE) {
                    SubjectContact.sendMessage("连接超时，请重试");
                    isProcessing = false;
                    return;
                } catch (FileNotFoundException FNFE) {
                    SubjectContact.sendMessage("图床图片文件返回异常，请重试");
                    isProcessing = false;
                    return;
                } catch (IllegalArgumentException IAE) {
                    SubjectContact.sendMessage("未知的参数错误，请联系主人维修");
                    isProcessing = false;
                    return;
                } catch (ClosedChannelException CCE) {
                    SubjectContact.sendMessage("连结终止，请重试");
                    isProcessing = false;
                    return;
                } catch (SSLHandshakeException SSLHE) {
                    SubjectContact.sendMessage("远程主机关闭了SSL连接，请重试，多次失败请联系主人维修，并提供时间");
                    isProcessing = false;
                    return;
                } catch (IOException IOE) {
                    if (IOE.getMessage().contains("timed out")) {
                        SubjectContact.sendMessage("连接超时，请重试");
                        isProcessing = false;
                        return;
                    }
                    SubjectContact.sendMessage("未知的IO错误，请联系主人维修，并提供时间");
                    isProcessing = false;
                    return;
                }
            }

            //Send message.
            Image image = ExternalResource.uploadAsImage(PictureLocalFile, SubjectContact);
            Log.WriteLog(Log.Level.Verbose,
                    "Using file: " + PictureFilePath,
                    Log.LogClass.ModuleMain,
                    PluginName);
            isProcessing = false;
            PictureAuthor = PictData.getAuthor();
            PicturePid = PictData.getPid();
            //PictureTags = PictData.getTags().toString();
            PictureTitle = PictData.getTitle();
            //PictureInfoMessage.append("标题: ").append(PictureTitle).append("\n");
            PictureInfoMessage.append("作者: ").append(PictureAuthor).append("\n");
            PictureInfoMessage.append("ID:  ").append(String.valueOf(PicturePid)).append("\n");
            //PictureInfoMessage.append("Tags:").append(PictureTags).append("\n");
            PictureInfoMessage.append("链接: ").append(PictureUrlString);
            PictureMessage.append(image);
            SubjectContact.sendMessage(PictureInfoMessage.build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.WriteLog(Log.Level.Warning,
                        "setu message sending cooling interrupted. ",
                        Log.LogClass.ModuleMain,
                        PluginName);
            }
            SubjectContact.sendMessage(PictureMessage.build());
            Log.WriteLog(Log.Level.Debug,
                    "Process completed. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            isProcessing = false;


        } else {


            PictureAuthor = PictData.getAuthor();
            PicturePid = PictData.getPid();
            //PictureTags = PictData.getTags().toString();
            PictureTitle = PictData.getTitle();
            //PictureInfoMessage.append("标题: ").append(PictureTitle).append("\n");
            PictureInfoMessage.append("作者: ").append(PictureAuthor).append("\n");
            PictureInfoMessage.append("ID:  ").append(String.valueOf(PicturePid)).append("\n");
            //PictureInfoMessage.append("Tags:").append(PictureTags).append("\n");
            PictureInfoMessage.append("链接: ").append(PictureUrlString);
            //PictureMessage.append(image);
            SubjectContact.sendMessage(PictureInfoMessage.build());
            //SenderContact.sendMessage(PictureMessage.build());
            Log.WriteLog(Log.Level.Debug,
                    "Process completed. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            isProcessing = false;
            // Try to cache the queried picture.
            String[] UrlSplit = PictureUrlString.split("/");
            HttpDownload PictureDownload = new HttpDownload();
            PictureDownload.targetUrl = PictureUrlString;
            PictureDownload.localFileName = UrlSplit[UrlSplit.length - 1];
            PictureDownload.localFilePath = PictureSavingPath;
            PictureFilePath = PictureDownload.localFilePath + PictureDownload.localFileName;
            PictureLocalFile = new File(PictureFilePath);
            if (PictureLocalFile.exists()) {
                Log.WriteLog(Log.Level.Verbose,
                        "File: " + PictureLocalFile + " exists. ",
                        Log.LogClass.ModuleMain,
                        PluginName);
            } else {
                try {
                    PictureDownload.Download(PluginName);
                } catch (IOException e) {
                    Log.Exception(e, "Failed to cache the picture. ", Log.LogClass.ModuleMain, PluginName);
                }
            }


        }
    }

    /**
     * Single cooling object.
     */
    public static class CoolingObject {
        public long FriendID;
        public short RemainingTime;

        public CoolingObject(long Friend, short RemainingTime) {
            this.FriendID = Friend;
            this.RemainingTime = RemainingTime;

        }
    }

    /**
     * List of cooling objects.
     */
    public static class CoolingObjectList {
        private static final CopyOnWriteArrayList<CoolingObject> CoolingObjectList = new CopyOnWriteArrayList<>();

        public static void Tick() {
            for (CoolingObject SingleObj :
                    CoolingObjectList) {
                SingleObj.RemainingTime--;
                if (SingleObj.RemainingTime <= 0) {
                    Log.WriteLog(Log.Level.Debug,
                            "CoolingObject removed: " + SingleObj.FriendID,
                            Log.LogClass.Multithreading,
                            "setu");
                    CoolingObjectList.remove(SingleObj);
                }
            }
        }

        public static short Add(long Friend, short RemainingTime) {
            CoolingObject ObjAdding = new CoolingObject(Friend, RemainingTime);
            for (CoolingObject SingleObject :
                    CoolingObjectList) {
                if (SingleObject.FriendID == Friend) {
                    return SingleObject.RemainingTime;
                }
            }
            Log.WriteLog(Log.Level.Debug,
                    "CoolingObject added: " + Friend,
                    Log.LogClass.Multithreading,
                    "setu");
            CoolingObjectList.add(ObjAdding);
            return -1;
        }
    }

    /**
     * Cooling thread.
     */
    public static class CoolingThread extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                    CoolingObjectList.Tick();
                } catch (InterruptedException e) {
                    Log.Exception(e, "Cooling thread stopped. ", Log.LogClass.Multithreading, "setu");
                }
            }
        }
    }
}
