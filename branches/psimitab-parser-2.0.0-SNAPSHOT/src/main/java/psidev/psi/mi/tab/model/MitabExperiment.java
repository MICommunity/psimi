package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
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

public class MitabExperiment extends DefaultExperiment implements FileSourceContext{

    private MitabPublication mitabPublication;

    /**
     * Detection method for that interaction.
     */
    private List<CrossReference> detectionMethods;

    private FileSourceLocator locator;

    /**
     * Organism where the interaction happens.
     */
    private Organism hostOrganism;

    public MitabExperiment(){
        super(new MitabPublication(), CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
        processNewInteractionDetectionMethodsList(getInteractionDetectionMethod());

        getPublication().getExperiments().add(this);
        mitabPublication = (MitabPublication) getPublication();
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getDetectionMethods() {
        if (detectionMethods == null){
           detectionMethods = new DetectionMethodsList();
        }
        return detectionMethods;
    }

    public FileSourceLocator getSourceLocator() {
        return locator;
    }

    public void setLocator(FileSourceLocator locator) {
        this.locator = locator;
        this.mitabPublication.setLocator(locator);
    }

    /**
     * {@inheritDoc}
     */
    public void setDetectionMethods(List<CrossReference> detectionMethods) {
        if (detectionMethods != null && !detectionMethods.isEmpty()) {
            ((DetectionMethodsList)getDetectionMethods()).clearOnly();
            this.detectionMethods.addAll(detectionMethods);
        }
        else {
            getDetectionMethods().clear();
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
        if (!getDetectionMethods().isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(detectionMethods), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getInteractionDetectionMethod().setShortName(name);
                getInteractionDetectionMethod().setFullName(name);
            }
        }
    }

    protected void resetInteractionDetectionMethodNameFromFirstReferences(){
        if (!getDetectionMethods().isEmpty()){
            Iterator<CrossReference> methodsIterator = detectionMethods.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getInteractionDetectionMethod().setShortName(name != null ? name : "unknown");
            getInteractionDetectionMethod().setFullName(name != null ? name : "unknown");
        }
    }

    @Override
    public void setInteractionDetectionMethod(CvTerm method) {
        super.setInteractionDetectionMethod(method);
        processNewInteractionDetectionMethodsList(method);
    }

    protected void setInteractionDetectionMethodOnly(CvTerm method) {
        super.setInteractionDetectionMethod(method);
    }

    private void processNewInteractionDetectionMethodsList(CvTerm method) {
        ((DetectionMethodsList)getDetectionMethods()).clearOnly();
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
            if (getPublication() != null){
                getPublication().getExperiments().remove(this);
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

            if (getInteractionDetectionMethod() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setInteractionDetectionMethodOnly(new DefaultCvTerm(name, name, added));
            }
            // it was a UNSPECIFIED method, needs to clear it
            else if ((size() == 1 || size() == 2) && Experiment.UNSPECIFIED_METHOD.equalsIgnoreCase(getInteractionDetectionMethod().getShortName().trim()) && (added.getText() == null || !Experiment.UNSPECIFIED_METHOD.equalsIgnoreCase(added.getText()))){
                // remove unspecified method
                CrossReference old = new CrossReferenceImpl(CvTerm.PSI_MI, Experiment.UNSPECIFIED_METHOD_MI, Experiment.UNSPECIFIED_METHOD);
                removeOnly(old);

                String name = added.getText() != null ? added.getText() : "unknown";

                setInteractionDetectionMethodOnly(new DefaultCvTerm(name, name, added));
            }
            else {
                getInteractionDetectionMethod().getXrefs().add(added);
                // reset shortname
                if (getInteractionDetectionMethod().getMIIdentifier() != null && getInteractionDetectionMethod().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getInteractionDetectionMethod().setShortName(name);
                    }
                    else {
                        resetInteractionDetectionMethodNameFromMiReferences();
                        if (getInteractionDetectionMethod().getShortName().equals("unknown")){
                            resetInteractionDetectionMethodNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getInteractionDetectionMethod() != null){
                getInteractionDetectionMethod().getXrefs().remove(removed);

                if (removed.getText() != null && getInteractionDetectionMethod().getShortName().equals(removed.getText())){
                    if (getInteractionDetectionMethod().getMIIdentifier() != null){
                        resetInteractionDetectionMethodNameFromMiReferences();
                        if (getInteractionDetectionMethod().getShortName().equals("unknown")){
                            resetInteractionDetectionMethodNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractionDetectionMethodNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setInteractionDetectionMethodOnly(CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
                processNewInteractionDetectionMethodsList(getInteractionDetectionMethod());
            }
        }

        @Override
        protected void clearProperties() {
            setInteractionDetectionMethodOnly(CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
            processNewInteractionDetectionMethodsList(getInteractionDetectionMethod());
        }
    }
}
