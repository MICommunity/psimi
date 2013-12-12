package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * This rule is for checking that all interactors have at least a valid shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class MissingInteractorNameRule extends AbstractMIRule<Interactor> {


    public MissingInteractorNameRule(OntologyManager ontologyManager) {
        super(ontologyManager, Interactor.class);
        setName( "Interactor name's check" );

        setDescription( "Check that each interactor has a valid name." );
    }

    @Override
    public Collection<ValidatorMessage> check(Interactor interactor) throws ValidatorException {

        if (interactor.getShortName() == null ||
                interactor.getShortName().length() == 0 ||
                PsiXml25Utils.UNSPECIFIED.equals(interactor.getShortName()) ||
                MitabUtils.UNKNOWN_NAME.equals(interactor.getShortName())){
            // list of messages to return
            Mi25Context experimentContext = RuleUtils.buildContext(interactor, "interactor");
            return Collections.singletonList(new ValidatorMessage( "Interactors must have a valid short name (or alias in MITAB).'",
                    MessageLevel.ERROR,
                    experimentContext,
                    this ) );
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R25";
    }
}
