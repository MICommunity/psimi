package psidev.psi.mi.validator.extension.rules.cvmapping;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;
import psidev.psi.tools.validator.rules.cvmapping.CvRuleImpl;
import psidev.psi.tools.validator.rules.cvmapping.Recommendation;
import psidev.psi.tools.validator.xpath.XPathResult;

import java.util.List;

/**
 * CvRule extension for MI validator.
 *
 * All cv rules have an interaction context
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/03/13</pre>
 */

public class MICvRule extends CvRuleImpl{

    public MICvRule(OntologyManager ontologyManager) {
        super(ontologyManager);
    }

    @Override
    /**
     * Build the message and improve context
     */
    public ValidatorMessage buildMessage( String xpath, Recommendation level, String message, Rule rule, List<XPathResult> results,  Object o ) {
        MiContext context = new MiContext(xpath);
        context.setContext(xpath);
        context.setContext(RuleUtils.extractObjectLabelFromXPath(xpath));

        if (o instanceof FileSourceContext){
            FileSourceContext fileSourceContext = (FileSourceContext) o;
            context.setLocator(fileSourceContext.getSourceLocator());
        }
        else if (o != null){
           context.setContext(o.toString());
        }
        return new ValidatorMessage( message,
                convertCvMappingLevel( level ),
                context,
                rule );
    }
}
