package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.MiValidatorContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the interactor cross reference specified matches the cross-reference type.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class InteractorCrossReference2CrossReferenceTypeDependencyRule extends AbstractMIRule<Interactor> {

    private static final Log log = LogFactory.getLog(InteractorCrossReference2CrossReferenceTypeDependencyRule.class);

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
    public InteractorCrossReference2CrossReferenceTypeDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager, Interactor.class);
        MiValidatorContext validatorContext = MiValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractorCrossReference2CrossReferenceType();

        try {

            URL resource = ExperimentCrossReference2CrossReferenceTypeDependencyRule.class
                    .getResource( fileName );

// Create a new instance of dependency mapping
            mapping = new DependencyMapping();
// Build the dependency mapping from the file
            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (psidev.psi.tools.validator.ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Dependency Check : interactor Cross reference database and cross reference qualifier" );
        setDescription( "Checks that each association database - qualifier respects IMEx curetion rules. For example, for each feature, all the interpro" +
                " cross references should have a qualifier 'identity'.");
        addTip( "Search the possible terms for database cross reference and reference type on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/interactorCrossReference2Location2CrossRefType.tsv for the possible dependencies cross reference database - qualifier" );
    }

///////////////////////
// ObjectRule


    /**
     * For each cross reference of this XrefContainer, collects all respective cross reference type(s) and
     * check if the dependencies are correct.
     *
     * @param container to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interactor container) throws psidev.psi.tools.validator.ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // Collect the identifiers
        Collection<Xref> identifiers = container.getIdentifiers();

        for ( Xref reference : identifiers) {
            MiContext context = RuleUtils.buildContext(reference, "database xref");
            context.addAssociatedContext(RuleUtils.buildContext(container, "interactor"));

// build a context in case of error
            messages.addAll( mapping.check( reference.getDatabase(), reference.getQualifier(), context, this ) );
        }

// Collect the db references
        Collection<Xref> databaseReferences = container.getXrefs();

        for ( Xref reference : databaseReferences) {
            MiContext context = RuleUtils.buildContext(reference, "database xref");
            context.addAssociatedContext(RuleUtils.buildContext(container, "interactor"));

// build a context in case of error
            messages.addAll( mapping.check( reference.getDatabase(), reference.getQualifier(), context, this ) );
        }

        return messages;
    }

    public String getId() {
        return "R54";
    }
}
