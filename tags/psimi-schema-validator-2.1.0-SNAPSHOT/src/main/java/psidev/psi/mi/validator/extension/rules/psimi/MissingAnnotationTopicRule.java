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
 * Check that an annotation topic is provided
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class MissingAnnotationTopicRule extends MiFileDataSourceRule {


    public MissingAnnotationTopicRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        setName( "Missing annotation topic check" );

        setDescription( "Check that each annotation has a non empty topic." );
    }

    @Override
    public Collection<ValidatorMessage> check(MolecularInteractionFileDataSource molecularInteractionFileDataSource) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Collection<FileSourceError> missingTopics = MolecularInteractionFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(molecularInteractionFileDataSource.getDataSourceErrors(), FileParsingErrorType.missing_annotation_topic.toString());
        for (FileSourceError error : missingTopics){
            Mi25Context context = null;
            if (error.getSourceContext() != null){
                context = RuleUtils.buildContext(error.getSourceContext().getSourceLocator(), "annotation");
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
        return "R18";
    }
}