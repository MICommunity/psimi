package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AliasWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for writing named features having aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25NamedFeatureWriter<F extends Feature> extends AbstractXml25FeatureWriter<F>{
    private PsiXml25ElementWriter<Alias> aliasWriter;
    public AbstractXml25NamedFeatureWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public AbstractXml25NamedFeatureWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                           PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                           PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> featureTypeWriter,
                                           PsiXml25ElementWriter<Range> rangeWriter, PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
    }

    @Override
    protected void writeNames(F object) throws XMLStreamException {
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
