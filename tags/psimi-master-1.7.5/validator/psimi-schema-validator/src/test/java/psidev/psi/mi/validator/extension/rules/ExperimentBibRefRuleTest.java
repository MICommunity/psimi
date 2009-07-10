package psidev.psi.mi.validator.extension.rules;

import static org.junit.Assert.*;
import org.junit.*;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.InputStream;
import java.util.Collection;

/**
 * ExperimentBibRefRule Tester.
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
        exp.getAttributes().add( new Attribute( "", "contact-email", "author@institute.co.uk" ) );
        exp.getAttributes().add( new Attribute( "", "author-list", "author1, author2, author3..." ) );

        ExperimentBibRefRule rule = new ExperimentBibRefRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
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
