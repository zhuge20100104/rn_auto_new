package osvc.simulators;

import base.utils.medias.beans.AMedia;
import osvc.simulators.beans.Msg;
import osvc.simulators.beans.ToRNMsg;

import java.util.List;

public interface IWechatSimulator {
    void sendWeChatMessage(String wechatMessage);
    void sendSubscribeEventMessage(String toUserName, String fromUserName);
    void sendUnsubscribeEventMessage(String toUserName, String fromUserName);
    void sendMenuClickEventMessage(String toUserName, String fromUserName, String eventKey);
    void sendTextMessage(String toUserName, String fromUserName, String content);
    void sendImageMessage(String toUserName, String fromUserName, AMedia media);
    void sendVoiceMessage(String toUserName, String fromUserName, AMedia media);
    void sendVideoMessage(String toUserName, String fromUserName, AMedia media);
    List<String> queryTemplateMsgToWechat(String toUserName);
    boolean queryMessageToWechat(String toUserName, String expectContent);
    List<Msg> getMessageToWechat(String wechatUserOpenId);
    boolean queryMessageToRightNow(String toRNUserName, String fromOpenId,String rnowName,String expectContent);
    boolean queryMediasToRightNow(String toRNUserName, String fromOpenId,String rnowName,String mediaType,String mediaId);
    List<ToRNMsg> getMessageToRightNow(String toRNUserName, String fromOpenId, String rnowName);
    void sendStartChatMsg(String toUserName,String fromUserName);
    void sendStartIncidentMsg(String toUserName,String fromUserName);
}
