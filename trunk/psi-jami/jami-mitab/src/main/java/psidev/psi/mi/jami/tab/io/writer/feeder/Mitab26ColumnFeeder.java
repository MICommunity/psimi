package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;
import java.util.Date;

/**
 * A MITAB 2.6 column feeder will write the content of MITAB 2.6 columns
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public interface Mitab26ColumnFeeder<T extends BinaryInteraction, P extends Participant> extends Mitab25ColumnFeeder<T, P>{

    /**
     * Writes the complex expansion of a binary interaction
     * @param binary
     */
    public void writeComplexExpansion(T binary) throws IOException;

    /**
     * Writes the biological role of a participant
     * @param participant
     * @throws IOException
     */
    public void writeBiologicalRole(P participant) throws IOException;

    /**
     * Writes the experimentsl role of a participant evidence
     * @param participant
     * @throws IOException
     */
    public void writeExperimentalRole(P participant) throws IOException;

    /**
     * Writes the interactor type of a participant
     * @param participant
     * @throws IOException
     */
    public void writeInteractorType(P participant) throws IOException;

    /**
     * Write Xref of participant and interactor
     * @param participant
     * @throws IOException
     */
    public void writeParticipantXrefs(P participant) throws IOException;

    /**
     * Write interaction Xref r
     * @param interaction
     * @throws IOException
     */
    public void writeInteractionXrefs(T interaction) throws IOException;

    /**
     * Writes participant annotations
     * @param participant
     */
    public void writeParticipantAnnotations(P participant) throws IOException;

    /**
     * Writes interaction annotations
     * @param interaction
     */
    public void writeInteractionAnnotations(T interaction) throws IOException;

    /**
     * Writes experiment host organism
     * @param interaction
     */
    public void writeHostOrganism(T interaction) throws IOException;

    /**
     * Writes interaction parameters
     * @param interaction
     */
    public void writeInteractionParameters(T interaction) throws IOException;

    /**
     * Writes created date of an interaction
     * @param date
     */
    public void writeDate(Date date) throws IOException;

    /**
     * Writes participant checksum
     * @param participant
     */
    public void writeParticipantChecksums(P participant) throws IOException;

    /**
     * Writes interaction checksum
     * @param interaction
     */
    public void writeInteractionChecksums(T interaction) throws IOException;

    /**
     * Writes interaction negative property if true
     * @param interaction
     */
    public void writeNegativeProperty(T interaction) throws IOException;

    /**
     * Writes the checksum
     * @param checksum
     */
    public void writeChecksum(Checksum checksum) throws IOException;

    /**
     * Writes the parameter
     * @param parameter
     * @throws IOException
     */
    public void writeParameter(Parameter parameter) throws IOException;

    /**
     * Writes an annotation
     * @param annotation
     * @throws IOException
     */
    public void writeAnnotation(Annotation annotation) throws IOException;

    /**
     * This methods write the database, id, version and qualifier of an xref
     * @param xref
     * @throws IOException
     */
    public void writeXref(Xref xref) throws IOException;
}
