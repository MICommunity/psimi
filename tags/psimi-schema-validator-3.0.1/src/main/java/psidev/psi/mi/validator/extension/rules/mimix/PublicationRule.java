package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <b> check that each experiment has a valid bibref to pubmed or DOI. Check if the IMEx ID is valid when a cross reference type 'imex-primary' appears. </b>
 * <p/>
 *
 * @author Samuel Kerrien, Luisa Montecchi
 * @version $Id$
 * @since 1.0
 */
public class PublicationRule extends AbstractMIRule<Publication>{

    Pattern EMAIL_VALIDATOR = Pattern.compile( "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" );

    public PublicationRule(OntologyManager ontologyMaganer) {
        super( ontologyMaganer,Publication.class );

        // describe the rule.
        setName("Experiment bibliographic reference check");
        setDescription("Checks that each experiment has a publication reference (pubmed or doi) or valid publication " +
                "details (contact email, author list and publication title).");
        addTip("You can search for pubmed identifier at http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=PubMed");
        addTip( "Your pubmed or DOI bibliographical reference should have a type: primary-reference or identity" );
        addTip( "The PSI-MI identifier for PubMed is: MI:0446" );
        addTip( "The PSI-MI identifier for DOI is: MI:0574" );
        addTip( "The PSI-MI identifier for primary-reference is: MI:0358" );
        addTip( "The PSI-MI identifier for identity is: MI:0356" );
        addTip( "The PSI-MI identifier for contact-email is: MI:0634" );
        addTip( "The PSI-MI identifier for author-list is: MI:0636" );
    }

    /**
     * Make sure that an experiment either has a pubmed id in its bibRef or that is has a publication title,
     * author name and contact email. Check also that at least one pubMed Id or DOI has a reference type set to 'primary-reference'.
     * Check if the id of references with a 'imex-primary' cross reference type is a valid IMEx ID (IM-xxx).
     *
     * @param pub a publication to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Publication pub ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = Collections.EMPTY_LIST;

        boolean hasPublicationIdentifier = false;

        final Collection<psidev.psi.mi.jami.model.Xref> dbReferences = pub.getIdentifiers();

        // check if we have a pubmed or doi identifier available

        final Collection<Xref> pubmeds = XrefUtils.collectAllXrefsHavingDatabase(dbReferences, Xref.PUBMED_MI, Xref.PUBMED);
        final Collection<Xref> dois = XrefUtils.collectAllXrefsHavingDatabase(dbReferences, Xref.DOI_MI, Xref.DOI);

        // the following line is commented because a new Rule has been implemented and is doing the same stuff
        //PublicationRuleUtils.checkPubmedId(pubmeds,messages,context,this);

        if ( !pubmeds.isEmpty() || !dois.isEmpty() ) {
            hasPublicationIdentifier = true;

            // Only one pubmed Id with a reference type set to 'primary-reference' or 'identity' is allowed
            if (!pubmeds.isEmpty()){

                // search for reference type: primary-reference/identity
                boolean hasPrimary = false;
                boolean hasSeveralPrimary = false;
                for (Xref pubmed : pubmeds){
                    if (XrefUtils.doesXrefHaveQualifier(pubmed, Xref.PRIMARY_MI, Xref.PRIMARY) ||
                            XrefUtils.doesXrefHaveQualifier(pubmed, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        if (hasPrimary){
                            hasSeveralPrimary = true;
                            break;
                        }
                        else{
                            hasPrimary = true;
                        }
                    }
                }

                if ( !hasPrimary ) {
                    MiContext context = RuleUtils.buildContext(pub, "publication");
                    messages = new ArrayList<ValidatorMessage>();
                    messages.add( new ValidatorMessage( "The publication has "+pubmeds.size()+" pubmed identifiers. Only one pubmed identifier should have a reference-type set to 'primary-reference' or 'identity' to identify the publication.",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                else if (hasSeveralPrimary){
                    MiContext context = RuleUtils.buildContext(pub, "publication");
                    messages = new ArrayList<ValidatorMessage>();
                    messages.add( new ValidatorMessage( "Only one pubmed identifier should have a reference-type set to 'primary-reference' or 'identity'.",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
            }
            if (!dois.isEmpty()){

                // search for reference type: primary-reference/identity
                // search for reference type: primary-reference/identity
                boolean hasPrimary = false;
                boolean hasSeveralPrimary = false;
                for (Xref doi : dois){
                    if (XrefUtils.doesXrefHaveQualifier(doi, Xref.PRIMARY_MI, Xref.PRIMARY) ||
                            XrefUtils.doesXrefHaveQualifier(doi, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        if (hasPrimary){
                            hasSeveralPrimary = true;
                            break;
                        }
                        else{
                            hasPrimary = true;
                        }
                    }
                }

                if ( !hasPrimary ) {
                    MiContext context = RuleUtils.buildContext(pub, "publication");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The publication has "+dois.size()+" DOI identifiers. Only one DOI identifier should have a reference-type set to 'primary-reference' or 'identity' to identify the publication.",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                else if (hasSeveralPrimary){
                    MiContext context = RuleUtils.buildContext(pub, "publication");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "Only one DOI identifier should have a reference-type set to 'primary-reference' or 'identity'.",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
            }
        }

        if ( !hasPublicationIdentifier ) {
            // check that we have author email, publication title and author list : look first into bibRef and then into the experiment
            Collection<Annotation> emails = AnnotationUtils.collectAllAnnotationsHavingTopic(pub.getAnnotations(), Annotation.CONTACT_EMAIL_MI, Annotation.CONTACT_EMAIL);
            int countValidEmail = 0;

            if ( emails.isEmpty() ) {
                MiContext context = RuleUtils.buildContext(pub, "publication");
                if (messages.isEmpty()){
                    messages = new ArrayList<ValidatorMessage>();
                }
                messages.add( new ValidatorMessage( "In the absence of a publication identifier, a contact email is " +
                        "required in the bibRef's or ExperimentDescription's attributes.",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            } else {
                int emptyEmailCount = 0;
                for ( Annotation email : emails ) {
                    final String address = email.getValue();
                    if ( address == null || address.trim().length() == 0 ) {
                        emptyEmailCount++;
                    } else {
                        if ( !EMAIL_VALIDATOR.matcher( address ).matches() ) {
                            MiContext context = RuleUtils.buildContext(pub, "publication");
                            if (messages.isEmpty()){
                                messages = new ArrayList<ValidatorMessage>();
                            }
                            messages.add( new ValidatorMessage( "An contact email seems to be invalid: " + address,
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        } else {
                            countValidEmail++;
                        }
                    }
                }

                if ( emptyEmailCount > 0 ) {
                    MiContext context = RuleUtils.buildContext(pub, "publication");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "While looking for experimentDescription's contact email, we found "
                            + emptyEmailCount + " empty value(s).",
                            MessageLevel.INFO,
                            context,
                            this ) );
                }

                if ( countValidEmail == 0 ) {
                    MiContext context = RuleUtils.buildContext(pub, "publication");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    // in the absence of a publication identifier, a contact email is expected.
                    messages.add( new ValidatorMessage( "In the absence of a publication identifier, a valid contact " +
                            "email is required in the publication's attributes.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }

            List<String> authorList = pub.getAuthors();

            if ( authorList.isEmpty() ) {
                MiContext context = RuleUtils.buildContext(pub, "publication");
                if (messages.isEmpty()){
                    messages = new ArrayList<ValidatorMessage>();
                }
                // in the absence of a publication identifier, a author list is required.
                messages.add( new ValidatorMessage( "In the absence of a publication identifier, an author list is " +
                        "required in the publication's attributes.",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            } else {
                for ( String author : authorList ) {
                    int nonEmptyCount = 0;
                    if ( author != null && author.trim().length() > 0 ) {
                        nonEmptyCount++;
                    }
                    if ( nonEmptyCount == 0 ) {
                        MiContext context = RuleUtils.buildContext(pub, "publication");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        // in the absence of a publication identifier, an author list is expected.
                        messages.add( new ValidatorMessage( "In the absence of a publication identifier, an non empty " +
                                "author list is required in the publication's " +
                                "attributes.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }

            String publicationTitle = pub.getTitle();

            if ( publicationTitle == null || publicationTitle.trim().length() == 0 ) {
                MiContext context = RuleUtils.buildContext(pub, "publication");
                if (messages.isEmpty()){
                    messages = new ArrayList<ValidatorMessage>();
                }
                // in the absence of a publication identifier, an publication title is expected (i.e. experimentDescritpion.names.fullname)
                messages.add( new ValidatorMessage( "In the absence of a publication identifier, a " +
                        "publication title is required.",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            }
        } // if hasPublicationIdentifier

        return messages;
    }

    public String getId() {
        return "R28";
    }
}