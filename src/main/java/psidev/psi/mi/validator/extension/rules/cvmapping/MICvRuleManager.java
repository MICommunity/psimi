package psidev.psi.mi.validator.extension.rules.cvmapping;

import psidev.psi.tools.cvrReader.mapping.jaxb.CvMapping;
import psidev.psi.tools.cvrReader.mapping.jaxb.CvMappingRule;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.rules.cvmapping.CvRuleManager;

import java.util.List;

/**
 * CvRuleManager extension for MI validator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/03/13</pre>
 */

public class MICvRuleManager extends CvRuleManager {
    public MICvRuleManager(OntologyManager ontoMngr, CvMapping cvMappingRules) {
        super(ontoMngr, cvMappingRules);
    }

    protected void addRules(List<CvMappingRule> cvMappingRules) {

        for (CvMappingRule cvMappingRule : cvMappingRules) {
            MICvRule rule = new MICvRule(getOntologyMngr());
            rule.setCvMappingRule(cvMappingRule);
            addCvRule(rule);
        }
    }
}
