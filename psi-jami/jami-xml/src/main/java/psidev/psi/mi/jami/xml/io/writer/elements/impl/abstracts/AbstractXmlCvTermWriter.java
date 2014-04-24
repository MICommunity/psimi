package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlPrimaryXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlSecondaryXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Abstract class for CvTerm writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */
public abstract class AbstractXmlCvTermWriter implements PsiXmlElementWriter<CvTerm> {
    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter primaryRefWriter;
    private PsiXmlXrefWriter secondaryRefWriter;

    public AbstractXmlCvTermWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlCvTermWriter");
        }
        this.streamWriter = writer;
    }

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (aliasWriter == null){
            aliasWriter = new XmlAliasWriter(streamWriter);
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
    }

    public PsiXmlXrefWriter getPrimaryRefWriter() {
        if (this.primaryRefWriter == null){
           this.primaryRefWriter = new XmlPrimaryXrefWriter(streamWriter);
        }
        return primaryRefWriter;
    }

    public void setPrimaryRefWriter(PsiXmlXrefWriter primaryRefWriter) {
        this.primaryRefWriter = primaryRefWriter;
    }

    public PsiXmlXrefWriter getSecondaryRefWriter() {
        if (this.secondaryRefWriter == null){
            this.secondaryRefWriter = new XmlSecondaryXrefWriter(streamWriter);
        }
        return secondaryRefWriter;
    }

    public void setSecondaryRefWriter(PsiXmlXrefWriter secondaryRefWriter) {
        this.secondaryRefWriter = secondaryRefWriter;
    }

    @Override
    public void write(CvTerm object) throws MIIOException {
        try {
            // write start
            writeStartCvTerm();

            // write names
            boolean hasShortLabel = object.getShortName() != null;
            boolean hasFullLabel = object.getFullName() != null;
            boolean hasAliases = !object.getSynonyms().isEmpty();
            if (hasShortLabel || hasFullLabel || hasAliases){
                this.streamWriter.writeStartElement("names");
                // write shortname
                if (hasShortLabel){
                    this.streamWriter.writeStartElement("shortLabel");
                    this.streamWriter.writeCharacters(object.getShortName());
                    this.streamWriter.writeEndElement();
                }
                // write fullname
                if (hasFullLabel){
                    this.streamWriter.writeStartElement("fullName");
                    this.streamWriter.writeCharacters(object.getFullName());
                    this.streamWriter.writeEndElement();
                }
                // write aliases
                for (Alias alias : object.getSynonyms()){
                    getAliasWriter().write(alias);
                }
                // write end names
                this.streamWriter.writeEndElement();
            }

            // write Xref
            if (!object.getIdentifiers().isEmpty()){
                writeXrefFromCvIdentifiers(object);
            }
            else if (!object.getXrefs().isEmpty()){
                writeXrefFromCvXrefs(object);
            }

            // write other properties
            writeOtherProperties(object);

            // write end cv term
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the Cv term : "+object.toString(), e);
        }
    }

    protected abstract void writeStartCvTerm() throws XMLStreamException;
    protected abstract void writeOtherProperties(CvTerm term) throws XMLStreamException;

    protected void writeXrefFromCvXrefs(CvTerm object) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        getPrimaryRefWriter().setDefaultRefType(null);
        getPrimaryRefWriter().setDefaultRefTypeAc(null);
        getSecondaryRefWriter().setDefaultRefType(null);
        getSecondaryRefWriter().setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                getPrimaryRefWriter().write(ref);
                index++;
            }
            // write secondaryref
            else{
                getSecondaryRefWriter().write(ref);
                index++;
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromCvIdentifiers(CvTerm object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // all these xrefs are identity
        getPrimaryRefWriter().setDefaultRefType(Xref.IDENTITY);
        getPrimaryRefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);
        getSecondaryRefWriter().setDefaultRefType(Xref.IDENTITY);
        getSecondaryRefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);

        String mi = object.getMIIdentifier();
        String mod = object.getMODIdentifier();
        String par = object.getPARIdentifier();
        Xref miXref = null;
        Xref modXref = null;
        Xref parXref = null;
        if (mi != null || mod != null || par != null){
            for (Xref ref : object.getIdentifiers()){
                // ignore mi that is already written
                if (mi != null && mi.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
                    mi = null;
                    miXref = ref;
                }
                // ignore mod that is already written
                else if (mod != null && mod.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD)){
                    mod = null;
                    modXref = ref;
                }
                // ignore par that is already written
                else if (par != null && par.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, null, CvTerm.PSI_PAR)){
                    par = null;
                    parXref = ref;
                }
            }
        }

        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (miXref != null){
            getPrimaryRefWriter().write(miXref);
            hasWrittenPrimaryRef = true;
            if (modXref != null){
                getSecondaryRefWriter().write(modXref);
            }
            if (parXref != null){
                getSecondaryRefWriter().write(parXref);
            }
        }
        else if (modXref != null){
            getPrimaryRefWriter().write(modXref);
            hasWrittenPrimaryRef = true;
            if (parXref != null){
                getSecondaryRefWriter().write(parXref);
            }
        }
        else if (parXref != null){
            getPrimaryRefWriter().write(parXref);
            hasWrittenPrimaryRef = true;
        }

        // write secondaryRefs (and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // ignore mi that is already written
            // ignore mod that is already written
            // ignore par that is already written
            if (ref != miXref && ref != modXref && ref != parXref){
                if (!hasWrittenPrimaryRef){
                    hasWrittenPrimaryRef = true;
                    getPrimaryRefWriter().write(ref);
                }
                else{
                    getSecondaryRefWriter().write(ref);
                }
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            getSecondaryRefWriter().setDefaultRefType(null);
            getSecondaryRefWriter().setDefaultRefTypeAc(null);
            for (Xref ref : object.getXrefs()){
                getSecondaryRefWriter().write(ref);
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
