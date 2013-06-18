package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * This factory allows to create a proper interactor depending on the type
 * and xrefs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class InteractorFactory {

    private Map<CvTerm, String> deterministicInteractorMap;

    public InteractorFactory(){
        // the map will check id and name with default cv term comparator
        deterministicInteractorMap = new TreeMap<CvTerm, String>(new DefaultCvTermComparator());
        initialiseDeterministicInteractorMap();
    }

    /**
     * Return the proper instance of the interactor if the type is recognized. It returns null otherwise.
     * @param type
     * @param name
     * @return the proper instance of the interactor if the type is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromInteractorType(CvTerm type, String name){

        if (this.deterministicInteractorMap.containsKey(type)){
            String recognizedType = this.deterministicInteractorMap.get(type);

            return createInteractorFromRecognizedCategory(recognizedType, name, type);
        }

        return null;
    }

    /**
     * Return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     * @param database
     * @param name
     * @return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromOnDatabase(CvTerm database, String name){

        if (this.deterministicInteractorMap.containsKey(database)){
            String recognizedType = this.deterministicInteractorMap.get(database);

            return createInteractorFromRecognizedCategory(recognizedType, name, database);
        }

        return null;
    }

    public Protein createProtein(String name, CvTerm type){
        return new DefaultProtein(name, type);
    }

    public NucleicAcid createNucleicAcid(String name, CvTerm type){
        return new DefaultNucleicAcid(name, type);
    }

    public Gene createGene(String name){
        return new DefaultGene(name);
    }

    public Complex createComplex(String name, CvTerm type){
        return new DefaultComplex(name, type);
    }

    public BioactiveEntity createBioactiveEntity(String name, CvTerm type){
        return new DefaultBioactiveEntity(name, type);
    }

    public Polymer createPolymer(String name, CvTerm type){
        return new DefaultPolymer(name, type);
    }

    public InteractorSet createInteractorSet(String name, CvTerm type){
        return new DefaultInteractorSet(name, type);
    }

    public Interactor createInteractor(String name, CvTerm type){
        return new DefaultInteractor(name, type);
    }

    protected void initialiseDeterministicInteractorMap(){
        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(InteractorFactory.class.getResourceAsStream("/interactorType.properties"));
            loadProperties(prop);

            // load the second property file
            prop.clear();
            prop.load(InteractorFactory.class.getResourceAsStream("/interactorDatabase.properties"));
            loadProperties(prop);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void loadProperties(Properties prop) {
        for (Map.Entry<Object, Object> entry : prop.entrySet()){
            CvTerm keyTerm = extractCvTermFromKey((String)entry.getKey());
            deterministicInteractorMap.put(keyTerm, (String)entry.getValue());
        }
    }

    protected CvTerm extractCvTermFromKey(String key){
        if (key.contains("(") && key.contains(")")){
            String[] values = key.split("\\(");
            if (values.length != 2){
                return new DefaultCvTerm(key);
            }
            else {
                return CvTermUtils.createMICvTerm(values[1].replaceAll("\\)", ""), values[0]);
            }
        }
        else{
            return new DefaultCvTerm(key);
        }
    }

    protected Interactor createInteractorFromRecognizedCategory(String category, String name, CvTerm type){

        if (category != null){
            if (Protein.class.getCanonicalName().equals(category)){
                return createProtein(name, type);
            }
            else if (Gene.class.getCanonicalName().equals(category)){
                return createGene(name);
            }
            else if (NucleicAcid.class.getCanonicalName().equals(category)){
                return createNucleicAcid(name, type);
            }
            else if (BioactiveEntity.class.getCanonicalName().equals(category)){
                return createBioactiveEntity(name, type);
            }
            else if (Complex.class.getCanonicalName().equals(category)){
                return createComplex(name, type);
            }
            else if (Polymer.class.getCanonicalName().equals(category)){
                return createPolymer(name, type);
            }
            else if (InteractorSet.class.getCanonicalName().equals(category)){
                return createInteractorSet(name, type);
            }
            else{
                return createInteractor(name, type);
            }
        }

        return createInteractor(name, type);
    }

    protected Map<CvTerm, String> getDeterministicInteractorMap() {
        return deterministicInteractorMap;
    }
}
