package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Abstract writer for Xml25Feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25FeatureWriter<F extends Feature> implements PsiXml25ElementWriter<F>{
    private XMLStreamWriter streamWriter;
    private PsiXml25ObjectCache objectIndex;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<CvTerm> featureTypeWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<Range> rangeWriter;
    private PsiXml25ElementWriter<Alias> aliasWriter;

    public AbstractXml25FeatureWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex){
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
        this.aliasWriter = new Xml25AliasWriter(writer);

    }

    protected AbstractXml25FeatureWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                         PsiXml25ElementWriter<Alias> aliasWriter,
                                         PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                         PsiXml25ElementWriter<CvTerm> featureTypeWriter, PsiXml25ElementWriter<Range> rangeWriter,
                                         PsiXml25ElementWriter<Annotation> attributeWriter) {
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
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
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
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                this.attributeWriter.write((Annotation)ann);
            }
            // write interaction dependency
            if (object.getInteractionDependency() != null){
                writeAttribute(object.getInteractionDependency().getShortName(), object.getInteractionDependency().getMIIdentifier(), null);
            }
            // write interaction effect
            if (object.getInteractionEffect() != null){
                writeAttribute(object.getInteractionEffect().getShortName(), object.getInteractionEffect().getMIIdentifier(), null);
            }
            // write participant ref
            if (!object.getRanges().isEmpty()){
                Set<Integer> participantSet = new HashSet<Integer>();
                for (Object obj : object.getRanges()){
                    Range range = (Range)obj;
                    if (range.getParticipant() != null){
                        Integer id = this.objectIndex.extractIdForParticipant(range.getParticipant());
                        if (!participantSet.contains(id)){
                            participantSet.add(id);
                            writeAttribute(CooperativeEffect.PARTICIPANT_REF, CooperativeEffect.PARTICIPANT_REF_ID, Integer.toString(id));
                        }
                    }
                }
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write interaction dependency
        else if (object.getInteractionDependency() != null){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            writeAttribute(object.getInteractionDependency().getShortName(), object.getInteractionDependency().getMIIdentifier(), null);
            // write interaction effect
            if (object.getInteractionEffect() != null){
                writeAttribute(object.getInteractionEffect().getShortName(), object.getInteractionEffect().getMIIdentifier(), null);
            }
            // write participant ref
            if (!object.getRanges().isEmpty()){
                Set<Integer> participantSet = new HashSet<Integer>();
                for (Object obj : object.getRanges()){
                    Range range = (Range)obj;
                    if (range.getParticipant() != null){
                        Integer id = this.objectIndex.extractIdForParticipant(range.getParticipant());
                        if (!participantSet.contains(id)){
                            participantSet.add(id);
                            writeAttribute(CooperativeEffect.PARTICIPANT_REF, CooperativeEffect.PARTICIPANT_REF_ID, Integer.toString(id));
                        }
                    }
                }
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write interaction effect
        else if (object.getInteractionEffect() != null){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            writeAttribute(object.getInteractionEffect().getShortName(), object.getInteractionEffect().getMIIdentifier(), null);
            // write participant ref
            if (!object.getRanges().isEmpty()){
                Set<Integer> participantSet = new HashSet<Integer>();
                for (Object obj : object.getRanges()){
                    Range range = (Range)obj;
                    if (range.getParticipant() != null){
                        Integer id = this.objectIndex.extractIdForParticipant(range.getParticipant());
                        if (!participantSet.contains(id)){
                            participantSet.add(id);
                            writeAttribute(CooperativeEffect.PARTICIPANT_REF, CooperativeEffect.PARTICIPANT_REF_ID, Integer.toString(id));
                        }
                    }
                }
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write participant ref
        else if (!object.getRanges().isEmpty()){

            Set<Integer> participantSet = new HashSet<Integer>();
            for (Object obj : object.getRanges()){
                Range range = (Range)obj;
                if (range.getParticipant() != null){
                    Integer id = this.objectIndex.extractIdForParticipant(range.getParticipant());
                    if (!participantSet.contains(id)){
                        participantSet.add(id);
                    }
                }
            }
            if (!participantSet.isEmpty()){
                // write start attribute list
                getStreamWriter().writeStartElement("attributeList");
                for (Integer id : participantSet){
                    writeAttribute(CooperativeEffect.PARTICIPANT_REF, CooperativeEffect.PARTICIPANT_REF_ID, Integer.toString(id));
                }
                // write end attributeList
                getStreamWriter().writeEndElement();
            }
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
                this.rangeWriter.write((Range)range);
            }
            // write end rangeList
            getStreamWriter().writeEndElement();
        }
    }

    protected abstract void writeOtherProperties(F object) throws XMLStreamException;

    protected void writeFeatureType(F object) throws XMLStreamException {
        if (object.getType() != null){
            this.featureTypeWriter.write(object.getType());
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
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                this.primaryRefWriter.write(ref);
                index++;
            }
            // write secondaryref
            else{
                this.secondaryRefWriter.write(ref);
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
                    this.secondaryRefWriter.write(ref);
                }
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            this.secondaryRefWriter.setDefaultRefType(null);
            this.secondaryRefWriter.setDefaultRefTypeAc(null);
            for (Object o : object.getXrefs()){
                Xref ref = (Xref)o;
                this.secondaryRefWriter.write(ref);
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
                this.aliasWriter.write((Alias)alias);
            }
            // write end names
            getStreamWriter().writeEndElement();
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXml25ObjectCache getObjectIndex() {
        return objectIndex;
    }
}
