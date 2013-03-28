package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.IDENTITY_MI_REF;

/**
 * This rule checks that each existing tissue has an identity cross reference to BRENDA
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class TissueXRefRule extends ObjectRule<Organism> {

    public TissueXRefRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Tissue XRef Check" );
        setDescription( "Checks that each organism tissue (if it is present) has a BRENDA or Tissue List cross reference with a qualifier 'identity'" );
        addTip( "Search for existing tissue terms at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=BTO or www.expasy.org/cgi-bin/lists?tisslist.txt" );
        addTip( "BRENDA accession in the PSI-MI ontology is " + RuleUtils.BRENDA_MI_REF );
        addTip( "Tissue List accession in the PSI-MI ontology is " + RuleUtils.TISSUE_LIST_MI_REF );
        addTip( "Identity accession in the PSI-MI ontology is " + IDENTITY_MI_REF );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Organism){
            return true;
        }
        return false;
    }

    /**
     * @param organism to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Organism organism ) throws ValidatorException {

        if (organism.getTissue() == null){
            return Collections.EMPTY_LIST;
        }

        CvTerm tissue = organism.getTissue();
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(tissue, "tissue");
        context.addAssociatedContext(RuleUtils.buildContext(organism, "organism"));

        if (!tissue.getIdentifiers().isEmpty()){

            Collection<psidev.psi.mi.jami.model.Xref> brendaRef = XrefUtils.collectAllXrefsHavingDatabase(tissue.getIdentifiers(), RuleUtils.BRENDA_MI_REF, RuleUtils.BRENDA);
            Collection<psidev.psi.mi.jami.model.Xref> tissueRef = XrefUtils.collectAllXrefsHavingDatabase(tissue.getIdentifiers(), RuleUtils.TISSUE_LIST_MI_REF, RuleUtils.TISSUE_LIST);

            if (brendaRef.isEmpty() && tissueRef.isEmpty()){
                messages.add( new ValidatorMessage( "The tissue " + tissue.getShortName() + " has "+tissue.getIdentifiers().size()+" identifier(s) but none of them is a BRENDA or Tissue List cross reference with a qualifier 'identity' and it  is strongly recommended.'",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
        }
        else {
            messages.add( new ValidatorMessage( "The tissue " + tissue.getShortName() + " doesn't have any cross references and at least one cross reference to BRENDA or Tissue List " +
                    "qualifier 'identity' is strongly recommended.'",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R76";
    }
}
