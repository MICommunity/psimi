package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

/**
 * NamesContainer for Proteins
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class ProteinNamesContainer extends NamesContainer{

    private Alias geneName;

    @XmlTransient
    public String getGeneName() {
        return this.geneName != null ? this.geneName.getName() : null;
    }

    public void setGeneName(String name) {
        Collection<Alias> proteinAliases = getAliases();

        // add new gene name if not null
        if (name != null){
            CvTerm geneNameType = CvTermUtils.createGeneNameAliasType();
            // first remove old gene name if not null
            if (this.geneName != null){
                proteinAliases.remove(this.geneName);
            }
            this.geneName = new DefaultAlias(geneNameType, name);
            proteinAliases.add(this.geneName);
        }
        // remove all gene names if the collection is not empty
        else if (!proteinAliases.isEmpty()) {
            AliasUtils.removeAllAliasesWithType(proteinAliases, Alias.GENE_NAME_MI, Alias.GENE_NAME);
            this.geneName = null;
        }
    }

    protected void processAddedAliasEvent(Alias added) {
        // the added alias is gene name and it is not the current gene name
        if (geneName == null && AliasUtils.doesAliasHaveType(added, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
            geneName = added;
        }
    }

    protected void processRemovedAliasEvent(Alias removed) {
        if (geneName != null && geneName.equals(removed)){
            geneName = AliasUtils.collectFirstAliasWithType(getAliases(), Alias.GENE_NAME_MI, Alias.GENE_NAME);
        }
    }

    protected void clearPropertiesLinkedToAliases() {
        geneName = null;
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(new AliasList());
    }

    private class AliasList extends AbstractListHavingProperties<Alias> {
        public AliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Alias added) {
            processAddedAliasEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Alias removed) {
            processRemovedAliasEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToAliases();
        }
    }
}
