package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.impl.DefaultPreassemby;
import psidev.psi.mi.jami.xml.PsiXml25IdIndex;
import psidev.psi.mi.jami.xml.reference.AbstractComplexReference;

/**
 * Xml implementation of preassembly
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class XmlPreAssembly extends DefaultPreassemby implements FileSourceContext{
    private PsiXmLocator sourceLocator;

    public XmlPreAssembly(CvTerm outcome) {
        super(outcome);
    }

    public XmlPreAssembly(CvTerm outcome, CvTerm response) {
        super(outcome, response);
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
        return "Pre-assembly: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    public void addAffectedInteractionRef(int affectedInteraction, PsiXmLocator locator){
        getAffectedInteractions().add(new ModelledInteractionRef(affectedInteraction, locator));
    }

    /**
     * interaction ref for affected cooperative interaction
     */
    private class ModelledInteractionRef extends AbstractComplexReference {
        private PsiXmLocator sourceLocator;

        public ModelledInteractionRef(int ref, PsiXmLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXml25IdIndex parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Object object = parsedObjects.get(this.ref);
                // convert interaction evidence in a complex
                if (object instanceof XmlInteractionEvidence){
                    ModelledInteraction interaction = new XmlInteractionEvidenceComplexWrapper((XmlInteractionEvidence)object);
                    getAffectedInteractions().remove(this);
                    getAffectedInteractions().add(interaction);
                    return true;
                }
                // wrap modelled interaction
                else if (object instanceof ModelledInteraction){
                    getAffectedInteractions().remove(this);
                    getAffectedInteractions().add((ModelledInteraction)object);
                }
                // wrap basic interaction
                else if (object instanceof XmlBasicInteraction){
                    ModelledInteraction interaction = new XmlBasicInteractionComplexWrapper((XmlBasicInteraction)object);
                    getAffectedInteractions().remove(this);
                    getAffectedInteractions().add(interaction);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Affected modelled interaction Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
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
