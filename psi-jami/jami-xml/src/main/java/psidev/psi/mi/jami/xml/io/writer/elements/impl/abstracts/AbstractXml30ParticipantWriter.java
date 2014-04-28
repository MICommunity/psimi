package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml30StoichiometryWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract Xml 3.0 writer for participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml30ParticipantWriter<P extends Participant, F extends Feature>
        extends  AbstractXmlParticipantWriter<P,F>{

    private PsiXmlElementWriter<Stoichiometry> stoichiometryWriter;

    public AbstractXml30ParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<Stoichiometry> getStoichiometryWriter() {
        if (this.stoichiometryWriter == null){
            this.stoichiometryWriter = new Xml30StoichiometryWriter(getStreamWriter());
        }
        return stoichiometryWriter;
    }

    public void setStoichiometryWriter(PsiXmlElementWriter<Stoichiometry> stoichiometryWriter) {
        this.stoichiometryWriter = stoichiometryWriter;
    }

    protected void writeStoichiometry(P object){
        if (object.getStoichiometry() != null){
            getStoichiometryWriter().write(object.getStoichiometry());
        }
    }

    protected void writeOtherAttributes(P object, boolean writeAttributeList) throws XMLStreamException {
        // nothing to do here
    }
}
