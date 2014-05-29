package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlHostOrganismWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlExperimentWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * PSI-XML 2.5 experiment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlExperimentWriter extends AbstractXmlExperimentWriter {

    public XmlExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherAttributes(Experiment object, boolean needToWriteAttributeList) throws XMLStreamException {
        // write annotations from publication
        if (object.getPublication() != null){
            Publication pub = object.getPublication();
            // write all attributes from publication if identifiers are not empty.
            // if the list of identifiers of a publication is not empty, annotations of a publication are not exported
            // in bibref elements
            if (!pub.getIdentifiers().isEmpty() && needToWriteAttributeList){
                getPublicationWriter().writeAllPublicationAttributes(pub);
            }
            else if (!pub.getIdentifiers().isEmpty() && !needToWriteAttributeList){
                getPublicationWriter().writeAllPublicationAttributes(pub, object.getAnnotations());
            }
        }
    }

    @Override
    protected void writeExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        // write xrefs
        if (!object.getXrefs().isEmpty() || imexId != null){
            // write start xref
            getStreamWriter().writeStartElement("xref");
            if (!object.getXrefs().isEmpty()){
                writeXrefFromExperimentXrefs(object, imexId);
            }
            else{
                writeImexId("primaryRef", imexId);
            }
            // write end xref
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeXrefFromExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        getXrefWriter().setDefaultRefType(null);
        getXrefWriter().setDefaultRefTypeAc(null);

        int index = 0;
        boolean foundImexId = false;
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
        getStreamWriter().writeStartElement(nodeName);
        // write database
        getStreamWriter().writeAttribute("db", Xref.IMEX);
        getStreamWriter().writeAttribute("dbAc", Xref.IMEX_MI);
        // write id
        getStreamWriter().writeAttribute("id", imexId);
        // write qualifier
        getStreamWriter().writeAttribute("refType", Xref.IMEX_PRIMARY);
        getStreamWriter().writeAttribute("refTypeAc", Xref.IMEX_PRIMARY_MI);
        // write end db ref
        getStreamWriter().writeEndElement();
    }

    protected void initialisePublicationWriter() {
        super.setPublicationWriter(new XmlPublicationWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseHostOrganismWriter() {
        super.setHostOrganismWriter(new XmlHostOrganismWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseConfidenceWriter() {
        super.setConfidenceWriter(new XmlConfidenceWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseDetectionMethodWriter() {
        super.setDetectionMethodWriter(new XmlCvTermWriter(getStreamWriter()));
    }
}
