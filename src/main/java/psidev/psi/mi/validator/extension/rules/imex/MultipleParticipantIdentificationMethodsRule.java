package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.jami.datasource.FileSourceError;
import psidev.psi.mi.jami.datasource.MolecularInteractionFileDataSource;
import psidev.psi.mi.jami.utils.MolecularInteractionFileDataSourceUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Rule to check if a participant has several participant identification methods
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class MultipleParticipantIdentificationMethodsRule extends ObjectRule<MolecularInteractionFileDataSource> {


    public MultipleParticipantIdentificationMethodsRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        setName( "Multiple Participant's identification methods check" );

        setDescription( "Check if a participant has several participant identification methods." );
    }

    @Override
    public boolean canCheck(Object t) {
        return ontologyManager instanceof MolecularInteractionFileDataSource;
    }

    @Override
    public Collection<ValidatorMessage> check(MolecularInteractionFileDataSource molecularInteractionFileDataSource) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Collection<FileSourceError> multipleDetMethod = MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.multiple_participant_identification_methods.toString());
        for (FileSourceError error : multipleDetMethod){
            Mi25Context context = null;
            if (error.getSourceContext() != null){
                context = RuleUtils.buildContext(error.getSourceContext());
            }
            else {
                context = new Mi25Context();
            }

            messages.add( new ValidatorMessage( error.getLabel() + ": " + error.getMessage(),
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R83";
    }
}