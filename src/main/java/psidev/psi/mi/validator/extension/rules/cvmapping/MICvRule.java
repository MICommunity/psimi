package psidev.psi.mi.validator.extension.rules.cvmapping;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;
import psidev.psi.tools.validator.rules.cvmapping.CvRuleImpl;
import psidev.psi.tools.validator.rules.cvmapping.Recommendation;
import psidev.psi.tools.validator.xpath.XPathResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CvRule extension for MI validator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/03/13</pre>
 */

public class MICvRule extends CvRuleImpl{

    private Map<String, String> equivalentXmlPath;

    public MICvRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        equivalentXmlPath = new HashMap<String, String>();
        initialiseMapOfEquivalentXMLPath();
    }

    private void initialiseMapOfEquivalentXMLPath(){

        equivalentXmlPath.put("interactionEvidence", "entrySet/entry/interactionList/interaction");
        equivalentXmlPath.put("experiment", "experiments/experimentDescription");
        equivalentXmlPath.put("participantEvidences", "participants");
        equivalentXmlPath.put("featureEvidences", "features");
        equivalentXmlPath.put("@MIIdentifier", "xref/primaryRef/@id");
        equivalentXmlPath.put("@PARIdentifier", "database-,*-xref/primaryRef/@id");
        equivalentXmlPath.put("@MODIdentifier", "xref/primaryRef/@id");
        equivalentXmlPath.put("detectionMethod", "featureDetectionMethod");
        equivalentXmlPath.put("identificationMethod", "participantIdentificationMethod");
        equivalentXmlPath.put("experimentalRole", "experimentalRoles");
        equivalentXmlPath.put("identifiers[1]", "xref/primaryRef");
        equivalentXmlPath.put("type", "featureEvidences-featureType,interactor-interactorType");
        equivalentXmlPath.put("publication", "bibref");
        equivalentXmlPath.put("database", "xref/primaryRef/@dbAc");
    }

    @Override
    /**
     * Build the message and improve context
     */
    public ValidatorMessage buildMessage( String xpath, Recommendation level, String message, Rule rule, List<XPathResult> results,  Object o ) {
        Mi25Context context = new Mi25Context(xpath);
        context.setContext(xpath);

        if (results != null && !results.isEmpty()){
            // takes the first XPathResult
            XPathResult firstResult = results.iterator().next();

            Object rootParent = firstResult.getRootNode();
            if (rootParent instanceof FileSourceContext){
                FileSourceContext fileSourceContext = (FileSourceContext) rootParent;
                context.extractFileContextFrom(fileSourceContext);
                context.getAssociatedContexts().add(RuleUtils.buildContext(o));

            }
            else if (o instanceof FileSourceContext){
                FileSourceContext fileSourceContext = (FileSourceContext) o;
                context.extractFileContextFrom(fileSourceContext);
            }
        }
        else if (o instanceof FileSourceContext){
            FileSourceContext fileSourceContext = (FileSourceContext) o;
            context.extractFileContextFrom(fileSourceContext);
        }
        return new ValidatorMessage( message,
                convertCvMappingLevel( level ),
                context,
                rule );
    }
}
