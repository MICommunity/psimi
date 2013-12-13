package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.impl.MIFileParserLogger;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger for MitabParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/13</pre>
 */

public class PsiXmlParserLogger extends MIFileParserLogger implements PsiXmlParserListener {
    private static final Logger logger = Logger.getLogger("PsiXmlParserLogger");

    public void onUnresolvedReference(XmlIdReference ref, String message) {
        logger.log(Level.SEVERE, "Unresolved reference: "+ref.toString()+ ", "+message);
    }

    @Override
    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        logger.log(Level.WARNING, "Found " + organisms + " host organisms attached to the same experiment. "+locator.toString());
    }

    @Override
    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        logger.log(Level.WARNING, "Found " + organisms + " host organisms attached to the same participant. "+locator.toString());
    }

    @Override
    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator) {
        logger.log(Level.WARNING, "Found " + roles + " experimental roles attached to the same participant. "+locator.toString());
    }

    @Override
    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator) {
        logger.log(Level.WARNING, "Found " + experiments + " experiments attached to the same interaction. "+locator.toString());
    }
}
