package psidev.psi.mi.jami.xml.io.writer.expanded;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultComplex;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import javax.xml.stream.XMLStreamException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Unit tester for LightExpandedXml25BinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/13</pre>
 */

public class LightExpandedXml25BinaryWriterTest {

    private String interaction = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "          <experimentDescription id=\"6\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactor id=\"8\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test2</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n"+
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "          <experimentDescription id=\"6\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactor id=\"8\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactionRef>4</interactionRef>\n" +
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
            "      <interaction id=\"4\">\n" +
            "        <names>\n" +
            "          <shortLabel>test complex</shortLabel>\n"+
            "        </names>\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"5\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication and experiment for modelled interactions that are not interaction evidences.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication and experiment for modelled interactions that are not interaction evidences.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"6\">\n" +
            "            <interactor id=\"7\">\n" +
            "              <names>\n" +
            "                <shortLabel>test protein</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>test complex</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>complex</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0314\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "          <experimentDescription id=\"6\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactor id=\"8\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test2</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"3\">\n" +
            "            <interactor id=\"4\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
            "          <experimentDescription id=\"6\">\n" +
            "            <names>\n" +
            "              <fullName>Mock publication for interactions that do not have experimental details.</fullName>\n" +
            "            </names>\n" +
            "            <bibref>\n" +
            "              <attributeList>\n" +
            "                <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for interactions that do not have experimental details.</attribute>\n" +
            "              </attributeList>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"7\">\n" +
            "            <interactor id=\"8\">\n" +
            "              <names>\n" +
            "                <shortLabel>protein test2</shortLabel>\n" +
            "              </names>\n" +
            "              <interactorType>\n" +
            "                <names>\n" +
            "                  <shortLabel>protein</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </interactorType>\n" +
            "            </interactor>\n"+
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
        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter();
        writer.write(new DefaultBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter();
        writer.initialiseContext(null);
    }

    @Test
    public void test_single_interaction() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();
        Assert.assertEquals(this.interaction, stringWriter.toString());
    }

    @Test
    public void test_several_interactions1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new DefaultBinaryInteraction();
        Participant participant2 = new DefaultParticipant(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_multiple, stringWriter.toString());
    }

    @Test
    public void test_several_interactions2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new DefaultBinaryInteraction();
        Participant participant2 = new DefaultParticipant(new DefaultProtein("protein test2"));
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

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new DefaultBinaryInteraction();
        Participant participant2 = new DefaultParticipant(participant.getInteractor());
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

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new DefaultBinaryInteraction();
        Participant participant2 = new DefaultParticipant(participant.getInteractor());
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_same_experiment_interactors, stringWriter.toString());
    }

    @Test
    public void test_single_interaction_complexes() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(complex);
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

        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);
        writer.setWriteComplexesAsInteractors(true);

        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(complex);
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

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);
        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);

        writer.start();
        writer.write(interaction);
        writer.write(interaction);
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries1, stringWriter.toString());
    }

    @Test
    public void test_interactions_different_entries2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new DefaultBinaryInteraction();
        Participant participant2 = new DefaultParticipant(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries2, stringWriter.toString());
    }

    @Test
    public void test_interactions_different_entries3() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        LightExpandedXml25BinaryWriter writer = new LightExpandedXml25BinaryWriter(stringWriter);

        BinaryInteraction interaction = new DefaultBinaryInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        BinaryInteraction interaction2 = new DefaultBinaryInteraction();
        Participant participant2 = new DefaultParticipant(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries2, stringWriter.toString());
    }
}
