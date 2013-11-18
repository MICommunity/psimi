package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25AliasWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Abstract class for writing named features having aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25NamedFeatureWriter<F extends Feature> extends AbstractXml25FeatureWriter<F>{
    private PsiXml25ElementWriter<Alias> aliasWriter;
    public AbstractXml25NamedFeatureWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex);
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public AbstractXml25NamedFeatureWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                              PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                              PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                              PsiXml25ElementWriter<Range> rangeWriter, PsiXml25ElementWriter<Alias> aliasWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter, rangeWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
    }

    @Override
    protected void writeNames(F object) throws XMLStreamException {
        NamedFeature namedFeature = (NamedFeature)object;
        boolean hasShortLabel = namedFeature.getShortName() != null;
        boolean hasFullLabel = namedFeature.getFullName() != null;
        boolean hasAliases = !namedFeature.getAliases().isEmpty();
        if (hasShortLabel || hasFullLabel || hasAliases){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("names");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(namedFeature.getShortName());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write fullname
            if (hasFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(namedFeature.getFullName());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write aliases
            for (Object alias : namedFeature.getAliases()){
                this.aliasWriter.write((Alias)alias);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end names
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}
