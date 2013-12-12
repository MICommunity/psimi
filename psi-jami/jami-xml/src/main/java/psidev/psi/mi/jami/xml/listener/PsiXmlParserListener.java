package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;

import java.util.Collection;

/**
 * A listener listening to events when parsing a mitab file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface PsiXmlParserListener extends MIFileParserListener{

    public void onUnresolvedReference(XmlIdReference ref, String message);

    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator);

    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator);

    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator);

    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator);
}
