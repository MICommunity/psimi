package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for interaction evidence writers that write expanded interactions (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionEvidenceWriter<I extends InteractionEvidence>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.AbstractXmlInteractionEvidenceWriter<I> {

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);

    }

    @Override
    protected void initialiseExperimentWriter(){
        super.setExperimentWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter(getStreamWriter(), getObjectIndex()));
    }
}
