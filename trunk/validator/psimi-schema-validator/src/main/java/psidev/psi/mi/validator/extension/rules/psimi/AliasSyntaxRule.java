package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Alias;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Rule to check that each alias has a name
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class AliasSyntaxRule extends AbstractMIRule<Alias> {


    public AliasSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager, Alias.class);
        setName( "Alias syntax check" );

        setDescription( "Check that each alias has a valid name and if it has a MI alias type, it must have a valid MI term for alias type." );
    }

    @Override
    public Collection<ValidatorMessage> check(Alias alias) throws ValidatorException {
        if (alias != null){
            // list of messages to return
            List<ValidatorMessage> messages = Collections.EMPTY_LIST;

            if (alias.getName() == null ||
                    alias.getName().trim().length() == 0 ||
                    PsiXml25Utils.UNSPECIFIED.equals(alias.getName()) ||
                    MitabUtils.UNKNOWN_NAME.equals(alias.getName())){
                Mi25Context aliasContext = RuleUtils.buildContext(alias, "alias");

                messages = new ArrayList<ValidatorMessage>();
                messages.add( new ValidatorMessage( "Aliases must have a valid and non empty name.'",
                        MessageLevel.ERROR,
                        aliasContext,
                        this ) );
            }

            if (alias.getType() != null && alias.getType().getMIIdentifier() != null){
                final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                final OntologyTermI dbTerm = access.getTermForAccession(alias.getType().getMIIdentifier());

                if (dbTerm == null){
                    Mi25Context context = RuleUtils.buildContext(alias, "alias");
                    if (messages.isEmpty()){
                       messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The alias type MI identifier "+alias.getType().getMIIdentifier()+"  does not exist in the PSI-MI ontology. The valid MI terms for alias types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0300&termName=alias%20type",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerm);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:0300".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        Mi25Context context = RuleUtils.buildContext(alias, "alias");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        messages.add( new ValidatorMessage( "The MI identifier "+alias.getType().getMIIdentifier()+" is not a valid MI identifier for alias types. The valid MI terms for alias types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0300&termName=alias%20type",
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
        return "R16";
    }
}
