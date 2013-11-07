package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;

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

    /**
     * Listen to an event fired when an organism does not have a valid taxid (positive integer or -1, -2, -3, -4, -5)
     * @param taxid
     * @param context
     */
    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context);

    /**
     * Listen to an event fired when a parameter does not have a value
     * @param context
     */
    public void onMissingParameterValue(FileSourceContext context);

    /**
     * Listen to an event fired when a parameter does not have a type
     * @param context
     */
    public void onMissingParameterType(FileSourceContext context);

    /**
     * Listen to an event fired when a confidence does not have a value
     * @param context
     */
    public void onMissingConfidenceValue(FileSourceContext context);

    /**
     * Listen to an event fired when a confidence does not have a confidence type
     * @param context
     */
    public void onMissingConfidenceType(FileSourceContext context);

    /**
     * Listen to an event fired when a checksum does not have a value
     * @param context
     */
    public void onMissingChecksumValue(FileSourceContext context);

    /**
     * Listen to an event fired when a checksum does not have a method
     * @param context
     */
    public void onMissingChecksumMethod(FileSourceContext context);

    /**
     * Listen to an event fired when a position is not valid
     * @param message
     * @param context
     */
    public void onInvalidPosition(String message, FileSourceContext context);

    /**
     * Listen to an event fired when a range is not valid
     * @param message
     * @param context
     */
    public void onInvalidRange(String message, FileSourceContext context);

    /**
     * Listen to an event fired when a stoichiometry is not valid
     * @param message
     * @param context
     */
    public void onInvalidStoichiometry(String message, FileSourceContext context);

    /**
     * Listen to an event fired when a xref does not have a database
     * @param context
     */
    public void onXrefWithoutDatabase(FileSourceContext context);

    /**
     * Listen to an event fired when a xref does not have an id
     * @param context
     */
    public void onXrefWithoutId(FileSourceContext context);

    /**
     * Listen to an event fired when an annotation does not have a topic
     * @param context
     */
    public void onAnnotationWithoutTopic(FileSourceContext context);

    /**
     * Listen to an event fired when an alias does not have a name
     * @param context
     */
    public void onAliasWithoutName(FileSourceContext context);
}
