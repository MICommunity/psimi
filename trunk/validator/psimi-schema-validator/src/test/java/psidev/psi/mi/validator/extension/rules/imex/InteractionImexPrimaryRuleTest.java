package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * InteractionImexPrimaryRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20-Jan-2011</pre>
 */

public class InteractionImexPrimaryRuleTest extends AbstractRuleTest {

    public InteractionImexPrimaryRuleTest() throws OntologyLoaderException {
        super( InteractionImexPrimaryRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void validate_ImexId() throws Exception {

        Xref primary = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "123", "primary-reference", "MI:0358");
        Xref secondary1 =  XrefUtils.createXrefWithQualifier( "intact", "MI:0469", "IM-111-11", "imex-primary", "MI:0662" );
        Xref secondary2 =  XrefUtils.createXrefWithQualifier( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        interaction.getXrefs().add(primary);
        interaction.getXrefs().add(secondary1);
        interaction.getXrefs().add(secondary2);

        InteractionImexPrimaryRule rule = new InteractionImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Wrong_ImexId() throws Exception {

        Xref primary = XrefUtils.createXrefWithQualifier( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        Xref secondary1 =  XrefUtils.createXrefWithQualifier( "intact", "MI:0469", "IM-1A1", "imex-primary", "MI:0662" );
        Xref secondary2 =  XrefUtils.createXrefWithQualifier( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        interaction.getXrefs().add(primary);
        interaction.getXrefs().add(secondary1);
        interaction.getXrefs().add(secondary2);

        InteractionImexPrimaryRule rule = new InteractionImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );

        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_No_ImexId() throws Exception {

        Xref primary = XrefUtils.createXrefWithQualifier( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        Xref secondary1 =  XrefUtils.createXrefWithQualifier( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        interaction.getXrefs().add(primary);
        interaction.getXrefs().add(secondary1);

        InteractionImexPrimaryRule rule = new InteractionImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

}
