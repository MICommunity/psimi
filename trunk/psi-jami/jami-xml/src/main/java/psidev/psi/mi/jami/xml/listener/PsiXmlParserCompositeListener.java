package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.impl.MIFileParserCompositeListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;

import java.util.Collection;

/**
 * This class contains several MI file listeners
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/12/13</pre>
 */

public class PsiXmlParserCompositeListener extends MIFileParserCompositeListener<PsiXmlParserListener> implements PsiXmlParserListener {

    public PsiXmlParserCompositeListener(){
        super();
    }


    @Override
    public void onUnresolvedReference(XmlIdReference ref, String message) {
        for (PsiXmlParserListener delegate : getDelegates()){
            delegate.onUnresolvedReference(ref, message);
        }
    }

    @Override
    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        for (PsiXmlParserListener delegate : getDelegates()){
            delegate.onSeveralHostOrganismFound(organisms, locator);
        }
    }

    @Override
    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        for (PsiXmlParserListener delegate : getDelegates()){
            delegate.onSeveralExpressedInOrganismFound(organisms, locator);
        }
    }

    @Override
    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator) {
        for (PsiXmlParserListener delegate : getDelegates()){
            delegate.onSeveralExperimentalRolesFound(roles, locator);
        }
    }

    @Override
    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator) {
        for (PsiXmlParserListener delegate : getDelegates()){
            delegate.onSeveralExperimentsFound(experiments, locator);
        }
    }
}
