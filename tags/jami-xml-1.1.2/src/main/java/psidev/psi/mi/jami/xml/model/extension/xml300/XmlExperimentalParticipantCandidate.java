package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlEntity;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlLocator;
import psidev.psi.mi.jami.xml.model.extension.XmlFeatureEvidence;
import psidev.psi.mi.jami.xml.model.extension.XmlInteractor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Xml implementation of ParticipantEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://psi.hupo.org/mi/mif300")
public class XmlExperimentalParticipantCandidate extends AbstractXmlEntity<FeatureEvidence> implements
        ExperimentalParticipantCandidate {

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private ExperimentalParticipantPool poolParent;

    public XmlExperimentalParticipantCandidate() {
        super();
    }

    public XmlExperimentalParticipantCandidate(Interactor interactor) {
        super(interactor);
    }

    public XmlExperimentalParticipantCandidate(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    @Override
    @XmlElement(name = "interactor", namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBInteractor(XmlInteractor interactor) {
        super.setJAXBInteractor(interactor);
    }

    @Override
    @XmlElement(name = "interactorRef", namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBInteractorRef(Integer value) {
        super.setJAXBInteractorRef(value);
    }

    @XmlAttribute(name = "id", required = true, namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBId(int value) {
        super.setId(value);
    }

    @XmlElement(name = "featureList", namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBFeatureWrapper(JAXBFeatureWrapper jaxbFeatureWrapper) {
        super.setFeatureWrapper(jaxbFeatureWrapper);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), getId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void processAddedFeature(FeatureEvidence feature) {
        // nothing to do
    }

    @Override
    protected void initialiseFeatureWrapper() {
        super.setFeatureWrapper(new JAXBFeatureWrapper());
    }

    @Override
    public ExperimentalParticipantPool getParentPool() {
        return poolParent;
    }

    @Override
    public void setParentPool(ExperimentalParticipantPool pool) {
        this.poolParent = pool;
    }

    ////////////////////////////////////////////////////// classes
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntityFeatureWrapper", namespace = "http://psi.hupo.org/mi/mif300")
    public static class JAXBFeatureWrapper extends AbstractXmlEntity.JAXBFeatureWrapper<FeatureEvidence> {

        public JAXBFeatureWrapper(){
            super();
        }

        @XmlElement(type=XmlFeatureEvidence.class, name="feature", required = true, namespace = "http://psi.hupo.org/mi/mif300")
        public List<FeatureEvidence> getJAXBFeatures() {
            return super.getJAXBFeatures();
        }
    }
}
