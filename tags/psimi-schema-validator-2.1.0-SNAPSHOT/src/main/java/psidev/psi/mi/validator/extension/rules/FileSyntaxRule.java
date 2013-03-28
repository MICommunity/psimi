package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.jami.datasource.FileSourceError;
import psidev.psi.mi.jami.datasource.MolecularInteractionFileDataSource;
import psidev.psi.mi.jami.utils.MolecularInteractionFileDataSourceUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule is for checking the fileSyntax
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class FileSyntaxRule extends ObjectRule<MolecularInteractionFileDataSource>{

    public FileSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        setName( "Molecular Interaction file syntax check" );

        setDescription( "Check that the file syntax is correct." );
        addTip( "If the error is too obscure, contact the intact team (intact-help@ebi.ac.uk) with your file attached to the e-mail." );
    }

    @Override
    public boolean canCheck(Object t) {
        return ontologyManager instanceof MolecularInteractionFileDataSource;
    }

    @Override
    public Collection<ValidatorMessage> check(MolecularInteractionFileDataSource molecularInteractionFileDataSource) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // validate syntax, relies on data source to do the proper syntax validation
        molecularInteractionFileDataSource.validateFileSyntax();

        Collection<FileSourceError> basicSyntaxErrors = MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.invalid_syntax.toString());

        for (FileSourceError error : basicSyntaxErrors){
            Mi25Context context = null;
            if (error.getSourceContext() != null){
                context = RuleUtils.buildContext(error.getSourceContext());
            }
            else {
                context = new Mi25Context();
            }

            messages.add( new ValidatorMessage( error.getLabel() + ": " + error.getMessage(),
                    MessageLevel.FATAL,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "1";
    }
}
