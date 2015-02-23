package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlPublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Xml30 writer for modelled confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlModelledConfidenceWriter extends XmlConfidenceWriter {
    private PsiXmlPublicationWriter publicationWriter;

    public XmlModelledConfidenceWriter(XMLStreamWriter writer){
        super(writer);
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
    protected void writeOtherProperties(Confidence object) {
        // write publication if not done yet
        ModelledConfidence modelledParam = (ModelledConfidence)object;

        // write bibref if necessary
        if (modelledParam.getPublication() != null){
            getPublicationWriter().write(modelledParam.getPublication());
        }
    }
}
