package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ExperimentAttributesRule Tester.
 *
 * @author Marine Dumousseau
 */
public class ExperimentXRefImexRuleTest extends AbstractRuleTest {

    public ExperimentXRefImexRuleTest() {
        super();
    }

    @Test
    public void validate_fail_NoPubmedOrDOI() throws Exception {

        final Bibref bibref = new Bibref();
        DbReference primary = new DbReference( "intact", "MI:0469", "EBI-2432100", "primary-reference", "MI:0358" );
        DbReference secondary1 = new DbReference("pubmed", "MI:0446", "IM-1", "imex-primary", "MI:0662");
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        final Xref xref = new Xref(primary, secondary);        
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentXRefImexRule rule = new ExperimentXRefImexRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_PubmedOrDOI_PrimaryReferenceType() throws Exception {

        final Bibref bibref = new Bibref();
        DbReference primary = new DbReference( "intact", "MI:0469", "EBI-2432100", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        DbReference secondary3 = new DbReference("pubmed", "MI:0446", "IM-1", "imex-primary", "MI:0662");
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        secondary.add(secondary3);
        final Xref xref = new Xref(primary, secondary);
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentXRefImexRule rule = new ExperimentXRefImexRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Fail_PubmedOrDOI_PrimaryReferenceType() throws Exception {

        final Bibref bibref = new Bibref();
        DbReference primary = new DbReference( "intact", "MI:0469", "EBI-2432100", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "pubmed", "MI:0446", "123", "identity", "MI:0356" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        DbReference secondary3 = new DbReference("pubmed", "MI:0446", "IM-1", "imex-primary", "MI:0662");
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        secondary.add(secondary3);        
        final Xref xref = new Xref(primary, secondary);
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentXRefImexRule rule = new ExperimentXRefImexRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_ImexId() throws Exception {

        final Bibref bibref = new Bibref();
        DbReference primary = new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "intact", "MI:0469", "IM-111", "imex-primary", "MI:0662" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        final Xref xref = new Xref(primary, secondary);
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentXRefImexRule rule = new ExperimentXRefImexRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Fail_ImexId() throws Exception {

        final Bibref bibref = new Bibref();
        DbReference primary = new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "intact", "MI:0469", "IM-1A1", "imex-primary", "MI:0662" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        final Xref xref = new Xref(primary, secondary);
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentXRefImexRule rule = new ExperimentXRefImexRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

}
