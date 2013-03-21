package psidev.psi.mi.validator.extension.rules.cvmapping;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;
import psidev.psi.tools.validator.rules.cvmapping.CvRuleImpl;
import psidev.psi.tools.validator.rules.cvmapping.Recommendation;
import psidev.psi.tools.validator.xpath.XPathResult;

import java.util.List;

/**
 * CvRule extension for MI validator
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
        Mi25Context context = new Mi25Context(xpath);
        context.setContext(xpath);

        if (results != null && !results.isEmpty()){
            // takes the first XPathResult
            XPathResult firstResult = results.iterator().next();

            Object rootParent = firstResult.getRootNode();
            if (rootParent instanceof FileSourceContext){
                FileSourceContext fileSourceContext = (FileSourceContext) rootParent;
                context.extractObjectIdAndLabelFrom(fileSourceContext);
                context.setLineNumber(fileSourceContext.getLineNumber());
                context.setColumnNumber(fileSourceContext.getColumnNumber());
            }
            else if (o instanceof FileSourceContext){
                FileSourceContext fileSourceContext = (FileSourceContext) o;
                context.extractObjectIdAndLabelFrom(fileSourceContext);
                context.setLineNumber(fileSourceContext.getLineNumber());
                context.setColumnNumber(fileSourceContext.getColumnNumber());
            }
        }
        else if (o instanceof FileSourceContext){
            FileSourceContext fileSourceContext = (FileSourceContext) o;
            context.extractObjectIdAndLabelFrom(fileSourceContext);
            context.setLineNumber(fileSourceContext.getLineNumber());
            context.setColumnNumber(fileSourceContext.getColumnNumber());
        }
        return new ValidatorMessage( message,
                convertCvMappingLevel( level ),
                context,
                rule );
    }
}
