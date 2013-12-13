package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.PublicationRuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * This rule checks if the experiment has at least one cross-reference type set to 'imex-primary'. Then check if the imex
 * imex ID(s) is(are) valid.
 *
 * Rule Id = 2. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class PublicationImexPrimaryRule extends AbstractMIRule<Publication> {

    public PublicationImexPrimaryRule(OntologyManager ontologyMaganer) {
        super( ontologyMaganer,Publication.class );

        // describe the rule.
        setName( "Publication Imex-primary cross reference check" );
        setDescription( "Checks that a publication imex" +
                "ID is correct." );
        addTip( "All records must have an IMEx ID (IM-xxx) when there is a cross reference type: imex-primary" );
        addTip( "The PSI-MI identifier for imex-primary is: MI:0662" );
    }

    /**
     * Make sure that an experiment has a valid IMEX id in its xref.
     *
     * @param pub a publication to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Publication pub ) throws ValidatorException {

        // Check xRef
        return PublicationRuleUtils.checkImexId(pub.getImexId(), pub, this);
    }

    public String getId() {
        return "R36";
    }
}
