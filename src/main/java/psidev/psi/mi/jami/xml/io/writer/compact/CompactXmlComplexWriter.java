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
 * Compact PSI-XML writer for named biological complexes (no experimental evidences).
 * Participants, features, experiments also have expanded names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlComplexWriter extends AbstractCompactXmlWriter<Complex> {

    public CompactXmlComplexWriter() {
        super(Complex.class);
    }

    public CompactXmlComplexWriter(File file) throws IOException, XMLStreamException {
        super(Complex.class, file);
    }

    public CompactXmlComplexWriter(OutputStream output) throws XMLStreamException {
        super(Complex.class, output);
    }

    public CompactXmlComplexWriter(Writer writer) throws XMLStreamException {
        super(Complex.class, writer);
    }

    public CompactXmlComplexWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(Complex.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(Complex interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(Complex interaction) {
        Experiment exp = getInteractionWriter().extractDefaultExperimentFrom(interaction);
        if (exp != null){
            getExperiments().add(exp);
        }
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
        super.initialiseSubWriters(false, true, PsiXmlType.compact, InteractionCategory.complex, ComplexType.n_ary);
    }
}
