package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ExperimentImexPrimaryRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class ExperimentImexPrimaryRuleTest extends AbstractRuleTest {

    public ExperimentImexPrimaryRuleTest() throws OntologyLoaderException {
        super( ExperimentImexPrimaryRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void validate_ImexId() throws Exception {

        DbReference primary = new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "intact", "MI:0469", "IM-111", "imex-primary", "MI:0662" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        final Xref xref = new Xref(primary, secondary);
        ExperimentDescription exp = new ExperimentDescription();
        exp.setXref(xref);

        ExperimentImexPrimaryRule rule = new ExperimentImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
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
        ExperimentDescription exp = new ExperimentDescription();
        exp.setXref(xref);

        ExperimentImexPrimaryRule rule = new ExperimentImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
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
        ExperimentDescription exp = new ExperimentDescription();
        exp.setXref(xref);

        ExperimentImexPrimaryRule rule = new ExperimentImexPrimaryRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
