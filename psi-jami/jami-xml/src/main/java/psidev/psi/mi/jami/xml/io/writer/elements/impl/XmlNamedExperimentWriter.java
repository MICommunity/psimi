package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlPublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 experiment writer for a named experiment having shortlabel, fullname and aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlNamedExperimentWriter extends XmlExperimentWriter {
    private PsiXmlElementWriter<Alias> aliasWriter;

    public XmlNamedExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
        this.aliasWriter = new XmlAliasWriter(writer);
    }

    public XmlNamedExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                    PsiXmlElementWriter<Alias> aliasWriter, PsiXmlPublicationWriter publicationWriter,
                                    PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                    PsiXmlElementWriter<Organism> hostOrganismWriter, PsiXmlElementWriter<CvTerm> detectionMethodWriter,
                                    PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, publicationWriter, primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter,
                confidenceWriter, attributeWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
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
            getStreamWriter().writeStartElement("names");
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(xmlExperiment.getShortName());
                getStreamWriter().writeEndElement();
            }
            // write fullname
            if (hasExperimentFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlExperiment.getFullName());
                getStreamWriter().writeEndElement();
            }
            else if (hasPublicationTitle){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlExperiment.getPublication().getTitle());
                getStreamWriter().writeEndElement();
            }
            // write aliases
            for (Alias alias : xmlExperiment.getAliases()){
                this.aliasWriter.write(alias);
            }
            // write end names
            getStreamWriter().writeEndElement();
        }
    }
}
