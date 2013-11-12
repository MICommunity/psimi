package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.extension.ExperimentalCvTerm;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for experimental cv terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml25ExperimentalCvTermWriter extends AbstractXml25CvTermWriter {
    public AbstractXml25ExperimentalCvTermWriter(XMLStreamWriter2 writer) {
        super(writer);
    }

    public AbstractXml25ExperimentalCvTermWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
    }

    @Override
    protected void writeOtherProperties(CvTerm term) throws XMLStreamException {

        if (term instanceof ExperimentalCvTerm){
            ExperimentalCvTerm expCv = (ExperimentalCvTerm) term;
            if (!expCv.getExperiments().isEmpty()){

            }
        }
    }
}
