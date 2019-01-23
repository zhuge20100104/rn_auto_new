package osvc.tests.integrator.web.Chat;

import base.BaseWebTestCase;
import org.testng.annotations.BeforeMethod;
import utils.JConfigUtil;

public class CustomIncidentTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String url = JConfigUtil.getBUIUrl();
        setupCase(url);
    }


}
