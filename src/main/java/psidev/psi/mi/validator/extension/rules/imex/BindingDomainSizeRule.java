package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiFeatureRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks that each binding domain contains more than three amino acids
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class BindingDomainSizeRule extends MiFeatureRule {
    public BindingDomainSizeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Binding domain Check");
        setDescription("Checks that each binding domain contains more than three amino acids. ");
        addTip("when the feature type is any children of binding site (MI:0117), the range should contain at least three amino acids, otherwise it is considered as a mutant.");
        addTip( "Mutant accessions in the PSI-MI ontology can be found at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0118&termName=mutation");
    }

    private long getMaxRangeLength(psidev.psi.mi.jami.model.Range range, boolean isStartDefined, boolean isEndDefined){
        long startPos = 0;
        long endPos = 0;

        if (isStartDefined){
            startPos = range.getStart().getStart();
        }

        if (isEndDefined) {
            endPos = range.getEnd().getEnd();
        }

        if (PositionUtils.isLessThan(range.getStart())){
            startPos =  Math.max(startPos - 1, 1);
        }
        else if (PositionUtils.isGreaterThan(range.getStart())){
            startPos =  Math.min(startPos + 1, endPos);
        }

        if (PositionUtils.isLessThan(range.getEnd())){
            endPos =  Math.max(endPos - 1, startPos);
        }
        else if (PositionUtils.isGreaterThan(range.getEnd())){
            endPos =  endPos + 1;
        }
        return endPos - startPos + 1;
    }

    private long getMinRangeLength(psidev.psi.mi.jami.model.Range range, boolean isStartDefined, boolean isEndDefined){

        long startPos = 0;
        long endPos = 0;

        if (isStartDefined){
            startPos = range.getStart().getEnd();
        }

        if (isEndDefined) {
            endPos = range.getEnd().getStart();
        }

        if (PositionUtils.isLessThan(range.getStart())){
            startPos =  Math.max(1, startPos - 1);
        }
        else if (PositionUtils.isGreaterThan(range.getStart())){
            startPos =  Math.min(startPos + 1, endPos);
        }

        if (PositionUtils.isLessThan(range.getEnd())){
            endPos =  Math.max(endPos - 1, startPos);
        }
        else if (PositionUtils.isGreaterThan(range.getEnd())){
            endPos =  endPos + 1;
        }

        return endPos - startPos + 1;
    }

    @Override
    public Collection<ValidatorMessage> check(FeatureEvidence feature) throws ValidatorException {
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        if (feature.getType() != null){
            if (RuleUtils.isBindingSite(ontologyManager, feature)){
                Collection<psidev.psi.mi.jami.model.Range> ranges = feature.getRanges();

                int minSize = 0;
                int maxSize = 0;
                boolean isFeatureSiteDefined = false;

                for (psidev.psi.mi.jami.model.Range range : ranges){

                    boolean isStartDefined = true;
                    boolean isEndDefined = true;

                    if (range.getStart().isPositionUndetermined()){
                        isStartDefined = false;
                    }

                    if (range.getEnd().isPositionUndetermined()){
                        isEndDefined = false;
                    }

                    isFeatureSiteDefined = isStartDefined && isEndDefined;

                    if (!isFeatureSiteDefined){
                        break;
                    }

                    minSize += getMinRangeLength(range, isStartDefined, isEndDefined);
                    maxSize += getMaxRangeLength(range, isStartDefined, isEndDefined);
                }

                if (minSize < 3 && maxSize < 3 && isFeatureSiteDefined){
                    Mi25Context context = RuleUtils.buildContext(feature, "feature");
                    if (feature.getParticipantEvidence() != null){
                        context.addAssociatedContext(RuleUtils.buildContext(feature.getParticipantEvidence(), "participant"));
                    }
                    messages.add( new ValidatorMessage( "The binding site does not contain more than three amino acids. For one or two amino acids, the feature type should be any children of mutation instead of binding site.'",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                else if (minSize < 3 && maxSize >= 3 && isFeatureSiteDefined){
                    Mi25Context context = RuleUtils.buildContext(feature, "feature");
                    if (feature.getParticipantEvidence() != null){
                        context.addAssociatedContext(RuleUtils.buildContext(feature.getParticipantEvidence(), "participant"));
                    }
                    messages.add( new ValidatorMessage( "The minimum size of this binding site is "+minSize+" and binding site should always contain more than three amino acids.'",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
            }
        }

        return messages;
    }

    public String getId() {
        return "R61";
    }
}
