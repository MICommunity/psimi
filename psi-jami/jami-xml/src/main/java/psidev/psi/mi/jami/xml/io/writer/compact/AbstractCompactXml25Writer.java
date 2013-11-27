package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ExperimentWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

/**
 * Abstract class for Compact XML 2.5 writers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractCompactXml25Writer<T extends Interaction> extends AbstractXml25Writer<T> {

    private PsiXml25ElementWriter<String> availabilityWriter;
    private PsiXml25ExperimentWriter experimentWriter;
    private PsiXml25ElementWriter<Interactor> interactorWriter;
    private List<T> subInteractionsToWrite;

    private Class<T> type;

    private Set<Experiment> experiments;
    private Set<String> availabilities;
    private Set<Interactor> interactors;

    public AbstractCompactXml25Writer(Class<T> type) {
        super();
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    public AbstractCompactXml25Writer(Class<T> type, File file) throws IOException, XMLStreamException {
        super(file);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    public AbstractCompactXml25Writer(Class<T> type, OutputStream output) throws XMLStreamException {
        super(output);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    public AbstractCompactXml25Writer(Class<T> type, Writer writer) throws XMLStreamException {
        super(writer);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    protected AbstractCompactXml25Writer(Class<T> type, XMLStreamWriter streamWriter) {
        super(streamWriter);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);

        if (options.containsKey(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET)){
            setExperimentSet((Set<Experiment>) options.get(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET));
        }
        // use the default cache option
        else{
            initialiseDefaultExperimentSet();
        }
        if (options.containsKey(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET)){
            setAvailabilitySet((Set<String>) options.get(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET));
        }
        // use the default cache option
        else{
            initialiseDefaultAvailabilitySet();
        }
        if (options.containsKey(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET)){
            setInteractorSet((Set<Interactor>) options.get(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET));
        }
        // use the default cache option
        else{
            initialiseDefaultInteractorSet();
        }
    }

    @Override
    public void close() throws MIIOException {
        this.interactors = null;
        this.availabilities = null;
        this.experiments = null;
        this.subInteractionsToWrite.clear();
        super.close();
    }

    @Override
    public void reset() throws MIIOException {
        this.interactors = null;
        this.availabilities = null;
        this.experiments = null;
        this.subInteractionsToWrite.clear();
        super.reset();
    }

    public void setExperimentSet(Set<Experiment> experiments) {
        this.experiments = experiments;
    }

    public void setAvailabilitySet(Set<String> availabilities) {
        this.availabilities = availabilities;
    }

    public void setInteractorSet(Set<Interactor> interactors) {
        this.interactors = interactors;
    }

    protected void registerAllInteractionsProperties() {
        // clear and initialise sets if not done yet
        getInteractors().clear();
        getAvailabilities().clear();
        getExperiments().clear();

        Source firstSource = getCurrentSource();
        T firstInteraction = getCurrentInteraction();
        boolean started = isStarted();
        this.subInteractionsToWrite.clear();

        // register first interaction
        // write first entry
        if (isStarted()){
            setStarted(false);
            registerInteractionProperties();
        }
        else{
            registerInteractionProperties();
        }

        while (getInteractionsIterator().hasNext()){
            T inter = getInteractionsIterator().next();
            setCurrentInteraction(inter);
            this.subInteractionsToWrite.add(inter);

            Source source = extractSourceFromInteraction();
            // write next entry after closing first one
            if (getCurrentSource() != source){
                // stops here for the current entry
                break;
            }
            else{
                registerInteractionProperties();
            }
        }

        // reset pointers
        setInteractionsIterator(this.subInteractionsToWrite.iterator());
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
            for (String availability : this.availabilities){
                this.availabilityWriter.write(availability);
            }
            // write end availability list
            getStreamWriter().writeEndElement();
        }
        // write experiment list
        if (!experiments.isEmpty()){
            // write start experiment list
            getStreamWriter().writeStartElement(PsiXml25Utils.EXPERIMENTLIST_TAG);
            for (Experiment experiment : this.experiments){
                this.experimentWriter.write(experiment);
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
        }
        // write interactor list
        if (!interactors.isEmpty()){
            // write start interactor list
            getStreamWriter().writeStartElement(PsiXml25Utils.INTERACTORLIST_TAG);
            for (Interactor interactor : this.interactors){
                this.interactorWriter.write(interactor);
            }
            // write end interactor list
            getStreamWriter().writeEndElement();

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

    protected PsiXml25ExperimentWriter getExperimentWriter() {
        return experimentWriter;
    }

    protected void setExperimentWriter(PsiXml25ExperimentWriter experimentWriter) {
        this.experimentWriter = experimentWriter;
    }

    protected PsiXml25ElementWriter<Interactor> getInteractorWriter() {
        return interactorWriter;
    }

    protected void setInteractorWriter(PsiXml25ElementWriter<Interactor> interactorWriter) {
        this.interactorWriter = interactorWriter;
    }

    protected void initialiseDefaultExperimentSet() {
        this.experiments = Collections.newSetFromMap(new IdentityHashMap<Experiment, Boolean>());
    }

    protected void initialiseDefaultInteractorSet() {
        this.interactors = Collections.newSetFromMap(new IdentityHashMap<Interactor, Boolean>());
    }

    protected void initialiseDefaultAvailabilitySet() {
        this.availabilities = new HashSet<String>();
    }

    protected Set<Experiment> getExperiments() {
        if (this.experiments == null){
            initialiseDefaultExperimentSet();
        }
        return experiments;
    }

    protected Set<String> getAvailabilities() {
        if (this.availabilities == null){
            initialiseDefaultAvailabilitySet();
        }
        return availabilities;
    }

    protected Set<Interactor> getInteractors() {
        if (this.interactors == null){
            initialiseDefaultInteractorSet();
        }
        return interactors;
    }

    @Override
    protected void initialiseDefaultElementCache() {
        setElementCache(new InMemoryIdentityObjectCache());
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
