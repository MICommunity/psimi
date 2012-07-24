/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package psidev.psi.mi.tab;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import static org.junit.Assert.*;

/**
 * @author Nadin Neuhauser
 * @version $Id$
 * @since 1.5.0-SNAPSHOT
 */
public abstract class TestHelper {

	static final String TAB_11585365 = "uniprotkb:P23367	uniprotkb:P23367	gene name:mutL	gene name:mutL	locus name:b4170	locus name:b4170	MI:0014(adenylate cyclase)	-	pubmed:11585365	taxid:562(ecoli)	taxid:562(ecoli)	MI:0218(physical interaction)	European Bioinformatics Institute:MI:0469	-	-	-\n"
			+ "uniprotkb:P23909	uniprotkb:P23909	gene name:mutS	gene name:mutS	gene name synonym:fdv|locus name:b2733	gene name synonym:fdv|locus name:b2733	MI:0014(adenylate cyclase)	-	pubmed:11585365	taxid:562(ecoli)	taxid:562(ecoli)	MI:0218(physical interaction)	European Bioinformatics Institute:MI:0469	-	-	-\n"
			+ "uniprotkb:P06722	uniprotkb:P23367	gene name:mutH	gene name:mutL	gene name synonym:mutR|gene name synonym:prv|locus name:b2831	locus name:b4170	MI:0018(two hybrid)|MI:0014(adenylate cyclase)	-	pubmed:11585365	taxid:562(ecoli)	taxid:562(ecoli)	MI:0218(physical interaction)	European Bioinformatics Institute:MI:0469	-	-	-\n"
			+ "uniprotkb:P23367	uniprotkb:P09184	gene name:mutL	gene name:vsr	locus name:b4170	locus name:b1960	MI:0018(two hybrid)|MI:0014(adenylate cyclase)	-	pubmed:11585365	taxid:562(ecoli)	taxid:562(ecoli)	MI:0218(physical interaction)	European Bioinformatics Institute:MI:0469	-	-	-\n";
		
	static final String HEADER_TAB_11585365 = "ID interactor A	ID interactor B	Alt. ID interactor A	Alt. ID interactor B	Alias(es) interactor A	Alias(es) interactor B	Interaction detection method(s)	Publication 1st author(s)	Publication Identifier(s)	Taxid interactor A	Taxid interactor B	Interaction type(s)	Source database(s)	Interaction identifier(s)	Confidence value(s)\n"
			+ "uniprotkb:P23367	uniprotkb:P06722	interpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913	interpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:GO:0005515|intact:EBI-545170	gene name:mutL|locus name:b4170	gene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831	adenylate cyclase:MI:0014	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:MI:0218	-	-	-\n"
			+ "uniprotkb:P23367	uniprotkb:P06722	-	-	-	-	-	-	-	taxid:562	taxid:562	-	-	-	-	-\n"
			+ "uniprotkb:P23909	uniprotkb:P23909	interpro:IPR005748|interpro:IPR000432|interpro:IPR007860|interpro:IPR007696|interpro:IPR007861|interpro:IPR007695|uniprotkb:P71279|go:GO:0005515|intact:EBI-554920	interpro:IPR005748|interpro:IPR000432|interpro:IPR007860|interpro:IPR007696|interpro:IPR007861|interpro:IPR007695|uniprotkb:P71279|go:GO:0005515|intact:EBI-554920	gene name:mutS|gene name synonym:fdv|locus name:b2733	gene name:mutS|gene name synonym:fdv|locus name:b2733	adenylate cyclase:MI:0014	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:MI:0218	-	-	-\n"
			+ "uniprotkb:P23367	uniprotkb:P23367	interpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913	interpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913	gene name:mutL|locus name:b4170	gene name:mutL|locus name:b4170	adenylate cyclase:MI:0014	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:MI:0218	-	-	-\n"
			+ "uniprotkb:P23367	uniprotkb:P09184	interpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913	interpro:IPR004603|intact:EBI-765033	gene name:mutL|locus name:b4170	gene name:vsr|locus name:b1960	two hybrid:MI:0018|adenylate cyclase:MI:0014	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:MI:0218	-	-	-\n"
			+ "uniprotkb:P06722	uniprotkb:P23367	interpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:GO:0005515|intact:EBI-545170	interpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913	gene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831	gene name:mutL|locus name:b4170	two hybrid:MI:0018	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:MI:0218	-	-	-\n";		
	
	static final String TXT_11585365 = "ID interactor A	ID interactor B	Alt. ID interactor A	Alt. ID interactor B	Alias(es) interactor A	Alias(es) interactor B	Interaction detection method(s)	Publication 1st author(s)	Publication Identifier(s)	Taxid interactor A	Taxid interactor B	Interaction type(s)	Source database(s)	Interaction identifier(s)	ConfidenceImpl value(s)\n"
			+ "uniprotkb:P23367|intact:EBI-554913	uniprotkb:P09184|intact:EBI-765033	intact:b4170|intact:JW4128	intact:b1960|intact:JW1943	uniprotkb:mutL	uniprotkb:vsr	MI:0014(adenylate cyclase)|MI:0018(2 hybrid)	Mansour et al. (2001)|Mansour et al. (2001)	pubmed:11585365|pubmed:11585365	taxid:83333(ecoli-2)	taxid:83333(ecoli-2)	MI:0218(physical interaction)|MI:0218(physical interaction)	MI:0469(intact)|MI:0469(intact)	intact:EBI-765062|intact:EBI-765039	-\n"
			+ "uniprotkb:P23367|intact:EBI-554913	uniprotkb:P23367|intact:EBI-554913	intact:b4170|intact:JW4128	intact:b4170|intact:JW4128	uniprotkb:mutL	uniprotkb:mutL	MI:0014(adenylate cyclase)	Mansour et al. (2001)	pubmed:11585365	taxid:83333(ecoli-2)	taxid:83333(ecoli-2)	MI:0218(physical interaction)	MI:0469(intact)	intact:EBI-765068	-\n"
			+ "uniprotkb:P23909|intact:EBI-554920	uniprotkb:P23909|intact:EBI-554920	intact:fdv|intact:b2733|intact:JW2703	intact:fdv|intact:b2733|intact:JW2703	uniprotkb:mutS	uniprotkb:mutS	MI:0014(adenylate cyclase)	Mansour et al. (2001)	pubmed:11585365	taxid:83333(ecoli-2)	taxid:83333(ecoli-2)	MI:0218(physical interaction)	MI:0469(intact)	intact:EBI-765077	-\n"
			+ "uniprotkb:P23367|intact:EBI-554913	uniprotkb:P06722|intact:EBI-545170	intact:b4170|intact:JW4128	intact:mutR|intact:prv|intact:b2831|intact:JW2799	uniprotkb:mutL	uniprotkb:mutH	MI:0014(adenylate cyclase)|MI:0014(adenylate cyclase)|MI:0014(adenylate cyclase)|MI:0018(2 hybrid)|MI:0004(affinity chrom)|MI:0018(2 hybrid)|MI:0018(2 hybrid)	Mansour et al. (2001)|Mansour et al. (2001)|Mansour et al. (2001)|Mansour et al. (2001)|Hall et al. (1999)|Hall et al. (1999)|Hall et al. (1999)	pubmed:11585365|pubmed:11585365|pubmed:11585365|pubmed:11585365|pubmed:9880500|pubmed:9880500|pubmed:9880500	taxid:83333(ecoli-2)	taxid:83333(ecoli-2)	MI:0218(physical interaction)|MI:0218(physical interaction)|MI:0218(physical interaction)|MI:0218(physical interaction)|MI:0407(direct interaction)|MI:0218(physical interaction)|MI:0218(physical interaction)	MI:0469(intact)|MI:0469(intact)|MI:0469(intact)|MI:0469(intact)|MI:0469(intact)|MI:0469(intact)|MI:0469(intact)	intact:EBI-765171|intact:EBI-765153|intact:EBI-765065|intact:EBI-765042|intact:EBI-761711|intact:EBI-762624|intact:EBI-761694	-\n";
	
    public static File getFileByResources( String fileName, Class clazz ) throws UnsupportedEncodingException {
        String strFile = clazz.getResource( fileName ).getFile();
        return new File( URLDecoder.decode( strFile, "utf-8" ) );
    }

    public static URL getURLByResources( String fileName, Class clazz ) throws UnsupportedEncodingException, MalformedURLException {
        String strURL = clazz.getResource( fileName ).toString();
        return new URL( URLDecoder.decode( strURL, "utf-8" ) );
    }

    public static  File getTargetDirectory() throws Exception {
        String outputDirPath = TestHelper.getFileByResources( "/", TestHelper.class ).getAbsolutePath();
        assertNotNull( outputDirPath );
        File outputDir = new File( outputDirPath );
        // we are in test-classes, move one up
        outputDir = outputDir.getParentFile();
        assertNotNull( outputDir );
        assertTrue( outputDir.isDirectory() );
        assertEquals( "target", outputDir.getName() );
        return outputDir;
    }

}
