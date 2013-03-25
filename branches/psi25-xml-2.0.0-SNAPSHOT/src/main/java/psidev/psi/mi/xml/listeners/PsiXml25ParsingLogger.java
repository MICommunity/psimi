package psidev.psi.mi.xml.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.events.*;

/**
 * Logger for psi25 xml parsing events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/03/13</pre>
 */

public class PsiXml25ParsingLogger implements PsiXml25ParserListener{
    private Log log = LogFactory.getLog(PsiXml25ParsingLogger.class);
    private StringBuffer stringBuffer = new StringBuffer();

    public void fireOnInvalidXmlSyntax(InvalidXmlEvent event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("There is a xml syntax error: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMultipleExperimentalRolesEvent(MultipleExperimentalRolesEvent event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("Multiple experimental roles are attached to the same participant: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMultipleExperimentsPerInteractionEvent(MultipleExperimentsPerInteractionEvent event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("Multiple experiments are attached to the same interaction: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMultipleExpressedInOrganismsEvent(MultipleExpressedInOrganisms event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("Multiple expressed in host organisms are attached to the same participant: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMultipleHostOrganismsPerExperimentEvent(MultipleHostOrganismsPerExperiment event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("Multiple host organsims are attached to the same experiment: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMultipleInteractionTypesEvent(MultipleInteractionTypesEvent event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("Multiple interaction types are attached to the same interaction: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMultipleParticipantIdentificationMethodsEvent(MultipleParticipantIdentificationMethodsPerParticipant event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("Multiple participant identification methods are attached to the same participant: ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.error(stringBuffer.toString());
        }
    }

    public void fireOnMissingCvEvent(MissingCvEvent event) {
        if (event != null){
            stringBuffer.setLength(0);
            stringBuffer.append("A cv term is missing : ");
            if (event.getSourceLocator() != null){
                FileSourceLocator locator = event.getSourceLocator();
                stringBuffer.append(locator.getLocationDescription());
            }
            stringBuffer.append(" ").append(event.getMessage());
            log.info(stringBuffer.toString());
        }
    }
}
