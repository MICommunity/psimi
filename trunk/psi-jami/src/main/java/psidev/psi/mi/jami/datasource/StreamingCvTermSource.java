package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.CvTerm;

import java.util.Iterator;

/**
 * A CV term data source allows to stream the controlled vocabulary terms of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface StreamingCvTermSource {

    /**
     * The CV terms iterator for this datasource.
     * @return iterator of CV terms for a given datasource
     */
    public Iterator<CvTerm> getCvTermsIterator();

    /**
     * The cell types iterator for this datasource.
     * @return iterator of Cell types for a given datasource
     */
    public Iterator<CvTerm> getCellTypesIterator();

    /**
     * The compartments iterator for this datasource.
     * @return iterator of compartments for a given datasource
     */
    public Iterator<CvTerm> getCompartmentsIterator();

    /**
     * The tissues iterator for this datasource.
     * @return iterator of tissues for a given datasource
     */
    public Iterator<CvTerm> getTissuesIterator();

    /**
     * The alias types iterator for this datasource.
     * @return iterator of alias types for a given datasource
     */
    public Iterator<CvTerm> getAliasTypesIterator();

    /**
     * The annotation topics iterator for this datasource.
     * @return iterator of annotation topics for a given datasource
     */
    public Iterator<CvTerm> getAnnotationTopicsIterator();

    /**
     * The biological roles iterator for this datasource.
     * @return iterator of biological roles for a given datasource
     */
    public Iterator<CvTerm> getBiologicalRolesIterator();

    /**
     * The xref qualifiers iterator for this datasource.
     * @return iterator of xref qualifiers for a given datasource
     */
    public Iterator<CvTerm> getXrefQualifiersIterator();

    /**
     * The databases iterator for this datasource.
     * @return iterator of databases for a given datasource
     */
    public Iterator<CvTerm> getDatabasesIterator();

    /**
     * The experimental preparations iterator for this datasource.
     * @return iterator of experimental preparations for a given datasource
     */
    public Iterator<CvTerm> getExperimentalPreparationsIterator();

    /**
     * The experimental roles iterator for this datasource.
     * @return iterator of experimental roles for a given datasource
     */
    public Iterator<CvTerm> getExperimentalRolesIterator();

    /**
     * The feature detection methods iterator for this datasource.
     * @return iterator of feature detection methods for a given datasource
     */
    public Iterator<CvTerm> getFeatureDetectionMethodsIterator();

    /**
     * The feature range status iterator for this datasource.
     * @return iterator of feature range status for a given datasource
     */
    public Iterator<CvTerm> getFeatureRangeStatusIterator();

    /**
     * The feature types iterator for this datasource.
     * @return iterator of feature types for a given datasource
     */
    public Iterator<CvTerm> getFeatureTypesIterator();

    /**
     * The confidence types iterator for this datasource.
     * @return iterator of confidence types for a given datasource
     */
    public Iterator<CvTerm> getConfidenceTypesIterator();

    /**
     * The interaction detection methods iterator for this datasource.
     * @return iterator of interaction detection methods for a given datasource
     */
    public Iterator<CvTerm> getInteractionDetectionMethodsIterator();

    /**
     * The interaction types iterator for this datasource.
     * @return iterator of interaction types for a given datasource
     */
    public Iterator<CvTerm> getInteractionTypesIterator();

    /**
     * The interactor types iterator for this datasource.
     * @return iterator of interactor types for a given datasource
     */
    public Iterator<CvTerm> getInteractorTypesIterator();

    /**
     * The parameter types iterator for this datasource.
     * @return iterator of parameter types for a given datasource
     */
    public Iterator<CvTerm> getParameterTypesIterator();

    /**
     * The parameter units iterator for this datasource.
     * @return iterator of parameter units for a given datasource
     */
    public Iterator<CvTerm> getParameterUnitsIterator();

    /**
     * The participant identification methods iterator for this datasource.
     * @return iterator of participant identification methods for a given datasource
     */
    public Iterator<CvTerm> getParticipantIdentificationMethodsIterator();
}
