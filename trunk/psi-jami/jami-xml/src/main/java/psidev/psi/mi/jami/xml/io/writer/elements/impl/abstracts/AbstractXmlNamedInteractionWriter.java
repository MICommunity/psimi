package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.NamedInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlExperiment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Date;

/**
 * Abstract class for named interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlNamedInteractionWriter<I extends Interaction, P extends Participant> extends AbstractXmlInteractionWriter<I,P> {

    private PsiXmlElementWriter<Alias> aliasWriter;

    public AbstractXmlNamedInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
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
    protected void writeNames(I object) throws XMLStreamException {
        if (object instanceof NamedInteraction){
            NamedInteraction xmlInteraction = (NamedInteraction) object;
            // write names
            boolean hasShortLabel = xmlInteraction.getShortName() != null;
            boolean hasInteractionFullLabel = xmlInteraction.getFullName() != null;
            boolean hasAliases = !xmlInteraction.getAliases().isEmpty();
            if (hasShortLabel || hasInteractionFullLabel || hasAliases){
                getStreamWriter().writeStartElement("names");
                // write shortname
                if (hasShortLabel){
                    getStreamWriter().writeStartElement("shortLabel");
                    getStreamWriter().writeCharacters(xmlInteraction.getShortName());
                    getStreamWriter().writeEndElement();
                }
                // write fullname
                if (hasInteractionFullLabel){
                    getStreamWriter().writeStartElement("fullName");
                    getStreamWriter().writeCharacters(xmlInteraction.getFullName());
                    getStreamWriter().writeEndElement();
                }
                // write aliases
                for (Object alias : xmlInteraction.getAliases()){
                    getAliasWriter().write((Alias)alias);
                }
                // write end names
                getStreamWriter().writeEndElement();
            }
            else{
                super.writeNames(object);
            }
        }
        else{
            super.writeNames(object);
        }
    }

    @Override
    protected void initialiseDefaultExperiment(){
        setDefaultExperiment(new XmlExperiment(new DefaultPublication("Mock publication for interactions that do not have experimental details.",(String)null,(Date)null)));
    }
}
