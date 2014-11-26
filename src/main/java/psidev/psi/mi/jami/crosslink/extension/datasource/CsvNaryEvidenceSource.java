package psidev.psi.mi.jami.crosslink.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.crosslink.extension.CsvInteractionEvidence;
import psidev.psi.mi.jami.crosslink.extension.CsvProtein;
import psidev.psi.mi.jami.crosslink.extension.CsvRange;
import psidev.psi.mi.jami.crosslink.extension.CsvSourceLocator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class is a source that can build a n-ary interaction evidence from a full Crosslink csv file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/08/14</pre>
 */

public class CsvNaryEvidenceSource implements CsvSource<InteractionEvidence>{
    private static final Logger logger = Logger.getLogger("CsvNaryEvidenceSource");

    private CsvSource<BinaryInteractionEvidence> csvDelegate;

    private SortedMap<Interactor, ParticipantEvidence> participants;

    private InteractionEvidence naryEvidence;

    private boolean hasRead = false;

    /**
     * Empty constructor for the factory
     */
    public CsvNaryEvidenceSource(){
        this.csvDelegate = new CsvBinaryEvidenceSource();
    }

    public CsvNaryEvidenceSource(File file) throws IOException {

        this.csvDelegate = new CsvBinaryEvidenceSource(file);
    }

    public CsvNaryEvidenceSource(InputStream input) {

        this.csvDelegate = new CsvBinaryEvidenceSource(input);
    }

    public CsvNaryEvidenceSource(Reader reader) {

        this.csvDelegate = new CsvBinaryEvidenceSource(reader);
    }

    public CsvNaryEvidenceSource(URL url) {

        this.csvDelegate = new CsvBinaryEvidenceSource(url);
    }

    public void onMismatchBetweenPeptideAndLinkedPositions(List<CsvRange> peptidePositions, List<CsvRange> linkedPositions) {
        this.csvDelegate.onMismatchBetweenPeptideAndLinkedPositions(peptidePositions, linkedPositions);
    }

    public void onMismatchBetweenRangePositionsAndProteins(List<CsvRange> rangePositions, List<CsvProtein> proteins) {
        this.csvDelegate.onMismatchBetweenRangePositionsAndProteins(rangePositions, proteins);
    }

    public void onInvalidProteinIdentifierSyntax(String[] identifiers, int lineNumber, int columnNumber) {
        this.csvDelegate.onInvalidProteinIdentifierSyntax(identifiers, lineNumber, columnNumber);
    }

    public void onMissingProtein1Column(int lineNumber) {
        this.csvDelegate.onMissingProtein1Column(lineNumber);
    }

    public Collection<InteractionEvidence> getInteractions() throws MIIOException {
        if (!hasRead){
           buildNaryInteraction();
        }
        return Collections.singleton(this.naryEvidence);
    }

    public long getNumberOfInteractions() {
        if (!hasRead){
            buildNaryInteraction();
        }
        return this.naryEvidence != null ? 1 : 0;
    }

    public Iterator<InteractionEvidence> getInteractionsIterator() throws MIIOException {
        if (!hasRead){
            buildNaryInteraction();
        }
        return Collections.singleton(this.naryEvidence).iterator();
    }

    public MIFileParserListener getFileParserListener() {
        return this.csvDelegate.getFileParserListener();
    }

    public void setFileParserListener(MIFileParserListener listener) {
        this.csvDelegate.setFileParserListener(listener);
    }

    public boolean validateSyntax() throws MIIOException {
        return false;
    }

    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException {
        return this.csvDelegate.validateSyntax(listener);
    }

    public void initialiseContext(Map<String, Object> options) {
        this.csvDelegate.initialiseContext(options);
    }

    public void close() throws MIIOException {
        this.csvDelegate.close();
        this.participants = null;
        this.naryEvidence = null;
        hasRead = false;
    }

    public void reset() throws MIIOException {
        this.csvDelegate.reset();
        this.participants = null;
        this.naryEvidence = null;
        hasRead = false;
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        this.csvDelegate.onInvalidSyntax(context, e);
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        this.csvDelegate.onSyntaxWarning(context, message);
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        this.csvDelegate.onMissingCvTermName(term, context, message);
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        this.csvDelegate.onMissingInteractorName(interactor, context);
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        this.csvDelegate.onParticipantWithoutInteractor(participant, context);
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        this.csvDelegate.onInteractionWithoutParticipants(interaction, context);
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        this.csvDelegate.onInvalidOrganismTaxid(taxid, context);
    }

    public void onMissingParameterValue(FileSourceContext context) {
        this.csvDelegate.onMissingParameterValue(context);
    }

    public void onMissingParameterType(FileSourceContext context) {
        this.csvDelegate.onMissingParameterType(context);
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        this.csvDelegate.onMissingConfidenceValue(context);
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        this.csvDelegate.onMissingConfidenceType(context);
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        this.csvDelegate.onMissingChecksumValue(context);
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        this.csvDelegate.onMissingChecksumMethod(context);
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        this.csvDelegate.onInvalidPosition(message, context);
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        this.csvDelegate.onInvalidRange(message, context);
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        this.csvDelegate.onInvalidStoichiometry(message, context);
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        this.csvDelegate.onXrefWithoutDatabase(context);
    }

    public void onXrefWithoutId(FileSourceContext context) {
        this.csvDelegate.onXrefWithoutId(context);
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        this.csvDelegate.onAnnotationWithoutTopic(context);
    }

    public void onAliasWithoutName(FileSourceContext context) {
        this.csvDelegate.onAliasWithoutName(context);
    }

    protected void buildNaryInteraction(){
        hasRead = true;
        // create map of participants
        this.participants = new TreeMap<Interactor, ParticipantEvidence>(new UnambiguousExactInteractorComparator());
        // read full binary interactions
        Collection<BinaryInteractionEvidence> binaryInteractions = this.csvDelegate.getInteractions();
        // no binary interactions in the file
        if (binaryInteractions.isEmpty()){
            this.naryEvidence = null;
        }
        // create n-ary
        else {
            // create new n-ary interaction
            this.naryEvidence = new CsvInteractionEvidence();
            ((CsvInteractionEvidence)this.naryEvidence).setSourceLocator(new CsvSourceLocator(1, -1, -1));

            Iterator<BinaryInteractionEvidence> binaryIterator = binaryInteractions.iterator();
            BinaryInteractionEvidence firstBinary = binaryIterator.next();

            // initialise basic properties of interaction
            naryEvidence.setInteractionType(firstBinary.getInteractionType());
            if (firstBinary.getExperiment() != null){
                firstBinary.getExperiment().getInteractionEvidences().clear();
                naryEvidence.setExperiment(firstBinary.getExperiment());
            }
            processBinaryInteraction(firstBinary);

            // process other binaries
            while(binaryIterator.hasNext()){
                processBinaryInteraction(binaryIterator.next());
            }
        }
    }

    protected void processBinaryInteraction(BinaryInteractionEvidence binary){
        ParticipantEvidence p1 = binary.getParticipantA();
        ParticipantEvidence p2 = binary.getParticipantB();

        mergeOrRegisterParticipant(p1);
        mergeOrRegisterParticipant(p2);
    }

    private void mergeOrRegisterParticipant(ParticipantEvidence p1) {
         if (p1 != null){
             Interactor interactor = p1.getInteractor();
             if (this.participants.containsKey(interactor)){
                 ParticipantEvidence registered = this.participants.get(interactor);

                 registered.addAllFeatures(p1.getFeatures());
             }
             // new participant to add
             else{
                 this.naryEvidence.addParticipant(p1);
                 this.participants.put(interactor, p1);
             }
         }
    }
}
