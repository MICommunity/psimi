package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of InteractorTypeRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class InteractorTypeRuleTest extends AbstractRuleTest {


    public InteractorTypeRuleTest() throws OntologyLoaderException {
        super(InteractorTypeRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void test_protein() throws ValidatorException {
        Protein protein = buildProtein("P12345");

        InteractorTypeRule rule =  new InteractorTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(protein);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_small_molecule() throws ValidatorException {
        BioactiveEntity small_molecule = buildSmallMolecule("xxx");

        InteractorTypeRule rule =  new InteractorTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(small_molecule);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_nucleic_acid() throws ValidatorException {
        NucleicAcid nucleic_acid = buildNucleicAcid("xxx");

        InteractorTypeRule rule =  new InteractorTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(nucleic_acid);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_nucleic_acid_children() throws ValidatorException {
        NucleicAcid nucleic_acid = buildRibonucleicAcid("xxx");

        InteractorTypeRule rule =  new InteractorTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(nucleic_acid);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
