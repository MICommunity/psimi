package psidev.psi.mi.jami.bridges.ontologymanager;

import psidev.psi.mi.jami.bridges.ontologymanager.impl.OntologyTermWrapper;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.local.MILocalOntology;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.ols.MIOlsOntology;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.tools.ontology_manager.OntologyManagerTemplate;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * JAMI extension of the ontologyManager
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/11/11</pre>
 */

public class MIOntologyManager extends OntologyManagerTemplate<MIOntologyTermI, MIOntologyAccess> {

    private static final String FILE_SOURCE = "file";
    private static final String OLS_SOURCE = "ols";

    private Map<String,String> dbMap;
    private Map<String,String> dbMIMap;
    private Map<String,Pattern> dbRegexp;
    private Map<String,String> dbParents;

    public MIOntologyManager(InputStream configFile) throws OntologyLoaderException {
        super(configFile);
    }

    @Override
    protected MIOntologyAccess findOntologyAccess(String sourceURI, String ontologyId, String ontologyName, String ontologyVersion, String format, String loaderClass) throws ClassNotFoundException {
        initialiseDbMap();

        try{
            if (FILE_SOURCE.equalsIgnoreCase(loaderClass)){
                MILocalOntology localOntology = new MILocalOntology(dbMap.get(ontologyId), dbMIMap.get(ontologyId),
                        dbRegexp.get(ontologyId), dbParents.get(ontologyId));
                return localOntology;
            }
            else if (OLS_SOURCE.equalsIgnoreCase(loaderClass)){
                MIOlsOntology olsOntology = new MIOlsOntology(dbMap.get(ontologyId), dbMIMap.get(ontologyId),
                        dbRegexp.get(ontologyId), dbParents.get(ontologyId));

                return olsOntology;
            }
            else {
                throw new IllegalArgumentException("The loader class in the configuration class must be file or ols.");
            }
        }catch (OntologyLoaderException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initialiseDbMap(){
        if (dbMap == null){
            dbMap = new HashMap<String, String>();
            dbMap.put("MI", CvTerm.PSI_MI);
            dbMap.put("MOD", CvTerm.PSI_MOD);
            dbMap.put("ECO", "evidence ontology");
        }
        if (dbMIMap == null){
            dbMIMap = new HashMap<String, String>();
            dbMIMap.put("MI", CvTerm.PSI_MI_MI);
            dbMIMap.put("MOD", CvTerm.PSI_MOD_MI);
            dbMIMap.put("ECO", "MI:1331");
        }
        if (dbRegexp == null){
            dbRegexp = new HashMap<String, Pattern>();
            dbRegexp.put("MI", OntologyTermWrapper.MI_REGEXP);
            dbRegexp.put("MOD", OntologyTermWrapper.MOD_REGEXP);
            dbRegexp.put("ECO", OntologyTermWrapper.ECO_REGEXP);
        }
        if (dbParents == null){
            dbParents = new HashMap<String, String>();
            dbParents.put("MOD", "MI:0252");
            dbParents.put("ECO", "MI:1331");
        }

    }
}
