package psidev.psi.mi.tab.converter.xml2tab;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * InteractionConverter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>10/11/2006</pre>
 */
public class InteractionConverterTest {

    public Interactor buildInteractorA() {

        List<CrossReference> identifiers = new ArrayList<CrossReference>();
        identifiers.add( new CrossReferenceImpl( "uniprotkb", "P23367" ) );
        Interactor i = new Interactor( identifiers );

        List<CrossReference> altIds = new ArrayList<CrossReference>();
        altIds.add( new CrossReferenceImpl( "interpro", "IPR003594" ) );
        altIds.add( new CrossReferenceImpl( "interpro", "IPR002099" ) );
        altIds.add( new CrossReferenceImpl( "go", "GO:0005515" ) );
        altIds.add( new CrossReferenceImpl( "intact", "EBI-554913" ) );
        i.setAlternativeIdentifiers( altIds );

        List<Alias> aliases = new ArrayList<Alias>();
        aliases.add( new AliasImpl( "gene name", "mutL" ) );
        aliases.add( new AliasImpl( "locus name", "b4170" ) );
        i.setAliases( aliases );

        i.setOrganism( new OrganismImpl( 562 ) );

        return i;
    }

    public Interactor buildInteractorB() {

        List<CrossReference> identifiers = new ArrayList<CrossReference>();
        identifiers.add( new CrossReferenceImpl( "uniprotkb", "P09184" ) );
        Interactor i = new Interactor( identifiers );

        List<CrossReference> altIds = new ArrayList<CrossReference>();
        altIds.add( new CrossReferenceImpl( "interpro", "IPR004603" ) );
        altIds.add( new CrossReferenceImpl( "intact", "EBI-765033" ) );
        i.setAlternativeIdentifiers( altIds );

        List<Alias> aliases = new ArrayList<Alias>();
        aliases.add( new AliasImpl( "gene name", "vsr" ) );
        aliases.add( new AliasImpl( "locus name", "b1960" ) );
        i.setAliases( aliases );

        i.setOrganism( new OrganismImpl( 562 ) );

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
        assertTrue( bi.getPublications().contains( new CrossReferenceImpl( "pubmed", "11585365" ) ) );

        // interaction detection method
        assertEquals( 1, bi.getDetectionMethods().size() );
        assertEquals( new CrossReferenceImpl( "psi-mi", "MI:0018", "two hybrid" ), bi.getDetectionMethods().iterator().next() );

        // interaction type
        assertEquals( 1, bi.getInteractionTypes().size() );
        assertEquals( new CrossReferenceImpl( "psi-mi", "MI:0218", "physical interaction" ), bi.getInteractionTypes().iterator().next() );

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
