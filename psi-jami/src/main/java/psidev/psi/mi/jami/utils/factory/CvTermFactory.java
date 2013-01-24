package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExternalIdentifier;

/**
 * Factory for creating CvTerms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class CvTermFactory {

    private static CvTerm psimi;
    private static CvTerm chebiDatabase;

    public static CvTerm createPsiMiDatabaseNameOnly(){
        return new DefaultCvTerm(CvTerm.PSI_MI);
    }

    public static CvTerm createPsiMiDatabase(){
        return new DefaultCvTerm(CvTerm.PSI_MI, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), CvTerm.PSI_MI_ID));
    }

    public static CvTerm createChebiDatabase(){
        return new DefaultCvTerm(CvTerm.CHEBI, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), CvTerm.CHEBI_ID));
    }

    public static CvTerm createSmile(){
        return new DefaultCvTerm(Checksum.SMILE, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Checksum.SMILE_ID));
    }

    public static CvTerm createStandardInchi(){
        return new DefaultCvTerm(Checksum.INCHI, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Checksum.INCHI_ID));
    }

    public static CvTerm createStandardInchiKey(){
        return new DefaultCvTerm(Checksum.INCHI_KEY, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Checksum.INCHI_KEY_ID));
    }

    public static CvTerm createUndeterminedStatus(){
        return new DefaultCvTerm(Position.UNDETERMINED, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Position.UNDETERMINED_MI));
    }

    public static CvTerm createNTerminalRangeStatus(){
        return new DefaultCvTerm(Position.N_TERMINAL_RANGE, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Position.N_TERMINAL_RANGE_MI));
    }

    public static CvTerm createCTerminalRangeStatus(){
        return new DefaultCvTerm(Position.C_TERMINAL_RANGE, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Position.C_TERMINAL_RANGE_MI));
    }

    public static CvTerm createNTerminalStatus(){
        return new DefaultCvTerm(Position.N_TERMINAL, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Position.N_TERMINAL_MI));
    }

    public static CvTerm createRaggedNTerminalStatus(){
        return new DefaultCvTerm(Position.RAGGED_N_TERMINAL, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Position.RAGGED_N_TERMINAL_MI));
    }

    public static CvTerm createGeneInteractorType(){
        return new DefaultCvTerm(Gene.GENE, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), Gene.GENE_ID));
    }
}
