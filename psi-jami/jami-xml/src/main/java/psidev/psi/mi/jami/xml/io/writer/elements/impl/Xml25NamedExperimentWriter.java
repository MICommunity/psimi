package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25PublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 experiment writer for a named experiment having shortlabel, fullname and aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25NamedExperimentWriter extends Xml25ExperimentWriter {
    private PsiXml25ElementWriter<Alias> aliasWriter;

    public Xml25NamedExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public Xml25NamedExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25PublicationWriter publicationWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter) {
        super(writer, objectIndex, publicationWriter, primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter, attributeWriter, confidenceWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
    }

    @Override
    protected void writeNames(Experiment object) throws XMLStreamException {
        NamedExperiment xmlExperiment = (NamedExperiment) object;
        // write names
        boolean hasShortLabel = xmlExperiment.getShortName() != null;
        boolean hasExperimentFullLabel = xmlExperiment.getFullName() != null;
        boolean hasAliases = !xmlExperiment.getAliases().isEmpty();
        boolean hasPublicationTitle = object.getPublication() != null && object.getPublication().getTitle() != null;
        if (hasShortLabel || hasExperimentFullLabel || hasPublicationTitle || hasAliases){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("names");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(xmlExperiment.getShortName());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write fullname
            if (hasExperimentFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlExperiment.getFullName());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            else if (hasPublicationTitle){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlExperiment.getPublication().getTitle());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write aliases
            for (Alias alias : xmlExperiment.getAliases()){
                this.aliasWriter.write(alias);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end names
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}
