package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAnnotationWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Abstract writer for Xml25Feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXmlFeatureWriter<F extends Feature> implements PsiXmlElementWriter<F> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlXrefWriter xrefWriter;
    private PsiXmlVariableNameWriter<CvTerm> featureTypeWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Range> rangeWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;

    public AbstractXmlFeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlFeatureWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlFeatureWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;

    }

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
            initialiseXrefWriter();
        }
        return xrefWriter;
    }

    protected abstract void initialiseXrefWriter();

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    public PsiXmlVariableNameWriter<CvTerm> getFeatureTypeWriter() {
        if (this.featureTypeWriter == null){
           initialiseFeatureTypeWriter();
        }
        return featureTypeWriter;
    }

    protected abstract void initialiseFeatureTypeWriter();

    public void setFeatureTypeWriter(PsiXmlVariableNameWriter<CvTerm> featureTypeWriter) {
        this.featureTypeWriter = featureTypeWriter;
    }

    public PsiXmlElementWriter<Annotation> getAttributeWriter() {
        if (this.attributeWriter == null){
            this.attributeWriter = new XmlAnnotationWriter(streamWriter);
        }
        return attributeWriter;
    }

    public void setAttributeWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        this.attributeWriter = attributeWriter;
    }

    public PsiXmlElementWriter<Range> getRangeWriter() {
        if (this.rangeWriter == null){
            initialiseRangeWriter();
        }
        return rangeWriter;
    }

    protected abstract void initialiseRangeWriter();

    public void setRangeWriter(PsiXmlElementWriter<Range> rangeWriter) {
        this.rangeWriter = rangeWriter;
    }

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (this.aliasWriter == null){
            this.aliasWriter = new XmlAliasWriter(streamWriter);
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
    }

    @Override
    public void write(F object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("feature");
                // write id attribute
                int id = this.objectIndex.extractIdForFeature(object);
                this.streamWriter.writeAttribute("id", Integer.toString(id));
                // write names
                writeNames(object);
                // write xref
                writeXrefs(object);
                // write feature type
                writeFeatureType(object);
                // write other properties
                writeOtherProperties(object);
                // write feature ranges
                writeRanges(object);
                // write feature role
                writeFeatureRole(object);
                // parameters
                writeParameters(object);
                // write attributes
                writeAttributes(object);
                // write end feature
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the feature : "+object.toString(), e);
            }
        }
    }

    protected abstract void writeParameters(F object) throws XMLStreamException;

    protected abstract void writeFeatureRole(F object) throws XMLStreamException;

    protected abstract void writeOtherAttributes(F object, boolean writeAttributeList) throws XMLStreamException;

    protected void writeAttributes(F object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation)ann);
            }
            // write interaction dependency
            writeOtherAttributes(object, false);

            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write interaction dependency
        else{
            // write role and participant ref attribute if not null
            writeOtherAttributes(object, true);
        }
    }

    protected void writeAttribute(String name, String nameAc, String description) throws XMLStreamException {
        // write start
        this.streamWriter.writeStartElement("attribute");
        // write topic
        this.streamWriter.writeAttribute("name", name);
        if (nameAc!= null){
            this.streamWriter.writeAttribute("nameAc", nameAc);
        }
        // write description
        if (description != null){
            this.streamWriter.writeCharacters(description);
        }
        // write end attribute
        this.streamWriter.writeEndElement();
    }

    protected void writeRanges(F object) throws XMLStreamException {
        if (!object .getRanges().isEmpty()){
            // write start range list
            getStreamWriter().writeStartElement("featureRangeList");
            for (Object range : object.getRanges()){
                getRangeWriter().write((Range)range);
            }
            // write end rangeList
            getStreamWriter().writeEndElement();
        }
    }

    protected abstract void writeOtherProperties(F object) throws XMLStreamException;

    protected void writeFeatureType(F object) throws XMLStreamException {
        if (object.getType() != null){
            getFeatureTypeWriter().write(object.getType(), "featureType");
        }
    }

    protected void writeXrefs(F object) throws XMLStreamException {
        if (!object.getIdentifiers().isEmpty()){
            writeXrefFromFeatureIdentifiers(object);
        }
        else if (!object.getXrefs().isEmpty()){
            writeXrefFromFeatureXrefs(object);
        }
    }

    protected void writeXrefFromFeatureXrefs(F object) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        getXrefWriter().setDefaultRefType(null);
        getXrefWriter().setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                getXrefWriter().write(ref, "primaryRef");
                index++;
            }
            // write secondaryref
            else{
                getXrefWriter().write(ref, "secondaryRef");
                index++;
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromFeatureIdentifiers(F object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // all these xrefs are identity
        getXrefWriter().setDefaultRefType(Xref.IDENTITY);
        getXrefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);

        String interpro = object.getInterpro();
        Xref interproXref = null;
        if (interpro != null){
            for (Object o : object.getIdentifiers()){
                Xref ref = (Xref)o;
                // ignore interpro that is already written
                if (interpro != null && interpro.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, Xref.INTERPRO_MI, Xref.INTERPRO)){
                    interpro = null;
                    interproXref = ref;
                }
            }
        }

        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (interproXref != null){
            getXrefWriter().write(interproXref, "primaryRef");
            hasWrittenPrimaryRef = true;
        }

        // write secondaryRefs (and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // ignore interpro that is already written
            if (ref != interproXref){
                if (!hasWrittenPrimaryRef){
                    hasWrittenPrimaryRef = true;
                    getXrefWriter().write(ref, "primaryRef");
                }
                else{
                    getXrefWriter().write(ref, "secondaryRef");
                }
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            getXrefWriter().setDefaultRefType(null);
            getXrefWriter().setDefaultRefTypeAc(null);
            for (Object o : object.getXrefs()){
                Xref ref = (Xref)o;
                getXrefWriter().write(ref, "secondaryRef");
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeNames(F object) throws XMLStreamException {
        boolean hasShortLabel = object.getShortName() != null;
        boolean hasFullLabel = object.getFullName() != null;
        boolean hasAliases = !object.getAliases().isEmpty();
        if (hasShortLabel || hasFullLabel || hasAliases){
            getStreamWriter().writeStartElement("names");
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(object.getShortName());
                getStreamWriter().writeEndElement();
            }
            // write fullname
            if (hasFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(object.getFullName());
                getStreamWriter().writeEndElement();
            }
            // write aliases
            for (Object alias : object.getAliases()){
                getAliasWriter().write((Alias)alias);
            }
            // write end names
            getStreamWriter().writeEndElement();
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }
}
