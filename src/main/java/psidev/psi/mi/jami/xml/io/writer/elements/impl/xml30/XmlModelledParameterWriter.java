package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlPublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer of a modelled parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlModelledParameterWriter extends AbstractXmlParameterWriter {

    private PsiXmlPublicationWriter publicationWriter;

    public XmlModelledParameterWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    public PsiXmlPublicationWriter getPublicationWriter() {
        if (this.publicationWriter == null){
            initialisePublicationWriter();
        }
        return publicationWriter;
    }

    protected void initialisePublicationWriter() {
        this.publicationWriter = new XmlPublicationWriter(getStreamWriter());
    }

    public void setPublicationWriter(PsiXmlPublicationWriter publicationWriter) {
        this.publicationWriter = publicationWriter;
    }

    @Override
    protected void writeOtherProperties(Parameter object) throws XMLStreamException {
        // write publication if not done yet
        ModelledParameter modelledParam = (ModelledParameter)object;

        // write bibref if necessary
        if (modelledParam.getPublication() != null){
            getPublicationWriter().write(modelledParam.getPublication());
        }
    }
}
