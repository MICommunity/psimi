package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * CvTerm writers for cvs without attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */
public class XmlCvTermWriter implements PsiXmlVariableNameWriter<CvTerm> {
    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter xrefWriter;

    public XmlCvTermWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlCvTermWriter");
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

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
           this.xrefWriter = new XmlDbXrefWriter(streamWriter);
        }
        return xrefWriter;
    }

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    @Override
    public void write(CvTerm object, String name) throws MIIOException {
        try {
            // write start
            writeStartCvTerm(name);

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

    protected void writeStartCvTerm(String name) throws XMLStreamException{
        getStreamWriter().writeStartElement(name);
    }
    protected void writeOtherProperties(CvTerm term) throws XMLStreamException{
        // nothing to do here
    }

    protected void writeXrefFromCvXrefs(CvTerm object) throws XMLStreamException {
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

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromCvIdentifiers(CvTerm object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // all these xrefs are identity
        getXrefWriter().setDefaultRefType(Xref.IDENTITY);
        getXrefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);

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
            getXrefWriter().write(miXref,"primaryRef");
            hasWrittenPrimaryRef = true;
            if (modXref != null){
                getXrefWriter().write(modXref,"secondaryRef");
            }
            if (parXref != null){
                getXrefWriter().write(parXref,"secondaryRef");
            }
        }
        else if (modXref != null){
            getXrefWriter().write(modXref,"primaryRef");
            hasWrittenPrimaryRef = true;
            if (parXref != null){
                getXrefWriter().write(parXref,"secondaryRef");
            }
        }
        else if (parXref != null){
            getXrefWriter().write(parXref,"primaryRef");
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

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
