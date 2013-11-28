package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interaction;
import psidev.psi.mi.jami.xml.extension.XmlModelledInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Unit tester for CompactXml25ModelledInteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/13</pre>
 */

public class ExpandedXml25ModelledInteractionWriterTest extends AbstractXml25WriterTest {

    private String interaction = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_complex = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactionRef>4</interactionRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_complexAsInteractor ="<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>test complex</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>complex</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0314\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_shortName ="<interaction id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>interaction test</shortLabel>\n"+
            "  </names>\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_fullName ="<interaction id=\"1\">\n" +
            "  <names>\n" +
            "    <fullName>interaction test</fullName>\n"+
            "  </names>\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_aliases ="<interaction id=\"1\">\n" +
            "  <names>\n" +
            "    <alias type=\"synonym\">interaction synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_identifier = "<interaction id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"intact\" id=\"EBI-xxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";
    private String interaction_xref = "<interaction id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxx2\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";
    private String interaction_inferred = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <featureList>\n" +
            "        <feature id=\"5\">\n" +
            "          <featureType>\n" +
            "            <names>\n" +
            "              <shortLabel>biological feature</shortLabel>\n" +
            "            </names>\n" +
            "            <xref>\n" +
            "              <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0252\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "            </xref>\n" +
            "          </featureType>\n" +
            "          <featureRangeList>\n" +
            "            <featureRange>\n" +
            "              <startStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </startStatus>\n" +
            "              <begin position=\"1\"/>\n"+
            "              <endStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </endStatus>\n" +
            "              <end position=\"4\"/>\n"+
            "            </featureRange>\n"+
            "          </featureRangeList>\n" +
            "        </feature>\n"+
            "      </featureList>\n" +
            "    </participant>\n"+
            "    <participant id=\"6\">\n" +
            "      <interactor id=\"7\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test2</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <featureList>\n" +
            "        <feature id=\"8\">\n" +
            "          <featureType>\n" +
            "            <names>\n" +
            "              <shortLabel>biological feature</shortLabel>\n" +
            "            </names>\n" +
            "            <xref>\n" +
            "              <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0252\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "            </xref>\n" +
            "          </featureType>\n" +
            "          <featureRangeList>\n" +
            "            <featureRange>\n" +
            "              <startStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </startStatus>\n" +
            "              <begin position=\"1\"/>\n"+
            "              <endStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </endStatus>\n" +
            "              <end position=\"4\"/>\n"+
            "            </featureRange>\n"+
            "          </featureRangeList>\n" +
            "        </feature>\n"+
            "      </featureList>\n" +
            "    </participant>\n"+
            "    <participant id=\"9\">\n" +
            "      <interactor id=\"10\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test3</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <featureList>\n" +
            "        <feature id=\"11\">\n" +
            "          <featureType>\n" +
            "            <names>\n" +
            "              <shortLabel>biological feature</shortLabel>\n" +
            "            </names>\n" +
            "            <xref>\n" +
            "              <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0252\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "            </xref>\n" +
            "          </featureType>\n" +
            "          <featureRangeList>\n" +
            "            <featureRange>\n" +
            "              <startStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </startStatus>\n" +
            "              <begin position=\"1\"/>\n"+
            "              <endStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </endStatus>\n" +
            "              <end position=\"4\"/>\n"+
            "            </featureRange>\n"+
            "          </featureRangeList>\n" +
            "        </feature>\n"+
            "      </featureList>\n" +
            "    </participant>\n"+
            "    <participant id=\"12\">\n" +
            "      <interactor id=\"13\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test4</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <featureList>\n" +
            "        <feature id=\"14\">\n" +
            "          <featureType>\n" +
            "            <names>\n" +
            "              <shortLabel>biological feature</shortLabel>\n" +
            "            </names>\n" +
            "            <xref>\n" +
            "              <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0252\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "            </xref>\n" +
            "          </featureType>\n" +
            "          <featureRangeList>\n" +
            "            <featureRange>\n" +
            "              <startStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </startStatus>\n" +
            "              <begin position=\"1\"/>\n"+
            "              <endStatus>\n" +
            "                <names>\n" +
            "                  <shortLabel>certain</shortLabel>\n"+
            "                </names>\n"+
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "                </xref>\n"+
            "              </endStatus>\n" +
            "              <end position=\"4\"/>\n"+
            "            </featureRange>\n"+
            "          </featureRangeList>\n" +
            "        </feature>\n"+
            "      </featureList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <inferredInteractionList>\n" +
            "    <inferredInteraction>\n" +
            "      <participant>\n" +
            "        <participantFeatureRef>5</participantFeatureRef>\n" +
            "      </participant>\n"+
            "      <participant>\n" +
            "        <participantFeatureRef>11</participantFeatureRef>\n" +
            "      </participant>\n"+
            "      <participant>\n" +
            "        <participantFeatureRef>8</participantFeatureRef>\n" +
            "      </participant>\n"+
            "    </inferredInteraction>\n"+
            "    <inferredInteraction>\n" +
            "      <participant>\n" +
            "        <participantFeatureRef>11</participantFeatureRef>\n" +
            "      </participant>\n"+
            "      <participant>\n" +
            "        <participantFeatureRef>14</participantFeatureRef>\n" +
            "      </participant>\n"+
            "    </inferredInteraction>\n"+
            "  </inferredInteractionList>\n" +
            "</interaction>";
    private String interaction_type =  "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <interactionType>\n" +
            "    <names>\n" +
            "      <shortLabel>association</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0914\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactionType>\n" +
            "</interaction>";
    private String interaction_attributes =  "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</interaction>";
    private String interaction_registered = "<interaction id=\"2\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"3\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"4\">\n" +
            "      <interactor id=\"5\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_confidence = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <confidenceList>\n" +
            "    <confidence>\n" +
            "      <unit>\n" +
            "        <names>\n" +
            "          <shortLabel>intact-miscore</shortLabel>\n"+
            "        </names>\n"+
            "      </unit>\n" +
            "      <value>0.8</value>\n" +
            "    </confidence>\n"+
            "  </confidenceList>\n" +
            "</interaction>";

    private String interaction_parameter = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <parameterList>\n" +
            "    <parameter term=\"kd\" base=\"10\" exponent=\"0\" factor=\"5\">\n" +
            "      <experimentRef>2</experimentRef>\n" +
            "    </parameter>\n"+
            "  </parameterList>\n" +
            "</interaction>";

    private String interaction_preAssembly = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"pre-assembly\" nameAc=\"MI:1158\"/>\n" +
            "    <attribute name=\"positive cooperative effect\" nameAc=\"MI:1154\"/>\n" +
            "    <attribute name=\"configurational pre-organization\" nameAc=\"MI:1174\"/>\n"+
            "    <attribute name=\"affected interaction\" nameAc=\"MI:1150\">5</attribute>\n" +
            "  </attributeList>\n" +
            "</interaction>";

    private String interaction_allostery = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"allostery\" nameAc=\"MI:1157\"/>\n" +
            "    <attribute name=\"allosteric molecule\" nameAc=\"MI:1159\">3</attribute>\n" +
            "    <attribute name=\"allosteric effector\" nameAc=\"MI:1160\">5</attribute>\n" +
            "    <attribute name=\"heterotropic allostery\" nameAc=\"MI:1168\"/>\n" +
            "    <attribute name=\"allosteric change in structure\" nameAc=\"MI:1165\"/>\n" +
            "    <attribute name=\"positive cooperative effect\" nameAc=\"MI:1154\"/>\n" +
            "    <attribute name=\"allosteric v-type response\" nameAc=\"MI:1163\"/>\n" +
            "    <attribute name=\"affected interaction\" nameAc=\"MI:1150\">6</attribute>\n" +
            "  </attributeList>\n" +
            "</interaction>";
    private String interaction_intra = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentDescription id=\"2\">\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </bibref>\n"+
            "      <interactionDetectionMethod>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified method</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </interactionDetectionMethod>\n"+
            "    </experimentDescription>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactor id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>protein test</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <intraMolecular>true</intraMolecular>\n" +
            "</interaction>";

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_interaction() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction, output.toString());
    }

    @Test
    public void test_write_participant_complex() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        ModelledParticipant participant = new DefaultModelledParticipant(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complex, output.toString());
    }

    @Test
    public void test_write_participant_complex_as_interactor() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        ModelledParticipant participant = new DefaultModelledParticipant(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.setComplexAsInteractor(true);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complexAsInteractor, output.toString());
    }

    @Test
    public void test_write_participant_complex_no_participants() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        Complex complex = new DefaultComplex("test complex");
        ModelledParticipant participant = new DefaultModelledParticipant(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complexAsInteractor, output.toString());
    }

    @Test
    public void test_write_interaction_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction("interaction test");
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_shortName, output.toString());
    }

    @Test
    public void test_write_interaction_fullName() throws XMLStreamException, IOException, IllegalRangeException {
        NamedInteraction interaction = new XmlModelledInteraction();
        interaction.setFullName("interaction test");
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write((ModelledInteraction)interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_fullName, output.toString());
    }

    @Test
    public void test_write_interaction_alias() throws XMLStreamException, IOException, IllegalRangeException {
        NamedInteraction interaction = new XmlModelledInteraction();
        interaction.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "interaction synonym"));
        interaction.getAliases().add(new DefaultAlias("test"));
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write((ModelledInteraction)interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_aliases, output.toString());
    }

    @Test
    public void test_write_interaction_identifier() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("intact"), "EBI-xxx"));
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_identifier, output.toString());
    }

    @Test
    public void test_write_interaction_xref() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxx2"));
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_xref, output.toString());
    }

    @Test
    @Ignore
    public void test_write_interaction_inferred() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(new DefaultProtein("protein test2"));
        ModelledParticipant participant3 = new DefaultModelledParticipant(new DefaultProtein("protein test3"));
        ModelledParticipant participant4 = new DefaultModelledParticipant(new DefaultProtein("protein test4"));
        // two inferred interactiosn f1, f2, f3 and f3,f4
        ModelledFeature f1 = new DefaultModelledFeature();
        f1.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        ModelledFeature f2 = new DefaultModelledFeature();
        f2.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        ModelledFeature f3 = new DefaultModelledFeature();
        f3.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        ModelledFeature f4 = new DefaultModelledFeature();
        f4.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        f1.getLinkedFeatures().add(f2);
        f1.getLinkedFeatures().add(f3);
        f2.getLinkedFeatures().add(f1);
        f2.getLinkedFeatures().add(f3);
        f3.getLinkedFeatures().add(f1);
        f3.getLinkedFeatures().add(f2);
        f3.getLinkedFeatures().add(f4);
        f4.getLinkedFeatures().add(f3);
        participant.addFeature(f1);
        participant2.addFeature(f2);
        participant3.addFeature(f3);
        participant4.addFeature(f4);
        interaction.addParticipant(participant);
        interaction.addParticipant(participant2);
        interaction.addParticipant(participant3);
        interaction.addParticipant(participant4);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_inferred, output.toString());
    }

    @Test
    public void test_write_interaction_type() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:0914"));
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_type, output.toString());
    }

    @Test
    public void test_write_interaction_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_attributes, output.toString());
    }

    @Test
    public void test_write_interaction_registered() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();
        elementCache.extractIdForInteraction(new DefaultInteraction());
        elementCache.extractIdForInteraction(interaction);

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_registered, output.toString());
    }

    @Test
    public void test_write_interaction_parameter() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5))));
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_parameter, output.toString());
    }

    @Test
    public void test_write_interaction_confidence() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("intact-miscore"), "0.8"));
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_confidence, output.toString());
    }

    @Test
    public void test_write_interaction_preassembly() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        Preassembly assembly = new DefaultPreassemby(CvTermUtils.createMICvTerm("positive cooperative effect", "MI:1154"));
        assembly.setResponse(CvTermUtils.createMICvTerm("configurational pre-organization", "MI:1174"));
        assembly.getAffectedInteractions().add(new DefaultModelledInteraction());
        interaction.getCooperativeEffects().add(assembly);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_preAssembly, output.toString());
    }

    @Test
    public void test_write_interaction_preassembly_defaultExperiment() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        Preassembly assembly = new DefaultPreassemby(CvTermUtils.createMICvTerm("positive cooperative effect", "MI:1154"));
        assembly.setResponse(CvTermUtils.createMICvTerm("configurational pre-organization", "MI:1174"));
        assembly.getAffectedInteractions().add(new DefaultModelledInteraction());
        assembly.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx")));
        interaction.getCooperativeEffects().add(assembly);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("12345")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_preAssembly, output.toString());
    }

    @Test
    public void test_write_interaction_allostery() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledInteraction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        Allostery allostery = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect", "MI:1154"),
                participant, new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor())));
        allostery.setResponse(CvTermUtils.createMICvTerm("allosteric v-type response", "MI:1163"));
        allostery.getAffectedInteractions().add(new DefaultModelledInteraction());
        allostery.setAllostericMechanism(CvTermUtils.createMICvTerm("allosteric change in structure", "MI:1165"));
        allostery.setAllosteryType(CvTermUtils.createMICvTerm("heterotropic allostery", "MI:1168"));
        interaction.getCooperativeEffects().add(allostery);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_allostery, output.toString());
    }

    @Test
    public void test_write_interaction_intraMolecular() throws XMLStreamException, IOException, IllegalRangeException {
        ExtendedPsi25Interaction interaction = new XmlModelledInteraction();
        ModelledParticipant participant = new DefaultModelledParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.setIntraMolecular(true);
        elementCache.clear();

        ExpandedXml25ModelledInteractionWriter writer = new ExpandedXml25ModelledInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write((ModelledInteraction)interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_intra, output.toString());
    }
}
