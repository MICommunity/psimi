package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * ExperimentAttributesRule Tester.
 *
 * @author Marine Dumousseau
 * @since 2.0
 */
public class ExperimentAttributesRuleTest extends AbstractRuleTest {

    public ExperimentAttributesRuleTest() {
        super();
    }

    @Test
    public void validate_AttributeName() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("MI:0959", "imex curation", ""));
        exp.getAttributes().add(new Attribute("MI:0957", "full coverage", ""));        

        ExperimentAttributesRule rule = new ExperimentAttributesRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Fail_AttributeName() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("journal", "Science (0036-8075)"));

        ExperimentAttributesRule rule = new ExperimentAttributesRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 2, messages.size() );
    }

    @Test
    public void validate_Fail_AttributeName_FullCoverage() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("MI:0959", "imex curation", ""));

        ExperimentAttributesRule rule = new ExperimentAttributesRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_Fail_AttributeName_ImexCuration() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("MI:0957", "full coverage", ""));

        ExperimentAttributesRule rule = new ExperimentAttributesRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }



}
