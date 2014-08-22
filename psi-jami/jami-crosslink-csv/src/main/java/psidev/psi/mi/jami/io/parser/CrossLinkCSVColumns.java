package psidev.psi.mi.jami.io.parser;

/**
 * Enum listing possible column names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/08/14</pre>
 */

public enum CrossLinkCSVColumns {

    protein1, protein2, peppos1, peppos2, linkpos1, linkpos2;

    public static CrossLinkCSVColumns convertFromString(String val){
         if (val == null){
              return null;
         }
        else {
            String fixedVal = val.trim().toLowerCase();
            if (fixedVal.equalsIgnoreCase(protein1.toString())){
                return protein1;
            }
             else if (fixedVal.equalsIgnoreCase(protein2.toString())){
                return protein2;
            }
            else if (fixedVal.equalsIgnoreCase(peppos1.toString())){
                return peppos1;
            }
            else if (fixedVal.equalsIgnoreCase(peppos2.toString())){
                return peppos2;
            }
            else if (fixedVal.equalsIgnoreCase(linkpos1.toString())){
                return linkpos1;
            }
            else if (fixedVal.equalsIgnoreCase(linkpos2.toString())){
                return linkpos2;
            }
            else {
                return null;
            }
        }
    }
}
