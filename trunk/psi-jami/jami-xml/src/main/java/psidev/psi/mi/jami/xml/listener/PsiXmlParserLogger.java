package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.XmlIdReference;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger for MitabParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/13</pre>
 */

public class PsiXmlParserLogger implements PsiXmlParserListener {
    private static final Logger logger = Logger.getLogger("PsiXmlParserLogger");

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + context.toString() + ")");
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        logger.log(Level.SEVERE, "Invalid syntax (" + context.toString() + "): ", e);
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + context.toString()+ ")");
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        logger.log(Level.WARNING, "No shortLabel or fullName have been found in (" + context.toString() + "). At least one shortLabel is expected for each interactor.");
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        logger.log(Level.SEVERE, "No interactor have been found in participant (" + context.toString() + "). The interactor is mandatory for each participants. The interactor will be loaded as unknown interactor.");
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        logger.log(Level.SEVERE, "No participant and interactor details have been found in interaction (" + context.toString() + "). At least one participant is expected per interaction. The interaction will be empty.");
    }

    @Override
    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        logger.log(Level.WARNING, "The taxid "+taxid+" is invalid. Only positive integer, -3, -2, -1, -4 and -5 are recognized. The organism will be loaded with -3(unknown)");
    }

    @Override
    public void onMissingParameterValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The parameter is invalid as it does not have a factor (" + context.toString() + "). It will be loaded with a value of 0.");
    }

    @Override
    public void onMissingParameterType(FileSourceContext context) {
        logger.log(Level.WARNING, "The parameter is invalid as it does not have a parameter type (" + context.toString() + "). It will be loaded with a 'unknown' parameter type.");
    }

    @Override
    public void onMissingConfidenceValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The confidence is invalid as it does not have a value (" + context.toString() + "). It will be loaded with a value of 0.");
    }

    @Override
    public void onMissingConfidenceType(FileSourceContext context) {
        logger.log(Level.WARNING, "The confidence is invalid as it does not have a type (" + context.toString() + "). It will be loaded with a 'unknown' type.");
    }

    @Override
    public void onMissingChecksumValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The checksum is invalid as it does not have a value (" + context.toString() + "). It will be loaded with a 'unknown' value.");
    }

    @Override
    public void onMissingChecksumMethod(FileSourceContext context) {
        logger.log(Level.WARNING, "The checksum is invalid as it does not have a method (" + context.toString() + "). It will be loaded with a 'unknown' method.");
    }

    @Override
    public void onInvalidPosition(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The position is invalid (" + context.toString() + "): "+message+". It will be loaded as undetermined position.");
    }

    @Override
    public void onInvalidRange(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The range is invalid (" + context.toString() + "): "+message+". It will be loaded as undetermined range.");
    }

    @Override
    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The stoichiometry is invalid (" + context.toString() + "): "+message+". It will be loaded with a value of 0.");
    }

    @Override
    public void onXrefWithoutDatabase(FileSourceContext context) {
        logger.log(Level.WARNING, "The xref is invalid as it does not have a database (" + context.toString() + "). It will be loaded with a 'unknwon database'.");
    }

    @Override
    public void onXrefWithoutId(FileSourceContext context) {
        logger.log(Level.WARNING, "The xref is invalid as it does not have an id (" + context.toString() + "). It will be loaded with a 'unknown' id.");
    }

    @Override
    public void onAnnotationWithoutTopic(FileSourceContext context) {
        logger.log(Level.WARNING, "The annotation is invalid as it does not have a topic (" + context.toString() + "). It will be loaded with a 'unknown' topic.");
    }

    @Override
    public void onAliasWithoutName(FileSourceContext context) {
        logger.log(Level.WARNING, "The alias is invalid as it does not have a value (" + context.toString() + "). It will be loaded with a 'unspecified' name.");
    }

    public void onUnresolvedReference(XmlIdReference ref, String message) {
        logger.log(Level.SEVERE, "Unresolved reference: "+ref.toString()+ ", "+message);
    }
}
