package base.utils.medias;

import base.utils.JsonUtil;
import base.utils.http.OkHttpUtil;
import base.utils.medias.beans.AMedia;

public class MediaUtil {

    /**
     * Convert the returned response as a media object
     * @param response  The returned response
     * @param accessToken Access token used for wechat access
     * @return  The media object
     *          <br> See also  {@link base.utils.medias.beans.AMedia}
     */
    private static AMedia fillAndReturnMedia(String response,String accessToken) {
        AMedia media = new AMedia();
        String mediaId = JsonUtil.getResultField(response,"media_id").toString();
        media.setMediaId(mediaId);
        String mediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id="+mediaId;
        media.setMediaUrl(mediaUrl);
        return media;
    }


    /**
     * Upload a temporary image to the wechat server,
     * <br> This image will be kept for three days on wechat server.
     * @param imageName  The image name
     * @param contentType  The content type of the image, eg. [image/jpeg]  [image/png]
     * @return  A media object
     * @throws Exception   This method will throw an exception when upload failed
     */
    public static AMedia uploadImage(String imageName, String contentType) throws Exception{
        AMedia media=null;
        String accessToken = AccessTokenManager.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image";
        String response = OkHttpUtil.httpPostFile(url,contentType,imageName);
        System.out.println(response);
        Object errorCode = JsonUtil.getResultField(response,"errcode");

        //There is an error
        if(errorCode != null) {
            AccessTokenManager.deleteAccessToken();
            accessToken= AccessTokenManager.getAccessToken();
            url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image";;
            response = OkHttpUtil.httpPostFile(url,contentType,imageName);
            System.out.println(response);

            errorCode = JsonUtil.getResultField(response,"errcode");
            if(errorCode != null) {
                return media;
            }else {
                media = fillAndReturnMedia(response,accessToken);
                return media;
            }

        }else {
            media = fillAndReturnMedia(response,accessToken);
            return media;
        }
    }


    /**
     *  Upload a temporary audio file to the wechat server,
     *  <br> This audio will be kept for three days on wechat server.
     * @param audioName  Name of the audio file
     * @param contentType  The content type of the audio, eg. [audio/arr]
     * @return  A media object
     * @throws Exception This method will throw an exception when upload failed
     */
    public static AMedia uploadAudio(String audioName,String contentType) throws Exception{
        AMedia media=null;
        String accessToken = AccessTokenManager.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=voice";
        String response = OkHttpUtil.httpPostFile(url,contentType,audioName);
        System.out.println(response);
        Object errorCode = JsonUtil.getResultField(response,"errcode");

        //There is an error
        if(errorCode != null) {
            AccessTokenManager.deleteAccessToken();
            accessToken= AccessTokenManager.getAccessToken();
            url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=voice";
            response = OkHttpUtil.httpPostFile(url,contentType,audioName);
            System.out.println(response);

            errorCode = JsonUtil.getResultField(response,"errcode");
            if(errorCode != null) {
                return media;
            }else {
                media = fillAndReturnMedia(response,accessToken);
                return media;
            }

        }else {
            media = fillAndReturnMedia(response,accessToken);
            return media;
        }
    }


    /**
     * Upload a temporary video file to the wechat server,
     * <br> This video will be kept for three days on wechat server.
     * @param videoName The video name
     * @param contentType The content type of the video, eg. [video/mp4]
     * @return A media object
     * @throws Exception  This method will throw an exception when upload failed
     */
    public static AMedia uploadVideo(String videoName,String contentType) throws Exception{
        AMedia media=null;
        String accessToken = AccessTokenManager.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=video";
        String response = OkHttpUtil.httpPostFile(url,contentType,videoName);
        System.out.println(response);
        Object errorCode = JsonUtil.getResultField(response,"errcode");

        //There is an error
        if(errorCode != null) {
            AccessTokenManager.deleteAccessToken();
            accessToken= AccessTokenManager.getAccessToken();
            url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=video";
            response = OkHttpUtil.httpPostFile(url,contentType,videoName);
            System.out.println(response);

            errorCode = JsonUtil.getResultField(response,"errcode");
            if(errorCode != null) {
                return media;
            }else {
                media = fillAndReturnMedia(response,accessToken);
                return media;
            }

        }else {
            media = fillAndReturnMedia(response,accessToken);
            return media;
        }
    }



    public static void main(String[] args) throws Exception{

//        String accessToken = AccessTokenManager.getAccessToken();
//        String jsonStr = "{\n" +
//                "    \"type\":\"voice\",\n" +
//                "    \"offset\":0,\n" +
//                "    \"count\":20\n" +
//                "}";
//        String materialInfo = OkHttpUtil.httpPost("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+accessToken,jsonStr);
//        System.out.println(materialInfo);

//        AMedia media = MediaUtil.uploadImage("1.jpg","image/jpeg");


//        AMedia media = uploadAudio("1.amr","audio/amr");
        AMedia media = uploadVideo("1.mp4","video/mp4");

        System.out.println(media.getMediaId());

        System.out.println(media.getMediaUrl());

//        System.out.println(MediaUtil.uploadVideo("1.mp4","video.mp4"));


//        System.out.println(MediaUtil.uploadAudio("1.amr","audio/amr"));
    }
}
