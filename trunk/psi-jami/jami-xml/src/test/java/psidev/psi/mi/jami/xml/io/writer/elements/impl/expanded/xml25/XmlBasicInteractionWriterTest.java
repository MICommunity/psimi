package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBasicInteractionWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlBasicInteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/11/13</pre>
 */

public class XmlBasicInteractionWriterTest extends AbstractXmlWriterTest {

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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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
            "    </experimentDescription>\n"+            "  </experimentList>\n" +
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

    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_interaction() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction, output.toString());
    }

    @Test
    public void test_write_participant_complex() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        Participant participant = new DefaultParticipant(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complex, output.toString());
    }

    @Test
    public void test_write_participant_complex_as_interactor() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        Participant participant = new DefaultParticipant(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.setComplexAsInteractor(true);
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complexAsInteractor, output.toString());
    }

    @Test
    public void test_write_participant_complex_no_participants() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Complex complex = new DefaultComplex("test complex");
        Participant participant = new DefaultParticipant(complex);
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_complexAsInteractor, output.toString());
    }

    @Test
    public void test_write_interaction_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction("interaction test");
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_shortName, output.toString());
    }

    @Test
    public void test_write_interaction_identifier() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("intact"), "EBI-xxx"));
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_identifier, output.toString());
    }

    @Test
    public void test_write_interaction_xref() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxx2"));
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_xref, output.toString());
    }

    @Test
    @Ignore
    public void test_write_interaction_inferred() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        Participant participant2 = new DefaultParticipant(new DefaultProtein("protein test2"));
        Participant participant3 = new DefaultParticipant(new DefaultProtein("protein test3"));
        Participant participant4 = new DefaultParticipant(new DefaultProtein("protein test4"));
        // two inferred interactiosn f1, f2, f3 and f3,f4
        Feature f1 = new DefaultFeature();
        f1.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        Feature f2 = new DefaultFeature();
        f2.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        Feature f3 = new DefaultFeature();
        f3.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        Feature f4 = new DefaultFeature();
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

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_inferred, output.toString());
    }

    @Test
    public void test_write_interaction_type() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:0914"));
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_type, output.toString());
    }

    @Test
    public void test_write_interaction_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_attributes, output.toString());
    }

    @Test
    public void test_write_interaction_registered() throws XMLStreamException, IOException, IllegalRangeException {
        Interaction interaction = new DefaultInteraction();
        Participant participant = new DefaultParticipant(new DefaultProtein("protein test"));
        interaction.addParticipant(participant);
        elementCache.clear();
        elementCache.extractIdForInteraction(new DefaultInteraction());
        elementCache.extractIdForInteraction(interaction);

        XmlBasicInteractionWriter writer = new XmlBasicInteractionWriter(createStreamWriter(), this.elementCache);
        writer.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("xxxxxx")));
        writer.write(interaction);
        streamWriter.flush();

        Assert.assertEquals(this.interaction_registered, output.toString());
    }
}