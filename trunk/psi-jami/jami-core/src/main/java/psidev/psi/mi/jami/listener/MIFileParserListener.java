package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Interface for listening to the parsing of a MI file.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/06/13</pre>
 */

public interface MIFileParserListener {

    /**
     * Listen to an invalid syntax causing an Exception
     * @param context
     * @param e
     */
    public void onInvalidSyntax(FileSourceContext context, Exception e);

    /**
     * Listen to an event fired when the syntax of the file is valid but the content is not
     * expected or some information may be lost.
     * @param context
     * @param message
     */
    public void onSyntaxWarning(FileSourceContext context, String message);

    /**
     * Listen to an event fired when a CvTerm is read and does not have any names.
     * At least a shortname is expected for each CvTerm so such a term may be loaded with an automatically generated shortname.
     * @param term
     * @param context
     * @param message
     */
    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message);

    /**
     * Listen to an event fired when an interactor is read and does not have any names.
     * At least a shortname is expected for each interactor so such an interactor may be loaded with an automatically generated shortname.
     * @param interactor
     * @param context
     */
    public void onMissingInteractorName(Interactor interactor, FileSourceContext context);

    /**
     * Listen to an event where several CvTerms were found and only one was expected.
     * Can happen when reading a clustered interaction evidence for instance
     * @param terms
     * @param context
     * @param message
     */
    public void onSeveralCvTermsFound(Collection<? extends CvTerm> terms, FileSourceContext context, String message);

    /**
     * Listen to an event where several host organisms were found in a single experiment and only one was expected.
     * Can happen when reading a clustered interaction evidence for instance
     * @param organisms
     * @param context
     */
    public void onSeveralHostOrganismFound(Collection<? extends Organism> organisms, FileSourceContext context);

    /**
     * Listen to en event fired when a Participant is read and no interactor details has been provided.
     * In such a case, the interactor can be automatically generated as an unknown interactor
     * @param participant
     * @param context
     */
    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context);

    /**
     * Listen to an event fired when an interaction is read and does not have any participants.
     * The interaction may be loaded as an empty interaction.
     * @param interaction
     * @param context
     */
    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context);
}
