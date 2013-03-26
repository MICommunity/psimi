package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the interaction cross reference specified matches the cross-reference type.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class InteractionCrossReference2CrossReferenceTypeDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog(InteractionCrossReference2CrossReferenceTypeDependencyRule.class);

    /**
     * The dependency mapping object for this rule
     */
    private DependencyMapping mapping;

    //////////////////
    // Constructors

    /**
     *
     * @param ontologyManager
     */
    public InteractionCrossReference2CrossReferenceTypeDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractionCrossReference2CrossReferenceType();

        try {

            URL resource = ExperimentCrossReference2CrossReferenceTypeDependencyRule.class
                    .getResource( fileName );

            // Create a new instance of dependency mapping
            mapping = new DependencyMapping();
            // Build the dependency mapping from the file
            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Dependency Check : Interaction Cross reference database and cross reference qualifier" );
        setDescription( "Checks that each association database - qualifier respects IMEx curetion rules. For example, for each feature, all the interpro" +
                " cross references should have a qualifier 'identity'.");
        addTip( "Search the possible terms for database cross reference and reference type on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/interactionCrossReference2Location2CrossRefType.tsv for the possible dependencies cross reference database - qualifier" );
    }

    ///////////////////////
    // ObjectRule

    /**
     *
     * @param o
     * @return true if o is an instance of XrefContainer.
     */
    public boolean canCheck(Object o) {
        if (o instanceof InteractionEvidence){
            return true;
        }
        return false;
    }


    /**
     * For each cross reference of this XrefContainer, collects all respective cross reference type(s) and
     * check if the dependencies are correct.
     *
     * @param container to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( InteractionEvidence container) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // Collect the db references
        Collection<psidev.psi.mi.jami.model.Xref> databaseReferences = container.getXrefs();

        for ( Xref reference : databaseReferences) {
            Mi25Context context = RuleUtils.buildContext(container, "interaction");
            context.addAssociatedContext(RuleUtils.buildContext(reference, "database cross reference"));

            // build a context in case of error
            messages.addAll( mapping.check( reference.getDatabase(), reference.getQualifier(), context, this ) );
        }

        // Collect the identifiers
        Collection<psidev.psi.mi.jami.model.Xref> identifiers = container.getIdentifiers();

        for ( Xref reference : identifiers) {
            Mi25Context context = RuleUtils.buildContext(container, "interaction");
            context.addAssociatedContext(RuleUtils.buildContext(reference, "database cross reference"));

            // build a context in case of error
            messages.addAll( mapping.check( reference.getDatabase(), reference.getQualifier(), context, this ) );
        }

        return messages;
    }

    public String getId() {
        return "R51";
    }
}