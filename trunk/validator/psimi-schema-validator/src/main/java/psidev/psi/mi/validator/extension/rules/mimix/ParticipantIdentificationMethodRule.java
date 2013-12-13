package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.ParticipantEvidence;
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
 * Checks if an experiment have a Participant identification Method and that all the participant identification methods are valid.
 *
 * Rule Id = 6. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: ParticipantIdentificationMethodRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class ParticipantIdentificationMethodRule extends AbstractMIRule<ParticipantEvidence> {

    public ParticipantIdentificationMethodRule(OntologyManager ontologyMaganer) {
        super( ontologyMaganer, ParticipantEvidence.class );

        setName( "Participant Identification Method check" );
        setDescription( "Checks that each participant has at least one Participant Identification Method (MI:0002)." );
        addTip( "Each participant should have a Participant Identification method" );
        addTip( "Each participant identification method should have a PSI MI cross reference with a reference type set to identical object (MI:0356)" );
        addTip( "Any child of MI:0002 is a Participant Identification method. You can look at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI." );
        addTip( "The PSI-MI identifier for identical object is: MI:0356" );
    }

    /**
     * Make sure that a participant evidence has a valid Participant Identification Method that all the participant identification methods are valid.
     *
     * @param participant an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( ParticipantEvidence participant ) throws ValidatorException {

        // list of messages to return
        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        if (participant.getIdentificationMethods().isEmpty()){
            Mi25Context context = RuleUtils.buildContext(participant, "participant");

            messages=Collections.singleton(new ValidatorMessage(" The participant does not have a participant identification method and it is required for MIMIx",
                    MessageLevel.ERROR,
                    context,
                    this));
        }

        return messages;
    }

    public String getId() {
        return "R30";
    }
}
