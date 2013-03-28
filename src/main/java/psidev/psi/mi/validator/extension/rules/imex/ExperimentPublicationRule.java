package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.XrefUtils;
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
 * <b> Imex Rule : check that each experiment has a valid bibref to pubmed or DOI. Check if the IMEx ID is valid when a cross reference type 'imex-primary' appears. </b>
 *
 * Rule Id = 1. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 * <p/>
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: BibRefRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class ExperimentPublicationRule extends ObjectRule<Publication> {

    public ExperimentPublicationRule(OntologyManager ontologyMaganer) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment bibliographic reference check" );
        setDescription( "Checks that each experiment has a at least one publication reference pubmed or doi." );
        addTip( "You can search for pubmed identifier at http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=PubMed" );
        addTip( "Your pubmed or DOI bibliographical reference should have a type: primary-reference" );
        addTip( "The PSI-MI identifier for PubMed is: MI:0446" );
        addTip( "The PSI-MI identifier for DOI is: MI:0574" );
        addTip( "The PSI-MI identifier for primary-reference is: MI:0358" );
    }

    @Override
    public boolean canCheck(Object t) {
        return t instanceof Publication;
    }

    /**
     * Make sure that an experiment either has a pubmed/DOI id in its bibRef. Check also that at least one pubMed Id or DOI has a reference type set to 'primary-reference'.
     *
     * @param pub a publication to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Publication pub ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(pub, "publication");

        final Collection<psidev.psi.mi.jami.model.Xref> dbReferences = pub.getIdentifiers();

        // search for database pubmed or DOI.
        Collection<psidev.psi.mi.jami.model.Xref> allPubmeds = XrefUtils.collectAllXrefsHavingDatabase(dbReferences, psidev.psi.mi.jami.model.Xref.PUBMED_MI, psidev.psi.mi.jami.model.Xref.PUBMED);
        Collection<psidev.psi.mi.jami.model.Xref> allDois = XrefUtils.collectAllXrefsHavingDatabase(dbReferences, psidev.psi.mi.jami.model.Xref.DOI_MI, psidev.psi.mi.jami.model.Xref.DOI);

        // At least one pubmed/DOI reference is required
        if ( !allPubmeds.isEmpty() || !allDois.isEmpty()){
            // Only one pubmed Id with a reference type set to 'primary-reference' is allowed
            if (allPubmeds.size() > 1){

                // search for reference type: primary-reference
                Collection<psidev.psi.mi.jami.model.Xref> primaryReferences = XrefUtils.collectAllXrefsHavingQualifier(allPubmeds, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY);
                primaryReferences.addAll(XrefUtils.collectAllXrefsHavingQualifier(allPubmeds, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY));

                // At least one reference-type set to 'primary-reference' is required
                if ( primaryReferences.size() > 1 ) {
                    // check if we have a pubmed or doi identifier available. Doesn't test if it is valid as BibRefRule is checking that.
                    messages.add( new ValidatorMessage( "The experiment has " + primaryReferences.size() + " pubmed references as 'primary-reference' or 'identity' but only one is allowed.",
                            MessageLevel.WARN,
                            context,
                            this ) );

                }
                else if (primaryReferences.isEmpty()) {
                    messages.add( new ValidatorMessage( "The experiment has " + allPubmeds.size() + " pubmed references but none of theme has a reference type set to 'primary-reference' or 'identity'. When several pubmed identifiers are provided, only one of them should have qualifier set to 'primary-reference' or 'identity'.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }

            }
            else if ( allPubmeds.isEmpty() && allDois.isEmpty()) {
                messages.add( new ValidatorMessage( "The experiment has " + dbReferences.size() + " bibliographical references but none of them is a PubMed or Digital Object reference. At least one Pubmed of DOI bibliographical primary reference is required.",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            }

            if (allDois.size() > 1){

                // search for reference type: primary-reference
                Collection<psidev.psi.mi.jami.model.Xref> primaryReferences = XrefUtils.collectAllXrefsHavingQualifier(allDois, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY);
                primaryReferences.addAll(XrefUtils.collectAllXrefsHavingQualifier(allDois, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY));

                // At least one reference-type set to 'primary-reference' is required
                if ( primaryReferences.size() > 1 ) {
                    // check if we have a pubmed or doi identifier available. Doesn't test if it is valid as BibRefRule is checking that.
                    messages.add( new ValidatorMessage( "The experiment has " + primaryReferences.size() + " DOI references as 'primary-reference' or 'identity' but only one is allowed.",
                            MessageLevel.WARN,
                            context,
                            this ) );

                }
                else if (primaryReferences.isEmpty()) {
                    messages.add( new ValidatorMessage( "The experiment has " + allPubmeds.size() + " DOI references but none of theme has a reference type set to 'primary-reference' or 'identity'. When several DOI identifiers are provided, only one of them should have qualifier set to 'primary-reference' or 'identity'.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }

            }
        }
        else {
            messages.add( new ValidatorMessage( "The experiment has " + dbReferences.size() + " bibliographical references but none of them is a PubMed or Digital Object reference. At least one Pubmed or DOI bibliographical primary reference is required.",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R63";
    }
}
