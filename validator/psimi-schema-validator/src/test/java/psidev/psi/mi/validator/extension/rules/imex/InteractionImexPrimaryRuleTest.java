package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
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

        DbReference primary = new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "intact", "MI:0469", "IM-111-11", "imex-primary", "MI:0662" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        final Xref xref = new Xref(primary, secondary);
        Interaction interaction = new Interaction();
        interaction.setXref(xref);

        InteractionImexPrimaryRule rule = new InteractionImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Wrong_ImexId() throws Exception {

        DbReference primary = new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "intact", "MI:0469", "IM-1A1", "imex-primary", "MI:0662" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        final Xref xref = new Xref(primary, secondary);
        Interaction interaction = new Interaction();
        interaction.setXref(xref);

        InteractionImexPrimaryRule rule = new InteractionImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );

        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_No_ImexId() throws Exception {

        DbReference primary = new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        final Xref xref = new Xref(primary, secondary);
        Interaction interaction = new Interaction();
        interaction.setXref(xref);

        InteractionImexPrimaryRule rule = new InteractionImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

}
