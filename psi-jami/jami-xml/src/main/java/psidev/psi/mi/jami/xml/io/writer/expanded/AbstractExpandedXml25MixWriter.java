package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25InteractionWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class for an expanded PSI-XML 2.5 writer of mixed interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public abstract class AbstractExpandedXml25MixWriter<I extends Interaction, M extends ModelledInteraction, E extends InteractionEvidence> extends AbstractExpandedXml25Writer<I>{

    private AbstractExpandedXml25Writer<E> evidenceWriter;
    private AbstractExpandedXml25Writer<M> modelledWriter;
    private AbstractExpandedXml25Writer<I> lightWriter;

    public AbstractExpandedXml25MixWriter(Class<I> type) {
        super(type);
    }

    public AbstractExpandedXml25MixWriter(Class<I> type, File file) throws IOException, XMLStreamException {
        super(type,file);
    }

    public AbstractExpandedXml25MixWriter(Class<I> type, OutputStream output) throws XMLStreamException {
        super(type,output);
    }

    public AbstractExpandedXml25MixWriter(Class<I> type, Writer writer) throws XMLStreamException {
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
    public void flush() throws MIIOException {
        super.flush();
        this.evidenceWriter.flush();
        this.modelledWriter.flush();
        this.lightWriter.flush();
    }

    @Override
    public void setWriteComplexesAsInteractors(boolean writeComplexesAsInteractors) {
        super.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        this.modelledWriter.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        this.evidenceWriter.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
        this.lightWriter.setWriteComplexesAsInteractors(writeComplexesAsInteractors);
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return null;
    }

    @Override
    public void write(Iterator<? extends I> interactions) throws MIIOException {
        if (this.modelledWriter == null || this.evidenceWriter == null){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        List<E> evidences = new ArrayList<E>();
        List<M> modelledList = new ArrayList<M>();
        List<I> interactionList = new ArrayList<I>();
        I interaction = null;
        while (interactions.hasNext()){
            interaction = interactions.next();
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
    }

    @Override
    public void write(Collection<? extends I> interactions) throws MIIOException {
        write(interactions.iterator());
    }

    @Override
    public void write(I interaction) throws MIIOException {
        if (this.modelledWriter == null || this.evidenceWriter == null){
            throw new IllegalStateException("The PSI-XML 2.5 writer was not initialised. The options for the PSI-XML 2.5 writer should contains at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
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
    public void setSourceWriter(PsiXml25ElementWriter<Source> sourceWriter) {
        super.setSourceWriter(sourceWriter);
        this.evidenceWriter.setSourceWriter(sourceWriter);
        this.modelledWriter.setSourceWriter(sourceWriter);
        this.lightWriter.setSourceWriter(sourceWriter);
    }

    @Override
    public void setComplexWriter(PsiXml25InteractionWriter<ModelledInteraction> complexWriter) {
        super.setComplexWriter(complexWriter);
        this.evidenceWriter.setComplexWriter(complexWriter);
        this.modelledWriter.setComplexWriter(complexWriter);
        this.lightWriter.setComplexWriter(complexWriter);

    }

    @Override
    public void setElementCache(PsiXml25ObjectCache elementCache) {
        super.setElementCache(elementCache);
        this.modelledWriter.setElementCache(elementCache);
        this.evidenceWriter.setElementCache(elementCache);
        this.lightWriter.setElementCache(elementCache);
    }

    @Override
    public void setStarted(boolean started) {
        super.setStarted(started);
        this.modelledWriter.setStarted(started);
        this.evidenceWriter.setStarted(started);
        this.lightWriter.setStarted(started);
    }

    protected AbstractExpandedXml25Writer<E> getEvidenceWriter() {
        return evidenceWriter;
    }

    protected void setEvidenceWriter(AbstractExpandedXml25Writer<E> evidenceWriter) {
        this.evidenceWriter = evidenceWriter;
    }

    protected AbstractExpandedXml25Writer<M> getModelledWriter() {
        return modelledWriter;
    }

    protected void setModelledWriter(AbstractExpandedXml25Writer<M> modelledWriter) {
        this.modelledWriter = modelledWriter;
    }

    protected AbstractExpandedXml25Writer<I> getLightWriter() {
        return lightWriter;
    }

    protected void setLightWriter(AbstractExpandedXml25Writer<I> lightWriter) {
        this.lightWriter = lightWriter;
    }

    protected abstract void initialiseDelegateWriters();
}
