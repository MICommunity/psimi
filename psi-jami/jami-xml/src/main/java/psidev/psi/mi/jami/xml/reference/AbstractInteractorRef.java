package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interactor;
import psidev.psi.mi.jami.xml.extension.XmlInteractor;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An implementation of XmlIdReference for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
public abstract class AbstractInteractorRef extends AbstractXmlIdReference implements ExtendedPsi25Interactor {
    private static final Logger logger = Logger.getLogger("AbstractInteractorRef");
    private ExtendedPsi25Interactor delegate;

    public AbstractInteractorRef(int ref) {
        super(ref);
    }

    public String getShortName() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getShortName();
    }

    public void setShortName(String name) {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        this.delegate.setShortName(name);
    }

    public String getFullName() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getFullName();
    }

    public void setFullName(String name) {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        this.delegate.setFullName(name);
    }

    public Collection<Xref> getIdentifiers() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getIdentifiers();
    }

    public Xref getPreferredIdentifier() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getPreferredIdentifier();
    }

    public Collection<Checksum> getChecksums() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getChecksums();
    }

    public Collection<Xref> getXrefs() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getAnnotations();
    }

    public Collection<Alias> getAliases() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getAliases();
    }

    public Organism getOrganism() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getOrganism();
    }

    public void setOrganism(Organism organism) {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        this.delegate.setOrganism(organism);
    }

    public CvTerm getInteractorType() {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        return this.delegate.getInteractorType();
    }

    public void setInteractorType(CvTerm type) {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        this.delegate.setInteractorType(type);
    }

    @Override
    public void setId(int id) {
        logger.log(Level.WARNING, "The interactor reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseInteractorDelegate();
        }
        this.delegate.setId(id);
    }

    @Override
    public int getId() {
        return this.delegate != null ? this.delegate.getId() : this.ref;
    }

    @Override
    public String toString() {
        return "Interactor Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
    protected void initialiseInteractorDelegate(){
        this.delegate = new XmlInteractor();
        this.delegate.setId(this.ref);
    }

    protected ExtendedPsi25Interactor getDelegate() {
        return delegate;
    }

    protected void setDelegate(ExtendedPsi25Interactor delegate) {
        this.delegate = delegate;
    }
}
