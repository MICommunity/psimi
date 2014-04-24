package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.NamedExperiment;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 experiment writer for a named experiment having shortlabel, fullname and aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml30NamedExperimentWriter extends Xml30ExperimentWriter {
    private PsiXmlElementWriter<Alias> aliasWriter;

    public Xml30NamedExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (this.aliasWriter == null){
            this.aliasWriter = new XmlAliasWriter(getStreamWriter());
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
    }

    @Override
    protected void writeNames(Experiment object) throws XMLStreamException {
        if (object instanceof  NamedExperiment){
            NamedExperiment xmlExperiment = (NamedExperiment) object;
            // write names
            PsiXmlUtils.writeCompleteNamesForExperiment(xmlExperiment,
                    getStreamWriter(),
                    getAliasWriter());
        }
        else{
            super.writeNames(object);
        }
    }
}
