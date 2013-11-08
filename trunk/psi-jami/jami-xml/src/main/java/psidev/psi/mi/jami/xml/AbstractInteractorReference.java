package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * An implementation of XmlIdReference for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
public abstract class AbstractInteractorReference extends AbstractXmlIdReference implements Interactor {

    public AbstractInteractorReference(int ref) {
        super(ref);
    }

    public String getShortName() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have a shortname for interactor id "+ref);
    }

    public void setShortName(String name) {
        throw new IllegalStateException("The interactor reference is not resolved and we cannot set a shortname for interactor id "+ref);
    }

    public String getFullName() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have a fullname for interactor id "+ref);
    }

    public void setFullName(String name) {
        throw new IllegalStateException("The interactor reference is not resolved and we cannot set a fullname for interactor id "+ref);
    }

    public Collection<Xref> getIdentifiers() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have identifiers for interactor id "+ref);
    }

    public Xref getPreferredIdentifier() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have a preferred identifier for interactor id "+ref);
    }

    public Collection<Checksum> getChecksums() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have checksums for interactor id "+ref);
    }

    public Collection<Xref> getXrefs() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have xrefs for interactor id "+ref);
    }

    public Collection<Annotation> getAnnotations() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have annotations for interactor id "+ref);
    }

    public Collection<Alias> getAliases() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have aliases for interactor id "+ref);
    }

    public Organism getOrganism() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have an organism for interactor id "+ref);
    }

    public void setOrganism(Organism organism) {
        throw new IllegalStateException("The interactor reference is not resolved and we cannot set an organism for interactor id "+ref);
    }

    public CvTerm getInteractorType() {
        throw new IllegalStateException("The interactor reference is not resolved and we don't have an interactor type for interactor id "+ref);
    }

    public void setInteractorType(CvTerm type) {
        throw new IllegalStateException("The interactor reference is not resolved and we cannot set an interactor type for interactor id "+ref);
    }

    @Override
    public String toString() {
        return "Interactor Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
