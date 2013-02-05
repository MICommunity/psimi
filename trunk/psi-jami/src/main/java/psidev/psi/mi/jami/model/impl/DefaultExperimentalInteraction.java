package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExperimentalInteraction extends DefaultInteraction<ExperimentalParticipant> implements ExperimentalInteraction {

    private Xref imexId;
    private Experiment experiment;
    private String availability;
    private Set<Parameter> parameters;
    private boolean isInferred = false;

    private static Pattern IMEx_INTERACTION_ID = Pattern.compile( "IM-[0-9]+-[0-9]+" );

    public DefaultExperimentalInteraction(Experiment experiment) {
        super();
        this.parameters = new HashSet<Parameter>();

        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName) {
        super(shortName);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, Source source) {
        super(shortName, source);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, CvTerm type) {
        super(shortName, type);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, Source source, CvTerm type) {
        super(shortName, source, type);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultExperimentalInteraction(Experiment experiment, Xref imexId) {
        super();
        this.parameters = new HashSet<Parameter>();

        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, Xref imexId) {
        super(shortName);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, Source source, Xref imexId) {
        super(shortName, source);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, CvTerm type, Xref imexId) {
        super(shortName, type);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultExperimentalInteraction(Experiment experiment, String shortName, Source source, CvTerm type, Xref imexId) {
        super(shortName, source, type);
        this.parameters = new HashSet<Parameter>();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        // add new imex id if not null
        if (identifier != null){
            if (this.imexId == null){
                if (IMEx_INTERACTION_ID.matcher(identifier).matches()){
                    CvTerm imexDatabase = CvTermFactory.createImexDatabase();
                    CvTerm imexPrimaryQualifier = CvTermFactory.createImexPrimaryQualifier();

                    Xref imexId = new DefaultXref(imexDatabase, identifier, imexPrimaryQualifier);
                    this.xrefs.add(imexId);
                }
                else {
                    throw new IllegalArgumentException("The IMEx identifier " + identifier + " is not a valid interaction IMEx identifier. It cannot be assigned");
                }
            }
            else {
                throw new IllegalArgumentException("Cannot assign a new IMEx identifier as the current interaction already has an IMEx id.");
            }
        }
        // cannot assign imex id of null
        else {
            throw new IllegalArgumentException("Cannot assign a null IMEx identifier.");
        }
    }

    public Experiment getExperiment() {
        return this.experiment;
    }

    public void setExperiment(Experiment experiment) {
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an ExperimentalInteraction, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public String getAvailability() {
        return this.availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Set<Parameter> getParameters() {
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    /**
     * Comparator which sorts Xrefs so imex primary references are always first. Only one imex-primary ref is kept
     */
    private class ExperimentalInteractionXrefComparator implements Comparator<Xref> {

        public int compare(Xref xref1, Xref xref2) {
            int EQUAL = 0;
            int BEFORE = -1;
            int AFTER = 1;

            if (xref1 == null && xref2 == null){
                return EQUAL;
            }
            else if (xref1 == null){
                return AFTER;
            }
            else if (xref2 == null){
                return BEFORE;
            }
            else {
                // compares databases first : imex is before
                CvTerm database1 = xref1.getDatabase();
                CvTerm database2 = xref2.getDatabase();
                ExternalIdentifier databaseId1 = database1.getOntologyIdentifier();
                ExternalIdentifier databaseId2 = database2.getOntologyIdentifier();

                // if external id of database is set, look at database id only otherwise look at shortname
                int comp;
                if (databaseId1 != null && databaseId2 != null){
                    // both are imex, sort by qualifier
                    if (Xref.IMEX_ID.equals(databaseId1.getId()) && Xref.IMEX_ID.equals(databaseId2.getId())){
                        comp = compareXrefQualifiers(xref1, xref2);
                    }
                    // IMEX is first
                    else if (Xref.IMEX_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.IMEX_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both databases are not imex
                    else {
                        comp = databaseId1.getId().compareTo(databaseId2.getId());
                    }
                }
                else {
                    String databaseName1 = database1.getShortName().toLowerCase().trim();
                    String databaseName2 = database2.getShortName().toLowerCase().trim();
                    // both are imex, sort by qualifier
                    if (Xref.IMEX.equals(databaseName1) && Xref.IMEX.equals(databaseName2)){
                        comp = compareXrefQualifiers(xref1, xref2);
                    }
                    // imex is first
                    else if (Xref.IMEX.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.IMEX.equals(databaseName2)){
                        return AFTER;
                    }
                    // both databases are not chebi
                    else {
                        comp = databaseName1.compareTo(databaseName2);
                    }
                }

                if (comp != 0){
                    return comp;
                }
                // check identifiers which cannot be null
                String id1 = xref1.getId();
                String id2 = xref2.getId();

                comp = id1.compareTo(id2);
                if (comp != 0){
                    return comp;
                }

                CvTerm qualifier1 = xref1.getQualifier();
                CvTerm qualifier2 = xref2.getQualifier();
                if (qualifier1 == null && qualifier2 == null){
                    return 0;
                }
                else if (qualifier1 != null){
                    return -1;
                }
                else if (qualifier2 != null){
                    return 1;
                }
                else {
                    ExternalIdentifier qualifierId1 = qualifier1.getOntologyIdentifier();
                    ExternalIdentifier qualifierId2 = qualifier2.getOntologyIdentifier();

                    // if external id of qualifier is set, look at qualifier id only otherwise look at shortname
                    int comp2;
                    if (qualifierId1 != null && qualifierId2 != null){
                        return qualifierId1.getId().compareTo(qualifierId2.getId());
                    }
                    else {
                        return qualifier1.getShortName().toLowerCase().trim().compareTo(qualifier2.getShortName().toLowerCase().trim());
                    }
                }
            }
        }

        private int compareXrefQualifiers(Xref xref1, Xref xref2){
            int comp;
            CvTerm qualifier1 = xref1.getQualifier();
            CvTerm qualifier2 = xref2.getQualifier();
            if (qualifier1 == null && qualifier2 == null){
                return 0;
            }
            else if (qualifier1 != null){
                return -1;
            }
            else if (qualifier2 != null){
                return 1;
            }
            else {
                ExternalIdentifier qualifierId1 = qualifier1.getOntologyIdentifier();
                ExternalIdentifier qualifierId2 = qualifier2.getOntologyIdentifier();

                if (qualifierId1 != null && qualifierId2 != null){
                    // both are imex-primary, they are equals because we can only have one imex id
                    if (Xref.IMEX_PRIMARY_ID.equals(qualifierId1.getId()) && Xref.IMEX_PRIMARY_ID.equals(qualifierId2.getId())){
                        return 0;
                    }
                    // IMEX-primary is first
                    else if (Xref.IMEX_PRIMARY_ID.equals(qualifierId1.getId())){
                        return -1;
                    }
                    else if (Xref.IMEX_PRIMARY_ID.equals(qualifierId2.getId())){
                        return 1;
                    }
                    // both xrefs are not imex-primary
                    else {
                        comp = qualifierId1.getId().compareTo(qualifierId2.getId());
                    }
                }
                else {
                    String qualifierName1 = qualifier1.getShortName().toLowerCase().trim();
                    String qualifierName2 = qualifier2.getShortName().toLowerCase().trim();
                    // both are imex-primary, they are equals because we can only have one imex id
                    if (Xref.IMEX.equals(qualifierName1) && Xref.IMEX.equals(qualifierName2)){
                        return 0;
                    }
                    // IMEX-primary is first
                    else if (Xref.IMEX_PRIMARY.equals(qualifierName1)){
                        return -1;
                    }
                    else if (Xref.IMEX_PRIMARY.equals(qualifierName2)){
                        return 1;
                    }
                    // both xrefs are not imex-primary
                    else {
                        comp = qualifierName1.compareTo(qualifierName2);
                    }
                }
            }

            return comp;
        }
    }

    private class ExperimentalInteractionXrefList extends TreeSet<Xref> {
        public ExperimentalInteractionXrefList(){
            super(new ExperimentalInteractionXrefComparator());
        }

        @Override
        public boolean add(Xref ref) {
            boolean added = super.add(ref);

            // set imex if not done
            if (imexId == null && added){
                Xref firstImex = first();

                if (XrefUtils.isXrefFromDatabase(firstImex, Xref.IMEX_ID, Xref.IMEX) &&
                        XrefUtils.doesXrefHaveQualifier(firstImex, Xref.IMEX_PRIMARY_ID, Xref.IMEX_PRIMARY)){
                    if (IMEx_INTERACTION_ID.matcher(imexId.getId()).matches()){
                        imexId = firstImex;
                    }
                    else {
                        remove(ref);
                        return false;
                    }
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // check imex
                Xref firstXref = first();

                // first ref is from imex
                if (!XrefUtils.isXrefFromDatabase(firstXref, Xref.IMEX_ID, Xref.IMEX) ||
                        !XrefUtils.doesXrefHaveQualifier(firstXref, Xref.IMEX_PRIMARY_ID, Xref.IMEX_PRIMARY)){
                    imexId = null;
                }
                return true;
            }

            return false;
        }

        @Override
        public void clear() {
            super.clear();
            imexId = null;
        }
    }
}
