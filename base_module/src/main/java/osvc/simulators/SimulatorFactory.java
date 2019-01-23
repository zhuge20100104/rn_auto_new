package osvc.simulators;


import osvc.simulators.beans.Msg;
import osvc.simulators.beans.ToRNMsg;
import osvc.simulators.exception.SimulatorNotSupportedException;
import config.utils.ConfigUtil;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SimulatorFactory {
    private static IWechatSimulator instance = null;

    public static IWechatSimulator getSimulator(SimType simType) throws Exception {
        switch (simType) {
            case OMS:
                if(instance == null) {
                    instance = new OmsWechatSimulator();
                }
                break;

            case PAS:
                if(instance == null) {
                    //TODO: should get these info from properties files.
                    String serviceName = ConfigUtil.getAdminConsoleServiceName();
                    String identityDomain = ConfigUtil.getAdminConsoleIdentityDomain();
                    String callbackURL = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatmobile/wx";

                    String token = ConfigUtil.getAccount1AppToken();
                    String appId = ConfigUtil.getAccount1AppId();

                    instance =  new PasWechatSimulator(callbackURL,token,appId);
                }
                break;

            default:
                throw new SimulatorNotSupportedException(simType.toString());
        }

        return instance;

    }

    public static void main(String[] args) throws Exception{
        //TODO:
        //Please migrate all the hard code configuration info into overrides.properties

        String toUserName = "gh_3871fa73668a";
        String fromUserName = "oZsxa0ujaT8cDvSxW108gRk6jcMU";
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);

        //TODO:
        //You should put code wait for Right now accept button occurred and accept it from UI here.

        //Simulator can send text message to right now
        pasSimulator.sendTextMessage(toUserName,fromUserName,"hello");

        List<ToRNMsg> rnMsg  = pasSimulator.getMessageToRightNow("fredric",fromUserName,"rnowgse00147");
        List<String> msgs = rnMsg.get(0).getMsgs();
        for(String s: msgs) {
            System.out.println(s);
        }
        Assert.assertTrue(msgs.contains("hello"));

        //TODO:
        //Then send a message from right now to wechat use Right now UI

        //Here we required an openid parameter
        List<Msg> toWechatMsgs = pasSimulator.getMessageToWechat(fromUserName);
        List<String> strMsgs = new ArrayList<>();

        for(Msg msg : toWechatMsgs) {
            System.out.println(msg.getContent());
            strMsgs.add(msg.getContent());
        }

        Assert.assertTrue(strMsgs.contains("{The message you have sent to wechat using right now UI}"));

    }
}
