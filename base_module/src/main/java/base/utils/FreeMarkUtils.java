package base.utils;

import base.report.beans.SuiteInfo;
import base.report.beans.TestInfo;
import config.utils.ConfigUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkUtils {

    public static void processReport(List<SuiteInfo> suiteInfoList) throws Exception{
        String path = "report" + File.separator + "templates" + File.separator;
        String reportTemplateFolder = ConfigUtil.getReportTemplateFolder();
        String reportTemplateName = ConfigUtil.getReportTemplateName();

        path += reportTemplateFolder;
        File cfgFile = new File(path);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(cfgFile);
        Template template = cfg.getTemplate(reportTemplateName);

        String suffixStr = JDateUtils.getCurrentTimeStr() + ".html";
        String htmlOutputPrefix = ConfigUtil.getHtmlOutputPrefix();
        String htmlOutputFolder = ConfigUtil.getHtmlOutputFolder();
        String htmlOutputFolderFullPath = "report" + File.separator + htmlOutputFolder;

        File fFolderFullPath = new File(htmlOutputFolderFullPath);
        if(!fFolderFullPath.exists()){
            fFolderFullPath.mkdirs();
        }

        String htmlOutputFilePath = htmlOutputFolderFullPath + File.separator + htmlOutputPrefix + "_" +suffixStr;

        File outputHtmlFile = FileUtils.getFile(htmlOutputFilePath);
        Writer out = new FileWriter(outputHtmlFile);
        Map<String, Object> map = new HashMap<>();

        int allPassCount = 0;
        int allFailCount = 0;
        int allSkipCount = 0;
        for(SuiteInfo suiteInfo:suiteInfoList) {
            allPassCount += suiteInfo.getPass();
            allFailCount += suiteInfo.getFail();
            allSkipCount += suiteInfo.getSkip();
        }

        map.put("suiteInfoList",suiteInfoList);
        map.put("pass",allPassCount);
        map.put("fail",allFailCount);
        map.put("skip",allSkipCount);
        map.put("datestr",JDateUtils.getCurrentDateStr());
        map.put("timestr",JDateUtils.getCurrentSubTimeStr());


        template.process(map,out);
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws Exception{

        List<SuiteInfo> suiteInfoList = new ArrayList<>();

        SuiteInfo suiteInfo = new SuiteInfo();
        suiteInfo.setTotal(3);
        suiteInfo.setPass(1);
        suiteInfo.setFail(2);
        suiteInfo.setSkip(0);

        TestInfo testInfo = new TestInfo();
        testInfo.setClassName("myClass");
        testInfo.setMethodName("test1");
        testInfo.setReason("没找到元素");
        testInfo.setExceptionDetails("osvc.JElementNotFoundException");

        suiteInfo.getFailTests().add(testInfo);
        testInfo = new TestInfo();
        testInfo.setClassName("myClass2");
        testInfo.setMethodName("test2");
        testInfo.setReason("没找到元素");
        testInfo.setExceptionDetails("osvc.JElementNotFoundException");
        suiteInfo.getFailTests().add(testInfo);

        suiteInfoList.add(suiteInfo);


        FreeMarkUtils.processReport(suiteInfoList);
    }
}
