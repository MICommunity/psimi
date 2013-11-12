package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * Xml25 writer for publications (bibref objects)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25PublicationWriter implements PsiXml25ElementWriter<Publication>{
    private XMLStreamWriter2 streamWriter;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;

    public Xml25PublicationWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25PublicationWriter");
        }
        this.streamWriter = writer;
        this.primaryRefWriter = new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = new Xml25SecondaryXrefWriter(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
    }

    public Xml25PublicationWriter(XMLStreamWriter2 writer, PsiXml25XrefWriter primaryRefWriter,
                                  PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Annotation> attributeWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25PublicationWriter");
        }
        this.streamWriter = writer;
        this.primaryRefWriter = primaryRefWriter!=null ? primaryRefWriter :new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter!=null ? secondaryRefWriter : new Xml25SecondaryXrefWriter(writer);
        this.attributeWriter = attributeWriter!=null ? attributeWriter : new Xml25AnnotationWriter(writer);
    }

    @Override
    public void write(Publication object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("bibref");
            // write xref
            if (!object.getIdentifiers().isEmpty()){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                writeXrefFromPublicationIdentifiers(object);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            else {
                boolean hasTitle = object.getTitle() != null;
                boolean hasJournal = object.getJournal() != null;
                boolean hasPublicationDate = object.getPublicationDate() != null;
                boolean hasCurationDepth = !CurationDepth.undefined.equals(object.getCurationDepth());
                boolean hasAuthors = !object.getAuthors().isEmpty();
                boolean hasAttributes = !object.getAnnotations().isEmpty();
                // write attributes if no identifiers available
                if (hasTitle || hasJournal || hasPublicationDate || hasCurationDepth || hasAuthors || hasAttributes){
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    // write start attribute list
                    this.streamWriter.writeStartElement("attributeList");
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    // write publication properties such as title, journal, etc..
                    writePublicationPropertiesAsAttributes(object, hasTitle, hasJournal, hasPublicationDate, hasCurationDepth, hasAuthors);
                    // write normal attributes
                    if (hasAttributes){
                        for (Annotation ann : object.getAnnotations()){
                            this.attributeWriter.write(ann);
                            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        }
                    }
                    // write end attributeList
                    this.streamWriter.writeEndElement();
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write xref if no identifiers and no attributes available
                else if (!object.getXrefs().isEmpty()){
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    writeXrefFromPublicationXrefs(object);
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
            }

            // write end publication
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the publication : "+object.toString(), e);
        }
    }

    protected void writePublicationPropertiesAsAttributes(Publication object, boolean hasTitle, boolean hasJournal, boolean hasPublicationDate, boolean hasCurationDepth, boolean hasAuthors) throws XMLStreamException {
        if (hasTitle){
            writeAnnotation(Annotation.PUBLICATION_TITLE, Annotation.PUBLICATION_TITLE_MI, object.getTitle());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        if (hasJournal){
            writeAnnotation(Annotation.PUBLICATION_JOURNAL, Annotation.PUBLICATION_JOURNAL_MI, object.getJournal());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        if (hasPublicationDate){
            writeAnnotation(Annotation.PUBLICATION_YEAR, Annotation.PUBLICATION_YEAR_MI, PsiXml25Utils.YEAR_FORMAT.format(object.getPublicationDate()));
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        if (hasCurationDepth){
            writeAnnotation(Annotation.CURATION_DEPTH, Annotation.CURATION_DEPTH_MI, object.getCurationDepth().toString());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        if (hasAuthors){
            writeAnnotation(Annotation.CURATION_DEPTH, Annotation.CURATION_DEPTH_MI, object.getCurationDepth().toString());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeAnnotation(String name, String nameAc, String value) throws XMLStreamException {
        // write start
        this.streamWriter.writeStartElement("attribute");
        // write topic (not null)
        this.streamWriter.writeAttribute("name", name);
        if (nameAc != null){
            this.streamWriter.writeAttribute("nameAc", nameAc);
        }
        // write description (not null)
        this.streamWriter.writeCharacters(value);

        // write end attribute
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromPublicationXrefs(Publication object) throws XMLStreamException {
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

    protected void writeXrefFromPublicationIdentifiers(Publication object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        String pubmed = object.getPubmedId();
        String doi = object.getDoi();
        Xref pubmedXref = null;
        Xref doiXref = null;
        if (pubmed != null || doi != null){
            for (Xref ref : object.getIdentifiers()){
                // ignore pubmed that is already written
                if (pubmed != null && pubmed.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, Xref.PUBMED_MI, Xref.PUBMED)){
                    pubmed = null;
                    pubmedXref = ref;
                }
                // ignore doi that is already writter
                else if (doi != null && doi.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, Xref.DOI_MI, Xref.DOI)){
                    doi = null;
                    doiXref = ref;
                }
            }
        }

        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (pubmedXref != null){
            writePrimaryRef(this.primaryRefWriter, pubmedXref);
            hasWrittenPrimaryRef = true;
            if (doiXref != null){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                writePrimaryRef(this.secondaryRefWriter, doiXref);
            }
        }
        else if (doiXref != null){
            writePrimaryRef(this.primaryRefWriter, doiXref);
            hasWrittenPrimaryRef = true;
        }

        // write secondaryRefs (and primary ref if not done already)
        int index = 0;
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        // default qualifier is identity
        this.primaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.primaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);
        this.secondaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.secondaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // ignore pubmed that is already written
            // ignore doi that is already written
            if (ref != pubmedXref && ref != doiXref){
                if (index == 0 && !hasWrittenPrimaryRef){
                    hasWrittenPrimaryRef = true;
                    this.primaryRefWriter.write(ref);
                }
                else{
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    this.secondaryRefWriter.write(ref);
                }
            }
            index++;
        }
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            this.secondaryRefWriter.setDefaultRefType(null);
            this.secondaryRefWriter.setDefaultRefTypeAc(null);
            String imexId = object.getImexId();
            for (Xref ref : object.getXrefs()){
                // ignore imex id!
                if (imexId != null && ref.getId().equals(imexId)
                        && XrefUtils.isXrefFromDatabase(ref, Xref.IMEX_MI, Xref.IMEX)
                        && XrefUtils.doesXrefHaveQualifier(ref, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    continue;
                }
                else{
                    this.secondaryRefWriter.write(ref);
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writePrimaryRef(PsiXml25XrefWriter writer, Xref ref) throws XMLStreamException {
        writer.setDefaultRefType(Xref.PRIMARY);
        writer.setDefaultRefTypeAc(Xref.PRIMARY_MI);
        writer.write(ref);
    }
}
