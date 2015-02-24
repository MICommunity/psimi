package psidev.psi.mi.jami.bridges.imex.extension;

import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.bridges.imex.PublicationStatus;

import java.util.*;

/**
 * Extension of publication for imex central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/14</pre>
 */

public class ImexPublication extends DefaultPublication{

    private edu.ucla.mbi.imex.central.ws.v20.Publication delegate;

    private Date expectedPublicationDate;
    private Date creationDate;
    private List<Source> sources;
    private PublicationStatus status;
    private List<String> curators;

    public ImexPublication(edu.ucla.mbi.imex.central.ws.v20.Publication delegate) {
        super();
        if (delegate == null){
            throw new IllegalArgumentException("The IMEx central publication object cannot be null");
        }
        this.delegate = delegate;

        // init title
        super.setTitle(this.delegate.getTitle());
        // init publication date
        if (this.delegate.getPublicationDate() != null){
            super.setPublicationDate(this.delegate.getPublicationDate().toGregorianCalendar().getTime());
        }
        // init expected publication date
        if (this.delegate.getExpectedPublicationDate() != null){
            this.expectedPublicationDate = this.delegate.getExpectedPublicationDate().toGregorianCalendar().getTime();
        }
        // init release date
        if (this.delegate.getReleaseDate() != null){
            super.setReleasedDate(this.delegate.getReleaseDate().toGregorianCalendar().getTime());
        }
        // init creation date
        if (this.delegate.getCreationDate() != null){
            this.creationDate = this.delegate.getCreationDate().toGregorianCalendar().getTime();
        }
        // init pub status
        if (this.delegate.getStatus() != null){
            this.status =  PublicationStatus.valueOf(this.delegate.getStatus());
        }
    }

    @Override
    protected void initialiseIdentifiers() {
        super.initialiseIdentifiers();
        super.initialiseXrefs();

        copyIdentifiersFromDelegate();
    }

    @Override
    protected void initialiseXrefs() {
        super.initialiseIdentifiers();
        super.initialiseXrefs();

        copyIdentifiersFromDelegate();
    }

    @Override
    protected void initialiseAuthors() {
        super.initialiseAuthors();

        copyAuthorsFromDelegate();
    }

    private void copyAuthorsFromDelegate() {
        String author = this.delegate.getAuthor();
        if (author != null){
           if (author.contains(",")){
               super.getAuthors().addAll(Arrays.asList(author.split(",")));
           }
            else{
               super.getAuthors().add(author);
           }
        }
    }

    private void copyIdentifiersFromDelegate() {
        List<Identifier> imexIdentifiers = this.delegate.getIdentifier();
        for (Identifier identifier : imexIdentifiers){
            if (identifier.getNs() == null || identifier.getAc() == null){
                // nothing to do
            }
            else if (identifier.getNs().equals("pmid")){
                super.getIdentifiers().add(XrefUtils.createPubmedIdentity(identifier.getAc()));
            }
            else if (identifier.getNs().equals("imex") && !identifier.getAc().equals("N/A")){
                super.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, identifier.getAc(), Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));
            }
            else if (identifier.getNs().equals("doi")){
                super.getIdentifiers().add(XrefUtils.createDoiIdentity(identifier.getAc()));
            }
            else if (identifier.getNs().equals("jint")){
                super.getIdentifiers().add(XrefUtils.createIdentityXref("jint", identifier.getAc()));
            }
        }

        if (super.getImexId() == null && this.delegate.getImexAccession() != null && !this.delegate.getImexAccession().equals("N/A")){
            super.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, this.delegate.getImexAccession(), Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));
        }
    }

    @Override
    public String getPubmedId() {
        // initialise identifiers
        getIdentifiers();
        return super.getPubmedId();
    }

    @Override
    public void setPubmedId(String pubmedId) {
        // initialise identifiers
        getIdentifiers();
        super.setPubmedId(pubmedId);
    }

    @Override
    public String getDoi() {
        // initialise identifiers
        getIdentifiers();
        return super.getDoi();
    }

    @Override
    public void setDoi(String doi) {
        // initialise identifiers
        getIdentifiers();
        super.setDoi(doi);
    }

    @Override
    public String getImexId() {
        // initialise xrefs
        getXrefs();
        return super.getImexId();
    }

    @Override
    public void assignImexId(String identifier) {
        // initialise xrefs
        getXrefs();
        super.assignImexId(identifier);
    }

    @Override
    public CurationDepth getCurationDepth() {
        if (getImexId() != null){
            setCurationDepth(CurationDepth.IMEx);
        }
        return super.getCurationDepth();
    }

    @Override
    public Source getSource() {
        if (!getSources().isEmpty()){
            return getSources().iterator().next();
        }
        return null;
    }

    @Override
    public void setSource(Source source) {
        if (source == null){
            getSources().clear();
        }
        else {
            if (!getSources().isEmpty()){
                getSources().remove(0);
            }
            getSources().add(0, source);
        }
    }

    public String getPaperAbstract() {
        return this.delegate.getPaperAbstract();
    }

    public Date getExpectedPublicationDate() {
        return expectedPublicationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public String getOwner() {
        return this.delegate.getOwner();
    }

    public Collection<String> getCurators() {
        if (this.delegate.getAdminUserList() != null){
           return this.delegate.getAdminUserList().getUser();
        }
        else if (this.curators == null){
            this.curators = new ArrayList<String>();
        }
        return this.curators;
    }

    public List<Source> getSources() {
        if (this.sources == null){
            this.sources = new ArrayList<Source>();
            if (this.delegate.getAdminGroupList() != null){
                for (String source : this.delegate.getAdminGroupList().getGroup()){
                     this.sources.add(new DefaultSource(source));
                }
            }
        }
        return this.sources;
    }
}
