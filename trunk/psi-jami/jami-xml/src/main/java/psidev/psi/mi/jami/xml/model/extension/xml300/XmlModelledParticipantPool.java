package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipantCandidate;
import psidev.psi.mi.jami.model.ModelledParticipantPool;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlParticipant;
import psidev.psi.mi.jami.xml.model.extension.XmlCvTerm;
import psidev.psi.mi.jami.xml.model.extension.XmlModelledParticipant;

import javax.xml.bind.annotation.*;
import java.util.Collection;

/**
 * Xml implementation of participant pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/09/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://psi.hupo.org/mi/mif300")
public class XmlModelledParticipantPool extends AbstractXmlParticipantPool<ModelledInteraction, ModelledFeature, ModelledParticipantCandidate>
implements ModelledParticipantPool{

    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlModelledParticipantPool() {
        super();
    }

    public XmlModelledParticipantPool(AbstractXmlParticipant<ModelledInteraction, ModelledFeature> delegate) {
        super(delegate);
    }

    @Override
    protected void initialiseDefaultDelegate() {
        super.setDelegate(new XmlModelledParticipant());
    }

    @Override
    @XmlElement(type=XmlCvTerm.class, name="moleculeSetType", required = true)
    public void setJAXBType(XmlCvTerm type) {
        super.setJAXBType(type);
    }

    @Override
    @XmlElement(name = "interactorCandidate", type = XmlModelledParticipantCandidate.class, required = true)
    public Collection<ModelledParticipantCandidate> getJAXBInteractorCandidates() {
        return super.getJAXBInteractorCandidates();
    }
}
