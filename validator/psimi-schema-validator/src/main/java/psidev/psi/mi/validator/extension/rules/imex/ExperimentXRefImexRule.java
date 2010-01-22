package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Bibref;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <b> Imex Rule : check that each experiment has a valid bibref to pubmed or DOI. Check if the IMEx ID is valid when a cross reference type 'imex-primary' appears. </b>
 * <p/>
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: ExperimentXRefImexRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class ExperimentXRefImexRule extends Mi25ExperimentRule {

    // The good syntax of an Imex ID.
    Pattern IMEx_ID = Pattern.compile( "IM-[0-9]+" );

    public ExperimentXRefImexRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment bibliographic reference IMEX check" );
        setDescription( "Checks that each experiment has a at least one publication reference (pubmed or doi)." );
        addTip( "You can search for pubmed identifier at http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=PubMed" );
        addTip( "Your pubmed or DOI bibliographical reference should have a type: primary-reference" );
        addTip( "All records must have an IMEx ID (IM-xxx) when there is a cross reference type: imex-primary" );
        addTip( "The PSI-MI identifier for PubMed is: MI:0446" );
        addTip( "The PSI-MI identifier for DOI is: MI:0574" );
        addTip( "The PSI-MI identifier for primary-reference is: MI:0358" );
        addTip( "The PSI-MI identifier for imex-primary is: MI:0662" );
    }

    /**
     * Make sure that an experiment either has a pubmed/DOI id in its bibRef. Check also that at least one pubMed Id or DOI has a reference type set to 'primary-reference'.
     * Check if there is at least one references with a 'imex-primary' cross reference type and a valid IMEx ID (IM-xxx).
     *
     * @param experiment an experiment to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int experimentId = experiment.getId();

        Mi25Context context = new Mi25Context();
        context.setExperimentId( experimentId );

        final Bibref bibref = experiment.getBibref();

        if ( bibref != null ) {

            final Xref xref = bibref.getXref();
            if ( xref != null ) {

                final Collection<DbReference> dbReferences = xref.getAllDbReferences();
                final Collection<DbReference> allDbReferences = new ArrayList<DbReference>();
                allDbReferences.addAll(dbReferences);

                // Check xRef
                if (experiment.hasXref()){
                    allDbReferences.addAll(experiment.getXref().getAllDbReferences());
                }

                // search for reference type: primary-reference
                Collection<DbReference> primaryReferences = RuleUtils.findByReferenceType( dbReferences, "MI:0358", "primary-reference" );
                // search for reference type: imex-primary (should not be empty)
                Collection<DbReference> imexReferences = RuleUtils.findByReferenceType( allDbReferences, "MI:0662", "imex-primary" );
                // search for database pubmed or DOI.
                Collection<DbReference> allPubmeds = RuleUtils.findByDatabase( dbReferences, "MI:0446", "pubmed" );
                Collection<DbReference> allDois = RuleUtils.findByDatabase( dbReferences, "MI:0574", "doi" );

                // At least one cross reference type 'imex-primary' is required. Doesn't need to test if the ID is valid as ExperimentBibRefRule is checking that.
                if (imexReferences.isEmpty()){
                    messages.add( new ValidatorMessage( "At least one reference with a reference type 'imex-primary' is required.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }

                // At least one pubmed/DOI reference is required
                if ( !allPubmeds.isEmpty() || !allDois.isEmpty()){

                    // At least one reference-type set to 'primary-reference' is required
                    if ( !primaryReferences.isEmpty() ) {
                        // check if we have a pubmed or doi identifier available. Doesn't test if it is valid as ExperimentBibRefRule is checking that.

                        final Collection<DbReference> pubmeds = RuleUtils.findByDatabase( primaryReferences, "MI:0446", "pubmed" );
                        final Collection<DbReference> dois = RuleUtils.findByDatabase( primaryReferences, "MI:0574", "doi" );

                        if ( pubmeds.isEmpty() && dois.isEmpty() ) {
                            messages.add( new ValidatorMessage( "At least one Database Pubmed Identifier or Digital Object Identifier with a reference type set to 'primary-reference' (MI:0358) is required.",
                                    MessageLevel.ERROR,
                                    context,
                                    this ) );
                        }
                    }
                    else {
                        messages.add( new ValidatorMessage( "At least one of the references must have a reference type set to 'primary-reference' (MI:0358).",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
                else {
                    messages.add( new ValidatorMessage( "At least one of the references has to be a Pubmed/DOI reference.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }
            else {
                messages.add( new ValidatorMessage( "At least one of the references has to be a Pubmed/DOI reference.",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            }
        }

        return messages;
    }
}
