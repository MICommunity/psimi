package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.reference.AbstractFeatureRef;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of feature modification effector
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */
@XmlTransient
public class XmlFeatureModificationEffector implements FeatureModificationEffector, FileSourceContext {
    private ModelledFeature feature;
    private PsiXmlLocator sourceLocator;

    public XmlFeatureModificationEffector(int feature, PsiXmlLocator locator){
        this.feature = new FeatureEffectorRef(feature, locator);
    }

    public ModelledFeature getFeatureModification() {
        return feature;
    }

    public AllostericEffectorType getEffectorType() {
        return AllostericEffectorType.feature_modification;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmlLocator locator) {
        this.sourceLocator = locator;
    }

    @Override
    public String toString() {
        return "Allosteric PTM effector: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    ////////////////////////////////// inner classes
    /**
     * feature ref for allosteric effector
     */
    private class FeatureEffectorRef extends AbstractFeatureRef<ModelledParticipant, ModelledFeature> implements ModelledFeature {
        private PsiXmlLocator sourceLocator;

        public FeatureEffectorRef(int ref, PsiXmlLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsFeature(this.ref)){
                Feature object = parsedObjects.getFeature(this.ref);
                if (object == null){
                    return false;
                }
                // convert feature evidence in a modelled feature
                else if (object instanceof FeatureEvidence){
                    feature = new XmlFeatureEvidenceWrapper((FeatureEvidence)object, null);
                    return true;
                }
                // use modelled feature
                else if (object instanceof ModelledFeature){
                    feature = (ModelledFeature)object;
                    return true;
                }
                // wrap basic feature
                else {
                    feature = new XmlFeatureWrapper(object, null);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Allosteric feature effector Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        @Override
        protected void initialiseFeatureDelegate() {
            XmlModelledFeature modelled = new XmlModelledFeature();
            modelled.setId(this.ref);
            setDelegate(modelled);
        }

        public FileSourceLocator getSourceLocator() {
            return this.sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        public void setSourceLocator(PsiXmlLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }
    }

}
