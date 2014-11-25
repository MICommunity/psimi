package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
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
 * Compact PSI-XML writer for named modelled binary interactions (no experimental evidences).
 * Participants, features, experiments also have expanded names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlNamedModelledBinaryWriter extends AbstractCompactXmlWriter<ModelledBinaryInteraction> {

    public CompactXmlNamedModelledBinaryWriter() {
        super(ModelledBinaryInteraction.class);
    }

    public CompactXmlNamedModelledBinaryWriter(File file) throws IOException, XMLStreamException {
        super(ModelledBinaryInteraction.class, file);
    }

    public CompactXmlNamedModelledBinaryWriter(OutputStream output) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, output);
    }

    public CompactXmlNamedModelledBinaryWriter(Writer writer) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, writer);
    }

    public CompactXmlNamedModelledBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(ModelledBinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(ModelledBinaryInteraction interaction) {
        // nothing to do
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
    protected void registerExperiment(ModelledBinaryInteraction interaction) {
        Experiment exp = getInteractionWriter().extractDefaultExperimentFrom(interaction);
        if (exp != null){
            getExperiments().add(exp);
        }
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource();
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, true, PsiXmlType.compact, InteractionCategory.modelled, ComplexType.binary);
        // initialise same default experiment
        getComplexWriter().setDefaultExperiment(getInteractionWriter().getDefaultExperiment());
    }
}