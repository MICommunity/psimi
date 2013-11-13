package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Abstract class forPSI-XML 2.5 writer for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml25OrganismWriter implements PsiXml25ElementWriter<Organism> {

    private XMLStreamWriter2 streamWriter;
    private PsiXml25ElementWriter<Alias> aliasWriter;
    private PsiXml25ElementWriter<CvTerm> tissueWriter;
    private PsiXml25ElementWriter<CvTerm> compartmentWriter;
    private PsiXml25ElementWriter<CvTerm> cellTypeWriter;

    public AbstractXml25OrganismWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25OrganismWriter");
        }
        this.streamWriter = writer;
        this.aliasWriter = new Xml25AliasWriter(writer);
        this.tissueWriter = new Xml25TissueWriter(writer);
        this.compartmentWriter = new Xml25CompartmentWriter(writer);
        this.cellTypeWriter = new Xml25CelltypeWriter(writer);
    }

    public AbstractXml25OrganismWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Alias> aliasWriter,
                                       PsiXml25ElementWriter<CvTerm> tissueWriter, PsiXml25ElementWriter<CvTerm> compartmentWriter,
                                       PsiXml25ElementWriter<CvTerm> cellTypeWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25OrganismWriter");
        }
        this.streamWriter = writer;
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
        this.tissueWriter = tissueWriter != null ? tissueWriter : new Xml25TissueWriter(writer);
        this.compartmentWriter = compartmentWriter != null ? compartmentWriter : new Xml25CompartmentWriter(writer);
        this.cellTypeWriter = cellTypeWriter != null ? cellTypeWriter : new Xml25CelltypeWriter(writer);
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
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.streamWriter.writeStartElement("names");
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write shortname
                if (hasShortLabel){
                    this.streamWriter.writeStartElement("shortLabel");
                    this.streamWriter.writeCharacters(object.getCommonName());
                    this.streamWriter.writeEndElement();
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write fullname
                if (hasExperimentFullLabel){
                    this.streamWriter.writeStartElement("fullName");
                    this.streamWriter.writeCharacters(object.getScientificName());
                    this.streamWriter.writeEndElement();
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write aliases
                for (Alias alias : object.getAliases()){
                    this.aliasWriter.write(alias);
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write end names
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write celltype
            if (object.getCellType() != null){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.cellTypeWriter.write(object.getCellType());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            //write compartment
            if (object.getCompartment() != null){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.compartmentWriter.write(object.getCompartment());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write tissue
            if (object.getTissue()!= null){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.tissueWriter.write(object.getTissue());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write end organism
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the db reference : "+object.toString(), e);
        }
    }

    protected abstract void writeStartOrganism() throws XMLStreamException;

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }
}
