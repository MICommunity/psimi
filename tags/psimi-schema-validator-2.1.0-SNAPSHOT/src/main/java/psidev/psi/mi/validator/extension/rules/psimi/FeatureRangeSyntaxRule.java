package psidev.psi.mi.validator.extension.rules.psimi;

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
 * Rule to check feature range syntax and consistency
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class FeatureRangeSyntaxRule extends ObjectRule<MolecularInteractionFileDataSource> {


    public FeatureRangeSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        setName( "Feature range syntax rule check" );

        setDescription( "Check that each feature has at least one range and each range has a start/end position and status." );
    }

    @Override
    public boolean canCheck(Object t) {
        return ontologyManager instanceof MolecularInteractionFileDataSource;
    }

    @Override
    public Collection<ValidatorMessage> check(MolecularInteractionFileDataSource molecularInteractionFileDataSource) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Collection<FileSourceError> wrongRanges = MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.invalid_feature_range.toString());
        wrongRanges.addAll(MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.feature_without_ranges.toString()));
        wrongRanges.addAll(MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.missing_range_position.toString()));
        wrongRanges.addAll(MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.missing_range_status.toString()));

        for (FileSourceError error : wrongRanges){
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
        return "R20";
    }
}