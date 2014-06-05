package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultNamedBinaryInteractionEvidence;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Unit tester for CompactXml25NamedBinaryInteractionEvidenceWrite
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/11/13</pre>
 */

public class XmlNamedBinaryInteractionEvidenceWriterTest extends AbstractXmlWriterTest {

    private String interaction = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_complex = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
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
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_shortName ="<interaction id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>interaction test</shortLabel>\n"+
            "  </names>\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_fullName ="<interaction id=\"1\">\n" +
            "  <names>\n" +
            "    <fullName>interaction test</fullName>\n"+
            "  </names>\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_aliases ="<interaction id=\"1\">\n" +
            "  <names>\n" +
            "    <alias type=\"synonym\">interaction synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_identifier = "<interaction id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"intact\" id=\"EBI-xxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";
    private String interaction_xref = "<interaction id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxx2\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";
    private String interaction_inferred = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "      <featureList>\n" +
            "        <feature id=\"5\">\n" +
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
            "      <interactorRef>7</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "      <featureList>\n" +
            "        <feature id=\"8\">\n" +
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
            "      <interactorRef>10</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "      <featureList>\n" +
            "        <feature id=\"11\">\n" +
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
            "      <interactorRef>13</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "      <featureList>\n" +
            "        <feature id=\"14\">\n" +
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
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
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
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "    <attribute name=\"spoke expansion\" nameAc=\"MI:1060\"/>\n"+
            "  </attributeList>\n"+
            "</interaction>";
    private String interaction_registered = "<interaction id=\"2\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"4\">\n" +
            "      <interactorRef>5</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_availability = "<interaction id=\"1\">\n" +
            "  <availabilityRef>2</availabilityRef>\n" +
            "  <experimentList>\n" +
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"4\">\n" +
            "      <interactorRef>5</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "</interaction>";

    private String interaction_negative = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <negative>true</negative>\n"+
            "</interaction>";

    private String interaction_confidence = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
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

    private String interaction_parameters = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <parameterList>\n" +
            "    <parameter term=\"kd\" base=\"10\" exponent=\"0\" factor=\"5\"/>\n" +
            "  </parameterList>\n" +
            "</interaction>";
    private String interaction_variable_parameters = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <experimentalVariableValueList>\n" +
            "    <experimentalVariableValues>\n" +
            "      <variableValueRef>5</variableValueRef>\n" +
            "    </experimentalVariableValues>\n"+
            "  </experimentalVariableValueList>\n" +
            "</interaction>";
    private String interaction_causal_relationship = "<interaction id=\"1\">\n" +
            "  <experimentList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentList>\n" +
            "  <participantList>\n" +
            "    <participant id=\"3\">\n" +
            "      <interactorRef>4</interactorRef>\n" +
            "      <biologicalRole>\n" +
            "        <names>\n" +
            "          <shortLabel>unspecified role</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </biologicalRole>\n" +
            "      <experimentalRoleList>\n" +
            "        <experimentalRole>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified role</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </experimentalRole>\n" +
            "      </experimentalRoleList>\n" +
            "    </participant>\n"+
            "  </participantList>\n" +
            "  <causalRelationshipList>\n" +
            "    <causalRelationship>\n" +
            "      <sourceParticipantRef>3</sourceParticipantRef>\n" +
            "      <causalityStatement>\n" +
            "        <names>\n" +
            "          <shortLabel>increases RNA expression of </shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:xxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </causalityStatement>\n"+
            "      <targetParticipantRef>5</targetParticipantRef>\n" +
            "    </causalRelationship>\n"+
            "  </causalRelationshipList>\n"+
            "</interaction>";

    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_interaction() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction, output.toString());
    }

    @Test
    public void test_write_participant_complex() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultNamedModelledParticipant(new DefaultProtein("test protein")));
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complex, output.toString());
    }

    @Test
    public void test_write_participant_complex_as_interactor() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultNamedModelledParticipant(new DefaultProtein("test protein")));
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.setComplexAsInteractor(true);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction, output.toString());
    }

    @Test
    public void test_write_participant_complex_no_participants() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        Complex complex = new DefaultComplex("test complex");
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction, output.toString());
    }

    @Test
    public void test_write_interaction_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence("interaction test");
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_shortName, output.toString());
    }

    @Test
    public void test_write_interaction_fullName() throws XMLStreamException, IOException, IllegalRangeException {
        NamedInteraction interaction = new DefaultNamedBinaryInteractionEvidence();
        interaction.setFullName("interaction test");
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write((BinaryInteractionEvidence)interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_fullName, output.toString());
    }

    @Test
    public void test_write_interaction_alias() throws XMLStreamException, IOException, IllegalRangeException {
        NamedInteraction interaction = new DefaultNamedBinaryInteractionEvidence();
        interaction.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "interaction synonym"));
        interaction.getAliases().add(new DefaultAlias("test"));
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write((BinaryInteractionEvidence)interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_aliases, output.toString());
    }

    @Test
    public void test_write_interaction_identifier() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("intact"), "EBI-xxx"));
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_identifier, output.toString());
    }

    @Test
    public void test_write_interaction_xref() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxx2"));
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_xref, output.toString());
    }

    @Test
    @Ignore
    public void test_write_interaction_inferred() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        ParticipantEvidence participant2 = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        ParticipantEvidence participant3 = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        ParticipantEvidence participant4 = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        // two inferred interactiosn f1, f2, f3 and f3,f4
        FeatureEvidence f1 = new DefaultFeatureEvidence();
        f1.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        FeatureEvidence f2 = new DefaultFeatureEvidence();
        f2.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        FeatureEvidence f3 = new DefaultFeatureEvidence();
        f1.getLinkedFeatures().add(f2);
        f1.getLinkedFeatures().add(f3);
        f2.getLinkedFeatures().add(f1);
        f2.getLinkedFeatures().add(f3);
        f3.getLinkedFeatures().add(f1);
        f3.getLinkedFeatures().add(f2);
        participant.addFeature(f1);
        participant2.addFeature(f2);
        participant3.addFeature(f3);
        interaction.addParticipant(participant);
        interaction.addParticipant(participant2);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_inferred, output.toString());
    }

    @Test
    public void test_write_interaction_type() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:0914"));
        elementCache.clear();

        XmlNamedInteractionEvidenceWriter writer = new XmlNamedInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_type, output.toString());
    }

    @Test
    public void test_write_interaction_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        interaction.setComplexExpansion(CvTermUtils.createMICvTerm("spoke expansion", "MI:1060"));
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_attributes, output.toString());
    }

    @Test
    public void test_write_interaction_registered() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        elementCache.clear();
        elementCache.extractIdForInteraction(new DefaultInteraction());
        elementCache.extractIdForInteraction(interaction);

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_registered, output.toString());
    }

    @Test
    public void test_write_interaction_negative() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.setNegative(true);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_negative, output.toString());
    }

    @Test
    public void test_write_interaction_confidences() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("intact-miscore"), "0.8"));
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_confidence, output.toString());
    }

    @Test
    public void test_write_interaction_parameters() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5))));
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_parameters, output.toString());
    }

    @Test
    public void test_write_interaction_availability() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        interaction.setAvailability("copyright");
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_availability, output.toString());
    }

    @Test
    public void test_write_interaction_variable_parameters() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        VariableParameterValueSet valueSet = new DefaultVariableParameterValueSet();
        valueSet.add(new DefaultVariableParameterValue("test", null));
        interaction.getVariableParameterValues().add(valueSet);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_variable_parameters, output.toString());
    }

    @Test
    public void test_write_interaction_causal_relationship() throws XMLStreamException, IOException, IllegalRangeException {
        BinaryInteractionEvidence interaction = new DefaultNamedBinaryInteractionEvidence();
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        interaction.addParticipant(participant);
        CausalRelationship rel = new DefaultCausalRelationship(CvTermUtils.createMICvTerm("increases RNA expression of ","MI:xxxx"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        participant.getCausalRelationships().add(rel);
        elementCache.clear();

        XmlNamedBinaryInteractionEvidenceWriter writer = new XmlNamedBinaryInteractionEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_causal_relationship, output.toString());
    }
}
