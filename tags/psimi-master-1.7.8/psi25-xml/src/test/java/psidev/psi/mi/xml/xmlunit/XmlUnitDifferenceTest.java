/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.xmlunit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlWriter;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.Reader;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05-Jul-2006</pre>
 */
public class XmlUnitDifferenceTest extends XMLTestCase {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( XmlUnitDifferenceTest.class );

    public XmlUnitDifferenceTest( String name ) {
        super( name );
    }

    protected void setUp() throws Exception {
        super.setUp();

        XMLUnit.setIgnoreWhitespace( true );
    }

    public void testXmlSimilar_parameter() throws Exception {

//        // TODO implement in IgnorePsiMiDifference a way to ignore attribute having default value when they are missing on the other side.
//
//        String test = "<parameterList>\n" +
//                      "   <parameter term=\"kd\" unit=\"picomolar\" factor=\"0.11\" />\n" +
//                      "</parameterList>";
//
//        String control = "<parameterList>\n" +
//                         "   <parameter unit=\"picomolar\" term=\"kd\" factor=\"0.11\" exponent=\"0\" base=\"10\">\n" +
//                         "   </parameter>\n" +
//                         "</parameterList>";
//
//        Diff myDiff = new Diff( control, test );
//        myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );
//
//        assertTrue( myDiff.toString(), myDiff.similar() );
    }

    public void testXmlSimilar_oneWay() throws Exception {

        String test = "<root><name>sam</name><isLink>false</isLink></root>";
        String control = "<root><name>sam</name></root>";

        Diff myDiff = new Diff( control, test );
        myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );

        assertTrue( myDiff.toString(), myDiff.similar() );
    }

    public void testXmlSimilar_reverse() throws Exception {

        String control = "<root><name>sam</name><isLink>false</isLink></root>";
        String test = "<root><name>sam</name></root>";

        Diff myDiff = new Diff( control, test );
        myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );

        assertTrue( myDiff.toString(), myDiff.similar() );
    }

    public void testXmlSimilar_twoDifference() throws Exception {

        String control = "<root>" +
                         "  <name>sam</name>" +
                         "  <isLink>false</isLink>" +
                         "  <negative>false</negative>" +
                         "</root>";
        String test = "<root>" +
                      "  <name>sam</name>" +
                      "  <intraMolecular>false</intraMolecular>" +
                      "</root>";

        Diff myDiff = new Diff( control, test );
        myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );

        assertTrue( myDiff.toString(), myDiff.similar() );
    }

    public void testXmlSimilar_interaction() throws Exception {

        String control = "            <interaction id=\"1\">\n" +
                         "                <names>\n" +
                         "                    <shortLabel>mdm2-p53</shortLabel>\n" +
                         "                </names>\n" +
                         "                <xref>\n" +
                         "                    <primaryRef secondary=\"mdm2-p53\" id=\"MINT-14981\" dbAc=\"MI:0471\" db=\"mint\"/>\n" +
                         "                </xref>\n" +
                         "                <experimentList>\n" +
                         "                    <experimentRef>2</experimentRef>\n" +
                         "                </experimentList>\n" +
                         "                <participantList>\n" +
                         "                    <participant id=\"3\">\n" +
                         "                        <names>\n" +
                         "                            <shortLabel>MDM2</shortLabel>\n" +
                         "                        </names>\n" +
                         "                        <interactorRef>4</interactorRef>\n" +
                         "                        <experimentalRoleList>\n" +
                         "                            <experimentalRole>\n" +
                         "                                <names>\n" +
                         "                                    <shortLabel>unspecified</shortLabel>\n" +
                         "                                    <fullName>unspecified</fullName>\n" +
                         "                                </names>\n" +
                         "                                <xref>\n" +
                         "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0499\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                </xref>\n" +
                         "                            </experimentalRole>\n" +
                         "                        </experimentalRoleList>\n" +
                         "                        <experimentalPreparationList>\n" +
                         "                            <experimentalPreparation>\n" +
                         "                                <names>\n" +
                         "                                    <shortLabel>purified</shortLabel>\n" +
                         "                                    <fullName>purified</fullName>\n" +
                         "                                </names>\n" +
                         "                                <xref>\n" +
                         "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0350\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                </xref>\n" +
                         "                            </experimentalPreparation>\n" +
                         "                        </experimentalPreparationList>\n" +
                         "                        <featureList>\n" +
                         "                            <feature id=\"5\">\n" +
                         "                                <names>\n" +
                         "                                    <shortLabel>binding site</shortLabel>\n" +
                         "                                </names>\n" +
                         "                                <featureType>\n" +
                         "                                    <names>\n" +
                         "                                        <shortLabel>binding site</shortLabel>\n" +
                         "                                        <fullName>binding site</fullName>\n" +
                         "                                    </names>\n" +
                         "                                    <xref>\n" +
                         "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0117\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                    </xref>\n" +
                         "                                </featureType>\n" +
                         "                                <featureDetectionMethod>\n" +
                         "                                    <names>\n" +
                         "                                        <shortLabel>deletion analysis</shortLabel>\n" +
                         "                                        <fullName>deletion analysis</fullName>\n" +
                         "                                    </names>\n" +
                         "                                    <xref>\n" +
                         "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0033\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                    </xref>\n" +
                         "                                </featureDetectionMethod>\n" +
                         "                                <featureRangeList>\n" +
                         "                                    <featureRange>\n" +
                         "                                        <startStatus>\n" +
                         "                                            <names>\n" +
                         "                                                <shortLabel>certain</shortLabel>\n" +
                         "                                            </names>\n" +
                         "                                            <xref>\n" +
                         "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                            </xref>\n" +
                         "                                        </startStatus>\n" +
                         "                                        <begin position=\"1\"/>\n" +
                         "                                        <endStatus>\n" +
                         "                                            <names>\n" +
                         "                                                <shortLabel>certain</shortLabel>\n" +
                         "                                            </names>\n" +
                         "                                            <xref>\n" +
                         "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                            </xref>\n" +
                         "                                        </endStatus>\n" +
                         "                                        <end position=\"109\"/>\n" +
                         "                                        <isLink>false</isLink>\n" +
                         "                                    </featureRange>\n" +
                         "                                </featureRangeList>\n" +
                         "                            </feature>\n" +
                         "                        </featureList>\n" +
                         "                    </participant>\n" +
                         "                    <participant id=\"6\">\n" +
                         "                        <names>\n" +
                         "                            <shortLabel>P53</shortLabel>\n" +
                         "                        </names>\n" +
                         "                        <interactorRef>7</interactorRef>\n" +
                         "                        <experimentalRoleList>\n" +
                         "                            <experimentalRole>\n" +
                         "                                <names>\n" +
                         "                                    <shortLabel>unspecified</shortLabel>\n" +
                         "                                    <fullName>unspecified</fullName>\n" +
                         "                                </names>\n" +
                         "                                <xref>\n" +
                         "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0499\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                </xref>\n" +
                         "                            </experimentalRole>\n" +
                         "                        </experimentalRoleList>\n" +
                         "                        <experimentalPreparationList>\n" +
                         "                            <experimentalPreparation>\n" +
                         "                                <names>\n" +
                         "                                    <shortLabel>purified</shortLabel>\n" +
                         "                                    <fullName>purified</fullName>\n" +
                         "                                </names>\n" +
                         "                                <xref>\n" +
                         "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0350\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                </xref>\n" +
                         "                            </experimentalPreparation>\n" +
                         "                        </experimentalPreparationList>\n" +
                         "                        <featureList>\n" +
                         "                            <feature id=\"8\">\n" +
                         "                                <names>\n" +
                         "                                    <shortLabel>binding site</shortLabel>\n" +
                         "                                </names>\n" +
                         "                                <featureType>\n" +
                         "                                    <names>\n" +
                         "                                        <shortLabel>binding site</shortLabel>\n" +
                         "                                        <fullName>binding site</fullName>\n" +
                         "                                    </names>\n" +
                         "                                    <xref>\n" +
                         "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0117\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                    </xref>\n" +
                         "                                </featureType>\n" +
                         "                                <featureDetectionMethod>\n" +
                         "                                    <names>\n" +
                         "                                        <shortLabel>deletion analysis</shortLabel>\n" +
                         "                                        <fullName>deletion analysis</fullName>\n" +
                         "                                    </names>\n" +
                         "                                    <xref>\n" +
                         "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0033\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                                    </xref>\n" +
                         "                                </featureDetectionMethod>\n" +
                         "                                <featureRangeList>\n" +
                         "                                    <featureRange>\n" +
                         "                                        <startStatus>\n" +
                         "                                            <names>\n" +
                         "                                                <shortLabel>certain</shortLabel>\n" +
                         "                                            </names>\n" +
                         "                                            <xref>\n" +
                         "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                            </xref>\n" +
                         "                                        </startStatus>\n" +
                         "                                        <begin position=\"10\"/>\n" +
                         "                                        <endStatus>\n" +
                         "                                            <names>\n" +
                         "                                                <shortLabel>certain</shortLabel>\n" +
                         "                                            </names>\n" +
                         "                                            <xref>\n" +
                         "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                                            </xref>\n" +
                         "                                        </endStatus>\n" +
                         "                                        <end position=\"40\"/>\n" +
                         "                                        <isLink>false</isLink>\n" +
                         "                                    </featureRange>\n" +
                         "                                </featureRangeList>\n" +
                         "                            </feature>\n" +
                         "                        </featureList>\n" +
                         "                    </participant>\n" +
                         "                </participantList>\n" +
                         "                <interactionType>\n" +
                         "                    <names>\n" +
                         "                        <shortLabel>ubiquitination</shortLabel>\n" +
                         "                        <fullName>ubiquitination reaction</fullName>\n" +
                         "                    </names>\n" +
                         "                    <xref>\n" +
                         "                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0220\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                         "                        <secondaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"GO:0016567\" dbAc=\"MI:0448\" db=\"go\"/>\n" +
                         "                        <secondaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"AA0125\" dbAc=\"MI:0248\" db=\"resid\"/>\n" +
                         "                        <secondaryRef refTypeAc=\"MI:0357\" refType=\"method reference\" id=\"11583613\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                         "                    </xref>\n" +
                         "                </interactionType>\n" +
                         "                <modelled>false</modelled>\n" +
                         "                <intraMolecular>false</intraMolecular>\n" +
                         "                <negative>false</negative>\n" +
                         "            </interaction>\n";


        String test = "            <interaction id=\"1\">\n" +
                      "                <names>\n" +
                      "                    <shortLabel>mdm2-p53</shortLabel>\n" +
                      "                </names>\n" +
                      "                <xref>\n" +
                      "                    <primaryRef secondary=\"mdm2-p53\" id=\"MINT-14981\" dbAc=\"MI:0471\" db=\"mint\"/>\n" +
                      "                </xref>\n" +
                      "                <experimentList>\n" +
                      "                    <experimentRef>2</experimentRef>\n" +
                      "                </experimentList>\n" +
                      "                <participantList>\n" +
                      "                    <participant id=\"3\">\n" +
                      "                        <names>\n" +
                      "                            <shortLabel>MDM2</shortLabel>\n" +
                      "                        </names>\n" +
                      "                        <interactorRef>4</interactorRef>\n" +
                      "                        <experimentalRoleList>\n" +
                      "                            <experimentalRole>\n" +
                      "                                <names>\n" +
                      "                                    <shortLabel>unspecified</shortLabel>\n" +
                      "                                    <fullName>unspecified</fullName>\n" +
                      "                                </names>\n" +
                      "                                <xref>\n" +
                      "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0499\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                </xref>\n" +
                      "                            </experimentalRole>\n" +
                      "                        </experimentalRoleList>\n" +
                      "                        <experimentalPreparationList>\n" +
                      "                            <experimentalPreparation>\n" +
                      "                                <names>\n" +
                      "                                    <shortLabel>purified</shortLabel>\n" +
                      "                                    <fullName>purified</fullName>\n" +
                      "                                </names>\n" +
                      "                                <xref>\n" +
                      "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0350\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                </xref>\n" +
                      "                            </experimentalPreparation>\n" +
                      "                        </experimentalPreparationList>\n" +
                      "                        <featureList>\n" +
                      "                            <feature id=\"5\">\n" +
                      "                                <names>\n" +
                      "                                    <shortLabel>binding site</shortLabel>\n" +
                      "                                </names>\n" +
                      "                                <featureType>\n" +
                      "                                    <names>\n" +
                      "                                        <shortLabel>binding site</shortLabel>\n" +
                      "                                        <fullName>binding site</fullName>\n" +
                      "                                    </names>\n" +
                      "                                    <xref>\n" +
                      "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0117\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                    </xref>\n" +
                      "                                </featureType>\n" +
                      "                                <featureDetectionMethod>\n" +
                      "                                    <names>\n" +
                      "                                        <shortLabel>deletion analysis</shortLabel>\n" +
                      "                                        <fullName>deletion analysis</fullName>\n" +
                      "                                    </names>\n" +
                      "                                    <xref>\n" +
                      "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0033\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                    </xref>\n" +
                      "                                </featureDetectionMethod>\n" +
                      "                                <featureRangeList>\n" +
                      "                                    <featureRange>\n" +
                      "                                        <startStatus>\n" +
                      "                                            <names>\n" +
                      "                                                <shortLabel>certain</shortLabel>\n" +
                      "                                            </names>\n" +
                      "                                            <xref>\n" +
                      "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                            </xref>\n" +
                      "                                        </startStatus>\n" +
                      "                                        <begin position=\"1\"/>\n" +
                      "                                        <endStatus>\n" +
                      "                                            <names>\n" +
                      "                                                <shortLabel>certain</shortLabel>\n" +
                      "                                            </names>\n" +
                      "                                            <xref>\n" +
                      "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                            </xref>\n" +
                      "                                        </endStatus>\n" +
                      "                                        <end position=\"109\"/>\n" +
                      "                                        <isLink>false</isLink>\n" +
                      "                                    </featureRange>\n" +
                      "                                </featureRangeList>\n" +
                      "                            </feature>\n" +
                      "                        </featureList>\n" +
                      "                    </participant>\n" +
                      "                    <participant id=\"6\">\n" +
                      "                        <names>\n" +
                      "                            <shortLabel>P53</shortLabel>\n" +
                      "                        </names>\n" +
                      "                        <interactorRef>7</interactorRef>\n" +
                      "                        <experimentalRoleList>\n" +
                      "                            <experimentalRole>\n" +
                      "                                <names>\n" +
                      "                                    <shortLabel>unspecified</shortLabel>\n" +
                      "                                    <fullName>unspecified</fullName>\n" +
                      "                                </names>\n" +
                      "                                <xref>\n" +
                      "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0499\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                </xref>\n" +
                      "                            </experimentalRole>\n" +
                      "                        </experimentalRoleList>\n" +
                      "                        <experimentalPreparationList>\n" +
                      "                            <experimentalPreparation>\n" +
                      "                                <names>\n" +
                      "                                    <shortLabel>purified</shortLabel>\n" +
                      "                                    <fullName>purified</fullName>\n" +
                      "                                </names>\n" +
                      "                                <xref>\n" +
                      "                                    <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0350\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                    <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                </xref>\n" +
                      "                            </experimentalPreparation>\n" +
                      "                        </experimentalPreparationList>\n" +
                      "                        <featureList>\n" +
                      "                            <feature id=\"8\">\n" +
                      "                                <names>\n" +
                      "                                    <shortLabel>binding site</shortLabel>\n" +
                      "                                </names>\n" +
                      "                                <featureType>\n" +
                      "                                    <names>\n" +
                      "                                        <shortLabel>binding site</shortLabel>\n" +
                      "                                        <fullName>binding site</fullName>\n" +
                      "                                    </names>\n" +
                      "                                    <xref>\n" +
                      "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0117\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                    </xref>\n" +
                      "                                </featureType>\n" +
                      "                                <featureDetectionMethod>\n" +
                      "                                    <names>\n" +
                      "                                        <shortLabel>deletion analysis</shortLabel>\n" +
                      "                                        <fullName>deletion analysis</fullName>\n" +
                      "                                    </names>\n" +
                      "                                    <xref>\n" +
                      "                                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0033\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                        <secondaryRef refTypeAc=\"MI:0358\" refType=\"primary-reference\" id=\"14755292\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                                    </xref>\n" +
                      "                                </featureDetectionMethod>\n" +
                      "                                <featureRangeList>\n" +
                      "                                    <featureRange>\n" +
                      "                                        <startStatus>\n" +
                      "                                            <names>\n" +
                      "                                                <shortLabel>certain</shortLabel>\n" +
                      "                                            </names>\n" +
                      "                                            <xref>\n" +
                      "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                            </xref>\n" +
                      "                                        </startStatus>\n" +
                      "                                        <begin position=\"10\"/>\n" +
                      "                                        <endStatus>\n" +
                      "                                            <names>\n" +
                      "                                                <shortLabel>certain</shortLabel>\n" +
                      "                                            </names>\n" +
                      "                                            <xref>\n" +
                      "                                                <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0335\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                                            </xref>\n" +
                      "                                        </endStatus>\n" +
                      "                                        <end position=\"40\"/>\n" +
                      "                                    </featureRange>\n" +
                      "                                </featureRangeList>\n" +
                      "                            </feature>\n" +
                      "                        </featureList>\n" +
                      "                    </participant>\n" +
                      "                </participantList>\n" +
                      "                <interactionType>\n" +
                      "                    <names>\n" +
                      "                        <shortLabel>ubiquitination</shortLabel>\n" +
                      "                        <fullName>ubiquitination reaction</fullName>\n" +
                      "                    </names>\n" +
                      "                    <xref>\n" +
                      "                        <primaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"MI:0220\" dbAc=\"MI:0488\" db=\"psi-mi\"/>\n" +
                      "                        <secondaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"GO:0016567\" dbAc=\"MI:0448\" db=\"go\"/>\n" +
                      "                        <secondaryRef refTypeAc=\"MI:0356\" refType=\"identity\" id=\"AA0125\" dbAc=\"MI:0248\" db=\"resid\"/>\n" +
                      "                        <secondaryRef refTypeAc=\"MI:0357\" refType=\"method reference\" id=\"11583613\" dbAc=\"MI:0446\" db=\"pubmed\"/>\n" +
                      "                    </xref>\n" +
                      "                </interactionType>\n" +
                      "            </interaction>\n";

        Diff myDiff = new Diff( control, test );
        myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );

        assertTrue( myDiff.toString(), myDiff.similar() );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Non test - used to check on compliance with IntAct and MINT datasets

    public void singleFileCheck() throws Exception {

        String intactRelease = "C:\\cygwin\\home\\Samuel\\psi-data\\intact\\";
        String mintRelease = "C:\\\\cygwin\\\\home\\\\Samuel\\\\psi-data\\\\mint\\\\mint.bio.uniroma2.it\\\\pub\\\\release\\\\psi25\\";

        File xmlFile = new File( mintRelease + "10082674_psi25.xml" );
//        File xmlFile = new File( intactRelease + "12624108.xml" );

        // probleme in IntAct data with special characters !

        //11172710.copy   --> [different] Expected text value 'Linear 10%?30% glycerol gradient.' but was 'Linear 10%?30% glycerol gradient.'

        //11316798.copy   --> Expected text value 'Note the antibody used for immunoprecipitation of rat dysbindin (Q5M834) was raised against amino acids 196?352 of mouse dysbindin protein.'
        //                    but was             'Note the antibody used for immunoprecipitation of rat dysbindin (Q5M834) was raised against amino acids 196?352 of mouse dysbindin protein.' - comparing <attribute ...>Note the antibody used for immunoprecipitation of rat dysbindin (Q5M834) was raised against amino acids 196?352 of mouse dysbindin protein.</attribute> at /entrySet[1]/entry[1]/interactionList[1]/interaction[8]/attributeList[1]/attribute[2]/text()[1] to <attribute ...>Note the antibody used for immunoprecipitation of rat dysbindin (Q5M834) was raised against amino acids 196?352 of mouse dysbindin protein.</attribute> at /entrySet[1]/entry[1]/interactionList[1]/interaction[8]/attributeList[1]/attribute[2]/text()[1]

        //12147674.copy   --> Expected text value 'For double immuno EM, sciatic nerve samples were colabeled with rabbit antigigaxonin and mouse anti?MAP1B-LC followed by gold-conjugated secondary antibodies against mouse (small particles) and rabbit (large particles).'
        //                    but was             'For double immuno EM, sciatic nerve samples were colabeled with rabbit antigigaxonin and mouse anti?MAP1B-LC followed by gold-conjugated secondary antibodies against mouse (small particles) and rabbit (large particles).' - comparing <attribute ...>For double immuno EM, sciatic nerve samples were colabeled with rabbit antigigaxonin and mouse anti?MAP1B-LC followed by gold-conjugated secondary antibodies against mouse (small particles) and rabbit (large particles).</attribute> at /entrySet[1]/entry[1]/experimentList[1]/experimentDescription[4]/attributeList[1]/attribute[4]/text()[1] to <attribute ...>For double immuno EM, sciatic nerve samples were colabeled with rabbit antigigaxonin and mouse anti?MAP1B-LC followed by gold-conjugated secondary antibodies against mouse (small particles) and rabbit (large particles).</attribute> at /entrySet[1]/entry[1]/experimentList[1]/experimentDescription[4]/attributeList[1]/attribute[4]/text()[1]

        //12441347.copy   --> Expected text value 'KaiA, KaiB, and SasA bound to KaiC more abundantly during subjective night in continuous light conditions (LL). The level of SasA interacting with KaiC is more abundant between 12?16 hours in LL whereas the levels of KaiA and KaiB interacting with KaiC peaks at hours 20?24 in LL.'
        //                    but was             'KaiA, KaiB, and SasA bound to KaiC more abundantly during subjective night in continuous light conditions (LL). The level of SasA interacting with KaiC is more abundant between 12?16 hours in LL whereas the levels of KaiA and KaiB interacting with KaiC peaks at hours 20?24 in LL.' - comparing <attribute ...>KaiA, KaiB, and SasA bound to KaiC more abundantly during subjective night in continuous light conditions (LL). The level of SasA interacting with KaiC is more abundant between 12?16 hours in LL whereas the levels of KaiA and KaiB interacting with KaiC peaks at hours 20?24 in LL.</attribute> at /entrySet[1]/entry[1]/interactionList[1]/interaction[1]/attributeList[1]/attribute[1]/text()[1] to <attribute ...>KaiA, KaiB, and SasA bound to KaiC more abundantly during subjective night in continuous light conditions (LL). The level of SasA interacting with KaiC is more abundant between 12?16 hours in LL whereas the levels of KaiA and KaiB interacting with KaiC peaks at hours 20?24 in LL.</attribute> at /entrySet[1]/entry[1]/interactionList[1]/interaction[1]/attributeList[1]/attribute[1]/text()[1]


        System.out.println( "xmlFile = " + xmlFile );

        // unmarshall
        PsimiXmlReader reader = new PsimiXmlReader();
        EntrySet entrySet = reader.read( xmlFile );

        // marshall
        PsimiXmlWriter writer = new PsimiXmlWriter();
        File testFile = new File( xmlFile.getAbsolutePath().replaceAll( ".xml", ".copy" ) );
        writer.write( entrySet, testFile );

        log.info( "Comparing " + xmlFile.getName() + " and " + testFile.getName() + " ..." );

        // build 2 readers
        Reader controlReader = new FileReader( xmlFile );
        Reader testReader = new FileReader( testFile );

        // compare them
        Diff myDiff = new Diff( controlReader, testReader );
        myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );

        assertTrue( myDiff.toString(), myDiff.similar() );

        // test passed, delete the test file.
        testFile.delete();
    }

    public void compareDirectory() throws Exception {

        String xmlSamplePath = XmlUnitDifferenceTest.class.getResource( "/sample-xml/mint" ).getFile();
        String mintRelease = "C:\\cygwin\\home\\Samuel\\psi-data\\mint\\mint.bio.uniroma2.it\\pub\\release\\psi25";
        String intactRelease = "C:\\cygwin\\home\\Samuel\\psi-data\\intact";

        File dir = new File( intactRelease );
        if ( ! dir.exists() ) {
            fail( "Could not find directory: " + dir.getAbsolutePath() );
        }

        // list XML files
        File[] files = dir.listFiles( new FileFilter() {
            public boolean accept( File pathname ) {
                return pathname.getName().endsWith( ".xml" );
            }
        } );
        for ( int i = 0; i < files.length; i++ ) {
            File xmlFile = files[ i ];

            log.info( " " );
            log.info( " " );
            log.info( " " );
            log.info( " " );

            // unmarshall
            System.out.println( "Loading " + xmlFile.getAbsolutePath() );
            PsimiXmlReader reader = new PsimiXmlReader();
            EntrySet entrySet = reader.read( xmlFile );

            // marshall
            PsimiXmlWriter writer = new PsimiXmlWriter();
            File testFile = new File( dir.getAbsolutePath() + File.separator + xmlFile.getName().replaceAll( ".xml", ".copy" ) );
            writer.write( entrySet, testFile );

            log.info( "Comparing " + xmlFile.getName() + " and " + testFile.getName() + " ..." );

            // build 2 readers
            Reader controlReader = new FileReader( xmlFile );
            Reader testReader = new FileReader( testFile );

            // compare them
            Diff myDiff = new Diff( controlReader, testReader );
            myDiff.overrideDifferenceListener( new IgnorePsiMiDifferences() );

//            assertTrue( myDiff.toString(), myDiff.similar() );

            // test passed, delete the test file.
            if ( myDiff.similar() ) {
                int tryDel = 0;
                while( testFile.exists() && tryDel < 15) {
                    if( i > 1 ) {
                        try {
                            Thread.sleep( 300 );
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                    }
                    testFile.delete();
                    System.out.println( "Deleting file, Try #" + (++tryDel) );
                }

                if( testFile.exists() ) {
                    System.err.println( "Failed to delete " + testFile.getAbsolutePath() );
                    testFile.deleteOnExit();
                }
            } else {
                System.out.println( myDiff.toString() );
            }

            System.out.println( i + "/" + files.length + " processed." );
        }
    }
}