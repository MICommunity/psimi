package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * PSI-XML 2.5 experiment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25ExperimentWriter implements PsiXml25ElementWriter<Experiment>{
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;
    private PsiXml25ElementWriter<Alias> aliasWriter;
    private PsiXml25ElementWriter<Publication> publicationWriter;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;

    public Xml25ExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ExperimentWriter");
        }
        this.streamWriter = writer;
        if (writer == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ExperimentWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    @Override
    public void write(Experiment object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("experimentDescription");
            int id = this.objectIndex.extractIdFor(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));

            boolean hasPublicationTitle = object.getPublication() != null && object.getPublication().getTitle() != null;
            // if experiment is an extended experiment
            if (object instanceof ExtendedPsi25Experiment){
                ExtendedPsi25Experiment xmlExperiment = (ExtendedPsi25Experiment)object;
                // write names
                boolean hasShortLabel = xmlExperiment.getShortLabel() != null;
                boolean hasExperimentFullLabel = xmlExperiment.getShortLabel() != null;
                boolean hasAliases = !xmlExperiment.getAliases().isEmpty();
                if (hasShortLabel || hasExperimentFullLabel || hasPublicationTitle || hasAliases){
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    this.streamWriter.writeStartElement("names");
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    // write shortname
                    if (hasShortLabel){
                        this.streamWriter.writeStartElement("shortLabel");
                        this.streamWriter.writeCharacters(xmlExperiment.getShortLabel());
                        this.streamWriter.writeEndElement();
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                    // write fullname
                    if (hasExperimentFullLabel){
                        this.streamWriter.writeStartElement("fullName");
                        this.streamWriter.writeCharacters(xmlExperiment.getFullName());
                        this.streamWriter.writeEndElement();
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                    else if (hasPublicationTitle){
                        this.streamWriter.writeStartElement("fullName");
                        this.streamWriter.writeCharacters(xmlExperiment.getPublication().getTitle());
                        this.streamWriter.writeEndElement();
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                    // write aliases
                    for (Alias alias : xmlExperiment.getAliases()){
                        this.aliasWriter.write(alias);
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                    // write end names
                    this.streamWriter.writeEndElement();
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
                // write publication and xrefs
                writePublicationAndXrefs(object);
            }
            // process normal fields of MI experiment
            else{
                // write names
                if (hasPublicationTitle){
                    writeNames(object);
                }
                // write publication and xrefs
                writePublicationAndXrefs(object);
            }

            // write end experiment
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the experiment : "+object.toString(), e);
        }
    }

    protected void writePublicationAndXrefs(Experiment object) throws XMLStreamException {
        String imexId=null;
        // write publication
        Publication publication = object.getPublication();
        if (publication != null){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.publicationWriter.write(publication);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            imexId = publication.getImexId();
        }
        // write xrefs
        if (!object.getXrefs().isEmpty() || imexId != null){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start xref
            this.streamWriter.writeStartElement("xref");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            if (!object.getXrefs().isEmpty()){
                writeXrefFromExperimentXrefs(object, imexId);
            }
            else{
                writeImexId("primaryRef", imexId);
            }
            // write end xref
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeXrefFromExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);

        int index = 0;
        boolean foundImexId = false;
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

            // found IMEx id
            if (imexId != null && imexId.equals(ref.getId())
                    && XrefUtils.isXrefFromDatabase(ref, Xref.IMEX_MI, Xref.IMEX)
                    && XrefUtils.doesXrefHaveQualifier(ref, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                foundImexId=true;
            }
        }

        // write imex id
        if (!foundImexId && imexId != null){
            writeImexId("secondaryRef", imexId);
        }
    }

    protected void writeImexId(String nodeName, String imexId) throws XMLStreamException {
        // write start
        this.streamWriter.writeStartElement(nodeName);
        // write database
        this.streamWriter.writeAttribute("db", Xref.IMEX);
        this.streamWriter.writeAttribute("dbAc", Xref.IMEX_MI);
        // write id
        this.streamWriter.writeAttribute("id", imexId);
        // write qualifier
        this.streamWriter.writeAttribute("refType", Xref.IMEX_PRIMARY);
        this.streamWriter.writeAttribute("refTypeAc", Xref.IMEX_PRIMARY_MI);
        // write end db ref
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeNames(Experiment object) throws XMLStreamException {
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.streamWriter.writeStartElement("names");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // write fullname
        this.streamWriter.writeStartElement("fullName");
        this.streamWriter.writeCharacters(object.getPublication().getTitle());
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // write end names
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }
}
