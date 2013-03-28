package psidev.psi.mi.validator.extension.rules;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.validator.extension.rules.psimi.DatabaseAccessionRule;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
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

        Interactor interactor = buildProtein("Q01314");

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (Xref p : interactor.getIdentifiers()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_BadUniprotAc() throws ValidatorException {

        Interactor interactor = buildProtein("AKT1_BOVIN");

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (Xref p : interactor.getIdentifiers()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_AccessionNull() throws ValidatorException {

        Interactor interactor = buildProtein(null);

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (Xref p : interactor.getIdentifiers()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_SeveralDatabaseMatchingName() throws ValidatorException {

        Interactor interactor = buildProtein("Q01314");

        DbReference ref = interactor.getXref().getAllDbReferences().iterator().next();
        ref.setDbAc(null);
        ref.setDb("uniprot");

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (Xref p : interactor.getIdentifiers()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_ProteinUniprot_OneDatabaseMatchingName() throws ValidatorException {

        Interactor interactor = buildProtein("Q01314");

        DbReference ref = interactor.getXref().getAllDbReferences().iterator().next();
        ref.setDbAc(null);
        ref.setDb("uniprot knowledge base");

        DatabaseAccessionRule rule = new DatabaseAccessionRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (Xref p : interactor.getIdentifiers()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }
}
