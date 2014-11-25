package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlRangeWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract writer for Xml30Feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXmlFeatureWriter<F extends Feature> extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlFeatureWriter<F> {

    public AbstractXmlFeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);

    }

    @Override
    protected void writeParameters(F object) throws XMLStreamException {
        // nothing to write
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseRangeWriter() {
         super.setRangeWriter(new XmlRangeWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseFeatureTypeWriter() {
        super.setFeatureTypeWriter(new XmlCvTermWriter(getStreamWriter()));
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
