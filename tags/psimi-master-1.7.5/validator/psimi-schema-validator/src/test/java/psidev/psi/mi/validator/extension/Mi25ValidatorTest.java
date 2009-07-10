package psidev.psi.mi.validator.extension;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.xpath.XPathHelper;
import psidev.psi.tools.validator.xpath.XPathResult;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;
import psidev.psi.mi.validator.ValidatorReport;

import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

/**
 * Mi25Validator Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class Mi25ValidatorTest {

    private Mi25Validator aValidator;

    private Mi25Validator buildValidator( boolean saxValidationEnabled ) throws Exception{
        if ( aValidator == null ) {

            InputStream ontologyConfig = Mi25ValidatorTest.class.getResource( "/config/ontologies.xml" ).openStream();
            Assert.assertNotNull(ontologyConfig);

            InputStream cvMappingConfig = Mi25ValidatorTest.class.getResource( "/config/cv-mapping.xml" ).openStream();
            Assert.assertNotNull(cvMappingConfig);

            InputStream objectRuleConfig = null;
//            Mi25ValidatorTest.class.getResource( "/config/object-rules.xml" ).openStream();
//            Assert.assertNotNull(objectRuleConfig);

            aValidator = new Mi25Validator( ontologyConfig, cvMappingConfig, objectRuleConfig );

            // set user preferences
            UserPreferences preferences = new UserPreferences();
            preferences.setKeepDownloadedOntologiesOnDisk( true );
            preferences.setSaxValidationEnabled( saxValidationEnabled );
            aValidator.setUserPreferences( preferences );

            final Collection<ValidatorMessage> messages = aValidator.checkCvMappingRules();
            Assert.assertNotNull( messages );
            for ( ValidatorMessage message : messages ) {
                System.out.println( message );
            }
            Assert.assertEquals( 0, messages.size() );
        }

        return aValidator;
    }

    private Mi25Validator buildValidator() throws Exception {
        return buildValidator( false );
    }

    private InputStream buildInputStream( String name ) throws FileNotFoundException {
        final String location = "/sample-xml/" + name;
        URL url = Mi25ValidatorTest.class.getResource( location );
        Assert.assertNotNull( "Could not find test file: " + location, url );
        File f = new File( url.getFile() );
        Assert.assertNotNull( f );
        Assert.assertTrue( "Cannot read file: " + f.getAbsolutePath(), f.canRead() );
        return new FileInputStream( f );
    }

    private Collection<ValidatorMessage> getValidationMessage( String filename ) throws Exception {
        final ValidatorReport report = buildValidator().validate(buildInputStream( filename ));
        return report.getSemanticMessages();
    }

    @Test
    public void validate_wrongExperimentalRole() throws Exception {
        // Defines MI:xxxx as a experimental role
        Collection<ValidatorMessage> messages = getValidationMessage( "10409737-wrongExperimentalRole.xml" );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_wrongExperimentalRole_2() throws Exception {
        // THIs one defines the term MI:0495 that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage("10409737-wrongExperimentalRole_2.xml");
        printMessages(messages);
        Assert.assertEquals(1, messages.size());
    }

    @Test
    public void validate_wrongBiologicalRole() throws Exception {
        // This one defines the term MI:xxxx that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage( "10409737-wrongBiologicalRole.xml" );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_wrongInteractorType() throws Exception {
        // This one defines the term MI:xxxx that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage("10409737-wrongInteractorType.xml" );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_wrongExperimentBibrefDb() throws Exception {
        // This one defines the term MI:xxxx that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage( "10409737-wrongExperimentBibrefDb.xml" );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_experimentWithPubmed() throws Exception {
        // This one defines a valid PubMed term
        Collection<ValidatorMessage> messages = getValidationMessage( "10409737-experimentWithPubmed.xml"  );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_experimentWithDOI() throws Exception {
        // This one defines the term MI:xxxx that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737-experimentWithDOI.xml" );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_wrongFeatureDetectionOnFeature() throws Exception {
        // This one defines a valid DOI term
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737-wrongFeatureDetectionOnFeature.xml" );
        printMessages( messages );

        // TODO here it throws 2 errors as the featureDetectionMethod on experiment doesn't exist and a rule requires it
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_wrongFeatureDetectionOnExperiment() throws Exception {
        // This one defines a valid DOI term
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737-wrongFeatureDetectionOnExperiment.xml"  );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_wrongFeatureType() throws Exception {
        // This one defines the term MI:xxxx that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737-wrongFeatureType.xml"  );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_noFeatureType() throws Exception {
        // This one does not defines the term ... the rule should tolerate it
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737-noFeatureType.xml"  );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_wrongParticipantDetection() throws Exception {
        // This one defines the term MI:xxxx that is prohibited by the rule
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737-wrongParticipantDetection.xml" );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

//    @Test
//    @Ignore
//    public void validate_lukasz() throws Exception {
//        final Mi25Validator mi25Validator = buildValidator();
//        long start = System.currentTimeMillis();
//        System.out.println( "Starting validation" );
//        Collection<ValidatorMessage> messages = mi25Validator.validate( new FileInputStream( new File( "C:\\validator-data\\test20081104_A.mif25" ) ) );
//        System.out.println( "Completed" );
//        System.out.println( "Time Elapsed to validate: "+ (System.currentTimeMillis() - start) +"ms" );
//        printMessages( messages );
//        Assert.assertEquals( 0, messages.size() );
//    }
//
//    @Test
//    @Ignore
//    public void validate_matrixdb() throws Exception {
//        final Mi25Validator mi25Validator = buildValidator();
//        long start = System.currentTimeMillis();
//        System.out.println( "Starting validation" );
//        Collection<ValidatorMessage> messages = mi25Validator.validate( new FileInputStream( new File( "C:\\validator-data\\MatrixBiology_part1.xml" ) ) );
//        System.out.println( "Completed" );
//        System.out.println( "Time Elapsed to validate: "+ (System.currentTimeMillis() - start) +"ms" );
//        printMessages( messages );
//        Assert.assertEquals( 0, messages.size() );
//    }

    ///////////////////////
    // To be checked on

    @Test
    public void validate() throws Exception {
        Collection<ValidatorMessage> messages = getValidationMessage( "17129785.xml"  );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    // TODO the cv mapping for go terms is commented
    @Test
    @Ignore
    public void validate_missingGO() throws Exception {
        Collection<ValidatorMessage> messages = getValidationMessage ("17129785-missingGO.xml"  );
        printMessages( messages );
        // 3 of the proteins in this file do not have GO terms as secondary reference.
        Assert.assertEquals( 3, messages.size() );
    }

    @Test
    public void validate_missingInteractorType() throws Exception {
        Collection<ValidatorMessage> messages = getValidationMessage ("17129785-missingInteractorType.xml" );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_validFile() throws Exception {
        Collection<ValidatorMessage> messages = getValidationMessage ("10409737.xml" );
        printMessages( messages );
        Assert.assertTrue( messages.isEmpty() );
    }

    @Test
    public void useXpathExpressionOnPsimiModel() throws Exception {
        PsimiXmlReader reader = new PsimiXmlReader();
        EntrySet es = reader.read( buildInputStream( "10409737-error1.xml" ) );
        List<XPathResult> pathResults =
                XPathHelper.evaluateXPath( "entries/interactions/participants/interactor", es );
        System.out.println( pathResults.size() );
    }

    @Test
    public void useXpathExpressionOnPsimiLightweightModel() throws Exception {
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( buildInputStream( "10409737.xml" ) );
        List<IndexedEntry> entries = reader.getIndexedEntries();
        for ( IndexedEntry entry : entries ) {
            Iterator<Interaction> ii = entry.unmarshallInteractionIterator();
            while ( ii.hasNext() ) {
                Interaction interaction = ii.next();
                List<XPathResult> pathResults = XPathHelper.evaluateXPath( "participants/interactor/xref/primaryRef/@id", interaction );
                for ( XPathResult pathResult : pathResults ) {
                    System.out.println( pathResult.getResult() );
                }
            }
        }
    }

    @Test
    public void validateSyntax253() throws Exception {
        final ValidatorReport report = buildValidator( true ).validate(buildInputStream( "17129785_syntaxError_253.xml" ));
        Assert.assertNotNull( report.getSyntaxMessages() );
        Assert.assertTrue( report.getSyntaxMessages().size() > 0 );
        printMessages( report.getSyntaxMessages() );
    }

    @Test
    public void validateSyntax254() throws Exception {
        final ValidatorReport report = buildValidator( true ).validate(buildInputStream( "17129785_syntaxError_254.xml" ));
        Assert.assertNotNull( report.getSyntaxMessages() );
        Assert.assertTrue( report.getSyntaxMessages().size() > 0 );
        printMessages( report.getSyntaxMessages() );
    }

    private void printMessages( Collection<ValidatorMessage> messages ) {
        for ( ValidatorMessage message : messages ) {
            System.out.println( message );
        }
    }
}