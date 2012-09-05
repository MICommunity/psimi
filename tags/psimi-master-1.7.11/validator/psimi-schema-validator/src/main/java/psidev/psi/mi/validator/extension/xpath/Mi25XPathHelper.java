package psidev.psi.mi.validator.extension.xpath;

import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.xpath.XPathHelper;
import psidev.psi.tools.validator.xpath.XPathResult;

import java.util.List;

/**
 *
 * @author Matthias Oesterheld
 * @version $Id$
 * @since 05.01.2006; 16:20:11
 */
public class Mi25XPathHelper extends XPathHelper {

    public static List<XPathResult> evaluateXPath( String xpath, Object root ) throws ValidatorException {
        return evaluateXPathWithClass( xpath, root, Mi25XPathResult.class );
    }

}
