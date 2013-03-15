package psidev.psi.mi.xml.listeners;

import psidev.psi.mi.xml.events.*;

import java.util.EventListener;

/**
 * Listener to psi25 XML parsing events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public interface PsiXml25ParserListener extends EventListener {

    public void fireOnInvalidXmlSyntax(InvalidXmlEvent event);

    public void fireOnMultipleExperimentalRolesEvent(MultipleExperimentalRolesEvent event);

    public void fireOnMultipleExperimentsPerInteractionEvent(MultipleExperimentsPerInteractionEvent event);

    public void fireOnMultipleExpressedInOrganismsEvent(MultipleExpressedInOrganisms event);

    public void fireOnMultipleHostOrganismsPerExperimentEvent(MultipleHostOrganismsPerExperiment event);

    public void fireOnMultipleInteractionTypesEvent(MultipleInteractionTypesEvent event);

    public void fireOnMultipleParticipantIdentificationMethodsEvent(MultipleParticipantIdentificationMethodsPerParticipant event);
}
