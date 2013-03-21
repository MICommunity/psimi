package psidev.psi.mi.validator.extension.xpath;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.xml.model.HasId;
import psidev.psi.tools.validator.Context;
import psidev.psi.tools.validator.xpath.XPathResult;

/**
 * <b> -- Short Description -- </b>.
 * <p/>
 * -- Description of Class goes here -- </p>
 *
 * @author Matthias Oesterheld
 * @version $Id$
 * @since 05.01.2006; 15:26:34
 */
public class Mi25XPathResult extends XPathResult {

    public Mi25XPathResult( Pointer pointer, JXPathContext rootContext ) {
        super( pointer, rootContext );
    }

    public Context getContext() {
        Mi25Context context = new Mi25Context();
        Pointer root = rootContext.getPointer( "self::node()" );
        Pointer currentPointer = pointer;
        while ( !currentPointer.equals( root ) ) {
            Object o = currentPointer.getNode();
            if (o instanceof FileSourceContext){
                context.extractObjectIdAndLabelFrom((FileSourceContext) o);
            }
            else if (o instanceof HasId){
                context.setId(((HasId)o).getId());
            }
            JXPathContext relCTX = rootContext.getRelativeContext( currentPointer );
            currentPointer = relCTX.getPointer( "parent::node()" );
        }
        return context;
    }

}
