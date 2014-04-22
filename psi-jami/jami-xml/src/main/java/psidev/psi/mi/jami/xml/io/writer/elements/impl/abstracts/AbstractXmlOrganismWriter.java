package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCelltypeWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCompartmentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlTissueWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class forPSI-XML 2.5 writer for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlOrganismWriter implements PsiXmlElementWriter<Organism> {

    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlElementWriter<CvTerm> tissueWriter;
    private PsiXmlElementWriter<CvTerm> compartmentWriter;
    private PsiXmlElementWriter<CvTerm> cellTypeWriter;

    public AbstractXmlOrganismWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlOrganismWriter");
        }
        this.streamWriter = writer;
        this.aliasWriter = new XmlAliasWriter(writer);
        this.tissueWriter = new XmlTissueWriter(writer);
        this.compartmentWriter = new XmlCompartmentWriter(writer);
        this.cellTypeWriter = new XmlCelltypeWriter(writer);
    }

    public AbstractXmlOrganismWriter(XMLStreamWriter writer, PsiXmlElementWriter<Alias> aliasWriter,
                                     PsiXmlElementWriter<CvTerm> cellTypeWriter, PsiXmlElementWriter<CvTerm> compartmentWriter,
                                     PsiXmlElementWriter<CvTerm> tissueWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlOrganismWriter");
        }
        this.streamWriter = writer;
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
        this.tissueWriter = tissueWriter != null ? tissueWriter : new XmlTissueWriter(writer);
        this.compartmentWriter = compartmentWriter != null ? compartmentWriter : new XmlCompartmentWriter(writer);
        this.cellTypeWriter = cellTypeWriter != null ? cellTypeWriter : new XmlCelltypeWriter(writer);
    }

    @Override
    public void write(Organism object) throws MIIOException {
        try {
            // write start
            writeStartOrganism();

            // write taxid
            this.streamWriter.writeAttribute("ncbiTaxId", Integer.toString(object.getTaxId()));

            // write names
            boolean hasShortLabel = object.getCommonName() != null;
            boolean hasExperimentFullLabel = object.getScientificName() != null;
            boolean hasAliases = !object.getAliases().isEmpty();
            if (hasShortLabel || hasExperimentFullLabel || hasAliases){
                this.streamWriter.writeStartElement("names");
                // write shortname
                if (hasShortLabel){
                    this.streamWriter.writeStartElement("shortLabel");
                    this.streamWriter.writeCharacters(object.getCommonName());
                    this.streamWriter.writeEndElement();
                }
                // write fullname
                if (hasExperimentFullLabel){
                    this.streamWriter.writeStartElement("fullName");
                    this.streamWriter.writeCharacters(object.getScientificName());
                    this.streamWriter.writeEndElement();
                }
                // write aliases
                for (Alias alias : object.getAliases()){
                    this.aliasWriter.write(alias);
                }
                // write end names
                this.streamWriter.writeEndElement();
            }

            // write celltype
            if (object.getCellType() != null){
                this.cellTypeWriter.write(object.getCellType());
            }
            //write compartment
            if (object.getCompartment() != null){
                this.compartmentWriter.write(object.getCompartment());
            }
            // write tissue
            if (object.getTissue()!= null){
                this.tissueWriter.write(object.getTissue());
            }

            // write other properties
            writeOtherProperties(object);

            // write end organism
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the db reference : "+object.toString(), e);
        }
    }

    protected abstract void writeOtherProperties(Organism object) throws XMLStreamException;

    protected abstract void writeStartOrganism() throws XMLStreamException;

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
