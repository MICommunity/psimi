package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * FullCoverageRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 2.0
 * @version $Id$
 */
public class PublicationRuleTest extends AbstractRuleTest {

    public PublicationRuleTest() {
        super();
    }

    @Test
    public void validate_pmid() throws Exception {

        final Publication bibref = new DefaultPublication();
        final Xref xref = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "123", "primary-reference", "MI:0358");
        bibref.getIdentifiers().add(xref);

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_doi() throws Exception {

        final Publication bibref = new DefaultPublication();
        final Xref xref = XrefUtils.createXrefWithQualifier( "doi", "MI:0574", "doi:12345677", "primary-reference", "MI:0358" );
        bibref.getIdentifiers().add(xref);

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_PubmedOrDOI_PrimaryReferenceType() throws Exception {

        final Publication bibref = new DefaultPublication();
        Xref primary = XrefUtils.createXrefWithQualifier( "intact", "MI:0469", "EBI-2432100", "primary-reference", "MI:0358" );
        Xref secondary1 = XrefUtils.createXrefWithQualifier( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        Xref secondary2 = XrefUtils.createXrefWithQualifier( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        bibref.getIdentifiers().add( primary );
        bibref.getIdentifiers().add( secondary1 );
        bibref.getXrefs().add(secondary2);

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Fail_PubmedOrDOI_PrimaryReferenceType() throws Exception {

        final Publication bibref = new DefaultPublication();
        Xref primary = XrefUtils.createXrefWithQualifier( "intact", "MI:0469", "EBI-2432100", "primary-reference", "MI:0358" );
        Xref secondary1 = XrefUtils.createXrefWithQualifier( "pubmed", "MI:0446", "123", "identity", "MI:0356" );
        Xref secondary2 = XrefUtils.createXrefWithQualifier( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        bibref.getXrefs().add( primary );
        bibref.getXrefs().add(secondary1);
        bibref.getXrefs().add(secondary2);

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 3, messages.size() );
    }

    @Test
    public void validate_ImexId() throws Exception {

        final Publication bibref = new DefaultPublication();
        Xref primary = XrefUtils.createXrefWithQualifier( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" );
        Xref secondary1 = XrefUtils.createXrefWithQualifier( "imex", "MI:0670", "IM-111", "imex-primary", "MI:0662" );
        Xref secondary2 = XrefUtils.createXrefWithQualifier( "DOI", "MI:0574", "1234","identity", "MI:0356" );
        bibref.getIdentifiers().add( primary );
        bibref.getXrefs().add(secondary1);
        bibref.getXrefs().add(secondary2);

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_fail() throws Exception {

        final Publication bibref = new DefaultPublication();

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        // should conplain about author-list, contact-email and publication title missing
        Assert.assertEquals( 3, messages.size() );
    }

    @Test
    public void validate_without_identifier() throws Exception {

        final Publication bibref = new DefaultPublication();
        bibref.setTitle("publication title");
        bibref.getAnnotations().add( new DefaultAnnotation(  new DefaultCvTerm("contact-email"), "author@institute.co.uk" ) );
        bibref.getAuthors().add( "author1" );
        bibref.getAuthors().add( "author2" );
        bibref.getAuthors().add( "author3" );

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_without_identifier2() throws Exception {

        final Publication bibref = new DefaultPublication();
        bibref.setTitle("publication title");
        bibref.getAnnotations().add( new DefaultAnnotation( new DefaultCvTerm("test", "MI:0634"), "author@institute.co.uk" ) );
        bibref.getAuthors().add("author1");
        bibref.getAuthors().add( "author2" );
        bibref.getAuthors().add( "author3" );

        PublicationRule rule = new PublicationRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( bibref );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }
}
