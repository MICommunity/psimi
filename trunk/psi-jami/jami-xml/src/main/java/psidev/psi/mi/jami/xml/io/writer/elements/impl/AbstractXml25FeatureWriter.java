package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * Abstract writer for Xml25Feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25FeatureWriter<F extends Feature> implements PsiXml25ElementWriter<F>{
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<CvTerm> featureTypeWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<Range> rangeWriter;

    public AbstractXml25FeatureWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25FeatureWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25FeatureWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.primaryRefWriter = new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = new Xml25SecondaryXrefWriter(writer);
        this.featureTypeWriter = new Xml25FeatureTypeWriter(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
        this.rangeWriter = new Xml25RangeWriter(writer);

    }

    protected AbstractXml25FeatureWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                         PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                         PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                         PsiXml25ElementWriter<Range> rangeWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25FeatureWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25FeatureWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new Xml25SecondaryXrefWriter(writer);
        this.featureTypeWriter = featureTypeWriter != null ? featureTypeWriter : new Xml25FeatureTypeWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new Xml25AnnotationWriter(writer);
        this.rangeWriter = rangeWriter != null ? rangeWriter : new Xml25RangeWriter(writer);
    }

    @Override
    public void write(F object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("feature");
                // write id attribute
                int id = this.objectIndex.extractIdFor(object);
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
                // write attributes
                writeAttributes(object);
                // write end feature
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the feature : "+object.toString(), e);
            }
        }
    }

    protected void writeAttributes(F object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                this.attributeWriter.write((Annotation)ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeRanges(F object) throws XMLStreamException {
        if (!object .getRanges().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            getStreamWriter().writeStartElement("featureRangeList");
            for (Object range : object.getRanges()){
                this.rangeWriter.write((Range)range);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected abstract void writeOtherProperties(F object) throws XMLStreamException;

    protected void writeFeatureType(F object) throws XMLStreamException {
        if (object.getType() != null){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.featureTypeWriter.write(object.getType());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeXrefs(F object) throws XMLStreamException {
        if (!object.getIdentifiers().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            writeXrefFromFeatureIdentifiers(object);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        else if (!object.getXrefs().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            writeXrefFromFeatureXrefs(object);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeXrefFromFeatureXrefs(F object) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                this.primaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                index++;
            }
            // write secondaryref
            else{
                this.secondaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                index++;
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromFeatureIdentifiers(F object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // all these xrefs are identity
        this.primaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.primaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);
        this.secondaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.secondaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);

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
            this.primaryRefWriter.write(interproXref);
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
                    this.primaryRefWriter.write(ref);
                }
                else{
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    this.secondaryRefWriter.write(ref);
                }
            }
        }
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            this.secondaryRefWriter.setDefaultRefType(null);
            this.secondaryRefWriter.setDefaultRefTypeAc(null);
            for (Object o : object.getXrefs()){
                Xref ref = (Xref)o;
                this.secondaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeNames(F object) throws XMLStreamException {
        boolean hasShortLabel = object.getShortName() != null;
        boolean hasFullLabel = object.getFullName() != null;
        if (hasShortLabel || hasFullLabel){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("names");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            if (hasShortLabel){
                this.streamWriter.writeStartElement("shortLabel");
                this.streamWriter.writeCharacters(object.getShortName());
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write fullname
            if (hasFullLabel){
                this.streamWriter.writeStartElement("fullName");
                this.streamWriter.writeCharacters(object.getFullName());
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end names
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }

    protected PsiXml25ObjectIndex getObjectIndex() {
        return objectIndex;
    }
}
