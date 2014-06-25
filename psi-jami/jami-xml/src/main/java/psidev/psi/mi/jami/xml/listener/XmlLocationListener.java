package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlLocator;

import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

/**
 * Listener for the location of an xml element
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/14</pre>
 */

public class XmlLocationListener extends Unmarshaller.Listener{

    private XMLStreamReader xsr;

    public XmlLocationListener(XMLStreamReader xsr) {
        this.xsr = xsr;
    }

    @Override
    public void beforeUnmarshal(Object target, Object parent) {
        if (target instanceof FileSourceContext){
           FileSourceContext context = (FileSourceContext)target;
           Location location = xsr.getLocation();
           context.setSourceLocator(new PsiXmlLocator(location.getLineNumber(), location.getColumnNumber(), location.getCharacterOffset()));
        }
    }
}
