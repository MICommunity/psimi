package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * Xml25 interactor writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25InteractorWriter implements PsiXml25ElementWriter<Interactor>{
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;
    private PsiXml25ElementWriter<Alias> aliasWriter;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<CvTerm> interactorTypeWriter;
    private PsiXml25ElementWriter<Organism> organismWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<Checksum> checksumWriter;

    public Xml25InteractorWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25InteractorWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25InteractorWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.aliasWriter = new Xml25AliasWriter(writer);
        this.primaryRefWriter = new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = new Xml25SecondaryXrefWriter(writer);
        this.interactorTypeWriter = new Xml25InteractorTypeWriter(writer);
        this.organismWriter = new Xml25OrganismWriter(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
        this.checksumWriter = new Xml25ChecksumWriter(writer);
    }

    public Xml25InteractorWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                 PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                 PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> interactorTypeWriter,
                                 PsiXml25ElementWriter<Organism> organismWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                 PsiXml25ElementWriter<Checksum> checksumWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25InteractorWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ExperimentWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new Xml25SecondaryXrefWriter(writer);
        this.interactorTypeWriter = interactorTypeWriter != null ? interactorTypeWriter : new Xml25InteractorTypeWriter(writer);
        this.organismWriter = organismWriter != null ? organismWriter : new Xml25OrganismWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new Xml25AnnotationWriter(writer);
        this.checksumWriter = checksumWriter != null ? checksumWriter : new Xml25ChecksumWriter(writer);
    }

    @Override
    public void write(Interactor object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("interactor");
            int id = this.objectIndex.extractIdFor(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));

            // write names
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("names");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            if (object.getShortName() != null){
                this.streamWriter.writeStartElement("shortLabel");
                this.streamWriter.writeCharacters(object.getShortName());
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write fullname
            if (object.getFullName() != null){
                this.streamWriter.writeStartElement("fullName");
                this.streamWriter.writeCharacters(object.getFullName());
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

            // write Xref
            if (!object.getIdentifiers().isEmpty()){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                writeXrefFromInteractorIdentifiers(object);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            else if (!object.getXrefs().isEmpty()){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                writeXrefFromInteractorXrefs(object);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write interactor type
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.interactorTypeWriter.write(object.getInteractorType());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

            // write organism
            if (object.getOrganism() != null){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.organismWriter.write(object.getOrganism());
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write sequence
            if (object instanceof Polymer){
                Polymer pol = (Polymer) object;
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.streamWriter.writeStartElement("sequence");
                this.streamWriter.writeCharacters(pol.getSequence());
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write attributes
            if (!object.getAnnotations().isEmpty()){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Annotation ann : object.getAnnotations()){
                    this.attributeWriter.write(ann);
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                for (Checksum c : object.getChecksums()){
                    this.checksumWriter.write(c);
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write checksum
            else if (!object.getChecksums().isEmpty()){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Checksum c : object.getChecksums()){
                    this.checksumWriter.write(c);
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write end interactor
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the interactor : "+object.toString(), e);
        }
    }

    protected void writeXrefFromInteractorXrefs(Interactor object) throws XMLStreamException {
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

    protected void writeXrefFromInteractorIdentifiers(Interactor object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // all these xrefs are identity
        this.primaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.primaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);
        this.secondaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.secondaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);

        Xref primaryRef = object.getPreferredIdentifier();
        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (primaryRef != null){
            this.primaryRefWriter.write(primaryRef);
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
            for (Xref ref : object.getXrefs()){
                this.secondaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }
}
