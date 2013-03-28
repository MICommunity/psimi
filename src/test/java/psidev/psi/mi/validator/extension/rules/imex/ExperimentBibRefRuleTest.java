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
 * FullCoverageRule Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: ExperimentBibRefRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class ExperimentBibRefRuleTest extends AbstractRuleTest {

    public ExperimentBibRefRuleTest() throws OntologyLoaderException {
        super( ExperimentBibRefRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
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

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

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

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

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

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
