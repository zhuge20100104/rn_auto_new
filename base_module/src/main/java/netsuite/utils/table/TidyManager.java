package netsuite.utils.table;

import org.w3c.tidy.Tidy;

public class TidyManager {

    private static Tidy tidy = null;

    public static Tidy getTidy() {

        if(tidy == null) {
            tidy = new Tidy();
            tidy.setQuiet(true);
            tidy.setXHTML(false);
            tidy.setXmlOut(true);
            tidy.setEscapeCdata(false);
            tidy.setWrapSection(false);
            tidy.setWrapJste(false);
            tidy.setWrapAsp(false);
            tidy.setWrapPhp(false);
            tidy.setWrapScriptlets(false);
            tidy.setLowerLiterals(false);
            tidy.setDropEmptyParas(false);
            tidy.setFixComments(false);
            tidy.setTrimEmptyElements(false);
            tidy.setQuoteAmpersand(false);
            tidy.setFixUri(false);
            tidy.setJoinStyles(false);
            tidy.setShowWarnings(false);
        }
        return tidy;
    }
}
