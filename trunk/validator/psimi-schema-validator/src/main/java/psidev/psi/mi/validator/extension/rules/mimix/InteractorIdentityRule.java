package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.BIOACTIVE_ENTITY_DATABASE_MI_REF;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.SEQUENCE_DATABASE_MI_REF;

/**
 * <b> Check that each interactor has a cross reference to an appropriate reference database.</b>
 * <p/>
 *
 * @author Samuel Kerrien, Luisa Montecchi
 * @version $Id$
 * @since 1.0
 */
public class InteractorIdentityRule extends AbstractMIRule<Interactor> {

    public InteractorIdentityRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer, Interactor.class );

        // describe the rule.
        setName( "Interactor database reference check" );
        setDescription( "Check that each interactor has a cross reference to an appropriate reference database. If not, a sequence should be provided." );
        addTip( "Sequence databases can be found in the PSI-MI ontology under term MI:0683" );
        addTip( "Bioactive entity databases can be found in the PSI-MI ontology under term MI:2054" );
        addTip( "The term " );
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interactor to check on.
     * @return a collection of validator messages.
     * @exception psidev.psi.tools.validator.ValidatorException if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interactor interactor ) throws ValidatorException {

        // list of messages to return
        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        final OntologyAccess mi = getMiOntology();

        // write the rule here ...
        if( interactor.getInteractorType() != null && (RuleUtils.isSmallMolecule( ontologyManager, interactor ) || RuleUtils.isPolysaccharide( ontologyManager, interactor ))) {

            final Set<OntologyTermI> dbs = mi.getValidTerms( BIOACTIVE_ENTITY_DATABASE_MI_REF, true, false );

            final Set<String> dbMiRefs = RuleUtils.collectAccessions( dbs );
            final Set<String> dbRefs = RuleUtils.collectNames( dbs );

            final Collection<Xref> identities = XrefUtils.searchAllXrefsHavingDatabases(interactor.getIdentifiers(), dbMiRefs, dbRefs);

            if( identities.isEmpty() ) {
                MiContext context = RuleUtils.buildContext(interactor, "interactor");
                messages=Collections.singleton(new ValidatorMessage("Bioactive entities should have an Xref to a bioactive entity database with a ref type 'identity' ",
                        MessageLevel.WARN,
                        context,
                        this));
            }

        }
        else if( interactor.getInteractorType() != null && RuleUtils.isBiopolymer(ontologyManager, interactor)) {

            final Set<OntologyTermI> dbs = mi.getValidTerms( SEQUENCE_DATABASE_MI_REF, true, false );

            final Set<String> dbMiRefs = RuleUtils.collectAccessions( dbs );
            final Set<String> dbRefs = RuleUtils.collectNames( dbs );

            final Collection<Xref> identities = XrefUtils.searchAllXrefsHavingDatabases(interactor.getIdentifiers(), dbMiRefs, dbRefs);
            if( identities.isEmpty() ) {

                if (interactor instanceof Polymer && ((Polymer) interactor).getSequence() == null){
                    MiContext context = RuleUtils.buildContext(interactor, "interactor");
                    messages=Collections.singleton( new ValidatorMessage( "Polymers should have a sequence or a Xref to a sequence database with a ref type 'identity'",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }

        }

        return messages;
    }

    public OntologyAccess getMiOntology() throws ValidatorException {
        return ontologyManager.getOntologyAccess( "MI" );
    }

    public String getId() {
        return "R32";
    }
}