package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.bridges.uniprot.uniprotutil.UniprotToJAMI;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 11:17
 */
public class CollectionManipulationUtils {


    /**
     * All the 'a's that aren't in 'b' when compared using the provided comparator.
     * @param a     The collection to subtract 'b' from
     * @param b     The collection to subtract from 'a'
     * @param c     How to compare 'a's to 'b's
     * @param <Type>
     * @return
     */
    public static <Type> Collection<Type> comparatorSubtract(Collection<Type> a, Collection<Type> b, Comparator<Type> c) {
        Set<Type> subtracted = new TreeSet<Type>(c);
        subtracted.addAll(b);
        Collection<Type> result = new ArrayList<Type>();
        for (Type item: a) {
            if (!subtracted.contains(item)) result.add(item);
        }
        return result;
    }

    private UniprotToJAMI data = new UniprotToJAMI();

    public Collection<Xref> findUniprotManagedXrefs(Collection<Xref> xrefs){
        Collection<Xref> uniprotManaged = new ArrayList<Xref>();
        for(Xref xref : xrefs){
            //Ignore if it hsa a qualifier
            if(xref.getQualifier() == null){
                if(data.getUniprotDatabases().containsValue(xref.getDatabase())){
                    uniprotManaged.add(xref);
                }
            }
        }
        return uniprotManaged;
    }
}
