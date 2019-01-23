package osvc.simulators;

import base.utils.medias.beans.AMedia;
import osvc.simulators.beans.Msg;
import osvc.simulators.beans.ToRNMsg;

import java.util.List;

public class OmsWechatSimulator implements IWechatSimulator{

    @Override
    public void sendWeChatMessage(String wechatMessage) {

    }

    @Override
    public void sendSubscribeEventMessage(String toUserName, String fromUserName) {

    }

    @Override
    public void sendUnsubscribeEventMessage(String toUserName, String fromUserName) {

    }

    @Override
    public void sendMenuClickEventMessage(String toUserName, String fromUserName, String eventKey) {

    }

    @Override
    public void sendTextMessage(String toUserName, String fromUserName, String content) {

    }

    @Override
    public void sendImageMessage(String toUserName, String fromUserName, AMedia media) {

    }

    @Override
    public void sendVoiceMessage(String toUserName, String fromUserName, AMedia media) {

    }

    @Override
    public void sendVideoMessage(String toUserName, String fromUserName, AMedia media) {

    }

    @Override
    public List<String> queryTemplateMsgToWechat(String fromUserName) { return null; }

    @Override
    public boolean queryMessageToWechat(String toUserName, String expectContent) {
        return false;
    }

    @Override
    public List<Msg> getMessageToWechat(String toUserName) {
        return null;
    }

    @Override
    public boolean queryMessageToRightNow(String toRNUserName, String fromOpenId, String rnowName, String expectContent) {
        return false;
    }

    @Override
    public boolean queryMediasToRightNow(String toRNUserName, String fromOpenId, String rnowName, String mediaType, String mediaId) {
        return false;
    }

    @Override
    public List<ToRNMsg> getMessageToRightNow(String toRNUserName, String fromOpenId, String rnowName) {
        return null;
    }

    @Override
    public void sendStartChatMsg(String toUserName, String fromUserName) {

    }

    @Override
    public void sendStartIncidentMsg(String toUserName, String fromUserName) {

    }
}
