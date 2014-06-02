package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for named abstract interactions (no experimental evidences).
 * Participants, features, experiments also have expanded names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlNamedModelledWriter extends AbstractCompactXmlWriter<ModelledInteraction> {

    public CompactXmlNamedModelledWriter() {
        super(ModelledInteraction.class);
    }

    public CompactXmlNamedModelledWriter(File file) throws IOException, XMLStreamException {
        super(ModelledInteraction.class, file);
    }

    public CompactXmlNamedModelledWriter(OutputStream output) throws XMLStreamException {
        super(ModelledInteraction.class, output);
    }

    public CompactXmlNamedModelledWriter(Writer writer) throws XMLStreamException {
        super(ModelledInteraction.class, writer);
    }

    public CompactXmlNamedModelledWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(ModelledInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(ModelledInteraction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(ModelledInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
    }

    @Override
    protected void registerInteractionProperties() {
        super.registerInteractionProperties();
        for (CooperativeEffect effect : getCurrentInteraction().getCooperativeEffects()){
            for (ModelledInteraction interaction : effect.getAffectedInteractions()){
                registerAllInteractorsAndExperimentsFrom(interaction);
            }
        }
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource();
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, true, PsiXmlType.compact, InteractionCategory.modelled, ComplexType.n_ary);
    }
}
