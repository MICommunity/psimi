package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXmlWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExperimentWriter;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;
import psidev.psi.mi.jami.xml.utils.PsiXmlWriterOptions;

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

public abstract class AbstractCompactXmlWriter<T extends Interaction> extends AbstractXmlWriter<T> {

    private PsiXmlElementWriter<String> availabilityWriter;
    private PsiXmlExperimentWriter experimentWriter;
    private PsiXmlElementWriter<Interactor> interactorWriter;
    private List<T> subInteractionsToWrite;

    private Class<T> type;

    private Set<Experiment> experiments;
    private Set<String> availabilities;
    private Set<Interactor> interactors;

    public AbstractCompactXmlWriter(Class<T> type) {
        super();
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    public AbstractCompactXmlWriter(Class<T> type, File file) throws IOException, XMLStreamException {
        super(file);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    public AbstractCompactXmlWriter(Class<T> type, OutputStream output) throws XMLStreamException {
        super(output);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    public AbstractCompactXmlWriter(Class<T> type, Writer writer) throws XMLStreamException {
        super(writer);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    protected AbstractCompactXmlWriter(Class<T> type, XMLStreamWriter streamWriter, PsiXmlObjectCache elementCache) {
        super(streamWriter, elementCache);
        this.type = type;
        this.subInteractionsToWrite = new ArrayList<T>();
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);

        if (options.containsKey(PsiXmlWriterOptions.COMPACT_XML_EXPERIMENT_SET_OPTION)){
            setExperimentSet((Set<Experiment>) options.get(PsiXmlWriterOptions.COMPACT_XML_EXPERIMENT_SET_OPTION));
        }
        // use the default cache option
        else{
            initialiseDefaultExperimentSet();
        }
        if (options.containsKey(PsiXmlWriterOptions.COMPACT_XML_AVAILABILITY_SET_OPTION)){
            setAvailabilitySet((Set<String>) options.get(PsiXmlWriterOptions.COMPACT_XML_AVAILABILITY_SET_OPTION));
        }
        // use the default cache option
        else{
            initialiseDefaultAvailabilitySet();
        }
        if (options.containsKey(PsiXmlWriterOptions.COMPACT_XML_INTERACTOR_SET_OPTION)){
            setInteractorSet((Set<Interactor>) options.get(PsiXmlWriterOptions.COMPACT_XML_INTERACTOR_SET_OPTION));
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

    @Override
    protected void initialiseOptionalWriters(PsiXmlExperimentWriter experimentWriter, PsiXmlElementWriter<String> availabilityWriter, PsiXmlElementWriter<Interactor> interactorWriter) {
        setExperimentWriter(experimentWriter);
        setInteractorWriter(interactorWriter);
        setAvailabilityWriter(availabilityWriter);
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

        boolean keepRegistering = true;
        while (getInteractionsIterator().hasNext()){
            T inter = getInteractionsIterator().next();
            setCurrentInteraction(inter);
            Source source = extractSourceFromInteraction();
            this.subInteractionsToWrite.add(inter);
            if(keepRegistering){
                // write next entry after closing first one
                if (getCurrentSource() != source){
                    // stops here for the current entry
                    keepRegistering = false;
                }
                else{
                    registerInteractionProperties();
                }
            }
        }

        // reset pointers
        setInteractionsIterator(new ArrayList<T>(this.subInteractionsToWrite).iterator());
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
            getStreamWriter().writeStartElement(PsiXmlUtils.AVAILABILITYLIST_TAG);
            for (String availability : this.availabilities){
                this.availabilityWriter.write(availability);
            }
            // write end availability list
            getStreamWriter().writeEndElement();
        }
        // write experiment list
        if (!experiments.isEmpty()){
            // write start experiment list
            getStreamWriter().writeStartElement(PsiXmlUtils.EXPERIMENTLIST_TAG);
            for (Experiment experiment : this.experiments){
                this.experimentWriter.write(experiment);
            }
            // write end experiment list
            getStreamWriter().writeEndElement();
        }
        // write interactor list
        if (!interactors.isEmpty()){
            // write start interactor list
            getStreamWriter().writeStartElement(PsiXmlUtils.INTERACTORLIST_TAG);
            for (Interactor interactor : this.interactors){
                this.interactorWriter.write(interactor);
            }
            // write end interactor list
            getStreamWriter().writeEndElement();

        }
        // write start interactionList
        writeStartInteractionList();
    }

    protected PsiXmlElementWriter<String> getAvailabilityWriter() {
        return availabilityWriter;
    }

    protected void setAvailabilityWriter(PsiXmlElementWriter<String> availabilityWriter) {
        this.availabilityWriter = availabilityWriter;
    }

    protected PsiXmlExperimentWriter getExperimentWriter() {
        return experimentWriter;
    }

    protected void setExperimentWriter(PsiXmlExperimentWriter experimentWriter) {
        this.experimentWriter = experimentWriter;
    }

    protected PsiXmlElementWriter<Interactor> getInteractorWriter() {
        return interactorWriter;
    }

    protected void setInteractorWriter(PsiXmlElementWriter<Interactor> interactorWriter) {
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

    protected void registerInteractionProperties() {
        T interaction = getCurrentInteraction();
        // register all experiments
        registerExperiment(interaction);
        // register all availabilities
        registerAvailabilities(interaction);
        // register all interactors
        registerAllInteractors(interaction);
    }
}
