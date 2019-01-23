package netsuite.components;

import base.search.engine.parser.JBy;
import base.exceptions.JBaseException;
import base.search.SearchTools;
import config.utils.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

public class TabWrapper {
    protected WebDriver driver;
    protected SearchTools sTools;
    public TabWrapper(WebDriver driver) {
        this.driver = driver;
        sTools = new SearchTools(driver);
    }

    public void clickFormTab(String formTab) throws Exception {
        this.clickTab(formTab, false);
    }

    private JBy getTabsCollapseLocator() {
        String byLocator = "//td[contains(@class,'unrollformtabbar')]//a[img[contains(@src,'/rolltabs.png')]]";
        JBy by = new JBy();
        by.setType("xpath");
        by.setValue(byLocator);
        return by;
    }

    private boolean areTabsExpanded() throws Exception {
        JBy by = this.getTabsCollapseLocator();
        return  this.sTools.wait(by.getType(),by.getValue());
    }

    private JBy getFormSubTabLocator(String formSubTab, boolean isSelected, boolean bTabsUnrolled) {
        String selectedSuffix = isSelected ? (bTabsUnrolled ? "expand" : "on") : "";
        String tabsXpath = "//table[contains(@class,'bgsubtabbar') and not(ancestor::div[contains(@style,'display:none')]) and not(ancestor::div[contains(@style,'display: none')])]//a[contains(.,'%s') and contains(@class,'formsubtabtext%s')]";
        tabsXpath = String.format(tabsXpath, formSubTab, selectedSuffix);
        String unrollXpath = "//table[contains(@id,'_pane')]//div[contains(@class,'formsubtabtext') and contains(@class,'unrollformsubtabheader%s') and a[contains(.,'%s')]]";
        unrollXpath = String.format(unrollXpath, selectedSuffix, formSubTab);
        String xpathStr = bTabsUnrolled ? unrollXpath : tabsXpath;

        JBy by = new JBy();
        by.setType("xpath");
        by.setValue(xpathStr);
        return by;
    }

    private List<WebElement> getFormSubTabHandle(String formSubTab) throws Exception{
        boolean bTabsUnrolled = this.areTabsExpanded();
        JBy locator = this.getFormSubTabLocator(formSubTab, false, bTabsUnrolled);
        return this.sTools.findAll(locator.getType(),locator.getValue());
    }

    private JBy getFormTabLocator(String formTab, boolean isSelected, boolean isExact, boolean tabsUnrolled) {
        String exactMatch = String.format("normalize-space(translate(.,'%s',' '))='%s'", Character.toString(' ') , formTab);
        String partialMatch = String.format("contains(.,'%s')", formTab);
        String selectedSuffix = isSelected ? (tabsUnrolled ? "expand" : "on") : "";
        String tabsXpath = "//table[contains(@class,'bgtabbar')]//a[%s and contains(@class,'formtabtext%s')]";
        tabsXpath = String.format(tabsXpath, isExact ? exactMatch : partialMatch, selectedSuffix);
        String unrollXpath = "//table[contains(@id,'_pane')]//div[contains(@class,'formtabtext') and contains(@class,'unrollformtabheader%s') and a[%s]]";
        unrollXpath = String.format(unrollXpath, selectedSuffix, isExact ? exactMatch : partialMatch);
        String xpathStr = tabsUnrolled ? unrollXpath : tabsXpath;

        JBy by = new JBy();
        by.setType("xpath");
        by.setValue(xpathStr);
        return by;
    }

    private List<WebElement> getFormTabHandle(String formTab) throws Exception {
        boolean bTabsUnrolled = this.areTabsExpanded();
        JBy locator = this.getFormTabLocator(formTab, false, true, bTabsUnrolled);
        List<WebElement> element = sTools.findAll(locator.getType(),locator.getValue());
        if (element == null || element.size() < 1) {
            locator = this.getFormTabLocator(formTab, false, false, bTabsUnrolled);
            element = this.sTools.findAll(locator.getType(),locator.getValue());
        }

        return element;
    }



    private boolean isTabSelected(WebElement handle, boolean isSubTab) {
        boolean isSelected = false;
        if (handle != null) {
            String sClass = handle.getAttribute("class");
            if (isSubTab) {
                isSelected = sClass.contains("formsubtabtexton") || sClass.contains("unrollformsubtabheaderexpand");
            } else {
                isSelected = sClass.contains("formtabtexton") || sClass.contains("unrollformtabheaderexpand");
            }
        }

        return isSelected;
    }


    public boolean isFormSubTabSelected(String formSubTab) throws Exception{
        List<WebElement> handles = this.getFormSubTabHandle(formSubTab);
        if (handles != null && handles.size() > 0) {
            Iterator var3 = handles.iterator();

            WebElement handle;
            do {
                if (!var3.hasNext()) {
                    return true;
                }

                handle = (WebElement) var3.next();
            } while(this.isTabSelected(handle, true));

            return false;
        } else {
            return false;
        }
    }


    public boolean isFormTabSelected(String formTab) throws Exception {
        List<WebElement> handles = this.getFormTabHandle(formTab);
        return handles != null && handles.size() > 0 && this.isTabSelected((WebElement) handles.get(0), false);
    }

    public void clickTab(String tab, boolean isSubTab) throws Exception {
        String tabType = isSubTab ? " form subtab " : " form tab ";
        System.out.println("Attempting to click" + tabType + tab);
        List<WebElement> handles = isSubTab ? this.getFormSubTabHandle(tab) : this.getFormTabHandle(tab);
        if (handles != null && handles.size() != 0) {
            label88: {
                if (isSubTab) {
                    if (!this.isFormSubTabSelected(tab)) {
                        break label88;
                    }
                } else if (!this.isFormTabSelected(tab)) {
                    break label88;
                }
                return;
            }

            boolean bTabsUnrolled = this.areTabsExpanded();
            Iterator var6 = handles.iterator();

            while(var6.hasNext()) {
                WebElement handle = (WebElement) var6.next();
                int retryCount = 3;

                do {
                    handle.click();
                    --retryCount;
                } while(retryCount > 0 && !this.isTabSelected(handle, isSubTab));

                if (!bTabsUnrolled || !isSubTab) {
                    break;
                }
            }

            try {
                JBy selectedLocator = isSubTab ? this.getFormSubTabLocator(tab, true, bTabsUnrolled) : this.getFormTabLocator(tab, true, false, bTabsUnrolled);
                this.sTools.wait(selectedLocator.getType(),selectedLocator.getValue());
                String loadingXpath = "//img[contains(@src,'loading')]";
                JBy loadingLocator = new JBy();
                loadingLocator.setType("xpath");
                loadingLocator.setValue(loadingXpath);
                WebElement loadingHandle = sTools.find(loadingLocator.getType(),loadingLocator.getValue());
            } catch (Exception var9) {
                System.out.println(String.format("After clicking '%s' %s, machine didn't complete loading", tab, tabType));
                System.out.println(StringUtils.getStackTrace(var9));
            }

        } else {
            throw new JBaseException("Could not find" + tabType + tab);
        }
    }
}
