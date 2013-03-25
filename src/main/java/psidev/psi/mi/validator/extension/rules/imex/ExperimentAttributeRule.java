package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Rule that checks that each attribute has a valid attribute ac (psi-mi number)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/11</pre>
 */

public class ExperimentAttributeRule extends Mi25ExperimentRule{

    private static final String COPYRIGHT = "Copyright";

    private List<String> acceptedAttributes;

    public ExperimentAttributeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        acceptedAttributes = new ArrayList<String>();
        initializeAcceptedAttributes();

        // describe the rule.
        setName( "Experiment Annotations Check" );
        setDescription( "Checks that each annotation has a name associated with a valid PSI-MI term and/or is a valid annotation for an experiment according to the IMEx curation rules." );
        addTip( "Existing experiment's attribute terms can be found at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0590&termName=attribute%20name." );
    }

    private void initializeAcceptedAttributes(){
        acceptedAttributes.add(RuleUtils.URL);
        acceptedAttributes.add(RuleUtils.LIBRARY_USED);
        acceptedAttributes.add(RuleUtils.EXP_MODIFICATION);
        acceptedAttributes.add(RuleUtils.DATASET);
        acceptedAttributes.add(RuleUtils.DATA_PROCESSING);
        acceptedAttributes.add(RuleUtils.COMMENT);
        acceptedAttributes.add(RuleUtils.CAUTION);
        acceptedAttributes.add(RuleUtils.AUTHOR_CONFIDENCE_MI_REF);
        acceptedAttributes.add(RuleUtils.ANTIBODIES);
        acceptedAttributes.add(RuleUtils.AUTHOR_LIST);
        acceptedAttributes.add(RuleUtils.JOURNAL);
        acceptedAttributes.add(RuleUtils.PUBLICATION_YEAR);
        acceptedAttributes.add(RuleUtils.AUTHOR_SUBMITTED);
        acceptedAttributes.add(RuleUtils.CONTACT_EMAIL_MI_REF);
        acceptedAttributes.add(RuleUtils.CURATION_REQUEST);
        acceptedAttributes.add(RuleUtils.IMEX_CURATION);
        acceptedAttributes.add(RuleUtils.FULL_COVERAGE);
    }

    @Override
    public Collection<ValidatorMessage> check(Experiment experiment) throws ValidatorException {
        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        if (!experiment.getAnnotations().isEmpty()){
            Collection<Annotation> attributes = experiment.getAnnotations();
            Mi25Context experimentContext = RuleUtils.buildContext(experiment, "experiment");

            for (Annotation attribute : attributes){
                if (attribute.getTopic().getMIIdentifier() == null && !AnnotationUtils.doesAnnotationHaveTopic(attribute, null, COPYRIGHT)){
                    Mi25Context context = RuleUtils.buildContext(attribute, "attribute");
                    context.addAssociatedContext(experimentContext);

                    messages.add( new ValidatorMessage( "The attribute " + attribute.getTopic().getShortName() + " does not have any nameAc. " +
                            "All experiment's attributes should have a nameAc pointing to a valid PSI MI term (Excepted '"+COPYRIGHT+"' attribute)." +
                            " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                else {

                    if (!acceptedAttributes.contains(attribute.getTopic().getMIIdentifier())){
                        Mi25Context context = RuleUtils.buildContext(attribute, "attribute");
                        context.addAssociatedContext(experimentContext);
                        messages.add( new ValidatorMessage( "The attribute " + attribute.getTopic().getMIIdentifier() + " ( " + attribute.getTopic().getShortName()+") is not a child of MI:0665 ( 'experiment attribute name' ). " +
                                " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }
                }
            }

        }

        return messages;
    }

    public String getId() {
        return "R27";
    }
}
