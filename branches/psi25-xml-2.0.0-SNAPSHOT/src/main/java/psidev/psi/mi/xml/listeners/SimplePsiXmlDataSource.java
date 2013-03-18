package psidev.psi.mi.xml.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlVersion;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.events.*;
import psidev.psi.mi.xml.io.*;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * A simple PSI-XML datasource based on the basic parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class SimplePsiXmlDataSource implements StreamingExperimentSource, StreamingInteractorSource, StreamingInteractionSource, PsiXml25ParserListener  {

    private PsimiXmlReader reader;
    private EntrySet entrySet;
    private File file;
    private InputStream stream;
    private Map<DataSourceError, List<FileSourceContext>> errors;

    private Log log = LogFactory.getLog(LightWeightSimplePsiXmlDataSource.class);

    public SimplePsiXmlDataSource(File file){
        this.reader = new PsimiXmlReader();

        this.reader.addXmlParserListener(this);
        this.file = file;
        if (file == null){
            throw new IllegalArgumentException("File is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public SimplePsiXmlDataSource(InputStream stream){
        this.reader = new PsimiXmlReader();

        this.reader.addXmlParserListener(this);
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public SimplePsiXmlDataSource(File file, PsimiXmlVersion version){
        this.reader = new PsimiXmlReader(version);

        this.reader.addXmlParserListener(this);
        this.file = file;
        if (file == null){
            throw new IllegalArgumentException("File is mandatory for a PSI-XML 2.5 datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public SimplePsiXmlDataSource(InputStream stream, PsimiXmlVersion version){
        this.reader = new PsimiXmlReader(version);

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
        if (file != null){
            try {
                this.entrySet = this.reader.read(file);
            } catch (Exception e) {
                log.error("Impossible to parse current file");
                this.entrySet = null;
                this.errors.put(new DataSourceError(e.getCause().toString(), e.getMessage()), Arrays.<FileSourceContext>asList(new InvalidXmlEvent("Impossible to parse current file")));
            }
        }
        else {
            try {
                this.entrySet = this.reader.read(stream);
            } catch (Exception e) {
                log.error("Impossible to parse current InputStream");
                this.entrySet = null;
                this.errors.put(new DataSourceError(e.getCause().toString(), e.getMessage()), Arrays.<FileSourceContext>asList(new InvalidXmlEvent("Impossible to parse current inputstream")));
            }
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
        return new XmlInteractionIterator(this.entrySet, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));
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
        return new XmlInteractionIterator(this.entrySet, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));    }

    public Iterator<? extends Experiment> getExperimentsIterator() {
        return new XmlExperimentIterator(this.entrySet, new ArrayList<PsiXml25ParserListener>(Arrays.asList(this)));    }

    public Iterator<? extends Interactor> getInteractorsIterator() {
        return new XmlInteractorIterator(this.entrySet);
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
