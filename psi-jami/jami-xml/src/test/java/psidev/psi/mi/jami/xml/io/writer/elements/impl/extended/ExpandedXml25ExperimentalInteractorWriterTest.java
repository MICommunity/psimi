package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for ExpandedXml25ExperimentalInteractorWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class ExpandedXml25ExperimentalInteractorWriterTest extends AbstractXml25WriterTest {

    private String experimentalInteractor = "<experimentalInteractor>\n" +
            "  <interactor id=\"1\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n"+
            "</experimentalInteractor>";

    private String experimentalInteractor_registered = "<experimentalInteractor>\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n"+
            "</experimentalInteractor>";

    private String experimentalInteractor_experiments = "<experimentalInteractor>\n" +
            "  <interactor id=\"1\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n" +
            "  </experimentRefList>\n" +
            "</experimentalInteractor>";

    private String experimentalInteractor_experiments_registered = "<experimentalInteractor>\n" +
            "  <interactor id=\"3\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n" +
            "  </experimentRefList>\n" +
            "</experimentalInteractor>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_experimental_interactor() throws XMLStreamException, IOException {
        ExperimentalInteractor interactor = new ExperimentalInteractor();
        interactor.setInteractor(new DefaultProtein("protein test"));
        elementCache.clear();

        ExpandedXml25ExperimentalInteractorWriter writer = new ExpandedXml25ExperimentalInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(experimentalInteractor, output.toString());
    }

    @Test
    public void test_write_experimental_interactor_registered() throws XMLStreamException, IOException {
        ExperimentalInteractor interactor = new ExperimentalInteractor();
        interactor.setInteractor(new DefaultProtein("protein test"));
        elementCache.clear();
        elementCache.extractIdForInteractor(new DefaultProtein("p12346"));
        elementCache.extractIdForInteractor(interactor.getInteractor());

        ExpandedXml25ExperimentalInteractorWriter writer = new ExpandedXml25ExperimentalInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(experimentalInteractor_registered, output.toString());
    }

    @Test
    public void test_write_experimental_interactor_experiments() throws XMLStreamException, IOException {
        ExperimentalInteractor interactor = new ExperimentalInteractor();
        interactor.setInteractor(new DefaultProtein("protein test"));
        interactor.getExperiments().add(new DefaultExperiment(new DefaultPublication()));
        elementCache.clear();

        ExpandedXml25ExperimentalInteractorWriter writer = new ExpandedXml25ExperimentalInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(experimentalInteractor_experiments, output.toString());
    }

    @Test
    public void test_write_experimental_interactor_experiments_registered() throws XMLStreamException, IOException {
        ExperimentalInteractor interactor = new ExperimentalInteractor();
        interactor.setInteractor(new DefaultProtein("protein test"));
        interactor.getExperiments().add(new DefaultExperiment(new DefaultPublication()));
        elementCache.clear();
        elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("12345")));
        elementCache.extractIdForExperiment(interactor.getExperiments().iterator().next());

        ExpandedXml25ExperimentalInteractorWriter writer = new ExpandedXml25ExperimentalInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(experimentalInteractor_experiments_registered, output.toString());
    }
}
