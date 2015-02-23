package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger for MitabParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/13</pre>
 */

public class MIFileParserLogger implements MIFileParserListener{
    private static final Logger logger = Logger.getLogger("MIFileParserLogger");

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        logger.log(Level.WARNING, "Missing Cv name: "+message + "(" + context.toString() + ")");
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        logger.log(Level.SEVERE, "Invalid syntax (" + context.toString() + "): ", e);
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + context.toString() + ")");
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        logger.log(Level.WARNING, "No shortLabel, fullName or aliases have been found in (" + context.toString() + "). At least one shortLabel is expected for each interactor.");
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        logger.log(Level.SEVERE, "No interactor details have been found in participant (" + context.toString() + "). The interactor will be loaded as unknown interactor.");
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        logger.log(Level.SEVERE, "No participant and interactor details have been found in interaction (" + context.toString() + "). At least one participant is expected per interaction. The interaction will be empty.");
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        logger.log(Level.WARNING, "The organism taxid "+taxid+" ("+context.toString()+") is not valid. Only positive integers, -1, -2, -3, -4 and -5 are recognized. The organism will be loaded as -3(unknown)");
    }

    public void onMissingParameterValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The parameter is not valid as it does not have a value ("+context.toString()+"). The parameter will be loaded with a value of 0.");
    }

    public void onMissingParameterType(FileSourceContext context) {
        logger.log(Level.WARNING, "The parameter is not valid as it does not have a parameter type ("+context.toString()+"). The parameter will be loaded with a type 'unknown'.");
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The confidence is not valid as it does not have a value ("+context.toString()+"). The confidence will be loaded with a value 'unknown'.");
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        logger.log(Level.WARNING, "The confidence is not valid as it does not have a confidence type ("+context.toString()+"). The confidence will be loaded with a type 'unknown'.");
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The checksum is not valid as it does not have a value ("+context.toString()+"). The checksum will be loaded with a value 'unknown'.");
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        logger.log(Level.WARNING, "The checksum is not valid as it does not have a checksum method ("+context.toString()+"). The checksum will be loaded with a method 'unknown'.");
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The position is not valid ("+context.toString()+"): "+message+". The position will be loaded as undetermined position.");
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The range is not valid ("+context.toString()+"): "+ message+". The range will be loaded as undetermined range.");
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The stoichiometry is not valid ("+context.toString()+"): "+message+". The stoichiometry will be loaded with a value of 0.");
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        logger.log(Level.WARNING, "The xref is not valid as it does not have a database ("+context.toString()+"). The xref will be loaded with a database 'unknown'.");
    }

    public void onXrefWithoutId(FileSourceContext context) {
        logger.log(Level.WARNING, "The xref is not valid as it does not have an id ("+context.toString()+"). The xref will be loaded with an id 'unknown'.");
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        logger.log(Level.WARNING, "The annotation is not valid as it does not have a topic ("+context.toString()+"). The annotation will be loaded with a topic 'unknown'.");
    }

    public void onAliasWithoutName(FileSourceContext context) {
        logger.log(Level.WARNING, "The alias is not valid as it does not have a name ("+context.toString()+"). The alias will be loaded with a name 'unspecified'.");
    }
}
