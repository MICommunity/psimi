package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract writer for Xml30Feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml30FeatureWriter<F extends Feature> extends AbstractXmlFeatureWriter<F> {

    public AbstractXml30FeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);

    }

    protected void writeFeatureRole(F object) throws XMLStreamException{
        if (object.getRole() != null){
            getFeatureTypeWriter().write(object.getRole(), "featureRole");
        }
    }

    protected void writeOtherAttributes(F object, boolean writeAttributeList) throws XMLStreamException{
        // nothing to write here
    }
}
