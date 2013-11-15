package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25IdIndex;
import psidev.psi.mi.jami.xml.reference.AbstractFeatureRef;

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
    private PsiXmLocator sourceLocator;

    public XmlFeatureModificationEffector(int feature, PsiXmLocator locator){
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
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmLocator locator) {
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
    private class FeatureEffectorRef extends AbstractFeatureRef<ModelledEntity, ModelledFeature> implements ModelledFeature {
        private PsiXmLocator sourceLocator;

        public FeatureEffectorRef(int ref, PsiXmLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXml25IdIndex parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Object object = parsedObjects.get(this.ref);
                // convert feature evidence in a modelled feature
                if (object instanceof FeatureEvidence){
                    feature = new XmlFeatureEvidenceWrapper((FeatureEvidence)object, null);
                    return true;
                }
                // use modelled feature
                else if (object instanceof ModelledFeature){
                    feature = (ModelledFeature)object;
                }
                // wrap basic feature
                else if (object instanceof Feature){
                    feature = new XmlFeatureWrapper((Feature)object, null);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Allosteric feature effector Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        public FileSourceLocator getSourceLocator() {
            return this.sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        public void setSourceLocator(PsiXmLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }
    }

}
