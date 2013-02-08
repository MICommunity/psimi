package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.impl.DefaultAlias;

/**
 * AliasFactory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class AliasFactory {
    
    public Alias createGeneName(String name){
        return new DefaultAlias(CvTermFactory.createGeneNameAliasType(), name);
    }

    public Alias createComplexSynonym(String name){
        return new DefaultAlias(CvTermFactory.createComplexSynonym(), name);
    }

    public Alias createAuthorAssignedName(String name){
        return new DefaultAlias(CvTermFactory.createAuthorAssignedName(), name);
    }

    public Alias createGeneNameSynonym(String name){
        return new DefaultAlias(CvTermFactory.createGeneNameSynonym(), name);
    }

    public Alias createIsoformSynonym(String name){
        return new DefaultAlias(CvTermFactory.createIsoformSynonym(), name);
    }

    public Alias createOrfName(String name){
        return new DefaultAlias(CvTermFactory.createOrfName(), name);
    }

    public Alias createLocusName(String name){
        return new DefaultAlias(CvTermFactory.createLocusName(), name);
    }

    public Alias createAlias(String typeName, String typeMi, String name){
        return new DefaultAlias(CvTermFactory.createMICvTerm(typeName, typeMi), name);
    }

    public Alias createAlias(String typeName, String name){
        return new DefaultAlias(CvTermFactory.createMICvTerm(typeName, null), name);
    }
}
