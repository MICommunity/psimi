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

public class DefaultInteractorFactory implements InteractorFactory {

    private Map<String, InteractorCategory> deterministicInteractorNameMap;
    private Map<String, InteractorCategory> deterministicInteractorIdMap;

    public DefaultInteractorFactory(){

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
            InteractorCategory recognizedType = this.deterministicInteractorIdMap.get(typeMI);

            return createInteractorFromRecognizedCategory(recognizedType, name, type);
        }
        else if (typeMI == null && this.deterministicInteractorNameMap.containsKey(typeName)){
            InteractorCategory recognizedType = this.deterministicInteractorNameMap.get(typeName);

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
            InteractorCategory recognizedType = this.deterministicInteractorIdMap.get(databaseMI);

            return createInteractorFromRecognizedCategory(recognizedType, name, null);
        }
        else if (databaseMI == null && this.deterministicInteractorNameMap.containsKey(databaseName)){
            InteractorCategory recognizedType = this.deterministicInteractorNameMap.get(databaseName);

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
     * Creates a default interactor from the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Interactor createInteractor(String name, CvTerm type){
        return new DefaultInteractor(name, type);
    }

    public InteractorPool createInteractorSet(String name, CvTerm type) {
        return new DefaultInteractorPool(name, type);
    }

    /**
     * Loads some properties to recognize interactor type from different MI terms
     */
    protected void initialiseDeterministicInteractorMaps(){
        deterministicInteractorNameMap = new HashMap<String, InteractorCategory>();
        deterministicInteractorIdMap = new HashMap<String, InteractorCategory>();
        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(DefaultInteractorFactory.class.getResourceAsStream("/interactorType.properties"));
            loadProperties(prop);

            // load the second property file
            prop.clear();
            prop.load(DefaultInteractorFactory.class.getResourceAsStream("/interactorDatabase.properties"));
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
                this.deterministicInteractorNameMap.put(values[0], InteractorCategory.valueOf((String)entry.getValue()));
            }
            else {
                this.deterministicInteractorNameMap.put(values[1].replaceAll("\\)", ""), InteractorCategory.valueOf((String)entry.getValue()));
                this.deterministicInteractorIdMap.put(values[0], InteractorCategory.valueOf((String)entry.getValue()));
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
    protected Interactor createInteractorFromRecognizedCategory(InteractorCategory category, String name, CvTerm type){

        switch (category){
            case protein:
                return createProtein(name, type);
            case gene:
                return createGene(name);
            case nucleic_acid:
                return createNucleicAcid(name, type);
            case bioactive_entity:
                return createBioactiveEntity(name, type);
            case polymer:
                return createPolymer(name, type);
            case complex:
                return createComplex(name, type);
            case interactor_set:
                return createInteractorSet(name, type);
            default:
                return createInteractor(name, type);
        }
    }
}
