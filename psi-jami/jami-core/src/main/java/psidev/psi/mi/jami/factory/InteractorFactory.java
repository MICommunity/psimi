package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;

import java.io.IOException;
import java.util.*;

/**
 * This factory allows to create a proper interactor depending on the type
 * and xrefs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class InteractorFactory {

    private Map<String, String> deterministicInteractorNameMap;
    private Map<String, String> deterministicInteractorIdMap;

    public InteractorFactory(){

        initialiseDeterministicInteractorMaps();
    }

    /**
     * Return the proper instance of the interactor if the type is recognized and not null. It returns null otherwise.
     * @param type
     * @param name
     * @return the proper instance of the interactor if the type is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromInteractorType(CvTerm type, String name){
        if (type == null){
            return null;
        }

        String typeName = type.getShortName().toLowerCase().trim();
        String typeMI = type.getMIIdentifier();

        if (typeMI != null && this.deterministicInteractorIdMap.containsKey(typeMI)){
            String recognizedType = this.deterministicInteractorIdMap.get(typeMI);

            return createInteractorFromRecognizedCategory(recognizedType, name, type);
        }
        else if (typeMI == null && this.deterministicInteractorNameMap.containsKey(typeName)){
            String recognizedType = this.deterministicInteractorNameMap.get(typeName);

            return createInteractorFromRecognizedCategory(recognizedType, name, type);
        }
        // we have a valid type that is not unknown or null
        else {
            return createInteractor(name, type);
        }
    }

    /**
     * Return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     * @param database
     * @param name
     * @return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromDatabase(CvTerm database, String name){
        if (database == null){
            return null;
        }
        String databaseName = database.getShortName().toLowerCase().trim();
        String databaseMI = database.getMIIdentifier();

        if (databaseMI != null && this.deterministicInteractorIdMap.containsKey(databaseMI)){
            String recognizedType = this.deterministicInteractorIdMap.get(databaseMI);

            return createInteractorFromRecognizedCategory(recognizedType, name, null);
        }
        else if (databaseMI == null && this.deterministicInteractorNameMap.containsKey(databaseName)){
            String recognizedType = this.deterministicInteractorNameMap.get(databaseName);

            return createInteractorFromRecognizedCategory(recognizedType, name, null);
        }

        return null;
    }

    /**
     * Return the proper instance of the interactor if the database is recognized (the interactor will be returned on the first database which is recognized). It returns null otherwise.
     * @param xrefs
     * @param name
     * @return the proper instance of the interactor if the database is recognized (the interactor will be returned on the first database which is recognized). It returns null otherwise.
     */
    public Interactor createInteractorFromIdentityXrefs(Collection<? extends Xref> xrefs, String name){

        Interactor interactor = null;
        Iterator<? extends Xref> xrefsIterator = xrefs.iterator();
        while (interactor == null && xrefsIterator.hasNext()){

            interactor = createInteractorFromDatabase(xrefsIterator.next().getDatabase(), name);
        }

        return interactor;
    }

    /**
     * Creates a new Protein with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Protein createProtein(String name, CvTerm type){
        return new DefaultProtein(name, type);
    }

    /**
     * Creates a new NucleicAcid with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public NucleicAcid createNucleicAcid(String name, CvTerm type){
        return new DefaultNucleicAcid(name, type);
    }

    /**
     * Creates a new Gene with the name
     * @param name
     * @return
     */
    public Gene createGene(String name){
        return new DefaultGene(name);
    }

    /**
     * Creates a new Complex with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Complex createComplex(String name, CvTerm type){
        return new DefaultComplex(name, type);
    }

    /**
     * Creates a new BioactiveEntity with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public BioactiveEntity createBioactiveEntity(String name, CvTerm type){
        return new DefaultBioactiveEntity(name, type);
    }

    /**
     * Creates a new Polymer with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Polymer createPolymer(String name, CvTerm type){
        return new DefaultPolymer(name, type);
    }

    /**
     * Creates a new InteractorSet with name and interactor type
     * @param name
     * @param type
     * @return
     */
    public InteractorSet createInteractorSet(String name, CvTerm type){
        return new DefaultInteractorSet(name, type);
    }

    /**
     * Creates a default interactor from the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Interactor createInteractor(String name, CvTerm type){
        return new DefaultInteractor(name, type);
    }

    /**
     * Loads some properties to recognize interactor type from different MI terms
     */
    protected void initialiseDeterministicInteractorMaps(){
        deterministicInteractorNameMap = new HashMap<String, String>();
        deterministicInteractorIdMap = new HashMap<String, String>();
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

    /**
     * Loads the properties in the deterministicInteractorMap
     * @param prop
     */
    protected void loadProperties(Properties prop) {
        for (Map.Entry<Object, Object> entry : prop.entrySet()){
            String[] values = extractCvTermFromKey((String)entry.getKey());
            if (values.length != 2){
                this.deterministicInteractorNameMap.put(values[0], (String)entry.getValue());
            }
            else {
                this.deterministicInteractorNameMap.put(values[1].replaceAll("\\)", ""), (String)entry.getValue());
                this.deterministicInteractorIdMap.put(values[0], (String)entry.getValue());
            }
        }
    }

    /**
     * Reads the cv term from the properties file
     * @param key
     * @return
     */
    protected String[] extractCvTermFromKey(String key){
        if (key.contains("(") && key.contains(")")){
            String[] values = key.split("\\(");
            return values;
        }
        else{
            return new String[]{key};
        }
    }

    /**
     * Creates an interactor from a given category (should be the canonical name of an Interactor interface)
     * @param category
     * @param name
     * @param type
     * @return
     */
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
}
