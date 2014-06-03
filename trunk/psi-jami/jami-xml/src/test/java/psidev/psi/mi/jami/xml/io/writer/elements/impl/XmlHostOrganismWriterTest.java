package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlHostOrganismWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlHostOrganismWriterTest extends AbstractXmlWriterTest {

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

    @Test
    public void test_write_organism_no_name() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606);

        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organism, output.toString());
    }

    @Test
    public void test_write_organism_shortName() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606, "human");

        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismShortName, output.toString());
    }

    @Test
    public void test_write_organism_fullName() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606, "human", "Homo Sapiens");

        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismFullName, output.toString());
    }

    @Test
    public void test_write_organism_aliases() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606, "human");
        organism.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "homo sapiens"));
        organism.getAliases().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismAliases, output.toString());
    }

    @Test
    public void test_write_organism_celltype() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606, "human");
        organism.setCellType(new DefaultCvTerm("293t"));

        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismCelltype, output.toString());
    }

    @Test
    public void test_write_organism_tissue() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606, "human");
        organism.setTissue(new DefaultCvTerm("embryo"));
        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismTissue, output.toString());
    }

    @Test
    public void test_write_organism_compartment() throws XMLStreamException, IOException {
        Organism organism = new DefaultOrganism(9606, "human");
        organism.setCompartment(new DefaultCvTerm("cytosol"));

        XmlHostOrganismWriter writer = new XmlHostOrganismWriter(createStreamWriter());
        writer.write(organism);
        streamWriter.flush();

        Assert.assertEquals(this.organismCompartment, output.toString());
    }
}
