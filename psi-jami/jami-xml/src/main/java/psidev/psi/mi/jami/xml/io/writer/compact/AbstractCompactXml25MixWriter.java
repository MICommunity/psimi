package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.datasource.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25InteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25SourceWriter;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

/**
 * Abstract class for a compact PSI-XML 2.5 writer of mixed interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public abstract class AbstractCompactXml25MixWriter<I extends Interaction, M extends ModelledInteraction, E extends InteractionEvidence> extends AbstractCompactXml25Writer<I>{

    private AbstractCompactXml25Writer<E> evidenceWriter;
    private AbstractCompactXml25Writer<M> modelledWriter;
    private AbstractCompactXml25Writer<I> lightWriter;

    public AbstractCompactXml25MixWriter(Class<I> type) {
        super(type);
    }

    public AbstractCompactXml25MixWriter(Class<I> type, File file) throws IOException, XMLStreamException {
        super(type,file);
    }

    public AbstractCompactXml25MixWriter(Class<I> type, OutputStream output) throws XMLStreamException {
        super(type,output);
    }

    public AbstractCompactXml25MixWriter(Class<I> type, Writer writer) throws XMLStreamException {
        super(type,writer);
    }

    @Override
    protected void initialiseSubWriters() {
        initialiseDelegateWriters();
    }

    @Override
    public void close() throws MIIOException {
        super.close();
        this.modelledWriter = null;
        this.evidenceWriter = null;
        this.lightWriter = null;
    }

    @Override
    public void reset() throws MIIOException {
        super.reset();
        this.modelledWriter = null;
        this.evidenceWriter = null;
        this.lightWriter = null;
    }

    @Override
    protected void setAvailabilityWriter(PsiXml25ElementWriter<String> availabilityWriter) {
        this.evidenceWriter.setAvailabilityWriter(availabilityWriter);
    }

    @Override
    protected void setExperimentWriter(PsiXml25ExperimentWriter experimentWriter) {
        super.setExperimentWriter(experimentWriter);
        if (this.modelledWriter != null){
            this.modelledWriter.setExperimentWriter(experimentWriter);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setExperimentWriter(experimentWriter);
        }
        if (this.lightWriter != null){
            this.lightWriter.setExperimentWriter(experimentWriter);
        }
    }

    @Override
    protected void setInteractorWriter(PsiXml25ElementWriter<Interactor> interactorWriter) {
        super.setInteractorWriter(interactorWriter);
        if (this.modelledWriter != null){
            this.modelledWriter.setInteractorWriter(interactorWriter);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setInteractorWriter(interactorWriter);
        }
        if (this.lightWriter != null){
            this.lightWriter.setInteractorWriter(interactorWriter);
        }
    }

    @Override
    public void setExperimentSet(Set<Experiment> experiments) {
        super.setExperimentSet(experiments);
        if (this.modelledWriter != null){
            this.modelledWriter.setExperimentSet(experiments);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setExperimentSet(experiments);
        }
        if (this.lightWriter != null){
            this.lightWriter.setExperimentSet(experiments);
        }    }

    @Override
    public void setAvailabilitySet(Set<String> availabilities) {
        super.setAvailabilitySet(availabilities);
        if (this.modelledWriter != null){
            this.modelledWriter.setAvailabilitySet(availabilities);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setAvailabilitySet(availabilities);
        }
        if (this.lightWriter != null){
            this.lightWriter.setAvailabilitySet(availabilities);
        }
    }

    @Override
    public void setVersion(PsiXmlVersion version) {
        super.setVersion(version);
        if (this.modelledWriter != null){
            this.modelledWriter.setVersion(version);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setVersion(version);
        }
        if (this.lightWriter != null){
            this.lightWriter.setVersion(version);
        }
    }

    @Override
    public void setInteractorSet(Set<Interactor> interactors) {
        super.setInteractorSet(interactors);
        if (this.modelledWriter != null){
            this.modelledWriter.setInteractorSet(interactors);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setInteractorSet(interactors);
        }
        if (this.lightWriter != null){
            this.lightWriter.setInteractorSet(interactors);
        }
    }

    @Override
    public void setInteractionSet(Set<Interaction> processedInteractions) {
        super.setInteractionSet(processedInteractions);
        if (this.modelledWriter != null){
            this.modelledWriter.setInteractionSet(processedInteractions);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setInteractionSet(processedInteractions);
        }
        if (this.lightWriter != null){
            this.lightWriter.setInteractionSet(processedInteractions);
        }
    }

    @Override
    public void setDefaultSource(Source defaultSource) {
        super.setDefaultSource(defaultSource);
        if (this.modelledWriter != null){
            this.modelledWriter.setDefaultSource(defaultSource);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setDefaultSource(defaultSource);
        }
        if (this.lightWriter != null){
            this.lightWriter.setDefaultSource(defaultSource);
        }    }

    @Override
    public void setDefaultReleaseDate(XMLGregorianCalendar defaultReleaseDate) {
        super.setDefaultReleaseDate(defaultReleaseDate);
        if (this.modelledWriter != null){
            this.modelledWriter.setDefaultReleaseDate(defaultReleaseDate);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setDefaultReleaseDate(defaultReleaseDate);
        }
        if (this.lightWriter != null){
            this.lightWriter.setDefaultReleaseDate(defaultReleaseDate);
        }
    }

    @Override
    public void flush() throws MIIOException {
        super.flush();
        this.evidenceWriter.flush();
        this.modelledWriter.flush();
        this.lightWriter.flush();
    }

    @Override
    public void setWriteComplexesAsInteractors(boolean writeComplexesAsInteractors) {
        super.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        if (this.modelledWriter != null){
            this.modelledWriter.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        }
        if (this.lightWriter != null){
            this.lightWriter.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        }
    }

    @Override
    protected void registerExperiment(I interaction) {
        // nothing to do
    }

    @Override
    protected void registerAvailabilities(I interaction) {
        // nothing to do
    }

    @Override
    public void write(Iterator<? extends I> interactions) throws MIIOException {
        if (this.modelledWriter == null || this.evidenceWriter == null || this.lightWriter == null){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        List<E> evidences = new ArrayList<E>();
        List<M> modelledList = new ArrayList<M>();
        List<I> interactionList = new ArrayList<I>();
        I interaction = null;

        if (interactions.hasNext()){
            interaction = interactions.next();
        }

        do{
            if (interaction != null && this.evidenceWriter.getInteractionType() != null
                    && this.evidenceWriter.getInteractionType().isAssignableFrom(interaction.getClass())){
                evidences.clear();
                do{
                    evidences.add((E)interaction);
                    if (interactions.hasNext()){
                        interaction = interactions.next();
                    }
                    else{
                        interaction = null;
                    }
                }
                while(interaction != null && this.evidenceWriter.getInteractionType().isAssignableFrom(interaction.getClass()));
                this.evidenceWriter.write(evidences);
            }
            else if (interaction != null && this.modelledWriter.getInteractionType() != null
                    && this.modelledWriter.getInteractionType().isAssignableFrom(interaction.getClass())){
                modelledList.clear();
                do{
                    modelledList.add((M)interaction);
                    if (interactions.hasNext()){
                        interaction = interactions.next();
                    }
                    else{
                        interaction = null;
                    }
                }
                while(interaction != null && this.modelledWriter.getInteractionType().isAssignableFrom(interaction.getClass()));
                this.modelledWriter.write(modelledList);
            }
            else if (interaction != null){
                interactionList.clear();
                do{
                    interactionList.add(interaction);
                    if (interactions.hasNext()){
                        interaction = interactions.next();
                    }
                    else{
                        interaction = null;
                    }
                }
                while(interaction != null && !this.evidenceWriter.getInteractionType().isAssignableFrom(interaction.getClass())
                        && !this.modelledWriter.getInteractionType().isAssignableFrom(interaction.getClass()));
                this.lightWriter.write(interactionList);
            }
            else{
                break;
            }
        }
        while (interaction != null);
    }

    @Override
    public void write(Collection<? extends I> interactions) throws MIIOException {
        write(interactions.iterator());
    }

    @Override
    public void write(I interaction) throws MIIOException {
        if (this.modelledWriter == null || this.evidenceWriter == null || this.lightWriter == null){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        if (this.evidenceWriter.getInteractionType() != null && this.evidenceWriter.getInteractionType().isAssignableFrom(interaction.getClass())){
            this.evidenceWriter.write((E)interaction);
        }
        else if (this.modelledWriter.getInteractionType() != null && this.modelledWriter.getInteractionType().isAssignableFrom(interaction.getClass())){
            this.modelledWriter.write((M)interaction);
        }
        else{
            this.lightWriter.write(interaction);
        }
    }

    @Override
    public void setSourceWriter(PsiXml25SourceWriter sourceWriter) {
        super.setSourceWriter(sourceWriter);
        if (this.modelledWriter != null){
            this.modelledWriter.setSourceWriter(sourceWriter);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setSourceWriter(sourceWriter);
        }
        if (this.lightWriter != null){
            this.lightWriter.setSourceWriter(sourceWriter);
        }
    }

    @Override
    public void setComplexWriter(PsiXml25InteractionWriter<ModelledInteraction> complexWriter) {
        super.setComplexWriter(complexWriter);
        if (this.modelledWriter != null){
            this.modelledWriter.setComplexWriter(complexWriter);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setComplexWriter(complexWriter);
        }
        if (this.lightWriter != null){
            this.lightWriter.setComplexWriter(complexWriter);
        }

    }

    @Override
    public void setElementCache(PsiXml25ObjectCache elementCache) {
        super.setElementCache(elementCache);
        if (this.modelledWriter != null){
            this.modelledWriter.setElementCache(elementCache);
        }
        if (this.evidenceWriter != null){
            this.evidenceWriter.setElementCache(elementCache);
        }
        if (this.lightWriter != null){
            this.lightWriter.setElementCache(elementCache);
        }
    }

    protected void setEvidenceWriter(AbstractCompactXml25Writer<E> evidenceWriter) {
        this.evidenceWriter = evidenceWriter;
        this.evidenceWriter.setElementCache(getElementCache());
    }

    protected void setModelledWriter(AbstractCompactXml25Writer<M> modelledWriter) {
        this.modelledWriter = modelledWriter;
        this.modelledWriter.setElementCache(getElementCache());
    }

    protected void setLightWriter(AbstractCompactXml25Writer<I> lightWriter) {
        this.lightWriter = lightWriter;
        this.lightWriter.setElementCache(getElementCache());
    }

    protected abstract void initialiseDelegateWriters();
}
