package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlXref;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Xml interactor writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlInteractorWriter implements PsiXmlElementWriter<Interactor> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter xrefWriter;
    private PsiXmlVariableNameWriter<CvTerm> interactorTypeWriter;
    private PsiXmlElementWriter<Organism> organismWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Checksum> checksumWriter;

    public XmlInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlInteractorWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlInteractorWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
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

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
            initialiseXrefWriter();

        }
        return xrefWriter;
    }

    protected void initialiseXrefWriter() {
        this.xrefWriter = new XmlDbXrefWriter(streamWriter);
    }

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    public PsiXmlVariableNameWriter<CvTerm> getInteractorTypeWriter() {
        if (this.interactorTypeWriter == null){
            initialiseInteractorTypeWriter();

        }
        return interactorTypeWriter;
    }

    protected void initialiseInteractorTypeWriter() {
        this.interactorTypeWriter = new XmlCvTermWriter(streamWriter);
    }

    public void setInteractorTypeWriter(PsiXmlVariableNameWriter<CvTerm> interactorTypeWriter) {
        this.interactorTypeWriter = interactorTypeWriter;
    }

    public PsiXmlElementWriter<Organism> getOrganismWriter() {
        if (this.organismWriter == null){
            initialiseOrganismWriter();

        }
        return organismWriter;
    }

    protected void initialiseOrganismWriter() {
        this.organismWriter = new XmlOrganismWriter(streamWriter);
    }

    public void setOrganismWriter(PsiXmlElementWriter<Organism> organismWriter) {
        this.organismWriter = organismWriter;
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

    public PsiXmlElementWriter<Checksum> getChecksumWriter() {
        if (this.checksumWriter == null){
            this.checksumWriter = new XmlChecksumWriter(streamWriter);
        }
        return checksumWriter;
    }

    public void setChecksumWriter(PsiXmlElementWriter<Checksum> checksumWriter) {
        this.checksumWriter = checksumWriter;
    }

    @Override
    public void write(Interactor object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("interactor");
            int id = this.objectIndex.extractIdForInteractor(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));

            // write names
            this.streamWriter.writeStartElement("names");
            // write shortname
            if (object.getShortName() != null){
                this.streamWriter.writeStartElement("shortLabel");
                this.streamWriter.writeCharacters(object.getShortName());
                this.streamWriter.writeEndElement();
            }
            // write fullname
            if (object.getFullName() != null){
                this.streamWriter.writeStartElement("fullName");
                this.streamWriter.writeCharacters(object.getFullName());
                this.streamWriter.writeEndElement();
            }
            // write aliases
            for (Alias alias : object.getAliases()){
                getAliasWriter().write(alias);
            }
            // write end names
            this.streamWriter.writeEndElement();

            // write Xref
            if (!object.getIdentifiers().isEmpty()){
                writeXrefFromInteractorIdentifiers(object);
            }
            else if (!object.getXrefs().isEmpty()){
                writeXrefFromInteractorXrefs(object);
            }
            // write set members if interactor pool
            else {
                writeOtherSetMembers(object, true, true);
            }

            // write interactor type
           getInteractorTypeWriter().write(object.getInteractorType(), "interactorType");

            // write organism
            if (object.getOrganism() != null){
                getOrganismWriter().write(object.getOrganism());
            }

            // write sequence
            processSequence(object);

            // write attributes
            if (!object.getAnnotations().isEmpty()){
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Annotation ann : object.getAnnotations()){
                    getAttributeWriter().write(ann);
                }
                for (Checksum c : object.getChecksums()){
                    getChecksumWriter().write(c);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
            }
            // write checksum
            else if (!object.getChecksums().isEmpty()){
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Checksum c : object.getChecksums()){
                    getChecksumWriter().write(c);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
            }

            // write end interactor
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the interactor : "+object.toString(), e);
        }
    }

    protected void processSequence(Interactor object) throws XMLStreamException {
        if (object instanceof Polymer){
            writePolymerSequence((Polymer) object);
        }
        else if (object instanceof InteractorPool){
            InteractorPool pool = (InteractorPool)object;

            // only write interactor set sequence if all interactors are from same type and have same sequence
            if (!pool.isEmpty()){
                String sequence = null;
                Iterator<Interactor> iterator = pool.iterator();
                Interactor subInteractor = iterator.next();
                if (subInteractor instanceof Polymer){
                    sequence = ((Polymer) subInteractor).getSequence();
                    if (sequence != null){
                        while (iterator.hasNext()){
                            subInteractor = iterator.next();
                            if (subInteractor instanceof Polymer){
                                String seq = ((Polymer) subInteractor).getSequence();
                                if (seq == null || !seq.equals(sequence)){
                                    break;
                                }
                            }

                            if (!iterator.hasNext()){
                                writePolymerSequence((Polymer)subInteractor);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void writePolymerSequence(Polymer object) throws XMLStreamException {
        Polymer pol = object;
        if (pol.getSequence() != null){
            this.streamWriter.writeStartElement("sequence");
            this.streamWriter.writeCharacters(pol.getSequence());
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeXrefFromInteractorXrefs(Interactor object) throws XMLStreamException {
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
                getXrefWriter().write(ref,"primaryRef");
                index++;
            }
            // write secondaryref
            else{
                getXrefWriter().write(ref,"secondaryRef");
                index++;
            }
        }

        // write set members if interactor pool
        writeOtherSetMembers(object, false, false);

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromInteractorIdentifiers(Interactor object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // all these xrefs are identity
        getXrefWriter().setDefaultRefType(Xref.IDENTITY);
        getXrefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);

        Xref primaryRef = object.getPreferredIdentifier();
        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (primaryRef != null){
            getXrefWriter().write(primaryRef,"primaryRef");
            hasWrittenPrimaryRef = true;
        }

        // write secondaryRefs (and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // ignore preferred identifier that is already written
            if (ref != primaryRef){
                if (!hasWrittenPrimaryRef){
                    hasWrittenPrimaryRef = true;
                    getXrefWriter().write(ref,"primaryRef");
                }
                else{
                    getXrefWriter().write(ref,"secondaryRef");
                }
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            getXrefWriter().setDefaultRefType(null);
            getXrefWriter().setDefaultRefTypeAc(null);
            for (Xref ref : object.getXrefs()){
                getXrefWriter().write(ref,"secondaryRef");
            }
        }

        // write set members if interactor pool
        writeOtherSetMembers(object, false, false);

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeOtherSetMembers(Interactor object, boolean needToWriteXref, boolean needToWritePrimaryRef) throws XMLStreamException {
        // write components of set
        if (object instanceof InteractorPool){
            InteractorPool pool = (InteractorPool)object;

            if (!pool.isEmpty()){
                // write xref if necessary
                if (needToWriteXref){
                    this.streamWriter.writeStartElement("xref");
                }

                boolean isFirst = needToWritePrimaryRef;
                // default qualifier is set member
                getXrefWriter().setDefaultRefType(Xref.INTERACTOR_SET_QUALIFIER);
                getXrefWriter().setDefaultRefTypeAc(Xref.INTERACTOR_SET_QUALIFIER_MI);
                for (Interactor interactor : pool){
                    Xref preferredIdentifier = interactor.getPreferredIdentifier();

                    if (preferredIdentifier != null && isFirst){
                        isFirst = false;
                        getXrefWriter().write(new XmlXref(preferredIdentifier.getDatabase(), preferredIdentifier.getId(),
                                preferredIdentifier.getVersion()),"primaryRef");
                    }
                    else if (preferredIdentifier != null){
                        getXrefWriter().write(new XmlXref(preferredIdentifier.getDatabase(), preferredIdentifier.getId(),
                                preferredIdentifier.getVersion()),"secondaryRef");
                    }
                }

                // write end xref if necessary
                if (needToWriteXref){
                    this.streamWriter.writeEndElement();
                }
            }
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }
}
