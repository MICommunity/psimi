package psidev.psi.mi.validator.extension.rules.psimi;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * DatabaseAccessionRule tester
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31-Aug-2010</pre>
 */

public class DatabaseAccessionRuleTest extends AbstractRuleTest {

    public DatabaseAccessionRuleTest() throws OntologyLoaderException {
        super( DatabaseAccessionRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void valid_ProteinUniprot() throws ValidatorException {

        Protein interactor = buildProtein("Q01314");

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( interactor.getIdentifiers().iterator().next() );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_BadUniprotAc() throws ValidatorException {

        Protein interactor = buildProtein("AKT1_BOVIN");

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( interactor.getIdentifiers().iterator().next() );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_AccessionNull() throws ValidatorException {

        Protein interactor = buildProtein(null);

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( interactor.getIdentifiers().iterator().next() );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_SeveralDatabaseMatchingName() throws ValidatorException {

        Protein interactor = new DefaultProtein("Q01314");
        interactor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("uniprot"), "Q01314"));

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( interactor.getIdentifiers().iterator().next() );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_OneDatabaseMatchingName() throws ValidatorException {

        Protein interactor = new DefaultProtein("Q01314");
        interactor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("uniprot knowledge base"), "Q01314"));

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( interactor.getIdentifiers().iterator().next() );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }
}
