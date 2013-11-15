package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25NonExperimentalInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Date;
import java.util.Set;

/**
 * Abstract class for interactions without an experiment.
 * XML 2.5 is always expecting an experiment.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXml25InteractionWithoutExperimentWriter<T extends Interaction, P extends Participant> extends AbstractXml25InteractionWriter<T,P> implements PsiXml25NonExperimentalInteractionWriter<T>{

    private Experiment defaultExperiment;
    private PsiXml25ElementWriter<Experiment> experimentWriter;

    public AbstractXml25InteractionWithoutExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25ElementWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
    }

    public AbstractXml25InteractionWithoutExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<P> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter, interactionTypeWriter, attributeWriter, inferredInteractionWriter);
    }

    protected void writeExperimentRef() throws XMLStreamException {
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeStartElement("experimentList");
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeStartElement("experimentRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdFor(getDefaultExperiment())));
        getStreamWriter().writeEndElement();
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeEndElement();
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeExperimentDescription() throws XMLStreamException {
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeStartElement("experimentList");
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.experimentWriter.write(getDefaultExperiment());
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        getStreamWriter().writeEndElement();
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    public Experiment getDefaultExperiment() {
        if (this.defaultExperiment == null){
            initialiseDefaultExperiment();
        }
        return defaultExperiment;
    }

    public void setDefaultExperiment(Experiment defaultExperiment) {
        if (defaultExperiment == null){
             throw new IllegalArgumentException("The default experiment is mandatory");
        }
        this.defaultExperiment = defaultExperiment;
    }

    protected void initialiseDefaultExperiment(){
        this.defaultExperiment = new DefaultExperiment(new DefaultPublication("Mock publication for modelled interactions that are not interaction evidences.",(String)null,(Date)null));
    }
}
