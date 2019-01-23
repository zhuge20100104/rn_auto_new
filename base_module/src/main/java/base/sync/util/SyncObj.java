package base.sync.util;


public class SyncObj  {

    private String key;
    private String signal;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SyncObj)) {
            return false;
        }

        SyncObj syncObj = (SyncObj) obj;
        return this.key.equals(syncObj.getKey()) && this.signal.equals(syncObj.getSignal());
    }

    @Override
    public int hashCode() {
        return this.key.hashCode()+this.signal.hashCode();
    }
}
