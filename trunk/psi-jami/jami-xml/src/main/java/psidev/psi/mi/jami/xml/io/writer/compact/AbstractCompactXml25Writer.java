package psidev.psi.mi.jami.xml.io.writer.compact;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.InMemoryLightIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class for Compact XML 2.5 writers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractCompactXml25Writer<T extends Interaction> extends AbstractXml25Writer<T> {

    private PsiXml25ElementWriter<String> availabilityWriter;
    private PsiXml25ElementWriter<Experiment> experimentWriter;
    private PsiXml25ElementWriter<Interactor> interactorWriter;

    private Class<T> type;

    private Set<Experiment> experiments;
    private Set<String> availabilities;
    private Set<Interactor> interactors;

    public AbstractCompactXml25Writer(Class<T> type) {
        super();
        experiments = new HashSet<Experiment>();
        availabilities = new HashSet<String>();
        interactors = new HashSet<Interactor>();
        this.type = type;
    }

    public AbstractCompactXml25Writer(Class<T> type, File file) throws IOException, XMLStreamException {
        super(file);
        experiments = new HashSet<Experiment>();
        availabilities = new HashSet<String>();
        interactors = new HashSet<Interactor>();
        this.type = type;
    }

    public AbstractCompactXml25Writer(Class<T> type, OutputStream output) throws XMLStreamException {
        super(output);
        experiments = new HashSet<Experiment>();
        availabilities = new HashSet<String>();
        interactors = new HashSet<Interactor>();
        this.type = type;
    }

    public AbstractCompactXml25Writer(Class<T> type, Writer writer) throws XMLStreamException {
        super(writer);
        experiments = new HashSet<Experiment>();
        availabilities = new HashSet<String>();
        interactors = new HashSet<Interactor>();
        this.type = type;
    }

    protected AbstractCompactXml25Writer(Class<T> type, XMLStreamWriter2 streamWriter) {
        super(streamWriter);
        experiments = new HashSet<Experiment>();
        availabilities = new HashSet<String>();
        interactors = new HashSet<Interactor>();
        this.type = type;
    }

    @Override
    public void close() throws MIIOException {
        this.interactors.clear();
        this.availabilities.clear();
        this.experiments.clear();
        super.close();
    }

    @Override
    public void reset() throws MIIOException {
        this.interactors.clear();
        this.availabilities.clear();
        this.experiments.clear();
        super.reset();
    }

    protected void registerAllInteractionsProperties() {
        this.interactors.clear();
        this.availabilities.clear();
        this.experiments.clear();
        Source firstSource = getCurrentSource();
        T firstInteraction = getCurrentInteraction();
        boolean started = isStarted();
        getInteractionsToWrite().clear();

        while (getInteractionsIterator().hasNext()){
            T inter = getInteractionsIterator().next();
            setCurrentInteraction(inter);
            getInteractionsToWrite().add(inter);

            Source source = extractSourceFromInteraction();
            // write first entry
            if (isStarted()){
                setStarted(false);
                setCurrentSource(source);
                registerInteractionProperties();
            }
            // write next entry after closing first one
            else if (getCurrentSource() != source){
                // stops here for the current entry
                break;
            }
            else{
                registerInteractionProperties();
            }
        }

        // reset iterator
        setInteractionsIterator(getInteractionsToWrite().iterator());
        setCurrentSource(firstSource);
        setCurrentInteraction(firstInteraction);
        setStarted(started);
    }

    protected void registerAllInteractors(T interaction){
        for (Object o : interaction.getParticipants()){
            Participant participant = (Participant)o;
            // we have a complex, we want to register default experiments
            if (!writeComplexesAsInteractors() && participant.getInteractor() instanceof Complex){
                Complex complex = (Complex)participant.getInteractor();
                // the complex will be written as interactor as it does not have any participants
                if (complex.getParticipants().isEmpty()){
                    this.interactors.add(complex);
                }
                else {
                    registerAllInteractorsAndExperimentsFrom(complex);
                }
            }
            // register interactor
            else{
                this.interactors.add(participant.getInteractor());
            }
        }
    }

    protected void registerAllInteractorsAndExperimentsFrom(ModelledInteraction interaction){
        // register default experiment
        this.experiments.add(getComplexWriter().extractDefaultExperimentFrom(interaction));

        for (Object o : interaction.getParticipants()){
            Participant participant = (Participant)o;
            // we have a complex, we want to register default experiments
            if (participant.getInteractor() instanceof Complex){
                Complex complex = (Complex)participant.getInteractor();
                // the complex will be written as interactor as it does not have any participants
                if (complex.getParticipants().isEmpty()){
                    this.interactors.add(complex);
                }
                else {
                    registerAllInteractorsAndExperimentsFrom(complex);
                }
            }
            // register interactor
            else{
                this.interactors.add(participant.getInteractor());
            }
        }
    }

    protected abstract void registerAvailabilities(T interaction);

    protected abstract void registerExperiment(T interaction);

    @Override
    protected void writeStartEntryContent() throws XMLStreamException {
        registerAllInteractionsProperties();
        // write start entry
        writeStartEntry();
        // write source
        writeSource();
        // write availability list
        if (!availabilities.isEmpty()){
            // write start availability list
            getStreamWriter().writeStartElement(PsiXml25Utils.AVAILABILITYLIST_TAG);
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (String availability : this.availabilities){
                this.availabilityWriter.write(availability);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end availability list
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write experiment list
        if (!experiments.isEmpty()){
            // write start experiment list
            getStreamWriter().writeStartElement(PsiXml25Utils.EXPERIMENTLIST_TAG);
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Experiment experiment : this.experiments){
                this.experimentWriter.write(experiment);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write interactor list
        if (!interactors.isEmpty()){
            // write start interactor list
            getStreamWriter().writeStartElement(PsiXml25Utils.INTERACTORLIST_TAG);
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Interactor interactor : this.interactors){
                this.interactorWriter.write(interactor);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end interactor list
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);

        }
        // write start interactionList
        writeStartInteractionList();
    }

    protected PsiXml25ElementWriter<String> getAvailabilityWriter() {
        return availabilityWriter;
    }

    protected void setAvailabilityWriter(PsiXml25ElementWriter<String> availabilityWriter) {
        this.availabilityWriter = availabilityWriter;
    }

    protected PsiXml25ElementWriter<Experiment> getExperimentWriter() {
        return experimentWriter;
    }

    protected void setExperimentWriter(PsiXml25ElementWriter<Experiment> experimentWriter) {
        this.experimentWriter = experimentWriter;
    }

    protected PsiXml25ElementWriter<Interactor> getInteractorWriter() {
        return interactorWriter;
    }

    protected void setInteractorWriter(PsiXml25ElementWriter<Interactor> interactorWriter) {
        this.interactorWriter = interactorWriter;
    }

    protected Set<Experiment> getExperiments() {
        return experiments;
    }

    protected Set<String> getAvailabilities() {
        return availabilities;
    }

    protected Set<Interactor> getInteractors() {
        return interactors;
    }

    @Override
    protected void initialiseDefaultElementCache() {
        setElementCache(new InMemoryLightIdentityObjectCache());
    }

    protected Class<T> getInteractionType() {
        return type;
    }

    private void registerInteractionProperties() {
        T interaction = getCurrentInteraction();
        // register all experiments
        registerExperiment(interaction);
        // register all availabilities
        registerAvailabilities(interaction);
        // register all interactors
        registerAllInteractors(interaction);
    }
}
