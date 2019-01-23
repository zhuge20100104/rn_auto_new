package base.utils.languages;

import base.utils.JsonUtil;
import base.utils.languages.bean.LanguageBean;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class LanguageLoader {

    public static void loadLanguageArrayToBeans(Object [][] languageArr,List<LanguageBean> languages ) {
        List<String> languageKeys = new ArrayList<>();
        for(int i=0;i<languageArr.length;i++) {
            if(languageArr[i][0].equals("keys")) {
                for(int j = 1; j<languageArr[i].length;j++) {
                    languageKeys.add(languageArr[i][j].toString());
                }
            }else {
                LanguageBean languageBean = new LanguageBean();
                languageBean.setLanguageName(languageArr[i][0].toString());
                for(int j = 1; j<languageArr[i].length;j++) {
                    languageBean.putProp(languageKeys.get(j-1),languageArr[i][j]);
                }
                languages.add(languageBean);
            }
        }
    }



    public static Object getLanguageArrayProp(List<LanguageBean> languages,String currentLanguage,String name) {
        for(LanguageBean languageBean: languages) {
            if(languageBean.getLanguageName().equals(currentLanguage)) {
                return languageBean.getProp(name);
            }
        }
        return null;
    }

    public static Object getProp(Class classPath,List<LanguageBean> languages,String currentLanguage,String name) {

        Object retVal = getLanguageArrayProp(languages,currentLanguage,name);
        if(retVal!=null)  {
            return retVal;
        }

        try {
            retVal= JsonUtil.getLanguageFileField(classPath, name);
            return retVal;
        }catch (Exception ex) {
            return null;
        }
    }
}
