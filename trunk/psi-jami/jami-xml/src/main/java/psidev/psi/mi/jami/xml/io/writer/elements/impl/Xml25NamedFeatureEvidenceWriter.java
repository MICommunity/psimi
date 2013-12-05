package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25FeatureEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for a feature evidence (with feature detection method) and aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25NamedFeatureEvidenceWriter extends AbstractXml25FeatureEvidenceWriter {
    private PsiXml25ElementWriter<Alias> aliasWriter;
    public Xml25NamedFeatureEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public Xml25NamedFeatureEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                           PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                           PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> featureTypeWriter,
                                           PsiXml25ElementWriter<CvTerm> detectionMethodWriter,PsiXml25ElementWriter<Range> rangeWriter,
                                           PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, detectionMethodWriter, rangeWriter, attributeWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
    }

    @Override
    protected void writeNames(FeatureEvidence object) throws XMLStreamException {
        NamedFeature namedFeature = (NamedFeature)object;
        boolean hasShortLabel = namedFeature.getShortName() != null;
        boolean hasFullLabel = namedFeature.getFullName() != null;
        boolean hasAliases = !namedFeature.getAliases().isEmpty();
        if (hasShortLabel || hasFullLabel || hasAliases){
            getStreamWriter().writeStartElement("names");
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(namedFeature.getShortName());
                getStreamWriter().writeEndElement();
            }
            // write fullname
            if (hasFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(namedFeature.getFullName());
                getStreamWriter().writeEndElement();
            }
            // write aliases
            for (Object alias : namedFeature.getAliases()){
                this.aliasWriter.write((Alias)alias);
            }
            // write end names
            getStreamWriter().writeEndElement();
        }
    }
}
