package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.CvTerm;
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
 * This rule is for checking that all cv terms have at least a valid shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class MissingCvTermNameRule extends AbstractMIRule<CvTerm> {


    public MissingCvTermNameRule(OntologyManager ontologyManager) {
        super(ontologyManager, CvTerm.class);
        setName( "Controlled vocabulary name's check" );

        setDescription( "Check that each controlled vocabulary term has a valid name." );
    }

    @Override
    public Collection<ValidatorMessage> check(CvTerm cv) throws ValidatorException {

        if (cv.getShortName() == null ||
                cv.getShortName().length() == 0 ||
                PsiXml25Utils.UNSPECIFIED.equals(cv.getShortName()) ||
                MitabUtils.UNKNOWN_NAME.equals(cv.getShortName()) ||
                MitabUtils.UNKNOWN_DATABASE.equals(cv.getShortName()) ||
                MitabUtils.UNKNOWN_TYPE.equals(cv.getShortName())){
            // list of messages to return
            Mi25Context experimentContext = RuleUtils.buildContext(cv, "Cv term");
            return Collections.singletonList(new ValidatorMessage( "Controlled vocabulary terms must have a valid short name.'",
                    MessageLevel.ERROR,
                    experimentContext,
                    this ) );
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R23";
    }
}
