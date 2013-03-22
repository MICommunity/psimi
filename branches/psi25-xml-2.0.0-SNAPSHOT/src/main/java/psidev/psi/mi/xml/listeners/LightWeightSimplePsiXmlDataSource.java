package psidev.psi.mi.xml.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.PsimiXmlVersion;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.events.*;
import psidev.psi.mi.xml.io.LightWeightXmlExperimentIterator;
import psidev.psi.mi.xml.io.LightWeightXmlInteractionIterator;
import psidev.psi.mi.xml.io.LightWeightXmlInteractorIterator;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * A simple PSI-XML datasource based on light weight parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class LightWeightSimplePsiXmlDataSource implements StreamingExperimentSource, StreamingInteractorSource, StreamingInteractionSource, PsiXml25ParserListener {

    private PsimiXmlLightweightReader reader;
    private List<IndexedEntry> indexedEntries;
    private File file;
    private InputStream stream;
    private Collection<FileSourceError> errors;

    private Log log = LogFactory.getLog(LightWeightSimplePsiXmlDataSource.class);

    public LightWeightSimplePsiXmlDataSource(File file){
        try {
            this.reader = new PsimiXmlLightweightReader(file);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The file cannot be read", e);

            if (e.getCurrentObject() instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) e.getCurrentObject();
                evt.setSourceLocator(context.getSourceLocator());
            }
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.file = file;
        if (file == null){
            throw new IllegalArgumentException("File is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new ArrayList<FileSourceError>();
    }

    public LightWeightSimplePsiXmlDataSource(InputStream stream){
        try {
            this.reader = new PsimiXmlLightweightReader(stream);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The file cannot be read", e);
            if (e.getCurrentObject() instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) e.getCurrentObject();
                evt.setSourceLocator(context.getSourceLocator());
            }
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new ArrayList<FileSourceError>();
    }

    public LightWeightSimplePsiXmlDataSource(File file, PsimiXmlVersion version){
        try {
            this.reader = new PsimiXmlLightweightReader(file, version);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The file cannot be read", e);
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.file = file;
        if (file == null){
            throw new IllegalArgumentException("File is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new ArrayList<FileSourceError>();
    }

    public LightWeightSimplePsiXmlDataSource(InputStream stream, PsimiXmlVersion version){
        try {
            this.reader = new PsimiXmlLightweightReader(stream, version);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The inputstream cannot be read", e);
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new ArrayList<FileSourceError>();
    }

    public void initialiseContext(Map<String, Object> options) {
        // nothing for the moment
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return errors;
    }

    public void open() {
        try {
            this.indexedEntries = reader.getIndexedEntries();
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("Impossible to extract the entries from the datasource", e);
            if (e.getCurrentObject() instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) e.getCurrentObject();
                evt.setSourceLocator(context.getSourceLocator());
            }
            fireOnInvalidXmlSyntax(evt);
        }
    }

    public void close() {
        ConverterContext.remove();
    }

    public void fireOnInvalidXmlSyntax(InvalidXmlEvent event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), event.getMessage(), event);

        errors.add(error);
    }

    public void fireOnMultipleExperimentalRolesEvent(MultipleExperimentalRolesEvent event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.multiple_experimental_roles.toString(), event.getMessage(), event);
        errors.add(error);
    }

    public void fireOnMultipleExperimentsPerInteractionEvent(MultipleExperimentsPerInteractionEvent event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.multiple_experiments.toString(), event.getMessage(), event);
        errors.add(error);
    }

    public void fireOnMultipleExpressedInOrganismsEvent(MultipleExpressedInOrganisms event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.multiple_expressed_in.toString(), event.getMessage(), event);
        errors.add(error);
    }

    public void fireOnMultipleHostOrganismsPerExperimentEvent(MultipleHostOrganismsPerExperiment event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.multiple_host_organisms.toString(), event.getMessage(), event);
        errors.add(error);
    }

    public void fireOnMultipleInteractionTypesEvent(MultipleInteractionTypesEvent event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.multiple_interaction_types.toString(), event.getMessage(), event);
        errors.add(error);
    }

    public void fireOnMultipleParticipantIdentificationMethodsEvent(MultipleParticipantIdentificationMethodsPerParticipant event) {
        FileSourceError error = new FileSourceError(FileParsingErrorType.multiple_participant_identification_methods.toString(), event.getMessage(), event);
        errors.add(error);
    }

    public void fireOnMissingCvEvent(MissingCvEvent event) {
        FileSourceError error = new FileSourceError(event.getErrorType().toString(), event.getMessage(), event);
        errors.add(error);
    }

    public Iterator<? extends InteractionEvidence> getInteractionEvidencesIterator() {
        return new LightWeightXmlInteractionIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
    }

    public Iterator<? extends ModelledInteraction> getModelledInteractionsIterator() {
        return null;
    }

    public Iterator<? extends CooperativeInteraction> getCooperativeInteractionsIterator() {
        return null;
    }

    public Iterator<? extends AllostericInteraction> getAllostericInteractionsIterator() {
        return null;
    }

    public Iterator<? extends Interaction> getInteractionsIterator() {
        return new LightWeightXmlInteractionIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
    }

    public Iterator<? extends Experiment> getExperimentsIterator() {
        return new LightWeightXmlExperimentIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
    }

    public Iterator<? extends Interactor> getInteractorsIterator() {
        return new LightWeightXmlInteractorIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
    }

    public Iterator<? extends Protein> getProteinsIterator() {
        return null;
    }

    public Iterator<? extends NucleicAcid> getNucleicAcidsIterator() {
        return null;
    }

    public Iterator<? extends Gene> getGenesIterator() {
        return null;
    }

    public Iterator<? extends BioactiveEntity> getBioactiveEntitiesIterator() {
        return null;
    }

    public Iterator<? extends Complex> getComplexesIterator() {
        return null;
    }
}
