/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
 * limitations under the License.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import psidev.psi.mi.tab.model.Alias;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * InteractorConverterTest
 *
 * @author Prem Anand (prem@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.1-SNAPSHOT
 */
public class InteractorConverterTest {

	private static final Log log = LogFactory.getLog( InteractorConverterTest.class );

	private static final List<String> uniprotKeys = new ArrayList<String>( Arrays.asList( new String[]
			{"gene name", "gene name synonym", "isoform synonym", "locus name", "ordered locus name", "open reading frame name"} ) );


	@Test
	public void dbSourceAndShortLabelofAliasTest() throws Exception {

		File file = new File( InteractorConverterTest.class.getResource( "/psi25-samples/11585365.xml" ).getFile() );
		PsimiXmlReader reader = new PsimiXmlReader();

		boolean shortLabelAliasExists = false;

		EntrySet entrySet = reader.read( file );
		Xml2Tab x2t = new Xml2Tab();
		try {
			Collection<BinaryInteraction> binaryInteractions = x2t.convert( entrySet );
			assertFalse( binaryInteractions.isEmpty() );


			for ( BinaryInteraction binaryInteraction : binaryInteractions ) {

				Collection<CrossReference> identifierCrossReference = binaryInteraction.getInteractorA().getIdentifiers();

				String identifier = null;
				for ( CrossReference identCR : identifierCrossReference ) {
					if ( log.isDebugEnabled() ) {
						log.debug( "Identifier " + identCR.getIdentifier() );
					}
					identifier = identCR.getIdentifier();
				}

				if ( identifier != null && identifier.equals( "P06722" ) ) {

					Collection<Alias> aliases = binaryInteraction.getInteractorA().getAliases();

					for ( Alias alias : aliases ) {
						if ( uniprotKeys.contains( alias.getAliasType() ) ) {
							Assert.assertEquals( InteractorConverter.UNIPROT, alias.getDbSource() );
						} else {
							Assert.assertEquals( "unknown", alias.getDbSource() );

						}
						if ( alias.getAliasType().equals( InteractorConverter.SHORT_LABEL ) ) {
							Assert.assertEquals( "muth_ecoli", alias.getName() );
							shortLabelAliasExists = true;
						}

					}//end for
					break;
				} //end of if

			}//outermost for loop

			Assert.assertEquals( true, shortLabelAliasExists );
		} catch ( TabConversionException e ) {
			Assert.fail();
		}

	}


}
