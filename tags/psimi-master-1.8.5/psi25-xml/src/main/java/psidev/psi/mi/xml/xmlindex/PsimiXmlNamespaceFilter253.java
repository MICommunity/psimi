package psidev.psi.mi.xml.xmlindex;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * TODO comment that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlNamespaceFilter253 extends XMLFilterImpl {

    public static final String PSIMI_NAMESPACE = "net:sf:psidev:mi";

    public PsimiXmlNamespaceFilter253( XMLReader arg ) {
        super( arg );
    }

    @Override
    public void startElement( String arg0, String arg1, String arg2, Attributes arg3 ) throws SAXException {
        super.startElement( PSIMI_NAMESPACE, arg1, arg2, arg3 );
    }
}
