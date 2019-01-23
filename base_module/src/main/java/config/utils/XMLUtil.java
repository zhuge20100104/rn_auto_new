package config.utils;

import base.exceptions.JSuitePackageShouldNotBeEmptyException;
import base.utils.JFileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.*;

public class XMLUtil {

    private static final String JAVA_PACKAGE_PATH = "src"+File.separator+"main"+File.separator+"java"+File.separator;
    private static final String TEST_XML_PATH = "src" + File.separator+"main"+File.separator+"resources"+File.separator+"xml"+File.separator;
    private static final String ROOT_BUILD_GRADLE_PATH = "build.gradle";

    private static  String getTestNameFromShortName(String shortName) {
        String [] shortNameArr = shortName.split(File.separator+File.separator);
        return shortNameArr[shortNameArr.length-1];
    }
    //osvc.tests.bui.mobile
    public static List<CaseElement> getAllJavaFilesList(String suiteStr) {
        List<CaseElement> caseElements = new ArrayList<CaseElement>();

        String folderPath = JAVA_PACKAGE_PATH;

        String [] packageArray = suiteStr.split("\\.");


        for(String packageEle : packageArray) {
            folderPath += packageEle;
            folderPath += File.separator;
        }

        List<String> tmpJavaFiles = new ArrayList<>();
        List<String> resultJavaFiles = JFileUtils.recusiveDir(new File(folderPath),tmpJavaFiles,"java");

        System.out.println("start print java files===");
        for(String javaFile: resultJavaFiles) {
            System.out.println(javaFile);
        }
        System.out.println("end print java files===");

        List<SingleCaseElement> singleCases = new ArrayList<>();

        for(String javaFile : resultJavaFiles) {
            SingleCaseElement singleCase = new SingleCaseElement();

            if(javaFile.contains("Web")) {
                String [] javaFileArray = javaFile.split("Web");
                String leftEle  = javaFileArray[0];
                singleCase.setShortName(leftEle);
                singleCase.setFullName(javaFile);
                singleCases.add(singleCase);
            }else if(javaFile.contains("Mobile")) {
                String [] javaFileArray = javaFile.split("Mobile");
                String leftEle  = javaFileArray[0];
                singleCase.setShortName(leftEle);
                singleCase.setFullName(javaFile);
                singleCases.add(singleCase);
            }else {
                singleCase.setShortName(javaFile);
                singleCase.setFullName(javaFile);
                singleCases.add(singleCase);
            }
        }


        Collections.sort(singleCases, new Comparator<SingleCaseElement>() {
            @Override
            public int compare(SingleCaseElement case1, SingleCaseElement case2) {
                Collator collator = Collator.getInstance(Locale.ENGLISH);
                return collator.compare(case1.getShortName(), case2.getShortName());
            }
        });

        System.out.println("start print Single cases===");
        for(SingleCaseElement sce: singleCases) {
            System.out.println(sce.getShortName()+":"+sce.getFullName());
        }
        System.out.println("end print Single cases===");


        int count = 0;


        List<Integer> listInt = new ArrayList<>();

        CaseElement caseEle = new CaseElement();
        SingleCaseElement tmp = singleCases.get(0);
        caseEle.addCase(tmp.getFullName());
        caseEle.setTestName(getTestNameFromShortName(tmp.getShortName()));
        caseElements.add(caseEle);

        for(int i=1; i<singleCases.size();i++) {
            if(!tmp.getShortName().equals(singleCases.get(i).getShortName())){
                caseEle = new CaseElement();
                tmp=singleCases.get(i);
                caseEle.setTestName(getTestNameFromShortName(tmp.getShortName()));
                caseEle.addCase(tmp.getFullName());
                caseElements.add(caseEle);
                count=1;
            }else {
                caseEle.addCase(singleCases.get(i).getFullName());
                count ++;
            }
        }

        System.out.println("Start to print all case elements");
        for(CaseElement caseE : caseElements) {
            System.out.println("Print a couple of case====");
            System.out.println(caseE.getCount());
            System.out.println(caseE.getTestName());
            for(String caseFullName : caseE.getCaseNames()) {
                System.out.println(caseFullName);
            }
            System.out.println("End print a couple of case====");
        }
        System.out.println("End print all case elements");

        return caseElements;

    }


    //Write to xml with pretty format
    public static void writeXML(String fileName,Document content) {
        FileWriter out = null;
        try {
            out = new FileWriter( fileName );
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter( out, format );
            writer.write(content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static Document generateXMLFromCaseElements(List<CaseElement> caseElements,String suiteStr) {
        Document content = DocumentHelper.createDocument();
        content.addDocType("suite",null,"http://testng.org/testng-1.0.dtd" );

        Element root = content.addElement( "suite" );
        root.addAttribute("name", suiteStr + " Test");
        root.addAttribute("verbose","1");

        for(CaseElement caseEle:caseElements) {
            Element test = root.addElement("test");
            test.addAttribute("name","Test - " + caseEle.getTestName() +" Test");
            test.addAttribute("parallel","classes");
            test.addAttribute("thread-count",""+caseEle.getCount());
            Element classes = test.addElement("classes");
            for(String className: caseEle.getCaseNames()) {
                Element classEle = classes.addElement("class");
                className = className.replace(JAVA_PACKAGE_PATH,"");
                className = className.replace(File.separator,".");
                className = className.replace(".java","");
                classEle.addAttribute("name",className);
            }
        }
        return content;
    }

    public static  void  generateXML(String suiteStr) throws Exception{

        // Already a xml string, no need to generate again
        if(suiteStr.endsWith("xml")) {
            String srcFileName = TEST_XML_PATH + suiteStr;
            String dstFileName = TEST_XML_PATH + "currentTest.xml";
            JFileUtils.copyFile(srcFileName,dstFileName);
            return;
        }

        //Empty suiteStr throw exception
        if(suiteStr.isEmpty()) {
            throw new JSuitePackageShouldNotBeEmptyException("Suite package should not be empty!!");
        }

        List<CaseElement> elements = getAllJavaFilesList(suiteStr);

        Document content = generateXMLFromCaseElements(elements,suiteStr);

        writeXML(TEST_XML_PATH+"currentTest.xml",content);
    }

}
