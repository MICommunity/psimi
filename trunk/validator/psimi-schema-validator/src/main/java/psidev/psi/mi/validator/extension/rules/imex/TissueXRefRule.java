package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Tissue;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.IDENTITY_MI_REF;

/**
 * This rule checks that each existing tissue has an identity cross reference to BRENDA
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class TissueXRefRule extends ObjectRule<Tissue> {

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
        if (t instanceof Tissue){
            return true;
        }
        return false;
    }

    /**
     * @param tissue to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Tissue tissue ) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();

        if (tissue.getXref() != null){
            Xref ref = tissue.getXref();

            Collection<DbReference> brendaRef = RuleUtils.findByDatabaseAndReferenceType(ref.getAllDbReferences(), RuleUtils.BRENDA_MI_REF, RuleUtils.BRENDA, RuleUtils.IDENTITY_MI_REF, RuleUtils.IDENTITY);
            Collection<DbReference> tissueRef = RuleUtils.findByDatabaseAndReferenceType(ref.getAllDbReferences(), RuleUtils.TISSUE_LIST_MI_REF, RuleUtils.TISSUE_LIST, RuleUtils.IDENTITY_MI_REF, RuleUtils.IDENTITY);

            if (brendaRef.isEmpty() && tissueRef.isEmpty()){
                messages.add( new ValidatorMessage( "The tissue " + tissue.getNames().getShortLabel() + " has "+ref.getAllDbReferences().size()+" cross refrences but no one is a BRENDA or Tissue List cross reference with a qualifier 'identity' and it  is strongly recommended.'",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
        }
        else {
            messages.add( new ValidatorMessage( "The tissue " + tissue.getNames().getShortLabel() + " doesn't have any cross references and at least one cross reference to BRENDA or Tissue List " +
                    "qualifier 'identity' is strongly recommended.'",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }
}
