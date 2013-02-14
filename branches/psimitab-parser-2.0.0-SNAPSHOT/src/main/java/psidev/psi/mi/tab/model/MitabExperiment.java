package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.PublicationCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Default MITAB experiment implementation which is a patch for backward compatibility.
 * It only contains experiment information such as interaction detection method and host organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class MitabExperiment extends DefaultExperiment{

    private MitabPublication mitabPublication;

    /**
     * Detection method for that interaction.
     */
    private List<CrossReference> detectionMethods
            = new DetectionMethodsList();

    /**
     * Organism where the interaction happens.
     */
    private Organism hostOrganism;

    public MitabExperiment(){
        super(new MitabPublication(), CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
        processNewInteractionDetectionMethodsList(interactionDetectionMethod);

        publication.getExperiments().add(this);
        mitabPublication = (MitabPublication) publication;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getDetectionMethods() {
        return detectionMethods;
    }

    /**
     * {@inheritDoc}
     */
    public void setDetectionMethods(List<CrossReference> detectionMethods) {

        if (detectionMethods != null) {
            ((DetectionMethodsList)this.detectionMethods).clearOnly();
            this.detectionMethods.addAll(detectionMethods);
        }
        else {
            this.detectionMethods.clear();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Organism getHostOrganism() {
        return hostOrganism;
    }

    /**
     * {@inheritDoc}
     */
    public void setHostOrganism(Organism hostOrganism) {
        this.hostOrganism = hostOrganism;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasHostOrganism() {
        return hostOrganism != null;
    }

    public MitabPublication getMitabPublication() {
        return mitabPublication;
    }

    protected void resetInteractionDetectionMethodNameFromMiReferences(){
        if (!detectionMethods.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(detectionMethods), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                interactionDetectionMethod.setShortName(name);
                interactionDetectionMethod.setFullName(name);
            }
        }
    }

    protected void resetInteractionDetectionMethodNameFromFirstReferences(){
        if (!detectionMethods.isEmpty()){
            Iterator<CrossReference> methodsIterator = detectionMethods.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            interactionDetectionMethod.setShortName(name != null ? name : "unknown");
            interactionDetectionMethod.setFullName(name != null ? name : "unknown");
        }
    }

    @Override
    public void setInteractionDetectionMethod(CvTerm method) {
        super.setInteractionDetectionMethod(method);
        processNewInteractionDetectionMethodsList(method);
    }

    private void processNewInteractionDetectionMethodsList(CvTerm method) {
        ((DetectionMethodsList)detectionMethods).clearOnly();
        if (method.getMIIdentifier() != null){
            ((DetectionMethodsList)detectionMethods).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, method.getMIIdentifier(), method.getFullName() != null ? method.getFullName() : method.getShortName()));
        }
        else{
            if (!method.getIdentifiers().isEmpty()){
                Xref ref = method.getIdentifiers().iterator().next();
                ((DetectionMethodsList)detectionMethods).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), method.getFullName() != null ? method.getFullName() : method.getShortName()));
            }
            else {
                ((DetectionMethodsList)detectionMethods).addOnly(new CrossReferenceImpl("unknown", "-", method.getFullName() != null ? method.getFullName() : method.getShortName()));
            }
        }
    }

    @Override
    public void setPublication(Publication publication) {
        if (publication == null){
            if (this.publication != null){
                this.publication.getExperiments().remove(this);
            }

            super.setPublication(null);
            this.mitabPublication = null;
        }
        else if (publication instanceof MitabPublication){

            super.setPublication(publication);
            this.mitabPublication = (MitabPublication) publication;
        }
        else {

            MitabPublication convertedPublication = new MitabPublication();

            PublicationCloner.copyAndOverridePublicationProperties(publication, convertedPublication);
            mitabPublication = convertedPublication;
            super.setPublication(convertedPublication);
        }
    }

    @Override
    public void setPublicationAndAddExperiment(Publication publication) {
        if (publication == null){

            super.setPublication(null);
            this.mitabPublication = null;
        }
        else if (publication instanceof MitabPublication){

            super.setPublication(publication);
            this.mitabPublication = (MitabPublication) publication;
            publication.getExperiments().add(this);
        }
        else {

            MitabPublication convertedPublication = new MitabPublication();

            PublicationCloner.copyAndOverridePublicationProperties(publication, convertedPublication);
            mitabPublication = convertedPublication;
            super.setPublication(convertedPublication);

            publication.getExperiments().add(this);
        }
    }

    protected class DetectionMethodsList extends AbstractListHavingPoperties<CrossReference> {
        public DetectionMethodsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (interactionDetectionMethod == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                interactionDetectionMethod = new DefaultCvTerm(name, name, added);
            }
            // it was a UNSPECIFIED method, needs to clear it
            else if (size() > 1 && Experiment.UNSPECIFIED_METHOD.equalsIgnoreCase(interactionDetectionMethod.getShortName().trim())){
                // remove unspecified method
                CrossReference old = new CrossReferenceImpl(CvTerm.PSI_MI, Experiment.UNSPECIFIED_METHOD_MI, Experiment.UNSPECIFIED_METHOD);
                removeOnly(old);
                interactionDetectionMethod.getXrefs().remove(old);

                // reset shortname
                if (interactionDetectionMethod.getMIIdentifier() != null && interactionDetectionMethod.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        interactionDetectionMethod.setShortName(name);
                    }
                    else {
                        resetInteractionDetectionMethodNameFromMiReferences();
                        if (interactionDetectionMethod.getShortName().equals("unknown")){
                            resetInteractionDetectionMethodNameFromFirstReferences();
                        }
                    }
                }
            }
            else {
                interactionDetectionMethod.getXrefs().add(added);
                // reset shortname
                if (interactionDetectionMethod.getMIIdentifier() != null && interactionDetectionMethod.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        interactionDetectionMethod.setShortName(name);
                    }
                    else {
                        resetInteractionDetectionMethodNameFromMiReferences();
                        if (interactionDetectionMethod.getShortName().equals("unknown")){
                            resetInteractionDetectionMethodNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (interactionDetectionMethod != null){
                interactionDetectionMethod.getXrefs().remove(removed);

                if (removed.getText() != null && interactionDetectionMethod.getShortName().equals(removed.getText())){
                    if (interactionDetectionMethod.getMIIdentifier() != null){
                        resetInteractionDetectionMethodNameFromMiReferences();
                        if (interactionDetectionMethod.getShortName().equals("unknown")){
                            resetInteractionDetectionMethodNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractionDetectionMethodNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                interactionDetectionMethod = CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI);
                processNewInteractionDetectionMethodsList(interactionDetectionMethod);
            }
        }

        @Override
        protected void clearProperties() {
            interactionDetectionMethod = CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI);
            processNewInteractionDetectionMethodsList(interactionDetectionMethod);
        }
    }
}
