package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.OntologyTermUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Json writer for features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonFeatureWriter<F extends Feature> implements JsonElementWriter<F>{

    private Writer writer;
    private JsonElementWriter<CvTerm> cvWriter;
    private JsonElementWriter<Xref> identifierWriter;
    private JsonRangeWriter rangeWriter;
    private Map<Feature, Integer> processedFeatures;
    private Map<String, String> processedInteractors;
    private Map<Entity, Integer> processedParticipants;
    private IncrementalIdGenerator idGenerator;
    private OntologyTermFetcher fetcher;
    private static final Logger logger = Logger.getLogger("SimpleJsonFeatureWriter");

    public SimpleJsonFeatureWriter(Writer writer, Map<Feature, Integer> processedFeatures,
            Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants){
        if (writer == null){
            throw new IllegalArgumentException("The json feature writer needs a non null Writer");
        }
        this.writer = writer;
        if (processedFeatures == null){
            throw new IllegalArgumentException("The json feature writer needs a non null map of processed features");
        }
        this.processedFeatures = processedFeatures;
        if (processedInteractors == null){
            throw new IllegalArgumentException("The json feature writer needs a non null map of processed interactors");
        }
        this.processedInteractors = processedInteractors;
        if (processedParticipants == null){
            throw new IllegalArgumentException("The json feature writer needs a non null map of processed participants");
        }
        this.processedParticipants = processedParticipants;
    }

    public SimpleJsonFeatureWriter(Writer writer, Map<Feature, Integer> processedFeatures,
                                   Map<String, String> processedInteractors, Map<Entity, Integer> processedParticipants,
                                   IncrementalIdGenerator idGenerator,
                                   OntologyTermFetcher fetcher){
        this(writer, processedFeatures, processedInteractors, processedParticipants);
        this.idGenerator = idGenerator;
        if (fetcher == null){
            logger.warning("The ontology fetcher is null so all the features will be listed as otherFeatures");
        }
        this.fetcher = fetcher;
    }

    public void write(F object) throws IOException {
        MIJsonUtils.writeStartObject(writer);

        // generate feature id
        int id = 0;
        if (this.processedFeatures.containsKey(object)){
            id = this.processedFeatures.get(object);
        }
        else{
            id = getIdGenerator().nextId();
            this.processedFeatures.put(object, id);
        }
        MIJsonUtils.writeProperty("id", Integer.toString(id), writer);

        // write name
        if (object.getFullName() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("name", JSONValue.escape(object.getFullName()), writer);
        }
        else if (object.getShortName() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("name", JSONValue.escape(object.getShortName()), writer);
        }

        String category = recognizeFeatureCategory(object);

        // write category
        if (category != null){
            // write type
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("category", category, writer);
        }

        // write type
        if (object.getType() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("type", writer);
            getCvWriter().write(object.getType());
        }

        // write role
        if (object.getRole() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("role", writer);
            getCvWriter().write(object.getRole());
        }

        // other properties such as detection methods
        writeOtherProperties(object);

        // ranges
        if (!object.getRanges().isEmpty()){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("sequenceData", writer);
            MIJsonUtils.writeOpenArray(writer);

            Iterator<Range> rangeIterator = object.getRanges().iterator();
            while (rangeIterator.hasNext()){
                getRangeWriter().write(rangeIterator.next(), object);
                if (rangeIterator.hasNext()){
                    MIJsonUtils.writeSeparator(writer);
                }
            }

            MIJsonUtils.writeEndArray(writer);
        }

        // write linked features if required
        if (!object.getLinkedFeatures().isEmpty()){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("linkedFeatures", writer);
            MIJsonUtils.writeOpenArray(writer);

            Iterator<F> featureIterator = object.getLinkedFeatures().iterator();
            while (featureIterator.hasNext()){
                F f = featureIterator.next();
                int id2 = 0;
                if (this.processedFeatures.containsKey(f)){
                    id2 = this.processedFeatures.get(f);
                }
                else{
                    id2 = getIdGenerator().nextId();
                    this.processedFeatures.put(f, id2);
                }
                MIJsonUtils.writePropertyValue(Integer.toString(id2), writer);
                if (featureIterator.hasNext()){
                    MIJsonUtils.writeSeparator(writer);
                }
            }

            MIJsonUtils.writeEndArray(writer);
        }

        // write interpro if required
        if (object.getInterpro() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("InterPro", JSONValue.escape(object.getInterpro()), writer);
        }
        MIJsonUtils.writeEndObject(writer);
    }

    protected void writeOtherProperties(F object) throws IOException {
        // nothing to write here but can be overridden
    }

    public JsonElementWriter<CvTerm> getCvWriter() {
        if (this.cvWriter == null){
            this.cvWriter = new SimpleJsonCvTermWriter(writer);
        }
        return cvWriter;
    }

    public void setCvWriter(JsonElementWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    public JsonElementWriter<Xref> getIdentifierWriter() {
        if (this.identifierWriter == null){
            this.identifierWriter = new SimpleJsonIdentifierWriter(writer);
        }
        return identifierWriter;
    }

    public void setIdentifierWriter(JsonElementWriter<Xref> identifierWriter) {
        this.identifierWriter = identifierWriter;
    }

    public JsonRangeWriter getRangeWriter() {
        if (this.rangeWriter == null){
           this.rangeWriter = new SimpleJsonRangeWriter(writer, processedInteractors, processedParticipants, getIdGenerator());
        }
        return rangeWriter;
    }

    public void setRangeWriter(JsonRangeWriter rangeWriter) {
        this.rangeWriter = rangeWriter;
    }

    public IncrementalIdGenerator getIdGenerator() {
        if (this.idGenerator == null){
            this.idGenerator = new IncrementalIdGenerator();
        }
        return idGenerator;
    }

    public void setIdGenerator(IncrementalIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    protected Writer getWriter() {
        return writer;
    }

    protected String recognizeFeatureCategory(F feature) {

        // feature type is not null, we can recognize the feature
        if (feature.getType() != null){
            CvTerm type = feature.getType();
            // all mod terms are ptms
            if (type.getMODIdentifier() != null){
                return "ptms";
            }
            else if (fetcher == null){
                if (CvTermUtils.isCvTerm(type, Feature.BINDING_SITE_MI, Feature.BINDING_SITE)) {
                    return "bindingSites";
                }
                else if (CvTermUtils.isCvTerm(type, Feature.MUTATION_MI, Feature.MUTATION) ||
                        CvTermUtils.isCvTerm(type, Feature.VARIANT_MI, Feature.VARIANT)) {
                    return "pointMutations";
                }
                else if (CvTermUtils.isCvTerm(type, Feature.EXPERIMENTAL_FEATURE_MI, Feature.EXPERIMENTAL_FEATURE)) {
                    return "experimentalFeatures";
                }
                else if (CvTermUtils.isCvTerm(type, Feature.ALLOSTERIC_PTM_MI, Feature.ALLOSTERIC_PTM)) {
                    return "ptms";
                }
                else {
                    return "otherFeatures";
                }
            }
            else if (type.getMIIdentifier() != null){
                OntologyTerm term = null;
                try {
                    term = fetcher.fetchByIdentifier(type.getMIIdentifier(), CvTerm.PSI_MI);
                } catch (BridgeFailedException e) {
                    logger.log(Level.SEVERE, "Cannot fetch the ontology information for the term " + type.getMIIdentifier(), e);
                }

                // we cannot retrieve the MI term
                if (term == null){
                    return "otherFeatures";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.BINDING_SITE_MI, Feature.BINDING_SITE)) {
                    return "bindingSites";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.MUTATION_MI, Feature.MUTATION) ||
                        OntologyTermUtils.isCvTermChildOf(term, Feature.VARIANT_MI, Feature.VARIANT)) {
                    return "pointMutations";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.EXPERIMENTAL_FEATURE_MI, Feature.EXPERIMENTAL_FEATURE)) {
                    return "experimentalFeatures";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.ALLOSTERIC_PTM_MI, Feature.ALLOSTERIC_PTM)){
                    return "ptms";
                }
                else {
                    return "otherFeatures";
                }
            }
            else {
                OntologyTerm term = null;
                String name = type.getFullName() != null ? type.getFullName() : type.getShortName();
                try {
                    term = fetcher.fetchByName(name, CvTerm.PSI_MI);
                    if (term == null){
                        term = fetcher.fetchByName(name, CvTerm.PSI_MOD);
                    }
                } catch (BridgeFailedException e) {
                    logger.log(Level.SEVERE, "Cannot fetch the ontology information for the term " + (type.getFullName() != null ? type.getFullName() : type.getShortName()), e);
                }

                // cannot retrieve the term using name
                if (term == null){
                    return "otherFeatures";
                }
                else if (term.getMODIdentifier() != null){
                    return "ptms";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.BINDING_SITE_MI, Feature.BINDING_SITE)) {
                    return "bindingSites";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.MUTATION_MI, Feature.MUTATION) ||
                        OntologyTermUtils.isCvTermChildOf(term, Feature.VARIANT_MI, Feature.VARIANT)) {
                    return "pointMutations";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.EXPERIMENTAL_FEATURE_MI, Feature.EXPERIMENTAL_FEATURE)) {
                    return "experimentalFeatures";
                }
                else if (OntologyTermUtils.isCvTermChildOf(term, Feature.ALLOSTERIC_PTM_MI, Feature.ALLOSTERIC_PTM)){
                    return "ptms";
                }
                else {
                    return "otherFeatures";
                }
            }
        }
        // we cannot recognize the feature
        else {
            return "otherFeatures";
        }
    }
}
