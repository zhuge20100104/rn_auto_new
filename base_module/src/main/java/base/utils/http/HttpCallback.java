package base.utils.http;

public interface HttpCallback {
    void onSuccess(String successMsg);
    void onFailure(String errorMsg);
}
