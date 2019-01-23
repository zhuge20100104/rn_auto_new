package base.utils.ocr.beans;

import java.util.List;

public class OciResults {
    private long log_id;
    private List<OneWord> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<OneWord> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<OneWord> words_result) {
        this.words_result = words_result;
    }
}
