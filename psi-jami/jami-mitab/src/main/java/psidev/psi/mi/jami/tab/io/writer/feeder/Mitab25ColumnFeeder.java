package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;

import java.io.IOException;

/**
 * A MITAB 2.5 column feeder will write the content of MITAB 2.5 columns
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public interface Mitab25ColumnFeeder<T extends BinaryInteraction, P extends Participant> {

    /**
     * This method will write the unique identifier of a participant
     * @param participant
     * @throws java.io.IOException
     */
    public void writeUniqueIdentifier(P participant) throws IOException;

    /**
     * This method writes all the remaining identifiers (ignore the first identifier) of the participant
     * @param participant
     * @throws IOException
     */
    public void writeAlternativeIdentifiers(P participant) throws IOException;

    /**
     * This method writes all the aliases of the participant
     * @param participant
     * @throws IOException
     */
    public void writeAliases(P participant) throws IOException;

    /**
     * Writes the interaction detection method of the experiment.
     * @param interaction
     */
    public void writeInteractionDetectionMethod(T interaction) throws IOException;

    /**
     * Writes the first author of a publication in an experiment
     * @param interaction
     */
    public void writeFirstAuthor(T interaction) throws IOException;

    /**
     * Writes the publication identifiers of a publication in an experiment.
     * This method will write the first publication identifier (pubmed before doi) and also the IMEx id
     * @param interaction
     */
    public void writePublicationIdentifiers(T interaction) throws IOException;

    /**
     * Writes the organism of a participant.
     * Empty column if the organism is not provided
     * @param participant
     */
    public void writeInteractorOrganism(P participant) throws IOException;

    /**
     * Writes the interaction type of an interaction
     * @param interaction
     */
    public void writeInteractionType(T interaction) throws IOException;

    /**
     * Writes the interaction source from the modelled interaction
     * @param interaction
     */
    public void writeSource(T interaction) throws IOException;

    /**
     * Writes the interaction identifiers of an interaction.
     * This method will write the first interaction identifier and also the IMEx id
     * @param interaction
     */
    public void writeInteractionIdentifiers(T interaction) throws IOException;

    /**
     * Writes the confidences of an interaction evidence
     * @param interaction
     */
    public void writeInteractionConfidences(T interaction) throws IOException;

    /**
     * This method will write a confidence with a text if text is not null
     * @param conf
     */
    public void writeConfidence(Confidence conf) throws IOException;

    /**
     * Write an organism.
     * Will duplicate taxid if needs to provide both common name and scientific name
     * @param organism
     * @throws IOException
     */
    public void writeOrganism(Organism organism) throws IOException;

    /**
     * Write the CvTerm. If it is null, it writes an empty column (-)
     * @param cv
     */
    public void writeCvTerm(CvTerm cv) throws IOException;


    /**
     * This methods write the dbsource, alias name and alias type of an alias
     * @param alias
     * @throws IOException
     */
    public void writeAlias(Alias alias) throws IOException;

    /**
     * This methods write the dbsource, alias name and alias type of an alias.  It can use the modelled participant to find dbsource
     * @param alias
     * @throws IOException
     */
    public void writeAlias(P participant, Alias alias) throws IOException;

    /**
     * This methods write the database, id and version of an identifier
     * @param identifier
     * @throws IOException
     */
    public void writeIdentifier(Xref identifier) throws IOException;

    /**
     * This method replaces line breaks and tab characters with a space.
     *
     * It escapes the StringToEscape with doble quote if it finds a special MITAB character
     * @param stringToEscape
     * @throws IOException
     */
    public void escapeAndWriteString(String stringToEscape) throws IOException;
}
