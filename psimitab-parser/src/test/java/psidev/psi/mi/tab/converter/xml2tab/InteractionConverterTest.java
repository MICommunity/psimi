package psidev.psi.mi.tab.converter.xml2tab;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Assert;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * InteractionConverter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>10/11/2006</pre>
 */
public class InteractionConverterTest {

    public Interactor buildInteractorA() {

        Collection<CrossReference> identifiers = new ArrayList<CrossReference>();
        identifiers.add( CrossReferenceFactory.getInstance().build( "uniprotkb", "P23367" ) );
        Interactor i = new Interactor( identifiers );

        Collection<CrossReference> altIds = new ArrayList<CrossReference>();
        altIds.add( CrossReferenceFactory.getInstance().build( "interpro", "IPR003594" ) );
        altIds.add( CrossReferenceFactory.getInstance().build( "interpro", "IPR002099" ) );
        altIds.add( CrossReferenceFactory.getInstance().build( "go", "GO:0005515" ) );
        altIds.add( CrossReferenceFactory.getInstance().build( "intact", "EBI-554913" ) );
        i.setAlternativeIdentifiers( altIds );

        Collection<Alias> aliases = new ArrayList<Alias>();
        aliases.add( new AliasImpl( "gene name", "mutL" ) );
        aliases.add( new AliasImpl( "locus name", "b4170" ) );
        i.setAliases( aliases );

        i.setOrganism( OrganismFactory.getInstance().build( 562 ) );

        return i;
    }

    public Interactor buildInteractorB() {

        Collection<CrossReference> identifiers = new ArrayList<CrossReference>();
        identifiers.add( CrossReferenceFactory.getInstance().build( "uniprotkb", "P09184" ) );
        Interactor i = new Interactor( identifiers );

        Collection<CrossReference> altIds = new ArrayList<CrossReference>();
        altIds.add( CrossReferenceFactory.getInstance().build( "interpro", "IPR004603" ) );
        altIds.add( CrossReferenceFactory.getInstance().build( "intact", "EBI-765033" ) );
        i.setAlternativeIdentifiers( altIds );

        Collection<Alias> aliases = new ArrayList<Alias>();
        aliases.add( new AliasImpl( "gene name", "vsr" ) );
        aliases.add( new AliasImpl( "locus name", "b1960" ) );
        i.setAliases( aliases );

        i.setOrganism( OrganismFactory.getInstance().build( 562 ) );

        return i;
    }

    ////////////////
    // Tests

    @Test
    public void toMitab() throws Exception {
        File file = TestHelper.getFileByResources( "/psi25-samples/single-interaction.xml", InteractionConverterTest.class );

        // read PSI 2.5
        PsimiXmlReader xmlReader = new PsimiXmlReader();
        EntrySet entrySet = null;
        entrySet = xmlReader.read( file );
        assertNotNull( entrySet );
        Entry entry = entrySet.getEntries().iterator().next();
        assertEquals( 1, entry.getInteractions().size() );
        Interaction interaction = entry.getInteractions().iterator().next();
        InteractionConverter ic = new MitabInteractionConverter();
        ic.addSourceDatabase( new CrossReferenceImpl( "MI", "0469", "intact" ) );

        BinaryInteraction<?> bi = ic.toMitab( interaction );
        assertNotNull( bi );

        // check on the generated interaction
        Interactor ia = buildInteractorA();
        Interactor ib = buildInteractorB();

        // either aa/bb or ab/ba
        assertTrue( ( ( bi.getInteractorA().equals( ia ) && bi.getInteractorB().equals( ib ) )
                      ||
                      ( bi.getInteractorA().equals( ib ) && bi.getInteractorB().equals( ia ) ) ) );

        // publications
        assertEquals( 1, bi.getPublications().size() );
        assertTrue( bi.getPublications().contains( CrossReferenceFactory.getInstance().build( "pubmed", "11585365" ) ) );

        // interaction detection method
        assertEquals( 1, bi.getDetectionMethods().size() );
        assertEquals( InteractionDetectionMethodFactory.getInstance().build( "MI", "0018", "two hybrid" ), bi.getDetectionMethods().iterator().next() );

        // interaction type
        assertEquals( 1, bi.getInteractionTypes().size() );
        assertEquals( InteractionTypeFactory.getInstance().build( "MI", "0218", "physical interaction" ), bi.getInteractionTypes().iterator().next() );

        // author
        //assertTrue( bi.getAuthors().isEmpty() );

        // confidence values
        assertTrue( bi.getConfidenceValues().isEmpty() );

        Assert.assertFalse( bi.getSourceDatabases().isEmpty() );

        Assert.assertFalse( bi.getInteractionAcs().isEmpty() );
        Assert.assertEquals( 1, bi.getInteractionAcs().size() );
        Assert.assertEquals( "EBI-765039", bi.getInteractionAcs().iterator().next().getIdentifier() );
    }
}
