package psidev.psi.mi.validator.extension.rules;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ExperimentFullCoverageRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 2.0
 * @version $Id$
 */
public class ExperimentBibRefRuleTest extends AbstractRuleTest {

    public ExperimentBibRefRuleTest() {
        super();
    }

    @Test
    public void validate_pmid() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_doi() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "doi", "MI:0574", "doi:12345677", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_PubmedOrDOI_PrimaryReferenceType() throws Exception {

        final Bibref bibref = new Bibref();
        DbReference primary = new DbReference( "intact", "MI:0469", "EBI-2432100", "primary-reference", "MI:0358" );
        DbReference secondary1 =  new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        DbReference secondary2 =  new DbReference( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
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
        Collection<DbReference> secondary = new ArrayList<DbReference>();
        secondary.add(secondary1);
        secondary.add(secondary2);
        final Xref xref = new Xref(primary, secondary);
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 3, messages.size() );
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

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_fail() throws Exception {

        final Bibref bibref = new Bibref();
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        // should conplain about author-list, contact-email and publication title missing
        Assert.assertEquals( 3, messages.size() );
    }

    @Test
    public void validate_without_identifier() throws Exception {

        final Bibref bibref = new Bibref();
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );
        exp.setNames( new Names() );
        exp.getNames().setFullName( "publication title" );
        exp.getAttributes().add( new Attribute( null, "contact-email", "author@institute.co.uk" ) );
        exp.getAttributes().add( new Attribute( null, "author-list", "author1, author2, author3..." ) );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_without_identifier2() throws Exception {

        final Bibref bibref = new Bibref();
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );
        exp.setNames( new Names() );
        exp.getNames().setFullName( "publication title" );
        exp.getAttributes().add( new Attribute( "MI:0634", "", "author@institute.co.uk" ) );
        exp.getAttributes().add( new Attribute( "MI:0636", "", "author1, author2, author3..." ) );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }
}
