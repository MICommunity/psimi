package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlIdReference;

import java.util.Collection;
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
        logger.log(Level.WARNING, message + "(" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + ")");
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        logger.log(Level.SEVERE, "Invalid syntax (" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + "): ", e);
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + ")");
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        logger.log(Level.WARNING, "No aliases have been found in (" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + "). At least one alias is expected for each interactor.");
    }

    public void onSeveralCvTermsFound(Collection<? extends CvTerm> terms, FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + ")");
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        logger.log(Level.SEVERE, "No interactor details have been found in participant (" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + "). At least one unique identifier and one alias is expected to describe the interactor. The interactor will be loaded as unknown interactor.");
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        logger.log(Level.SEVERE, "No participant and interactor details have been found in interaction (" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + "). At least one participant is expected per interaction. The interaction will be empty.");
    }

    public void onSeveralHostOrganismFound(Collection<? extends Organism> organisms, FileSourceContext context) {
        logger.log(Level.WARNING, organisms.size() + " host organisms have been found in (" + (context.getSourceLocator() != null ? context.getSourceLocator().toString() : "") + "). Only one host organism is expected per interaction so only the first host organism will be loaded.");
    }

    public void onUnresolvedReference(XmlIdReference ref, String message) {
        logger.log(Level.SEVERE, "Unresolved reference "+ref.toString()+ ", "+message);
    }
}
