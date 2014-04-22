package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.model.extension.XmlComplex;
import psidev.psi.mi.jami.xml.model.extension.XmlModelledParticipant;
import psidev.psi.mi.jami.xml.model.extension.XmlParticipant;
import psidev.psi.mi.jami.xml.model.extension.XmlProtein;
import psidev.psi.mi.jami.xml.model.extension.binary.XmlBinaryInteraction;

import javax.xml.stream.XMLStreamException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Unit tester for LightCompactXml25BinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/13</pre>
 */

public class LightCompactXml25BinaryWriterTest {

    private String interaction = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"3\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"4\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_multiple = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "      <interactor id=\"3\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"4\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"5\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"6\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactorRef>3</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_same_experiment_interactors = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"3\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"4\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"6\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_complexes = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "      <experimentDescription id=\"2\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication and experiment for modelled interactions that are not interaction evidences.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication and experiment for modelled interactions that are not interaction evidences.</attribute>\n"+
            "          </attributeList>\n"+
            "        </bibref>\n"+
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"3\">\n" +
            "        <names>\n" +
            "          <shortLabel>test protein</shortLabel>\n" +
            "        </names>\n" +
            "        <interactorType>\n" +
            "          <names>\n" +
            "            <shortLabel>protein</shortLabel>\n" +
            "          </names>\n" +
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "          </xref>\n" +
            "        </interactorType>\n" +
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"4\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"5\">\n" +
            "            <interactionRef>6</interactionRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"6\">\n" +
            "        <names>\n" +
            "          <shortLabel>test complex</shortLabel>\n"+
            "        </names>\n" +
            "        <experimentList>\n" +
            "          <experimentRef>2</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactorRef>3</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_complexes_as_interactor = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"3\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"4\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_different_entries1 = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"3\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"4\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"3\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"4\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_different_entries2 = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/molecular_interactions/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "      <interactor id=\"3\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"4\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"5\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"6\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactorRef>3</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "  <entry>\n" +
            "    <experimentList>\n" +
            "      <experimentDescription id=\"1\">\n" +
            "        <names>\n" +
            "          <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "        </names>\n" +
            "        <bibref>\n" +
            "          <attributeList>\n" +
            "            <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "          </attributeList>\n" +
            "        </bibref>\n" +
            "        <interactionDetectionMethod>\n" +
            "          <names>\n" +
            "            <shortLabel>unspecified method</shortLabel>\n"+
            "          </names>\n"+
            "          <xref>\n" +
            "            <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "          </xref>\n"+
            "        </interactionDetectionMethod>\n"+
            "      </experimentDescription>\n"+
            "    </experimentList>\n" +
            "    <interactorList>\n" +
            "      <interactor id=\"2\">\n" +
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
            "      </interactor>\n"+
            "      <interactor id=\"3\">\n" +
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
            "      </interactor>\n"+
            "    </interactorList>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"4\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"5\">\n" +
            "            <interactorRef>2</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"6\">\n" +
            "        <experimentList>\n" +
            "          <experimentRef>1</experimentRef>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactorRef>3</interactorRef>\n" +
            "            <biologicalRole>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified role</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </biologicalRole>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter();
        writer.write(new XmlBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter();
        writer.initialiseContext(null);
    }

    @Test
    public void test_single_interaction() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();
        Assert.assertEquals(this.interaction, stringWriter.toString());
    }

    @Test
    @Ignore
    public void test_several_interactions1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new XmlBinaryInteraction();
        Participant participant2 = new XmlParticipant(new XmlProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_multiple, stringWriter.toString());
    }

    @Test
    @Ignore
    public void test_several_interactions2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new XmlBinaryInteraction();
        Participant participant2 = new XmlParticipant(new XmlProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_multiple, stringWriter.toString());
    }

    @Test
    public void test_interactions_same_interactors1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new XmlBinaryInteraction();
        Participant participant2 = new XmlParticipant(participant.getInteractor());
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_same_experiment_interactors, stringWriter.toString());
    }

    @Test
    public void test_interactions_same_interactors2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new XmlBinaryInteraction();
        Participant participant2 = new XmlParticipant(participant.getInteractor());
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_same_experiment_interactors, stringWriter.toString());
    }

    @Test
    @Ignore
    public void test_single_interaction_complexes() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        Complex complex = new XmlComplex("test complex");
        complex.getParticipants().add(new XmlModelledParticipant(new XmlProtein("test protein")));

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(complex);
        interaction.addParticipant(participant);

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_complexes, stringWriter.toString());
    }

    @Test
    public void test_single_interaction_complexes_as_Interactor() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        Complex complex = new XmlComplex("test complex");
        complex.getParticipants().add(new XmlModelledParticipant(new XmlProtein("test protein")));

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);
        writer.setWriteComplexesAsInteractors(true);

        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(complex);
        interaction.addParticipant(participant);

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_complexes_as_interactor, stringWriter.toString());
    }

    @Test
    public void test_interactions_different_entries1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);

        writer.start();
        writer.write(interaction);
        writer.write(interaction);
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries1, stringWriter.toString());
    }

    @Test
    @Ignore
    public void test_interactions_different_entries2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new XmlBinaryInteraction();
        Participant participant2 = new XmlParticipant(new XmlProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries2, stringWriter.toString());
    }

    @Test
    @Ignore
    public void test_interactions_different_entries3() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightCompactXml25BinaryWriter writer = new LightCompactXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new XmlBinaryInteraction();
        Participant participant = new XmlParticipant(new XmlProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new XmlBinaryInteraction();
        Participant participant2 = new XmlParticipant(new XmlProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries2, stringWriter.toString());
    }
}
