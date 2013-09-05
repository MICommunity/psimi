package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.jami.datasource.FileSourceError;
import psidev.psi.mi.jami.datasource.MolecularInteractionFileDataSource;
import psidev.psi.mi.jami.utils.MolecularInteractionFileDataSourceUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiFileDataSourceRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

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

public class FeatureRangeSyntaxRule extends MiFileDataSourceRule {


    public FeatureRangeSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        setName( "Feature range syntax check" );

        setDescription( "Check that each feature range has a start/end position and start/end status." );
    }

    @Override
    public Collection<ValidatorMessage> check(MolecularInteractionFileDataSource molecularInteractionFileDataSource) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Collection<FileSourceError> wrongRanges = MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), new String[]{FileParsingErrorType.invalid_feature_range.toString(), FileParsingErrorType.missing_range_position.toString(), FileParsingErrorType.missing_range_status.toString()});

        for (FileSourceError error : wrongRanges){
            Mi25Context context = null;
            if (error.getSourceContext() != null){
                context = RuleUtils.buildContext(error.getSourceContext().getSourceLocator(), "feature range");
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