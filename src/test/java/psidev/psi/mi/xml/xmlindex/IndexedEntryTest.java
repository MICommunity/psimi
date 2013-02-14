package psidev.psi.mi.xml.xmlindex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * IndexedEntry Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class IndexedEntryTest {

    @Test
    public void unmarshalledEntry() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");
        final Entry e = entry.unmarshalledEntry();
        Assert.assertNotNull( e );
    }

    @Test
    public void unmarshallSource() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");
        final Source source = entry.unmarshallSource();
        Assert.assertNotNull( source );
        Assert.assertEquals( "European Bioinformatics Institute", source.getNames().getShortLabel() );
    }

    @Test
    public void unmarshallAvailabilityList_empty() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");
        final List<Availability> availabilityList = entry.unmarshallAvailabilityList();
        Assert.assertTrue( availabilityList.isEmpty() );
    }

    @Test
    public void unmarshallAvailabilityList() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.v3.253.xml");

        final List<Availability> availabilityList = entry.unmarshallAvailabilityList();
        Assert.assertEquals( 1, availabilityList.size() );
        final Availability availability = availabilityList.iterator().next();
        Assert.assertEquals( 99999, availability.getId() );
        Assert.assertEquals( "blablabla", availability.getValue() );
    }

    //////////////////////
    // Experiment

    @Test
    public void unmarshallExperimentList() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final List<ExperimentDescription> experiments = entry.unmarshallExperimentList();
        Assert.assertNotNull( experiments );
        Assert.assertEquals( 1, experiments.size() );
        final ExperimentDescription e = experiments.iterator().next();
        Assert.assertEquals( 2, e.getId() );
    }

    @Test
    public void unmarshallExperimentById() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        final ExperimentDescription i1 = e1.unmarshallExperimentById( 2 );
        Assert.assertNotNull( i1 );
        Assert.assertEquals( 2, i1.getId() );

        IndexedEntry e2 = entries.get( 1 );
        final ExperimentDescription i2 = e2.unmarshallExperimentById( 2 );
        Assert.assertNotNull( i2 );
        Assert.assertEquals( 2, i2.getId() );

        final Interaction i2x = e1.unmarshallInteractionById( 3 );
        Assert.assertNull( i2x );
    }

    @Test
    public void unmarshallExperimentIterator() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final Iterator<ExperimentDescription> iterator = entry.unmarshallExperimentIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 2, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void unmarshallExperimentIterator_multipleEntries() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        Iterator<ExperimentDescription> iterator;

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        iterator = e1.unmarshallExperimentIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 2, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );

        // check the second entry
        IndexedEntry e2 = entries.get( 1 );
        iterator = e2.unmarshallExperimentIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 2, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void getLineNumber_Experiment() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final Iterator<ExperimentDescription> iterator = entry.unmarshallExperimentIterator();
        Assert.assertNotNull( iterator );
        int experimentId = iterator.next().getId();
        Assert.assertEquals( 2, experimentId );
        Assert.assertEquals( 23, entry.getExperimentLineNumber( experimentId ) );
    }

    ///////////////////
    // Interactor

    @Test
    public void unmarshallInteractorList() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final List<Interactor> interactors = entry.unmarshallInteractorList();
        Assert.assertNotNull( interactors );
        Assert.assertEquals( 3, interactors.size() );

        Assert.assertEquals( 4, interactors.get( 0 ).getId() );
        Assert.assertEquals( 7, interactors.get( 1 ).getId() );
        Assert.assertEquals( 11, interactors.get( 2 ).getId() );
    }

    @Test
    public void unmarshallInteractorById() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        Interactor i1 = e1.unmarshallInteractorById( 4 );
        Assert.assertNotNull( i1 );
        Assert.assertEquals( 4, i1.getId() );

        Interactor i2 = e1.unmarshallInteractorById( 7 );
        Assert.assertNotNull( i2 );
        Assert.assertEquals( 7, i2.getId() );

        Interactor i3 = e1.unmarshallInteractorById( 11 );
        Assert.assertNotNull( i3 );
        Assert.assertEquals( 11, i3.getId() );

        e1 = entries.get( 1 );
        i1 = e1.unmarshallInteractorById( 4 );
        Assert.assertNotNull( i1 );
        Assert.assertEquals( 4, i1.getId() );

        i2 = e1.unmarshallInteractorById( 7 );
        Assert.assertNotNull( i2 );
        Assert.assertEquals( 7, i2.getId() );

        i3 = e1.unmarshallInteractorById( 11 );
        Assert.assertNotNull( i3 );
        Assert.assertEquals( 11, i3.getId() );
    }

    @Test
    public void unmarshallInteractorIterator() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final Iterator<Interactor> iterator = entry.unmarshallInteractorIterator();

        Assert.assertNotNull( iterator );

        Assert.assertEquals( 4, iterator.next().getId() );
        Assert.assertEquals( 7, iterator.next().getId() );
        Assert.assertEquals( 11, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void unmarshallInteractorIterator_multipleEntries() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        Iterator<Interactor> iterator;

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        iterator = e1.unmarshallInteractorIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 4, iterator.next().getId() );
        Assert.assertEquals( 7, iterator.next().getId() );
        Assert.assertEquals( 11, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );

        // check the second entry
        IndexedEntry e2 = entries.get( 1 );
        iterator = e2.unmarshallInteractorIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 4, iterator.next().getId() );
        Assert.assertEquals( 7, iterator.next().getId() );
        Assert.assertEquals( 11, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void getInteractorLineNumber() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        Interactor i1 = e1.unmarshallInteractorById( 4 );
        Assert.assertNotNull( i1 );
        Assert.assertEquals( 86, e1.getInteractorLineNumber( 4  ));

        Interactor i2 = e1.unmarshallInteractorById( 7 );
        Assert.assertNotNull( i2 );
        Assert.assertEquals( 142, e1.getInteractorLineNumber( 7  ));

        Interactor i3 = e1.unmarshallInteractorById( 11 );
        Assert.assertNotNull( i3 );
        Assert.assertEquals( 194, e1.getInteractorLineNumber( 11  ));

        e1 = entries.get( 1 );
        i1 = e1.unmarshallInteractorById( 4 );
        Assert.assertNotNull( i1 );
        Assert.assertEquals( 779, e1.getInteractorLineNumber( 4  ));

        i2 = e1.unmarshallInteractorById( 7 );
        Assert.assertNotNull( i2 );
        Assert.assertEquals( 835, e1.getInteractorLineNumber( 7  ));

        i3 = e1.unmarshallInteractorById( 11 );
        Assert.assertNotNull( i3 );
        Assert.assertEquals( 887, e1.getInteractorLineNumber( 11  ));
    }

    ///////////////////////
    // Interaction

    @Test
    public void unmarshallInteractionList() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final List<Interaction> interactions = entry.unmarshallInteractionList();
        Assert.assertNotNull( interactions );
        Assert.assertEquals( 2, interactions.size() );

        Assert.assertEquals( 1, interactions.get( 0 ).getId() );
        Assert.assertEquals( 9, interactions.get( 1 ).getId() );
    }

    @Test
    public void unmarshallInteractionById() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        final Interaction i1 = e1.unmarshallInteractionById( 9 );
        Assert.assertNotNull( i1 );
        Assert.assertEquals( 9, i1.getId() );

        final Interaction i2 = e1.unmarshallInteractionById( 1 );
        Assert.assertNotNull( i2 );
        Assert.assertEquals( 1, i2.getId() );

        IndexedEntry e2 = entries.get( 1 );
        final Interaction i21 = e2.unmarshallInteractionById( 1 );
        Assert.assertNotNull( i21 );
        Assert.assertEquals( 1, i21.getId() );

        final Interaction i22 = e2.unmarshallInteractionById( 987 );
        Assert.assertNotNull( i22 );
        Assert.assertEquals( 987, i22.getId() );

        final Interaction i2x = e1.unmarshallInteractionById( 33 );
        Assert.assertNull( i2x );
    }

    @Test
    public void unmarshallInteractionIterator() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.253.xml");

        final Iterator<Interaction> iterator = entry.unmarshallInteractionIterator();

        Assert.assertNotNull( iterator );

        Assert.assertEquals( 1, iterator.next().getId() );
        Assert.assertEquals( 9, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void unmarshallInteractionIterator_multipleEntries() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        Iterator<Interaction> iterator;

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        iterator = e1.unmarshallInteractionIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 1, iterator.next().getId() );
        Assert.assertEquals( 9, iterator.next().getId() );

        // check the second entry
        IndexedEntry e2 = entries.get( 1 );
        iterator = e2.unmarshallInteractionIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 1, iterator.next().getId() );
        Assert.assertEquals( 987, iterator.next().getId() );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void getInteractionLineNumber_multipleEntries() throws Exception {
        final List<IndexedEntry> entries = getIndexedEntries("/sample-xml/intact/10320477.v4.253.xml");
        Assert.assertNotNull( entries );
        Assert.assertEquals( 2, entries.size() );

        Iterator<Interaction> iterator;

        // check the first entry
        IndexedEntry e1 = entries.get( 0 );
        iterator = e1.unmarshallInteractionIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 1, iterator.next().getId() );
        Assert.assertEquals(258, e1.getInteractionLineNumber( 1 ) );
        Assert.assertEquals( 9, iterator.next().getId() );
        Assert.assertEquals(476, e1.getInteractionLineNumber( 9 ) );

        // check the second entry
        IndexedEntry e2 = entries.get( 1 );
        iterator = e2.unmarshallInteractionIterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 1, iterator.next().getId() );
        Assert.assertEquals(951, e2.getInteractionLineNumber( 1 ) );
        Assert.assertEquals( 987, iterator.next().getId() );
        Assert.assertEquals(1169, e2.getInteractionLineNumber( 987 ) );
        Assert.assertFalse( iterator.hasNext() );
    }

    @Test
    public void unmarshallAttributeList() throws Exception {
        final IndexedEntry entry = getIndexedEntry("/sample-xml/intact/10320477.v3.253.xml");

        final List<Attribute> attributes = entry.unmarshallAttributeList();
        Assert.assertNotNull( attributes );
        Assert.assertEquals( 2, attributes.size() );

        Assert.assertEquals( "comment", attributes.get( 0 ).getName() );
        Assert.assertEquals( "a nice comment", attributes.get( 0 ).getValue() );

        Assert.assertEquals( "comment", attributes.get( 1 ).getName() );
        Assert.assertEquals( "an other nice comment", attributes.get( 1 ).getValue() );
    }

    ///////////////////////
    // Utilities

    private IndexedEntry getIndexedEntry( String filename ) throws PsimiXmlReaderException {
        File file = new File( PsimiXmlIndexerTest.class.getResource( filename ).getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
        return indexedEntries.iterator().next();
    }

    private List<IndexedEntry> getIndexedEntries( String filename ) throws PsimiXmlReaderException {
        File file = new File( PsimiXmlIndexerTest.class.getResource( filename ).getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        return reader.getIndexedEntries();
    }
}
