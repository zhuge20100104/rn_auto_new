package config.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class JProperties {
    private static final Logger log = LoggerFactory.getLogger(JProperties.class);

    private static final String PROPERTIES_FILE_PATH = "config/testautomation.properties";
    private static final String OVERRIDES_FILE_PATH = "config/overrides.properties";

    protected Properties properties;


    public JProperties() throws IOException {
        this.readProperties();
    }


    public String getPropertyValue(String propertyName) {
        String property = this.properties.getProperty(propertyName.trim());

        if (property != null && !"".equals(property)) {
            property = property.trim();
        }

        log.debug("retrieving property [name=" + property + ", value=" + (property == null ? "null" : property) + "]");
        return property;
    }

    public void setPropertyValue(String propertyName, String propertyValue) {
        this.properties.setProperty(propertyName.trim(),propertyValue.trim());
    }




    public String getConfigFileSuffix() {
        String suffix = System.getenv("testautomation.config.file_suffix");
        if(suffix!=null) {
            return suffix;
        }else {
            return this.getPropertyValue("testautomation.config.file_suffix");
        }
    }



    private void loadOriginConfigFile(FileInputStream testautomationFileInputStream, InputStreamReader testautomationInputStreamReader)
     throws IOException{
        testautomationFileInputStream = new FileInputStream(new File(PROPERTIES_FILE_PATH).getAbsoluteFile());
        testautomationInputStreamReader = new InputStreamReader(testautomationFileInputStream, "UTF-8");
        this.properties.load(testautomationInputStreamReader);
    }



    private boolean shouldOverrideConfigFile(String fconfigFileSuffix) {
        return fconfigFileSuffix!=null && !fconfigFileSuffix.equals("default");
    }


    private String loadOverrideOriginFile(FileInputStream testautomationFileInputStream, InputStreamReader testautomationInputStreamReader)
            throws IOException{

        String fconfigFileSuffix= getConfigFileSuffix();
        if(shouldOverrideConfigFile(fconfigFileSuffix)) {
            String propertiesFilePath = PROPERTIES_FILE_PATH.replace("testautomation","testautomation"+fconfigFileSuffix);
            File propertiesFile = new File(propertiesFilePath);

            if(propertiesFile.exists()) {
                this.properties.clear();
                testautomationFileInputStream = new FileInputStream(new File(propertiesFilePath).getAbsoluteFile());
                testautomationInputStreamReader = new InputStreamReader(testautomationFileInputStream, "UTF-8");
                this.properties.load(testautomationInputStreamReader);
            }
        }

        return fconfigFileSuffix;
    }



    private String getOverridesFilePath(String fconfigFileSuffix) {
        String overrideFilePath;
        if(!shouldOverrideConfigFile(fconfigFileSuffix)) {
            overrideFilePath = OVERRIDES_FILE_PATH;
        }else {
            overrideFilePath = OVERRIDES_FILE_PATH.replace("overrides","overrides"+fconfigFileSuffix);
        }
        return overrideFilePath;
    }


    private void loadOverrideFiles(String foverrideFilePath) throws IOException {
        File overridesFile = new File(foverrideFilePath);

        if (overridesFile.exists()) {
            FileInputStream overridesFileInputStream = null;
            InputStreamReader overridesInputStreamReader = null;

            try {
                Properties overridesProperties = new java.util.Properties();
                overridesFileInputStream = new FileInputStream(overridesFile);
                overridesInputStreamReader = new InputStreamReader(overridesFileInputStream, "UTF-8");
                overridesProperties.load(overridesInputStreamReader);
                this.properties.putAll(overridesProperties);
            } finally {
                if (overridesInputStreamReader != null) {
                    overridesInputStreamReader.close();
                }

                if (overridesFileInputStream != null) {
                    overridesFileInputStream.close();
                }

            }
        }
    }


    private void loadSystemEnvs() {
        this.properties.putAll(System.getenv());
        this.properties.putAll(System.getProperties());
    }

    
    public void readProperties() throws IOException {
        this.properties = new Properties();
        FileInputStream testautomationFileInputStream = null;
        InputStreamReader testautomationInputStreamReader = null;
        String fconfigFileSuffix;

        try {
            //Load origin config file properties
            this.loadOriginConfigFile(testautomationFileInputStream,testautomationInputStreamReader);
            //May have overrides for original properties file, should read it,eg. [testautomation1.properties]
            fconfigFileSuffix = loadOverrideOriginFile(testautomationFileInputStream,testautomationInputStreamReader);
        } finally {
            if (testautomationInputStreamReader != null) {
                testautomationInputStreamReader.close();
            }

            if (testautomationFileInputStream != null) {
                testautomationFileInputStream.close();
            }
        }


        //Load [overrides.properties] file
        String foverrideFilePath = getOverridesFilePath(fconfigFileSuffix) ;
        this.loadOverrideFiles(foverrideFilePath);

        //Load system environment variables
        this.loadSystemEnvs();
    }
}
