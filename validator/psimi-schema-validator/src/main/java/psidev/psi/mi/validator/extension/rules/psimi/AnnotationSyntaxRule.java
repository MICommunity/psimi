package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * Check that an annotation has a valid syntax
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class AnnotationSyntaxRule extends AbstractMIRule<Annotation> {


    public AnnotationSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager, Annotation.class);
        setName( "Missing annotation topic check" );

        setDescription( "Check that each annotation has a non empty topic. If a topic MI identifier is provided, check that it is a valid " +
                "MI identifier for Cv topics" );
    }

    @Override
    public Collection<ValidatorMessage> check(Annotation annotation) throws ValidatorException {
        if (annotation != null){
            // list of messages to return
            Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

            CvTerm topic = annotation.getTopic();

            if (topic == null ||
                    PsiXml25Utils.UNSPECIFIED.equals(topic.getShortName()) ||
                    MitabUtils.UNKNOWN_DATABASE.equals(topic.getShortName())){
                Mi25Context annotationContext = RuleUtils.buildContext(annotation, "annotation");

                messages = Collections.singleton(new ValidatorMessage("Annotations must have a valid and non empty topic.'",
                        MessageLevel.ERROR,
                        annotationContext,
                        this));
            }
            else if (topic != null && topic.getMIIdentifier() != null){
                final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                final OntologyTermI dbTerm = access.getTermForAccession(topic.getMIIdentifier());

                if (dbTerm == null){
                    Mi25Context context = RuleUtils.buildContext(annotation, "annotation");
                    messages = Collections.singleton( new ValidatorMessage( "The annotation topic MI identifier "+topic.getMIIdentifier()+"  does not exist in the PSI-MI ontology. The valid MI terms for annotation topics are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0590&termName=attribute%20name",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerm);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:0590".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        Mi25Context context = RuleUtils.buildContext(annotation, "annotation");

                        messages = Collections.singleton( new ValidatorMessage( "The MI identifier "+topic.getMIIdentifier()+" is not a valid MI identifier for annotation topics. The valid MI terms for annotation topics are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0590&termName=attribute%20name",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }

            return messages;
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R18";
    }
}