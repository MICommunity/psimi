package psidev.psi.mi.jami.extension;

import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Crosslink CSV implementation of BinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class CsvBinaryInteractionEvidence extends DefaultBinaryInteractionEvidence implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public CsvBinaryInteractionEvidence() {
        super();
    }

    public CsvBinaryInteractionEvidence(String shortName) {
        super(shortName);
    }

    public CsvBinaryInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public CsvBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(participantA, participantB);
    }

    public CsvBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, participantA, participantB);
    }

    public CsvBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, type, participantA, participantB);
    }

    public CsvBinaryInteractionEvidence(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public CsvBinaryInteractionEvidence(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public CsvBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public CsvBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public CsvBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "CSV Binary interaction evidence : "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
