package base.utils.languages.bean;

import java.util.HashMap;

public class LanguageBean {
    private String languageName = "";
    private HashMap<String,Object> languageMap = new HashMap<>();

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public HashMap<String, Object> getLanguageMap() {
        return languageMap;
    }

    public void setLanguageMap(HashMap<String, Object> languageMap) {
        this.languageMap = languageMap;
    }

    public void putProp(String name,Object value) {
        this.languageMap.put(name,value);
    }

    public Object getProp(String name) {
        return this.languageMap.get(name);
    }
}
