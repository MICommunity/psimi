package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.listener.AliasesChangeListener;
import psidev.psi.mi.jami.listener.ChecksumsChangeListener;
import psidev.psi.mi.jami.listener.IdentifiersChangeListener;
import psidev.psi.mi.jami.listener.XrefsChangeListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.checksum.DefaultChecksumComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utilities for enrichers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class EnricherUtils {

    /* Characters to be used for new rows, new columns, blank cells */
    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";
    public static final String BLANK_SPACE = "-";

    /**
     * Takes two lists of Xrefs and produces a list of those to add and those to remove.
     *
     * It will add in toEnrichXrefs all xref from fetchedXrefs that are not there. It will also remove extra identifiers from toEnrichXrefs
     * if remove boolean is true.
     *
     *
     * @param termToEnrich     The object to enrich
     * @param fetchedXrefs      The new xrefs to be added.
     * @param remove: if true, we remove xrefs that are not in enriched list
     * @param isIdentifier if true compare identifiers, otherwise xrefs
     */
    public static <T extends Object> void mergeXrefs(T termToEnrich, Collection<Xref> toEnrichXrefs, Collection<Xref> fetchedXrefs , boolean remove,
                              boolean isIdentifier, XrefsChangeListener<T> xrefListener, IdentifiersChangeListener<T> identifierListener){

        Iterator<Xref> refIterator = toEnrichXrefs.iterator();
        // remove xrefs in toEnrichXrefs that are not in fetchedXrefs
        while(refIterator.hasNext()){
            Xref ref = refIterator.next();
            boolean containsRef = false;
            for (Xref ref2 : fetchedXrefs){
                // when we allow to removexrefs, we compare qualifiers as well
                if (remove){
                    // identical xrefs
                    if (DefaultXrefComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
                // when we don't allow to remove xrefs, we compare only database+identifier to avoid dupicating xrefs with same db/id but different qualifiers.
                // it would be too confusing for the suer
                else{
                    // identical identifier
                    if (DefaultExternalIdentifierComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
            }
            // remove xref not in second list
            if (remove && !containsRef){
                refIterator.remove();
                if (isIdentifier){
                    if (identifierListener != null){
                        identifierListener.onRemovedIdentifier(termToEnrich, ref);
                    }
                }
                else{
                    if (xrefListener != null){
                        xrefListener.onRemovedXref(termToEnrich, ref);
                    }
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        refIterator = fetchedXrefs.iterator();
        while(refIterator.hasNext()){
            Xref ref = refIterator.next();
            boolean containsRef = false;
            for (Xref ref2 : toEnrichXrefs){
                // when we allow to removexrefs, we compare qualifiers as well
                if (remove){
                    // identical xrefs
                    if (DefaultXrefComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
                // when we don't allow to remove xrefs, we compare only database+identifier to avoid dupicating xrefs with same db/id but different qualifiers.
                // it would be too confusing for the suer
                else{
                    // identical identifier
                    if (DefaultExternalIdentifierComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
            }
            // add missing xref not in second list
            if (!containsRef){
                toEnrichXrefs.add(ref);
                if (isIdentifier){
                    if (identifierListener != null){
                        identifierListener.onAddedIdentifier(termToEnrich, ref);
                    }
                }
                else{
                    if (xrefListener != null){
                        xrefListener.onAddedXref(termToEnrich, ref);
                    }
                }
            }
        }
    }

    public static <T extends Object> void mergeAliases(T termToEnrich, Collection<Alias> toEnrichAliases, Collection<Alias> fetchedAliases, boolean remove, AliasesChangeListener<T> aliasListener){
        Iterator<Alias> aliasIterator = toEnrichAliases.iterator();
        // remove aliases in toEnrichAliases that are not in fetchedAliases
        while(aliasIterator.hasNext()){
            Alias alias = aliasIterator.next();
            boolean containsAlias = false;
            for (Alias alias2 : fetchedAliases){
                // identical aliases
                if (DefaultAliasComparator.areEquals(alias, alias2)){
                    containsAlias = true;
                    break;
                }
            }
            // remove alias not in second list
            if (remove && !containsAlias){
                aliasIterator.remove();
                if (aliasListener != null){
                    aliasListener.onRemovedAlias(termToEnrich, alias);
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        aliasIterator = fetchedAliases.iterator();
        while(aliasIterator.hasNext()){
            Alias alias = aliasIterator.next();
            boolean containsAlias = false;
            for (Alias alias2 : toEnrichAliases){
                // identical aliases
                if (DefaultAliasComparator.areEquals(alias, alias2)){
                    containsAlias = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsAlias){
                toEnrichAliases.add(alias);
                if (aliasListener != null){
                    aliasListener.onAddedAlias(termToEnrich, alias);
                }
            }
        }
    }

    public static <T extends Object> void mergeChecksums(T termToEnrich, Collection<Checksum> toEnrichChecksums, Collection<Checksum> fetchedCehcksum, boolean remove, ChecksumsChangeListener<T> aliasListener){
        Iterator<Checksum> checksumIterator = toEnrichChecksums.iterator();
        // remove aliases in toEnrichAliases that are not in fetchedAliases
        while(checksumIterator.hasNext()){
            Checksum checksum = checksumIterator.next();
            boolean containsChecksum = false;
            for (Checksum checksum2 : fetchedCehcksum){
                // identical checksum
                if (DefaultChecksumComparator.areEquals(checksum, checksum2)){
                    containsChecksum = true;
                    break;
                }
            }
            // remove alias not in second list
            if (remove && !containsChecksum){
                checksumIterator.remove();
                if (aliasListener != null){
                    aliasListener.onRemovedChecksum(termToEnrich, checksum);
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        checksumIterator = fetchedCehcksum.iterator();
        while(checksumIterator.hasNext()){
            Checksum checksum = checksumIterator.next();
            boolean containsChecksum = false;
            for (Checksum checksum2 : toEnrichChecksums){
                // identical aliases
                if (DefaultChecksumComparator.areEquals(checksum, checksum2)){
                    containsChecksum = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsChecksum){
                toEnrichChecksums.add(checksum);
                if (aliasListener != null){
                    aliasListener.onAddedChecksum(termToEnrich, checksum);
                }
            }
        }
    }
}
