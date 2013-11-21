package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.HostOrganism;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25HostOrganismWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25HostOrganismWriterTest extends AbstractXml25WriterTest {

    private String organism = "<hostOrganism ncbiTaxId=\"9606\"/>";
    private String organismShortName = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "  </names>\n"+
            "</hostOrganism>";
    private String organismFullName = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "    <fullName>Homo Sapiens</fullName>\n"+
            "  </names>\n"+
            "</hostOrganism>";
    private String organismAliases = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "    <alias type=\"synonym\">homo sapiens</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "</hostOrganism>";
    private String organismCelltype = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "  </names>\n"+
            "  <cellType>\n" +
            "    <names>\n" +
            "      <shortLabel>293t</shortLabel>\n"+
            "    </names>\n"+
            "  </cellType>\n"+
            "</hostOrganism>";
    private String organismTissue = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "  </names>\n"+
            "  <tissue>\n" +
            "    <names>\n" +
            "      <shortLabel>embryo</shortLabel>\n"+
            "    </names>\n"+
            "  </tissue>\n"+
            "</hostOrganism>";
    private String organismCompartment = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "  </names>\n"+
            "  <compartment>\n" +
            "    <names>\n" +
            "      <shortLabel>cytosol</shortLabel>\n"+
            "    </names>\n"+
            "  </compartment>\n"+
            "</hostOrganism>";
    private String organismExperiments = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "  </names>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>1</experimentRef>\n"+
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</hostOrganism>";
    private String organismExperiments2 = "<hostOrganism ncbiTaxId=\"9606\">\n" +
            "  <names>\n" +
            "    <shortLabel>human</shortLabel>\n"+
            "  </names>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</hostOrganism>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_organism_no_name() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606);

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organism, output.toString());
    }

    @Test
    public void test_write_organism_shortName() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606, "human");

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismShortName, output.toString());
    }

    @Test
    public void test_write_organism_fullName() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606, "human", "Homo Sapiens");

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismFullName, output.toString());
    }

    @Test
    public void test_write_organism_aliases() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606, "human");
        organism.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "homo sapiens"));
        organism.getAliases().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismAliases, output.toString());
    }

    @Test
    public void test_write_organism_celltype() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606, "human");
        organism.setCellType(new DefaultCvTerm("293t"));

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismCelltype, output.toString());
    }

    @Test
    public void test_write_organism_tissue() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606, "human");
        organism.setTissue(new DefaultCvTerm("embryo"));
        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismTissue, output.toString());
    }

    @Test
    public void test_write_organism_compartment() throws XMLStreamException, IOException {
        Organism organism = new HostOrganism(9606, "human");
        organism.setCompartment(new DefaultCvTerm("cytosol"));

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismCompartment, output.toString());
    }

    @Test
    public void test_write_organism_experiments() throws XMLStreamException, IOException {
        HostOrganism organism = new HostOrganism(9606, "human");
        organism.getExperiments().add(new DefaultExperiment(new DefaultPublication("P12345")));
        organism.getExperiments().add(new DefaultExperiment(new DefaultPublication("P123456")));
        this.elementCache.clear();

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismExperiments, output.toString());
    }

    @Test
    public void test_write_organism_experimentsRegistered() throws XMLStreamException, IOException {
        HostOrganism organism = new HostOrganism(9606, "human");
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("P12345"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("P123456"));
        organism.getExperiments().add(exp1);
        organism.getExperiments().add(exp2);
        this.elementCache.clear();
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("P1234")));
        this.elementCache.extractIdForExperiment(exp1);
        this.elementCache.extractIdForExperiment(exp2);

        Xml25HostOrganismWriter writer = new Xml25HostOrganismWriter(createStreamWriter(), this.elementCache);
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismExperiments2, output.toString());
    }
}
