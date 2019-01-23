package osvc.simulators;

import base.utils.JsonUtil;
import base.utils.adb.CommandExecutor;
import base.utils.adb.OSVer;
import base.utils.http.HttpMethod;
import base.utils.http.OkHttpUtil;
import base.utils.medias.beans.AMedia;
import osvc.simulators.beans.Msg;
import osvc.simulators.beans.ToRNMsg;
import base.utils.sqllite.dao.MsgIdDao;
import base.utils.xmlparser.XMLParser;
import base.search.engine.wait.base.CoreWait;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import config.utils.ConfigUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.testng.Assert;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasWechatSimulator implements IWechatSimulator{

    private static final char[] HEX_DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                    'e', 'f' };

    private String callbackURL = null;
    private String token = null;
    private String appId = null;

    private BigDecimal currentMsgId;



    public PasWechatSimulator(String callbackURL, String token, String appId) {
        this.callbackURL = callbackURL;
        this.token = token;
        this.appId = appId;
    }

    private String calcSignature(String token, String ts, String nonce) {
        String[] tmpArr = new String[] { token, ts, nonce };
        Arrays.sort(tmpArr);
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update((tmpArr[0] + tmpArr[1] +
                    tmpArr[2]).getBytes());

            byte[] bytes = messageDigest.digest();
            int len = bytes.length;
            StringBuilder buf = new StringBuilder(len * 2);
            for (int j = 0; j < len; j++) {
                buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            }
            result = buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void sendWeChatMessage(String wechatMessage)  {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = "123456";
        String signature = calcSignature(token, timestamp, nonce);

        String url = this.callbackURL + "?appId=" + this.appId + "&timestamp=" + timestamp + "&nonce=" + nonce + "&signature=" + signature;
        System.out.println("url: " + url);
        System.out.println("request body: " + wechatMessage);

        try {
            String result = OkHttpUtil.sendRequest(HttpMethod.POST, url, wechatMessage);
            System.out.println(result);
        }catch (Exception ex) {
            System.out.println("failed to send Wechat message: [" + ex.getMessage() +" ]");
        }
    }

    public void sendStartChatMsg(String toUserName,String fromUserName) {
        sendMenuClickEventMessage(toUserName, fromUserName,"SRV_WA");
        sendTextMessage(toUserName, fromUserName,"Y");
        sendTextMessage(toUserName, fromUserName,"1");
    }

    @Override
    public void sendStartIncidentMsg(String toUserName, String fromUserName) {
        sendMenuClickEventMessage(toUserName, fromUserName,"SRV_WA");
        sendTextMessage(toUserName, fromUserName,"Y");
        sendTextMessage(toUserName, fromUserName,"3");
    }

    private void sendSubscribeOrUnSubscribeEventMessage(String toUserName, String fromUserName, boolean flag) {
        String wechatMessage =  "<xml><ToUserName><![CDATA[" + toUserName + "]]></ToUserName>\n" +
                "<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>\n" +
                "<CreateTime>" + String.valueOf(System.currentTimeMillis()) + "</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[" + (flag?"subscribe":"unsubscribe") + "]]></Event>\n" +
                "</xml>";
        sendWeChatMessage(wechatMessage);
    }

    @Override
    public void sendSubscribeEventMessage(String toUserName, String fromUserName) {
        sendSubscribeOrUnSubscribeEventMessage(toUserName, fromUserName, true);
    }

    @Override
    public void sendUnsubscribeEventMessage(String toUserName, String fromUserName) {
        sendSubscribeOrUnSubscribeEventMessage(toUserName, fromUserName, false);
    }

    @Override
    public void sendMenuClickEventMessage(String toUserName, String fromUserName, String eventKey) {
        String wechatMessage =  "<xml>\n" +
                "<ToUserName><![CDATA[" + toUserName + "]]></ToUserName>\n" +
                "<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>\n" +
                "<CreateTime>" + String.valueOf(System.currentTimeMillis()) + "</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[CLICK]]></Event>\n" +
                "<EventKey><![CDATA[" + eventKey + "]]></EventKey>\n" +
                "</xml>";
        sendWeChatMessage(wechatMessage);
    }

    @Override
    public void sendTextMessage(String toUserName, String fromUserName, String content)  {


        MsgIdDao msgIdDao;

        try {
            msgIdDao = new MsgIdDao();
            this.currentMsgId = msgIdDao.getCurrentMsgId();
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }


        String wechatMessage =  "<xml>\n" +
                " <ToUserName><![CDATA[" + toUserName + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + fromUserName + "]]></FromUserName> \n" +
                " <CreateTime>" + String.valueOf(System.currentTimeMillis()) + "</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA[" + content + "]]></Content>\n" +
                " <MsgId>" + String.valueOf(this.currentMsgId) + "</MsgId>\n" +
                " </xml>";

        sendWeChatMessage(wechatMessage);
    }



    public void sendImageMessage(String toUserName, String fromUserName, AMedia media) {

        MsgIdDao msgIdDao;

        try {
            msgIdDao = new MsgIdDao();
            this.currentMsgId = msgIdDao.getCurrentMsgId();
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        String wechatMessage =  "<xml>\n" +
                " <ToUserName><![CDATA[" + toUserName + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + fromUserName + "]]></FromUserName> \n" +
                " <CreateTime>" + String.valueOf(System.currentTimeMillis()) + "</CreateTime>\n" +
                " <MsgType><![CDATA[image]]></MsgType>\n" +
                " <PicUrl><![CDATA["+media.getMediaUrl()+"]]></PicUrl>\n" +
                " <MsgId>" + String.valueOf(this.currentMsgId) + "</MsgId>\n" +
                " <MediaId><![CDATA["+media.getMediaId()+"]]></MediaId>\n" +
                " </xml>";

        sendWeChatMessage(wechatMessage);
    }


    public void sendVoiceMessage(String toUserName, String fromUserName, AMedia media) {
        MsgIdDao msgIdDao;

        try {
            msgIdDao = new MsgIdDao();
            this.currentMsgId = msgIdDao.getCurrentMsgId();
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        String wechatMessage =  "<xml>\n" +
                " <ToUserName><![CDATA[" + toUserName + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + fromUserName + "]]></FromUserName> \n" +
                " <CreateTime>" + String.valueOf(System.currentTimeMillis()) + "</CreateTime>\n" +
                " <MsgType><![CDATA[voice]]></MsgType>\n" +
                " <MediaId><![CDATA["+media.getMediaId()+"]]></MediaId>\n" +
                " <Format><![CDATA[amr]]></Format>" +
                " <MsgId>" + String.valueOf(this.currentMsgId) + "</MsgId>\n" +
                "<Recognition><![CDATA[]]></Recognition>"+
                " </xml>";

        sendWeChatMessage(wechatMessage);

    }


    public void sendVideoMessage(String toUserName, String fromUserName, AMedia media) {
        MsgIdDao msgIdDao;

        try {
            msgIdDao = new MsgIdDao();
            this.currentMsgId = msgIdDao.getCurrentMsgId();
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        String wechatMessage =  "<xml>\n" +
                " <ToUserName><![CDATA[" + toUserName + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + fromUserName + "]]></FromUserName> \n" +
                " <CreateTime>" + String.valueOf(System.currentTimeMillis()) + "</CreateTime>\n" +
                " <MsgType><![CDATA[video]]></MsgType>\n" +
                " <MediaId><![CDATA["+media.getMediaId()+"]]></MediaId>\n" +
                " <ThumbMediaId><![CDATA[TsIIV3mJxUYedq-8EkmO5iDYKgVPlVyicDrRRQj1scjH7uvPnjaE_e7lJiTrTeaw]]></ThumbMediaId>" +
                " <MsgId>" + String.valueOf(this.currentMsgId) + "</MsgId>\n" +
                " </xml>";

        sendWeChatMessage(wechatMessage);

    }

    public List<String> queryTemplateMsgToWechat(String toUserName) {
        List<String> urls = new ArrayList<>();
        List<Msg> toWechatTemplateMsgs = getTemplateMsgToWechat(toUserName);
        int length = toWechatTemplateMsgs.size();

        for(int i = length-1; i>=0;i--) {
            urls.add(toWechatTemplateMsgs.get(i).getUrl());
        }

        return urls;
    }


    public boolean queryMessageToWechat(String toUserName,String expectContent) {
        CoreWait wait = new CoreWait();
        Result<Boolean> result = wait.waitForConditon(new Condition<Boolean>() {
            @Override
            public boolean check(Result<Boolean> r) {
                List<Msg> toWechatMsgs = getMessageToWechat(toUserName);
                int length = toWechatMsgs.size();
                if(length == 0) {
                    return false;
                }

                for(int i = length-1; i>=0;i--) {
                    if(toWechatMsgs.get(i).getContent().contains(expectContent)) {
                        r.setCode(true);
                        return true;
                    }
                }

                return false;
            }
        },500,5);

        return result.Code();
    }



    public String getJavaCloudCmd() {
        String javaCloudCmd;
        if(CommandExecutor.getOsVer().equals(OSVer.WIN)) {
            javaCloudCmd = "oracle-javacloud-sdk"+ File.separator +"javacloud.cmd ";
        }else {
            javaCloudCmd = "oracle-javacloud-sdk"+ File.separator +"javacloud ";
        }
        return javaCloudCmd;
    }



    @Override
    public List<Msg> getMessageToWechat(String toUserName) {
        String javaCloudCmd = getJavaCloudCmd();
        javaCloudCmd += "-query-service-logs -u ";
        javaCloudCmd += ConfigUtil.getAdminConsoleUsername();
        javaCloudCmd += " -p ";
        javaCloudCmd += ConfigUtil.getAdminConsolePassword();
        javaCloudCmd += " -id ";
        javaCloudCmd += ConfigUtil.getAdminConsoleIdentityDomain();
        javaCloudCmd += " -dc us2 ";
        javaCloudCmd += " -si ";
        javaCloudCmd += ConfigUtil.getAdminConsoleServiceName();
        javaCloudCmd += " -app rnwechat -sev TRACE,NOTIFICATION,WARNING,ERROR,INCIDENT_ERROR -last MINUTE -unit 60 -limit 1000 -c osvcchatmessage";
        System.out.println(javaCloudCmd);

        Result<String> result = CommandExecutor.execCommand(javaCloudCmd);
        Assert.assertTrue(result.Code(), "Get messages to wechat log failure!!!");
        String javaCmdResult = result.getMessage();


//        javaCmdResult = "\n" +
//                "-----------[Fetching at Fri Oct 12 17:54:13 CST 2018]-----------\n" +
//                "[2018-10-12T04:07:25.026-05:00][NOTIFICATION][1][Debug Trace [pool-9-thread-4] :  10-12 17:07:13 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"question\\n1: chat\\n2: wait\\n3: create incident\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:32:55.598-05:00][NOTIFICATION][1][ 10-12 17:32:55 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"question\\n1: chat\\n2: wait\\n3: create incident\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:33:03.133-05:00][NOTIFICATION][1][ 10-12 17:33:03 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"您的请求已提交，请耐心等待。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:33:26.327-05:00][NOTIFICATION][1][ 10-12 17:33:26 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"您当前排在第1位,请耐心等待,我们将尽快为你提供服务。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:38:26.290-05:00][NOTIFICATION][1][ 10-12 17:38:26 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"系统错误,请稍后再试。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:34.974-05:00][NOTIFICATION][1][ 10-12 17:51:34 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"question\\n1: chat\\n2: wait\\n3: create incident\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:42.703-05:00][NOTIFICATION][1][ 10-12 17:51:42 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"您的请求已提交，请耐心等待。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:55.983-05:00][NOTIFICATION][1][ 10-12 17:51:55 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"您当前排在第1位,请耐心等待,我们将尽快为你提供服务。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:57.197-05:00][NOTIFICATION][1][ 10-12 17:51:57 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"Hi, my name is Fredric and I will be assisting you today. Please give me a moment to look at your account.\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:52:06.029-05:00][NOTIFICATION][1][ 10-12 17:52:06 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"nihk\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:52:07.358-05:00][NOTIFICATION][1][ 10-12 17:52:07 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"nihk2\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:54:11.122-05:00][NOTIFICATION][1][ 10-12 17:54:11 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"haoni\"}}</osvcchatmessage>]\n" +
//                "-----------[Fetched at Fri Oct 12 17:54:18 CST 2018]-----------\n";


        String pattern = "(\\<osvcchatmessage\\>[\\s\\S]*?\\<\\/osvcchatmessage\\>)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(javaCmdResult);
        List<String> l = new ArrayList<String>();

        while(m.find()) {
            l.add(m.group(1));
        }


        List<Msg> msgs = new ArrayList<>();

        for(String sMsg  : l) {
            Msg msg = new Msg();
            sMsg = sMsg.replace("<osvcchatmessage>","");
            sMsg = sMsg.replace("</osvcchatmessage>","");
            String toUser = (String)JsonUtil.getResultField(sMsg,"touser");
            String type = (String)JsonUtil.getResultField(sMsg,"msgtype");
            String content = (String)JsonUtil.getResultField(sMsg,"text/content");

            msg.setToUser(toUser);
            msg.setType(type);
            msg.setContent(content);
            msgs.add(msg);
        }

        List<Msg> toUserMsgs = new ArrayList<>();

        for(Msg msg: msgs) {
            if(msg.getToUser().equals(toUserName)) {
                toUserMsgs.add(msg);
            }
        }



        return toUserMsgs;

    }

    public List<Msg> getTemplateMsgToWechat(String toUserName) {
        String javaCloudCmd = getJavaCloudCmd();
        javaCloudCmd += "-query-service-logs -u ";
        javaCloudCmd += ConfigUtil.getAdminConsoleUsername();
        javaCloudCmd += " -p ";
        javaCloudCmd += ConfigUtil.getAdminConsolePassword();
        javaCloudCmd += " -id ";
        javaCloudCmd += ConfigUtil.getAdminConsoleIdentityDomain();
        javaCloudCmd += " -dc us2 ";
        javaCloudCmd += " -si ";
        javaCloudCmd += ConfigUtil.getAdminConsoleServiceName();
        javaCloudCmd += " -app rnwechat -sev TRACE,NOTIFICATION,WARNING,ERROR,INCIDENT_ERROR -last MINUTE -unit 60 -limit 1000 -c template";
        System.out.println(javaCloudCmd);

        Result<String> result = CommandExecutor.execCommand(javaCloudCmd);
        Assert.assertTrue(result.Code(), "Get messages to wechat log failure!!!");
        String javaCmdResult = result.getMessage();

//        if (javaCmdResult.contains("ERROR") && javaCmdResult.contains("Read timed out")) {
//            throw new Exception("[ERROR] Read timed out");
//        }


//        javaCmdResult = "\n" +
//                "-----------[Fetching at Fri Oct 12 17:54:13 CST 2018]-----------\n" +
//                "[2018-10-12T04:07:25.026-05:00][NOTIFICATION][1][Debug Trace [pool-9-thread-4] :  10-12 17:07:13 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"question\\n1: chat\\n2: wait\\n3: create incident\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:32:55.598-05:00][NOTIFICATION][1][ 10-12 17:32:55 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"question\\n1: chat\\n2: wait\\n3: create incident\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:33:03.133-05:00][NOTIFICATION][1][ 10-12 17:33:03 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"您的请求已提交，请耐心等待。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:33:26.327-05:00][NOTIFICATION][1][ 10-12 17:33:26 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"您当前排在第1位,请耐心等待,我们将尽快为你提供服务。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:38:26.290-05:00][NOTIFICATION][1][ 10-12 17:38:26 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0dHfjTp5TFsmX5EtIpZLqoQ\",\"msgtype\":\"text\",\"text\":{\"content\":\"系统错误,请稍后再试。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:34.974-05:00][NOTIFICATION][1][ 10-12 17:51:34 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"question\\n1: chat\\n2: wait\\n3: create incident\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:42.703-05:00][NOTIFICATION][1][ 10-12 17:51:42 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"您的请求已提交，请耐心等待。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:55.983-05:00][NOTIFICATION][1][ 10-12 17:51:55 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"您当前排在第1位,请耐心等待,我们将尽快为你提供服务。\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:51:57.197-05:00][NOTIFICATION][1][ 10-12 17:51:57 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"Hi, my name is Fredric and I will be assisting you today. Please give me a moment to look at your account.\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:52:06.029-05:00][NOTIFICATION][1][ 10-12 17:52:06 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"nihk\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:52:07.358-05:00][NOTIFICATION][1][ 10-12 17:52:07 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"nihk2\"}}</osvcchatmessage>]\n" +
//                "[2018-10-12T04:54:11.122-05:00][NOTIFICATION][1][ 10-12 17:54:11 wechat.connector.WechatConnector <osvcchatmessage>{\"touser\":\"oc9ya0azPrpQdirN5Y_OUbfP6LrU\",\"msgtype\":\"text\",\"text\":{\"content\":\"haoni\"}}</osvcchatmessage>]\n" +
//                "-----------[Fetched at Fri Oct 12 17:54:18 CST 2018]-----------\n";


        String pattern = "(Send template msg \\: \\_\\_[\\s\\S]*?])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(javaCmdResult);
        List<String> l = new ArrayList<String>();

        while(m.find()) {
            l.add(m.group(1));
        }


        List<Msg> msgs = new ArrayList<>();

        for(String sMsg  : l) {
            Msg msg = new Msg();
            sMsg = sMsg.replace("Send template msg : __","");
            sMsg = sMsg.replace("]","");
            String toUser = (String)JsonUtil.getResultField(sMsg,"touser");
            String tempateId = (String)JsonUtil.getResultField(sMsg,"template_id");
            String url = (String)JsonUtil.getResultField(sMsg,"data/portalUrl/value");

            msg.setToUser(toUser);
            msg.setTemplateId(tempateId);
            msg.setUrl(url);
            msgs.add(msg);
        }

        List<Msg> toUserMsgs = new ArrayList<>();

        for(Msg msg: msgs) {
            if(msg.getToUser().equals(toUserName)) {
                toUserMsgs.add(msg);
            }
        }



        return toUserMsgs;

    }



    public boolean queryMessageToRightNow(String toRNUserName, String fromOpenId,String rnowName,String expectContent) {
        CoreWait wait = new CoreWait();
        Result<Boolean> result = wait.waitForConditon(new Condition<Boolean>() {
            @Override
            public boolean check(Result<Boolean> r) {
                List<ToRNMsg> toRNMsgs = getMessageToRightNow(toRNUserName,fromOpenId,rnowName);
                int length = toRNMsgs.size();
                if(length == 0) {
                    return false;
                }

                for(int i = length-1; i>=0;i--) {
                    if(toRNMsgs.get(i).getMsgs().contains(expectContent)) {
                        r.setCode(true);
                        return true;
                    }
                }

                return false;
            }
        },500,5);

        return result.Code();
    }


    public boolean queryMediasToRightNow(String toRNUserName, String fromOpenId,String rnowName,String mediaType,String mediaId) {
        CoreWait wait = new CoreWait();
        Result<Boolean> result = wait.waitForConditon(new Condition<Boolean>() {
            @Override
            public boolean check(Result<Boolean> r) {
                List<ToRNMsg> toRNMsgs = getMessageToRightNow(toRNUserName,fromOpenId,rnowName);
                int length = toRNMsgs.size();
                if(length == 0) {
                    return false;
                }

                for(int i = length-1; i>=0;i--) {
                    List<String> messages = toRNMsgs.get(i).getMsgs();
                    for(String message: messages) {
                        if(message.startsWith(mediaType+" url")) {
                            String url = message.replace(mediaType+" url"+":","").trim();

                            try {
                                String longUrl = OkHttpUtil.httpGetRedirectUrl(url);
                                String [] urlArr = longUrl.split("/");
                                String [] mediaIdArr = urlArr[urlArr.length-1].split("\\.");
                                String mediaIdActual = mediaIdArr[0];
                                if(mediaIdActual.equals(mediaId)) {
                                    r.setCode(true);
                                    return true;
                                }
                            }catch (Exception ex) {
                                return false;
                            }

                        }
                    }

                }

                return false;
            }
        },500,5);

        return result.Code();
    }

    @Override
    public List<ToRNMsg> getMessageToRightNow(String toRNUserName, String fromOpenId,String rnowName) {

        String javaCloudCmd = getJavaCloudCmd();
        javaCloudCmd += "-query-service-logs -u ";
        javaCloudCmd += ConfigUtil.getAdminConsoleUsername();
        javaCloudCmd += " -p ";
        javaCloudCmd += ConfigUtil.getAdminConsolePassword();
        javaCloudCmd += " -id ";
        javaCloudCmd += ConfigUtil.getAdminConsoleIdentityDomain();
        javaCloudCmd += " -dc us2 ";
        javaCloudCmd += " -si ";
        javaCloudCmd += ConfigUtil.getAdminConsoleServiceName();
        javaCloudCmd += " -app rnwechat -sev TRACE,NOTIFICATION,WARNING,ERROR,INCIDENT_ERROR -last MINUTE -unit 60 -limit 1000 -c osvcrnmessage";
        System.out.println(javaCloudCmd);
        Result<String> result = CommandExecutor.execCommand(javaCloudCmd);
        Assert.assertTrue(result.Code(), "Get messages to right now log failure!!!");
        String javaCmdResult = result.getMessage();

//        javaCmdResult = "\n" +
////                "-----------[Fetching at Mon Oct 15 15:51:44 CST 2018]-----------\n" +
////                "[2018-10-15T02:48:31.229-05:00][NOTIFICATION][1][ 10-15 15:48:31 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RequestChat><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:48:30:646Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData><v11:CustomerInformation><v1:EMailAddress>oc9ya0azprpqdirn5y_oubfp6lru@wechat.com</v1:EMailAddress><v1:FirstName>oc9ya0azPrpQdirN5Y_OUbfP6LrU</v1:FirstName><v1:LastName>wechat</v1:LastName><v1:InterfaceID><v1:ID id=\"1\"/></v1:InterfaceID><v1:ContactID id=\"825\"/><v1:Question>WeChat_wechatoc9ya0azPrpQdirN5Y_OUbfP6LrU</v1:Question><v1:CategoryID id=\"61\"/></v11:CustomerInformation><v11:ResumeType>DO_NOT_RESUME</v11:ResumeType><v11:ChatSessionToken>7bf4baf4-c91a-4fc7-b94a-343985403105</v11:ChatSessionToken></v11:RequestChat></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:48:38.120-05:00][NOTIFICATION][1][ 10-15 15:48:38 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RetrieveMessages><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:48:37:946Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData></v11:RetrieveMessages></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:48:44.093-05:00][NOTIFICATION][1][ 10-15 15:48:44 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:PostChatMessage><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:48:43:983Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData><v11:Body>哈哈哈哈哈哈</v11:Body></v11:PostChatMessage></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:49:12.998-05:00][NOTIFICATION][1][ 10-15 15:49:12 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RetrieveMessages><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:48:42:927Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData></v11:RetrieveMessages></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:49:48.092-05:00][NOTIFICATION][1][ 10-15 15:49:48 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RetrieveMessages><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:49:18:019Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData></v11:RetrieveMessages></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:50:23.160-05:00][NOTIFICATION][1][ 10-15 15:50:23 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RetrieveMessages><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:49:52:932Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData></v11:RetrieveMessages></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:51:01.148-05:00][NOTIFICATION][1][ 10-15 15:51:01 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RetrieveMessages><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:50:27:937Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData></v11:RetrieveMessages></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:51:18.885-05:00][NOTIFICATION][1][ 10-15 15:51:18 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:PostChatMessage><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:51:18:822Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData><v11:Body>嘻嘻嘻嘻嘻嘻嘻</v11:Body></v11:PostChatMessage></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:51:28.053-05:00][NOTIFICATION][1][ 10-15 15:51:28 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:PostChatMessage><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:51:27:908Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData><v11:Body>呵呵呵呵呵呵</v11:Body></v11:PostChatMessage></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "[2018-10-15T02:51:33.136-05:00][NOTIFICATION][1][ 10-15 15:51:33 oracle.apps.ext.rnwechat.rightnow.RightNowAdapter <osvcrnmessage><env:Envelope xmlns:v1=\"urn:messages.common.chat.ws.rightnow.com/v1\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v11=\"urn:messages.enduser.chat.ws.rightnow.com/v1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header><wsse:Security xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" env:mustUnderstand=\"1\"><wsse:UsernameToken><wsse:Username>fredric</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">********</wsse:Password></wsse:UsernameToken></wsse:Security><v1:ChatClientInfoHeader><v1:AppID>RNWI</v1:AppID><v1:SessionID>zi8p80tpq0y41hckbryypenu9</v1:SessionID></v1:ChatClientInfoHeader></env:Header><env:Body><v11:RetrieveMessages><v11:TransactionRequestData><v11:ClientRequestTime>2018-10-15T07:51:02:938Z</v11:ClientRequestTime><v11:ClientTransactionID>1</v11:ClientTransactionID><v11:SiteName>rnowgse00147</v11:SiteName></v11:TransactionRequestData></v11:RetrieveMessages></env:Body></env:Envelope></osvcrnmessage>]\n" +
////                "-----------[Fetched at Mon Oct 15 15:51:54 CST 2018]-----------\n";


        String pattern = "(\\<osvcrnmessage\\>[\\s\\S]*?\\<\\/osvcrnmessage\\>)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(javaCmdResult);
        List<String> l = new ArrayList<String>();

        while(m.find()) {
            l.add(m.group(1));
        }

        List<ToRNMsg> rnMsgs = new ArrayList<>();

        ToRNMsg currentRnMsg = null;

        for(String msgStr: l) {
            System.out.println(msgStr);

            try {
                if(msgStr.contains("v11:Body")) {

                    Document doc = DocumentHelper.parseText(msgStr);
                    Element root = doc.getRootElement();

                    XMLParser parser = new XMLParser();
                    parser.resetEles();
                    Element bodyNode = parser.getChildNode(root,"v11:Body");

                    currentRnMsg.getMsgs().add(bodyNode.getText());
                }else if(msgStr.contains("v11:CustomerInformation")) {

                    currentRnMsg = new ToRNMsg();
                    rnMsgs.add(currentRnMsg);

                    Document doc = DocumentHelper.parseText(msgStr);
                    Element root = doc.getRootElement();

                    XMLParser parser = new XMLParser();
                    parser.resetEles();
                    Element firstNameNode = parser.getChildNode(root,"v1:FirstName");
                    currentRnMsg.setFromOpenId(firstNameNode.getText());

                    parser.resetEles();
                    Element userNameNode = parser.getChildNode(root,"wsse:Username");
                    currentRnMsg.setToUserName(userNameNode.getText());

                    parser.resetEles();
                    Element siteNameNode = parser.getChildNode(root,"v11:SiteName");
                    currentRnMsg.setRightNowName(siteNameNode.getText());

                    parser.resetEles();
                    Element contactIdNode = parser.getChildNode(root,"v1:ContactID");
                    currentRnMsg.setContactId(contactIdNode.attribute("id").getValue());
                }

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        System.out.println("Rn messages length:" + rnMsgs.size());
        List<ToRNMsg> retRNMsgs = new ArrayList<>();

        for(ToRNMsg rnMsg0 : rnMsgs) {
            if(toRNUserName.equals(rnMsg0.getToUserName()) &&
                    fromOpenId.equals(rnMsg0.getFromOpenId()) &&
                    rnowName.equals(rnMsg0.getRightNowName())){
                retRNMsgs.add(rnMsg0);
            }
        }

        return retRNMsgs;

    }




    public static void main(String[] args) {

//        List<String> aList = new ArrayList<>();
//        aList.add("1");
//        aList.add("2");
//        aList.add("3");
//        int length = aList.size();
//        for(int i= length-1;i>=0;i--) {
//            System.out.println(aList.get(i));
//        }

        //To Right now message test
        PasWechatSimulator pws = new PasWechatSimulator("","","");
        List<ToRNMsg> rnMsgs = pws.getMessageToRightNow("fredric","oc9ya0azPrpQdirN5Y_OUbfP6LrU","rnowgse00147");

        boolean isContains = false;
        if(rnMsgs.size()>0) {
            for(ToRNMsg rnMsg: rnMsgs) {
                if(rnMsg.getMsgs().contains("hello")) {
                    isContains = true;
                    break;
                }
            }
        }


         // To Wechat message test

        List<Msg> msgs = pws.getMessageToWechat("oc9ya0dHfjTp5TFsmX5EtIpZLqoQ");
        for(Msg msg : msgs) {
            System.out.println("---------------->Start to print a wechat message<----------------------");
            System.out.println(msg.getToUser());
            System.out.println(msg.getType());
            System.out.println(msg.getContent());
            System.out.println("---------------->End      print a wechat message<----------------------");
        }


    }


}
