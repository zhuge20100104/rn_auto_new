package base.utils.http;

import base.utils.JsonUtil;
import config.utils.ConfigUtil;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * A http util to handle network request
 */
public class OkHttpUtil {


    //region HttpGet related methods

    /**
     * Http get method, return the response as string
     * @param url  The url you want to request
     * @return   Return the response as string
     * @throws OkHttpException   When request failed, this method will throw an exception
     */
    public static String httpGet(String url)  throws OkHttpException{
        try {
            return sendRequest(HttpMethod.GET, url, null);
        }catch (Exception ex) {
            throw new OkHttpException("HTTPGET REQUEST FAILED："+ ex.getMessage());
        }
    }


    /**
     * Sometimes you want to get a field of the returned json, then you can use this method
     * <br> If the returned json is as follows, and you want to get the name field,then you can do like this,
     * <br> {"name":"Zhangsan","age":29}
     * <br> OkHttpUtil.httpGetField(url,"name")
     * @param url  The url you want to request
     * @param xpath  Xpath for the field
     * @return  The json field as an object
     * @throws OkHttpException When request failed, this method will throw an exception
     */
    public static Object httpGetField(String url,String xpath) throws OkHttpException {
        String response  = httpGet(url);
        return  JsonUtil.getResultField(response,xpath);
    }


    /**
     * Http asynchronous get
     * @param url  The url you want to request
     * @param callback The callback method you should implement
     */
    public static void httpGetAsync(String url,HttpCallback callback)  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = httpGet(url);
                    callback.onSuccess(response);
                }catch (Exception ex) {
                    callback.onFailure(ex.getMessage());
                }
            }
        }).start();
    }

    //endregion


    /**
     * Method to post a file to a server
     * @param url  The url you want to post
     * @param mediaType  RFC media types, eg. [image/png] [video/mp4]
     * @param fileName  The file name
     * @return Return response as string
     * @throws Exception   When request failed, this method will throw an exception
     */
    public static String httpPostFile(String url,String mediaType,String fileName) throws Exception{
        MediaType currentMediaType = MediaType.parse(mediaType);

        String proxyHost =  "www-proxy.us.oracle.com" ;
        int proxyPort =  80;

        Proxy oracleProxy =
                new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(proxyHost,proxyPort));
        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(oracleProxy);

        OkHttpClient client = builder.build();

        File f = new File(fileName);



        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("media", f.getName(), RequestBody.create(currentMediaType, f))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }




    //region http post related methods

    /**
     * The http post method
     * @param url  The url you want to post
     * @param jsonParams  The params to post as string
     * @return   The response as string
     * @throws OkHttpException  When request failed, this method will throw an exception
     */
    public static String httpPost(String url,String jsonParams) throws OkHttpException {
        try {
            return sendRequest(HttpMethod.POST, url, jsonParams);
        }catch (Exception ex) {
            throw new OkHttpException("HTTPPOST REQUEST FAILED："+ ex.getMessage());
        }
    }

    public static Object httpPostField(String url,String jsonParams,String xpath) throws OkHttpException {
        String response = httpPost(url,jsonParams);
        return JsonUtil.getResultField(response,xpath);
    }

    /**
     * The http asynchronous post method
     * @param url The url you want to post
     * @param jsonParams The params to post as string
     * @param callback  The callback you need to implement
     */
    public static void httpPostAsync(String url,String jsonParams,HttpCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = httpPost(url, jsonParams);
                    callback.onSuccess(response);
                }catch (Exception ex) {
                    callback.onFailure(ex.getMessage());
                }
            }
        }).start();
    }
    //endregion


    /**
     * Get redirect url from origin url
     * @param url  The origin url
     * @return  The redirect url
     * @throws Exception  When request failed, this method will throw an exception
     */
    public static String httpGetRedirectUrl(String url) throws Exception {
        String proxyHost = ConfigUtil.getProxyHost()==null ? "www-proxy.us.oracle.com" :ConfigUtil.getProxyHost();
        int proxyPort = ConfigUtil.getProxyPort()==null ? 80 :ConfigUtil.getProxyPort();

        Proxy oracleProxy =
                new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(proxyHost,proxyPort));
        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(oracleProxy).followRedirects(false);
        OkHttpClient client = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return  response.header("location");
        }

    }


    public static void main(String[] args) throws Exception{
        System.out.println(OkHttpUtil.httpGetRedirectUrl("https://w.url.cn/s/AbiuDv8"));
    }


    /**
     * Http delete method
     * @param url  The url you desired to request
     * @return  The response as string
     * @throws OkHttpException   When request failed, this method will throw an exception
     */
    public static String httpDelete(String url) throws OkHttpException {
        try {
            return sendRequest(HttpMethod.DELETE, url,null);
        }catch (Exception ex) {
            throw new OkHttpException("HTTPDELETE REQUEST FAILED："+ ex.getMessage());
        }
    }

    /**
     * Send a http request to a server
     * @param method   Http request method,  include GET, POST and DELETE
     * @param url  The url you want to send request to
     * @param jsonParams  The request params as string
     * @return   The response as string
     * @throws Exception When request failed, this method will throw an exception
     */
    public static String sendRequest(HttpMethod method,String url,String jsonParams)  throws Exception{

        String proxyHost = ConfigUtil.getProxyHost()==null ? "www-proxy.us.oracle.com" :ConfigUtil.getProxyHost();
        int proxyPort = ConfigUtil.getProxyPort()==null ? 80 :ConfigUtil.getProxyPort();

        Proxy oracleProxy =
                new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(proxyHost,proxyPort));
        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(oracleProxy);
        OkHttpClient client = builder.build();
        Request request = null;
        switch(method) {
            case GET:
                request = new Request.Builder()
                        .url(url)
                        .build();
                break;
            case POST:
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonParams);
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                break;
            case DELETE:
                request = new Request.Builder()
                        .url(url).delete()
                        .build();
                break;
        }

        try (Response response = client.newCall(request).execute()) {
            // Deserialize HTTP response to concrete type.
            ResponseBody body = response.body();
            return body.string();
        }
    }

}
