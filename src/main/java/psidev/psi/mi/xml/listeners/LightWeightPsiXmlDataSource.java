package psidev.psi.mi.xml.listeners;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.PsimiXmlVersion;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.events.*;
import psidev.psi.mi.xml.io.XmlExperimentIterator;
import psidev.psi.mi.xml.io.XmlInteractionIterator;
import psidev.psi.mi.xml.io.XmlInteractorIterator;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * A simple PSI-XML datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class LightWeightPsiXmlDataSource implements StreamingExperimentSource, StreamingInteractorSource, StreamingInteractionSource, PsiXml25ParserListener {

    private PsimiXmlLightweightReader reader;
    private List<IndexedEntry> indexedEntries;
    private File file;
    private InputStream stream;
    private Map<DataSourceError, List<FileSourceContext>> errors;

    private Log log = LogFactory.getLog(LightWeightPsiXmlDataSource.class);

    public LightWeightPsiXmlDataSource(File file){
        try {
            this.reader = new PsimiXmlLightweightReader(file);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The file cannot be read because of " + ExceptionUtils.getFullStackTrace(e));
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.file = file;
        if (file == null){
            throw new IllegalArgumentException("File is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public LightWeightPsiXmlDataSource(InputStream stream){
        try {
            this.reader = new PsimiXmlLightweightReader(stream);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The file cannot be read because of " + ExceptionUtils.getFullStackTrace(e));
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public LightWeightPsiXmlDataSource(File file, PsimiXmlVersion version){
        try {
            this.reader = new PsimiXmlLightweightReader(file, version);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The file cannot be read because of " + ExceptionUtils.getFullStackTrace(e));
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.file = file;
        if (file == null){
            throw new IllegalArgumentException("File is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public LightWeightPsiXmlDataSource(InputStream stream, PsimiXmlVersion version){
        try {
            this.reader = new PsimiXmlLightweightReader(stream, version);
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("The inputstream cannot be read because of " + ExceptionUtils.getFullStackTrace(e));
            fireOnInvalidXmlSyntax(evt);
        }
        this.reader.addXmlParserListener(this);
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public void initialiseContext(Map<String, Object> options) {
        // nothing for the moment
    }

    public Map<DataSourceError, List<FileSourceContext>> getDataSourceErrors() {
        return errors;
    }

    public void open() {
        try {
            this.indexedEntries = reader.getIndexedEntries();
        } catch (PsimiXmlReaderException e) {
            InvalidXmlEvent evt = new InvalidXmlEvent("Impossible to extract the entries from the datasource because of " + ExceptionUtils.getFullStackTrace(e));
            fireOnInvalidXmlSyntax(evt);
        }
    }

    public void close() {
        ConverterContext.remove();
    }

    public void fireOnInvalidXmlSyntax(InvalidXmlEvent event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.invalid_syntax.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public void fireOnMultipleExperimentalRolesEvent(MultipleExperimentalRolesEvent event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.clustered_content.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public void fireOnMultipleExperimentsPerInteractionEvent(MultipleExperimentsPerInteractionEvent event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.clustered_content.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public void fireOnMultipleExpressedInOrganismsEvent(MultipleExpressedInOrganisms event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.clustered_content.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public void fireOnMultipleHostOrganismsPerExperimentEvent(MultipleHostOrganismsPerExperiment event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.clustered_content.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public void fireOnMultipleInteractionTypesEvent(MultipleInteractionTypesEvent event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.clustered_content.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public void fireOnMultipleParticipantIdentificationMethodsEvent(MultipleParticipantIdentificationMethodsPerParticipant event) {
        DataSourceError error = new DataSourceError(FileSourceParsingError.clustered_content.toString(), event.getMessage());
        if (errors.containsKey(error)){
            errors.get(error).add(event);
        }
        else{
            List<FileSourceContext> contexts = new ArrayList<FileSourceContext>();
            contexts.add(event);
            errors.put(error, contexts);
        }
    }

    public Iterator<? extends InteractionEvidence> getInteractionEvidencesIterator() {
        return new XmlInteractionIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
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
        return new XmlInteractionIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
    }

    public Iterator<? extends Experiment> getExperimentsIterator() {
        return new XmlExperimentIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
    }

    public Iterator<? extends Interactor> getInteractorsIterator() {
        return new XmlInteractorIterator(this.indexedEntries, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
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
