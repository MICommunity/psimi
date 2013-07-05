package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.OntologyTermUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorBaseComparator;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JSON writer for InteractionEvidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class JsonBinaryWriter implements InteractionWriter<BinaryInteractionEvidence> {

    private boolean isInitialised = false;
    private Writer writer;
    private boolean hasOpened = false;
    private Set<String> processedInteractors;
    private static final Logger logger = Logger.getLogger("MitabParserLogger");
    private Integer expansionId;
    private Collection<FeatureEvidence> experimentalFeatures;
    private Collection<FeatureEvidence> bindingSites;
    private Collection<FeatureEvidence> mutations;
    private Collection<FeatureEvidence> ptms;
    private OntologyTermFetcher fetcher;

    public JsonBinaryWriter(){
        processedInteractors = new HashSet<String>();
        initialiseFeatureCollections();
    }

    public JsonBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        initialiseFile(file);
        processedInteractors = new HashSet<String>();
        initialiseFeatureCollections();
        if (fetcher == null){
            throw new IllegalArgumentException("The json writer needs an OntologyTermFetcher to write feature categories.");
        }
        this.fetcher = fetcher;
    }

    public JsonBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {

        initialiseOutputStream(output);
        processedInteractors = new HashSet<String>();
        initialiseFeatureCollections();
        if (fetcher == null){
            throw new IllegalArgumentException("The json writer needs an OntologyTermFetcher to write feature categories.");
        }
        this.fetcher = fetcher;
    }

    public JsonBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {

        initialiseWriter(writer);
        processedInteractors = new HashSet<String>();
        initialiseFeatureCollections();
        if (fetcher == null){
            throw new IllegalArgumentException("The json writer needs an OntologyTermFetcher to write feature categories.");
        }
        this.fetcher = fetcher;
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+JsonUtils.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterFactory.OUTPUT_OPTION_KEY)){
            Object output = options.get(InteractionWriterFactory.OUTPUT_OPTION_KEY);

            if (output instanceof File){
                try {
                    initialiseFile((File) output);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                }
            }
            else if (output instanceof OutputStream){
                initialiseOutputStream((OutputStream) output);
            }
            else if (output instanceof Writer){
                initialiseWriter((Writer) output);
            }
            // suspect a file path
            else if (output instanceof String){
                try {
                    initialiseFile(new File((String)output));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + output, e);
                }
            }
            else {
                throw new IllegalArgumentException("Impossible to write in the provided output "+output.getClass().getName() + ", a File, OuputStream, Writer or file path was expected.");
            }

            if (options.containsKey(InteractionWriterFactory.OUTPUT_OPTION_KEY)){
                this.fetcher = (OntologyTermFetcher) options.get(InteractionWriterFactory.OUTPUT_OPTION_KEY);
            }
            else {
                throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+JsonUtils.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+JsonUtils.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }

        isInitialised = true;
    }

    public void write(BinaryInteractionEvidence interaction) throws DataSourceWriterException {
        if (!isInitialised){
            throw new IllegalArgumentException("The json writer has not been initialised. The options for the json writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions and "+JsonUtils.ONTOLOGY_FETCHER_OPTION_KEY+" to know which OntologyTermFetcher to use.");
        }

        try{
            ParticipantEvidence A = interaction.getParticipantA();
            ParticipantEvidence B = interaction.getParticipantB();

            if (A != null || B != null){
                // write start element and interactor and beginning of interaction
                if (!hasOpened){
                    writeStart();

                    if (A != null && B != null){
                        registerAndWriteInteractor(A, false);
                        registerAndWriteInteractor(B, true);
                    }
                    else if (A != null){
                        registerAndWriteInteractor(A, false);
                    }
                    else {
                        registerAndWriteInteractor(B, false);
                    }
                }
                // write interactors
                else if (A != null && B != null){
                    registerAndWriteInteractor(A, true);
                    registerAndWriteInteractor(B, true);
                }
                else if (A != null){
                    registerAndWriteInteractor(A, true);
                }
                else {
                    registerAndWriteInteractor(B, true);
                }

                writeInteraction(interaction, A, B);
            }
            else {
                logger.log(Level.WARNING, "Ignore interaction as it does not contain any participants : "+interaction.toString());
            }
        }
        catch (IOException e) {
            throw new DataSourceWriterException("Impossible to write " +interaction.toString(), e);
        }
    }

    public void write(Collection<BinaryInteractionEvidence> interactions) throws DataSourceWriterException {
        Iterator<BinaryInteractionEvidence> binaryIterator = interactions.iterator();
        write(binaryIterator);
    }

    public void write(Iterator<BinaryInteractionEvidence> interactions) throws DataSourceWriterException {
        while(interactions.hasNext()){
            write(interactions.next());
        }
    }

    public void flush() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to flush the JSON writer", e);
            }
        }
    }

    public void close() throws DataSourceWriterException{
        if (isInitialised){
            try {
                writeEnd();
            } catch (IOException e) {
                throw new DataSourceWriterException("Impossible to close the JSON writer", e);
            }
            finally{
                try {
                    flush();
                }
                finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        throw new DataSourceWriterException("Impossible to close the JSON writer", e);
                    }
                }
                clearFeatureCollections();
                isInitialised = false;
                writer = null;
                hasOpened = false;
                processedInteractors.clear();
                expansionId = null;
            }
        }
    }

    public Integer getExpansionId() {
        return expansionId;
    }

    public void setExpansionId(Integer expansionId) {
        this.expansionId = expansionId;
    }

    protected void writeFeatures(String name, Collection<FeatureEvidence> features, boolean writeLinkedFeatures, boolean writeInterpro) throws IOException {
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);
        writeStartObject(name);
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<FeatureEvidence> featureIterator = features.iterator();
        while (featureIterator.hasNext()){
            FeatureEvidence feature = featureIterator.next();
            writeFeature(feature, writeLinkedFeatures, writeInterpro);
            if (featureIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected void writeAllFeatures(Collection<FeatureEvidence> features) throws IOException {
        // first split the features in the proper collection of features
        clearFeatureCollections();
        Iterator<FeatureEvidence> featureIterator = features.iterator();
        while (featureIterator.hasNext()){
            recognizeFeatureTypeAndSplitInFeatureCollections(featureIterator.next());
        }

        if (!experimentalFeatures.isEmpty()){
            writeFeatures("experimentalFeatures", experimentalFeatures, false, false);
        }
        if (!mutations.isEmpty()){
            writeFeatures("pointMutations", mutations, false, false);
        }
        if (!bindingSites.isEmpty()){
            writeFeatures("bindingSites", bindingSites, true, true);
        }
        if (!ptms.isEmpty()){
            writeFeatures("ptms", ptms, true, false);
        }
    }

    protected void recognizeFeatureTypeAndSplitInFeatureCollections(FeatureEvidence feature) {

        // feature type is not null, we can recognize the feature
        if (feature.getType() != null){
            CvTerm type = feature.getType();
            // all mod terms are ptms
            if (type.getMODIdentifier() != null){
                ptms.add(feature);
            }
            else if (type.getMIIdentifier() != null){
                OntologyTerm term = null;
                try {
                    term = fetcher.getCvTermByIdentifier(type.getMIIdentifier(), CvTerm.PSI_MI, 0, -1);
                } catch (BridgeFailedException e) {
                    logger.log(Level.SEVERE, "Cannot fetch the ontology information for the term " + type.getMIIdentifier(), e);
                }

                // we cannot retrieve the MI term
                if (term == null){
                    // we have linked features, it could be a binding site
                    if (!feature.getLinkedFeatureEvidences().isEmpty()){
                        bindingSites.add(feature);
                    }
                    // if one range is not undetermined, it is likely to be a binding site
                    else {
                        for (Range r : feature.getRanges()){
                            if (!r.getStart().isPositionUndetermined() || !r.getEnd().isPositionUndetermined()){
                                bindingSites.add(feature);
                                return;
                            }
                        }
                        experimentalFeatures.add(feature);
                    }
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.EXPERIMENTAL_FEATURE_MI, Feature.EXPERIMENTAL_FEATURE)) {
                    experimentalFeatures.add(feature);
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.BINDING_SITE_MI, Feature.BINDING_SITE)) {
                    bindingSites.add(feature);
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.MUTATION_MI, Feature.MUTATION) ||
                        OntologyTermUtils.isCvTermChildOf(term, Feature.VARIANT_MI, Feature.VARIANT)) {
                    mutations.add(feature);
                }
                // we consider any other MI terms as old PTM
                else {
                    ptms.add(feature);
                }
            }
            else {
                OntologyTerm term = null;
                try {
                    term = fetcher.getCvTermByExactName(type.getFullName() != null ? type.getFullName() : type.getShortName(), CvTerm.PSI_MI, 0, -1);
                    if (term == null){
                        term = fetcher.getCvTermByExactName(type.getFullName() != null ? type.getFullName() : type.getShortName(), CvTerm.PSI_MOD, 0, -1);
                    }
                } catch (BridgeFailedException e) {
                    logger.log(Level.SEVERE, "Cannot fetch the ontology information for the term " + (type.getFullName() != null ? type.getFullName() : type.getShortName()), e);
                }

                // cannot retrieve the term using name
                if (term == null){

                    // we have linked features, it could be a binding site
                    if (!feature.getLinkedFeatureEvidences().isEmpty()){
                        bindingSites.add(feature);
                    }
                    // if one range is not undetermined, it is likely to be a binding site
                    else {
                        for (Range r : feature.getRanges()){
                            if (!r.getStart().isPositionUndetermined() || !r.getEnd().isPositionUndetermined()){
                                bindingSites.add(feature);
                                return;
                            }
                        }
                        experimentalFeatures.add(feature);
                    }
                }
                else if (term.getMODIdentifier() != null){
                    ptms.add(feature);
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.EXPERIMENTAL_FEATURE_MI, Feature.EXPERIMENTAL_FEATURE)) {
                    experimentalFeatures.add(feature);
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.BINDING_SITE_MI, Feature.BINDING_SITE)) {
                    bindingSites.add(feature);
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.MUTATION_MI, Feature.MUTATION) ||
                        OntologyTermUtils.isCvTermChildOf(term, Feature.VARIANT_MI, Feature.VARIANT)) {
                    mutations.add(feature);
                }
                else if (!feature.getLinkedFeatureEvidences().isEmpty()){
                    bindingSites.add(feature);
                }
                // if one range is not undetermined, it is likely to be a binding site
                else {
                    for (Range r : feature.getRanges()){
                        if (!r.getStart().isPositionUndetermined() || !r.getEnd().isPositionUndetermined()){
                            bindingSites.add(feature);
                            return;
                        }
                    }
                    experimentalFeatures.add(feature);
                }
            }
        }
        // we need to recognize the feature
        else {
            // we have linked features, it could be a binding site
            if (!feature.getLinkedFeatureEvidences().isEmpty()){
                bindingSites.add(feature);
            }
            // if one range is not undetermined, it is likely to be a binding site
            else {
                for (Range r : feature.getRanges()){
                    if (!r.getStart().isPositionUndetermined() || !r.getEnd().isPositionUndetermined()){
                        bindingSites.add(feature);
                        return;
                    }
                }
                experimentalFeatures.add(feature);
            }
        }
    }

    protected void writeFeature(FeatureEvidence feature, boolean writeLinkedFeatures, boolean writeInterpro) throws IOException {

        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.OPEN);
        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.INDENT);

        // write identifier == hashcode of feature
        writerProperty("id", Integer.toString(feature.hashCode()));

        // write name
        if (feature.getFullName() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writer.write(JsonUtils.INDENT);
            writerProperty("name", feature.getFullName());
        }
        else if (feature.getShortName() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writer.write(JsonUtils.INDENT);
            writerProperty("name", feature.getShortName());
        }

        // write type
        if (feature.getType() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("type");
            writeCvTerm(feature.getType());
        }

        // detection methods
        if (!feature.getDetectionMethods().isEmpty()){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("detmethods");
            writer.write(JsonUtils.OPEN_ARRAY);

            Iterator<CvTerm> methodIterator = feature.getDetectionMethods().iterator();
            while (methodIterator.hasNext()){
                CvTerm method = methodIterator.next();
                writeCvTerm(method);
                if (methodIterator.hasNext()){
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                }
            }

            writer.write(JsonUtils.CLOSE_ARRAY);
        }

        // ranges
        if (!feature.getRanges().isEmpty()){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("sequenceData");
            writer.write(JsonUtils.OPEN_ARRAY);

            Iterator<Range> rangeIterator = feature.getRanges().iterator();
            while (rangeIterator.hasNext()){
                Range range = rangeIterator.next();
                writeRange(range);
                if (rangeIterator.hasNext()){
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                }
            }

            writer.write(JsonUtils.CLOSE_ARRAY);
        }

        // write linked features if required
        if (writeLinkedFeatures && !feature.getLinkedFeatureEvidences().isEmpty()){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("linkedFeatures");
            writer.write(JsonUtils.OPEN_ARRAY);

            Iterator<FeatureEvidence> featureIterator = feature.getLinkedFeatureEvidences().iterator();
            while (featureIterator.hasNext()){
                FeatureEvidence f = featureIterator.next();
                writerProperty("id", Integer.toString(f.hashCode()));
                if (featureIterator.hasNext()){
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                }
            }

            writer.write(JsonUtils.CLOSE_ARRAY);
        }

        // write interpro if required
        if (writeInterpro && feature.getInterpro() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writerProperty("Binding_Site_Domain", feature.getInterpro());
        }

        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeRange(Range range) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("range", RangeUtils.convertRangeToString(range));
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeParticipant(ParticipantEvidence participant, String name) throws IOException {

        writeNextPropertySeparatorAndIndent();
        writeStartObject(name);
        writer.write(JsonUtils.OPEN);
        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);

        // write identifier
        writeStartObject("identifier");
        Xref preferredIdentifier = participant.getInteractor().getPreferredIdentifier();
        String interactorId = null;
        String db = null;
        if (preferredIdentifier != null){
            interactorId = preferredIdentifier.getId();
            db = preferredIdentifier.getDatabase().getShortName();
        }
        else{
            interactorId = Integer.toString(UnambiguousExactInteractorBaseComparator.hashCode(participant.getInteractor()));
            db = "generated";
        }
        writeIdentifier(db, interactorId);

        // write biorole
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);
        writeStartObject("bioRole");
        writeCvTerm(participant.getBiologicalRole());

        // write expRole
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.INDENT);
        writeStartObject("expRole");
        writeCvTerm(participant.getExperimentalRole());

        // identification methods
        if (!participant.getIdentificationMethods().isEmpty()){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("identificationMethods");
            writer.write(JsonUtils.OPEN_ARRAY);

            Iterator<CvTerm> methodIterator = participant.getIdentificationMethods().iterator();
            while (methodIterator.hasNext()){
                CvTerm method = methodIterator.next();
                writeCvTerm(method);
                if (methodIterator.hasNext()){
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                }
            }

            writer.write(JsonUtils.CLOSE_ARRAY);
        }

        // expressed in
        if (participant.getExpressedInOrganism() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("expressedIn");
            writeOrganism(participant.getExpressedInOrganism());
        }

        // features
        if (!participant.getFeatures().isEmpty()){
            writeAllFeatures(participant.getFeatures());
        }

        writeNextPropertySeparatorAndIndent();
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeAnnotation(String name, String text) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty(name, text != null ? text : "");
        writer.write(JsonUtils.CLOSE);

    }

    protected void writeAllAnnotations(String name, Collection<Annotation> figures) throws IOException {
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<Annotation> annotIterator = figures.iterator();
        while (annotIterator.hasNext()){
            Annotation annot = annotIterator.next();
            writeAnnotation(name, annot != null ? annot.getValue():"");
            if (annotIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected void writePublication(Publication publication) throws IOException {
        // publication identifiers
        if (!publication.getIdentifiers().isEmpty()){
            writeStartObject("pubid");
            writeAllIdentifiers(publication.getIdentifiers());
        }

        // publication source
        if (publication.getSource() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);
            writeStartObject("source");
            writeCvTerm(publication.getSource());
        }
    }

    protected void writeExpansionMethod(CvTerm expansion) throws IOException {
        writer.write(JsonUtils.OPEN);
        if (expansionId != null){
            writerProperty("id", Integer.toString(expansionId));
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
        }
        writerProperty("name", expansion.getShortName());
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeConfidence(String type, String value) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("type", type);
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writerProperty("value", value);
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeAllConfidences(Collection<Confidence> confidences) throws IOException {
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<Confidence> confidencesIterator = confidences.iterator();
        while (confidencesIterator.hasNext()){
            Confidence conf = confidencesIterator.next();
            if (conf != null){
                writeConfidence(conf.getType().getShortName(), conf.getValue());
            }
            else {
                writeConfidence("unknown","");
            }
            if (confidencesIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected void writeAllIdentifiers(Collection<Xref> identifiers) throws IOException {
        writer.write(JsonUtils.OPEN_ARRAY);

        Iterator<Xref> identifierIterator = identifiers.iterator();
        while (identifierIterator.hasNext()){
            Xref identifier = identifierIterator.next();
            if (identifier != null){
                writeIdentifier(identifier.getDatabase().getShortName(), identifier.getId());
            }
            else {
                writeIdentifier("unknown", identifier.getId());
            }
            if (identifierIterator.hasNext()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
        }

        writer.write(JsonUtils.CLOSE_ARRAY);
    }

    protected boolean writeExperiment(InteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        Collection<Annotation> figures = AnnotationUtils.collectAllAnnotationsHavingTopic(interaction.getAnnotations(), Annotation.FIGURE_LEGEND_MI, Annotation.FIGURE_LEGEND);

        if (experiment != null){
            writeNextPropertySeparatorAndIndent();
            writeStartObject("experiment");
            writer.write(JsonUtils.OPEN);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);

            // write detection method
            writeStartObject("detmethod");
            writeCvTerm(experiment.getInteractionDetectionMethod());

            if (experiment.getHostOrganism() != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("host");
                writeOrganism(experiment.getHostOrganism());
            }

            if (experiment.getHostOrganism() != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("host");
                writeOrganism(experiment.getHostOrganism());
            }

            // write publication
            if (experiment.getPublication() != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writePublication(experiment.getPublication());
            }

            Collection<Annotation> expModifications = AnnotationUtils.collectAllAnnotationsHavingTopic(experiment.getAnnotations(), Annotation.EXP_MODIFICATION_MI, Annotation.EXP_MODIFICATION);
            if (!expModifications.isEmpty()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("experimentModifications");
                writeAllAnnotations("modification", expModifications);
            }

            if (!figures.isEmpty()){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writer.write(JsonUtils.INDENT);
                writeStartObject("figures");
                writeAllAnnotations("figure", figures);
            }

            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.CLOSE);
            return true;
        }
        else if (!figures.isEmpty()){
            writeNextPropertySeparatorAndIndent();
            writeStartObject("experiment");
            writer.write(JsonUtils.OPEN);
            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.INDENT);

            // write figures
            writeStartObject("figures");
            writeAllAnnotations("figure", figures);

            writeNextPropertySeparatorAndIndent();
            writer.write(JsonUtils.CLOSE);
            return true;
        }
        return false;
    }

    protected void writeIdentifier(String db, String id) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("db", db);
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writerProperty("id", id);
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeOrganism(Organism organism) throws IOException {
        writer.write(JsonUtils.OPEN);
        writerProperty("taxid", Integer.toString(organism.getTaxId()));
        if (organism.getCommonName() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writerProperty("common", organism.getCommonName());
        }
        if (organism.getScientificName() != null){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writerProperty("scientific", organism.getScientificName());
        }
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeCvTerm(CvTerm term) throws IOException {
        writer.write(JsonUtils.OPEN);
        boolean hasId = false;
        if (term.getMIIdentifier() != null){
            writerProperty("id", term.getMIIdentifier());
            hasId = true;
        }
        else if (term.getMODIdentifier() != null){
            writerProperty("id", term.getMODIdentifier());
            hasId = true;
        }
        else if (term.getPARIdentifier() != null){
            writerProperty("id", term.getPARIdentifier());
            hasId = true;
        }

        if (hasId){
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
        }

        if (term.getFullName() != null){
            writerProperty("name", term.getFullName());
        }
        else {
            writerProperty("name", term.getShortName());
        }
        writer.write(JsonUtils.CLOSE);
    }

    protected void writeStartObject(String object) throws IOException {
        writer.write(JsonUtils.PROPERTY_DELIMITER);
        writer.write(object);
        writer.write(JsonUtils.PROPERTY_DELIMITER);
        writer.write(JsonUtils.PROPERTY_VALUE_SEPARATOR);
    }

    protected void writerProperty(String propertyName, String value) throws IOException {
        writeStartObject(propertyName);
        writer.write(JsonUtils.PROPERTY_DELIMITER);
        writer.write(value);
        writer.write(JsonUtils.PROPERTY_DELIMITER);
    }

    protected void writeNextPropertySeparatorAndIndent() throws IOException {
        writer.write(JsonUtils.LINE_SEPARATOR);
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.INDENT);
    }

    protected void writeInteraction(BinaryInteractionEvidence binary, ParticipantEvidence A, ParticipantEvidence B) throws IOException {
        writer.write(JsonUtils.ELEMENT_SEPARATOR);
        writer.write(JsonUtils.LINE_SEPARATOR);
        writer.write(JsonUtils.INDENT);
        writer.write(JsonUtils.OPEN);

        // first experiment
        boolean hasExperiment = writeExperiment(binary);

        // then interaction type
        boolean hasType = binary.getInteractionType() != null;
        if (hasType){
            if (hasExperiment){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("interactionType");
            writeCvTerm(binary.getInteractionType());
        }

        // then interaction identifiers
        boolean hasIdentifiers = !binary.getIdentifiers().isEmpty();
        if (hasIdentifiers){
            if (hasType || (!hasType && hasExperiment)){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("identifiers");
            writeAllIdentifiers(binary.getIdentifiers());
        }

        // then confidences
        boolean hasConfidences = !binary.getConfidences().isEmpty();
        if (hasConfidences){
            if (hasIdentifiers || (!hasIdentifiers && (hasType || (!hasType && hasExperiment)))){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("confidences");
            writeAllConfidences(binary.getConfidences());
        }

        // then complex expansion
        boolean hasComplexExpansion = binary.getComplexExpansion() != null;
        if (hasComplexExpansion){
            if (hasConfidences || (!hasConfidences && (hasIdentifiers || (!hasIdentifiers && (hasType || (!hasType && hasExperiment)))))){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeStartObject("expansion");
            writeExpansionMethod(binary.getComplexExpansion());
        }

        // then participant A and B
        if (A != null){
            if (hasComplexExpansion || (!hasComplexExpansion && (hasConfidences || (!hasConfidences && (hasIdentifiers || (!hasIdentifiers && (hasType || (!hasType && hasExperiment)))))))){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeParticipant(A, "source");

            if (B != null){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writeParticipant(B, "target");
            }
            else {
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writeParticipant(A, "target");
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writerProperty("intramolecular", "true");
            }
        }
        else if (B != null){
            if (hasComplexExpansion || (!hasComplexExpansion && (hasConfidences || (!hasConfidences && (hasIdentifiers || (!hasIdentifiers && (hasType || (!hasType && hasExperiment)))))))){
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
            }
            writeNextPropertySeparatorAndIndent();
            writeParticipant(B, "source");
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeParticipant(B, "target");
            writer.write(JsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writerProperty("intramolecular", "true");
        }
    }

    protected void registerAndWriteInteractor(ParticipantEvidence participant, boolean writeElementSeparator) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            Xref preferredIdentifier = interactor.getPreferredIdentifier();
            String interactorId = null;
            String db = null;
            if (preferredIdentifier != null){
                interactorId = preferredIdentifier.getId();
                db = preferredIdentifier.getDatabase().getShortName();
            }
            else{
                interactorId = Integer.toString(UnambiguousExactInteractorBaseComparator.hashCode(interactor));
                db = "generated";
            }
            String interactorKey = db+"_"+interactorId;

            // if the interactor has not yet been processed, we write the interactor
            if (processedInteractors.add(interactorKey)){
                if (writeElementSeparator){
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                    writer.write(JsonUtils.LINE_SEPARATOR);
                }
                writer.write(JsonUtils.INDENT);
                writer.write(JsonUtils.OPEN);
                writeNextPropertySeparatorAndIndent();

                writerProperty("object","interactor");
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();

                // write sequence if possible
                if (interactor instanceof Polymer){
                    Polymer polymer = (Polymer) interactor;
                    if (polymer.getSequence() != null){
                        writerProperty("sequence", polymer.getSequence());
                        writer.write(JsonUtils.ELEMENT_SEPARATOR);
                        writeNextPropertySeparatorAndIndent();
                    }
                }
                // write interactor type
                writeStartObject("type");
                writeCvTerm(interactor.getInteractorType());
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                // write organism
                if (interactor.getOrganism() != null){
                    writeStartObject("organism");
                    writer.write(JsonUtils.ELEMENT_SEPARATOR);
                    writeOrganism(interactor.getOrganism());
                    writeNextPropertySeparatorAndIndent();
                }
                // write accession
                writeStartObject("identifier");
                writeIdentifier(db, interactorId);
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                // write label
                writerProperty("label", interactor.getShortName());
                writer.write(JsonUtils.ELEMENT_SEPARATOR);
                writer.write(JsonUtils.LINE_SEPARATOR);
                writer.write(JsonUtils.INDENT);
                writer.write(JsonUtils.CLOSE);
            }
        }
    }

    protected void writeStart() throws IOException {
        hasOpened = true;
        writer.write(JsonUtils.OPEN);
        writer.write(JsonUtils.LINE_SEPARATOR);
        writeStartObject("data");
        writer.write(JsonUtils.OPEN_ARRAY);
    }

    protected void writeEnd() throws IOException {
        hasOpened = false;
        writer.write(JsonUtils.LINE_SEPARATOR);
        writer.write(JsonUtils.CLOSE_ARRAY);
        writer.write(JsonUtils.CLOSE);

    }

    private void initialiseWriter(Writer writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
        isInitialised = true;
    }

    private void initialiseOutputStream(OutputStream output) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new OutputStreamWriter(output);
        isInitialised = true;
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
        isInitialised = true;
    }

    private void initialiseFeatureCollections(){
        this.experimentalFeatures = new ArrayList<FeatureEvidence>();
        this.bindingSites = new ArrayList<FeatureEvidence>();
        this.ptms = new ArrayList<FeatureEvidence>();
        this.mutations = new ArrayList<FeatureEvidence>();
    }

    private void clearFeatureCollections(){
        this.experimentalFeatures.clear();
        this.bindingSites.clear();
        this.ptms.clear();
        this.mutations.clear();
    }
}
