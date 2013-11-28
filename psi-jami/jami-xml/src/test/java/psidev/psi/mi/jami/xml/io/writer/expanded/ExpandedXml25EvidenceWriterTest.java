package psidev.psi.mi.jami.xml.io.writer.expanded;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.impl.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Unit tester for ExpandedXml25EvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/13</pre>
 */

public class ExpandedXml25EvidenceWriterTest {
    private String interaction = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"6\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n" +
            "              </names>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "              </xref>\n" +
            "            </interactionDetectionMethod>\n" +
            "          </experimentDescription>\n" +
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"6\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"6\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
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
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"6\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_source = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <source releaseDate=\"2013-09-02Z\">\n" +
            "      <names>\n" +
            "        <shortLabel>intact</shortLabel>\n"+
            "      </names>\n"+
            "    </source>\n"+
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_different_source = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <source releaseDate=\"2013-09-02Z\">\n" +
            "      <names>\n" +
            "        <shortLabel>mint</shortLabel>\n"+
            "      </names>\n"+
            "    </source>\n"+
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"6\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "  <entry>\n" +
            "    <source releaseDate=\"2013-09-02Z\">\n" +
            "      <names>\n" +
            "        <shortLabel>intact</shortLabel>\n"+
            "      </names>\n"+
            "    </source>\n"+
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"2\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "      <interaction id=\"5\">\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"6\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    private String interaction_availability = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<entrySet xmlns=\"http://psi.hupo.org/mi/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://psi.hupo.org/mi/mif http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd\" " +
            "level=\"2\" version=\"5\" minorVersion=\"4\">\n" +
            "  <entry>\n" +
            "    <interactionList>\n" +
            "      <interaction id=\"1\">\n" +
            "        <availability id=\"2\">copyright</availability>\n" +
            "        <experimentList>\n" +
            "          <experimentDescription id=\"3\">\n" +
            "            <bibref>\n" +
            "              <xref>\n" +
            "                <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </bibref>\n" +
            "            <interactionDetectionMethod>\n" +
            "              <names>\n" +
            "                <shortLabel>unspecified method</shortLabel>\n"+
            "              </names>\n"+
            "              <xref>\n" +
            "                <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "              </xref>\n"+
            "            </interactionDetectionMethod>\n"+
            "          </experimentDescription>\n"+
            "        </experimentList>\n" +
            "        <participantList>\n" +
            "          <participant id=\"4\">\n" +
            "            <interactor id=\"5\">\n" +
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
            "            <experimentalRoleList>\n" +
            "              <experimentalRole>\n" +
            "                <names>\n" +
            "                  <shortLabel>unspecified role</shortLabel>\n" +
            "                </names>\n" +
            "                <xref>\n" +
            "                  <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "                </xref>\n" +
            "              </experimentalRole>\n" +
            "            </experimentalRoleList>\n" +
            "          </participant>\n"+
            "        </participantList>\n" +
            "      </interaction>\n"+
            "    </interactionList>\n"+
            "  </entry>\n" +
            "</entrySet>";
    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter();
        writer.write(new DefaultInteractionEvidence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter();
        writer.initialiseContext(null);
    }

    @Test
    public void test_single_interaction() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();
        Assert.assertEquals(this.interaction, stringWriter.toString());
    }

    @Test
    public void test_several_interactions1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_multiple, stringWriter.toString());
    }

    @Test
    public void test_several_interactions2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_multiple, stringWriter.toString());
    }

    @Test
    public void test_interactions_same_interactors1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(participant.getInteractor());
        interaction2.addParticipant(participant2);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_same_experiment_interactors, stringWriter.toString());
    }

    @Test
    public void test_interactions_same_interactors2() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(participant.getInteractor());
        interaction2.addParticipant(participant2);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());

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

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(complex);
        interaction.addParticipant(participant);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));

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

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        writer.setWriteComplexesAsInteractors(true);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(complex);
        interaction.addParticipant(participant);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_complexes_as_interactor, stringWriter.toString());
    }

    @Test
    public void test_interactions_different_entries1() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));

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

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());

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

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.write(Arrays.asList(interaction, interaction2).iterator());
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_entries2, stringWriter.toString());
    }

    @Test
    public void test_interaction_source() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        Source source = new DefaultSource("intact");
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction.getExperiment().getPublication().setSource(source);
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            writer.setDefaultReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();
        Assert.assertEquals(this.interaction_source, stringWriter.toString());
    }

    @Test
    public void test_interactions_different_sources() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence();
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("protein test2"));
        interaction2.addParticipant(participant2);
        Source source = new DefaultSource("mint");
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        interaction2.setExperiment(interaction.getExperiment());
        interaction.getExperiment().getPublication().setSource(source);
        InteractionEvidence interaction3 = new DefaultInteractionEvidence();
        interaction3.addParticipant(participant);
        interaction3.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        InteractionEvidence interaction4 = new DefaultInteractionEvidence();
        interaction4.addParticipant(participant2);
        interaction4.setExperiment(interaction3.getExperiment());
        Source source2 = new DefaultSource("intact");
        interaction3.getExperiment().getPublication().setSource(source2);
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            writer.setDefaultReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }

        writer.start();
        writer.write(Arrays.asList(interaction, interaction2, interaction3, interaction4));
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_different_source, stringWriter.toString());
    }

    @Test
    public void test_single_interaction_availability() throws XMLStreamException {
        StringWriter stringWriter = new StringWriter();

        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("protein test")));

        ExpandedXml25EvidenceWriter writer = new ExpandedXml25EvidenceWriter(stringWriter);
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        interaction.setAvailability("copyright");
        ParticipantEvidence participant = new DefaultParticipantEvidence(complex.getParticipants().iterator().next().getInteractor());
        interaction.addParticipant(participant);
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));

        writer.start();
        writer.write(interaction);
        writer.end();
        writer.close();

        Assert.assertEquals(this.interaction_availability, stringWriter.toString());
    }
}
