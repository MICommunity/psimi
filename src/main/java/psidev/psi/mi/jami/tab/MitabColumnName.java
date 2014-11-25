package psidev.psi.mi.jami.tab;

/**
 * Mitab column names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public enum MitabColumnName {

    //MITAB 2.5

    ID_INTERACTOR_A("ID(s) interactor A"), // 1
    ID_INTERACTOR_B("ID(s) interactor B"), // 2
    ALTID_INTERACTOR_A("Alt. ID(s) interactor A"), // 3
    ALTID_INTERACTOR_B("Alt. ID(s) interactor B"), // 4
    ALIAS_INTERACTOR_A("Alias(es) interactor A"), // 5
    ALIAS_INTERACTOR_B("Alias(es) interactor B"), // 6
    INT_DET_METHOD("Interaction detection method(s)"), // 7
    PUB_AUTH("Publication 1st author(s)"), // 8
    PUB_ID("Publication Identifier(s)"), // 9
    TAXID_A("Taxid interactor A"), // 10
    TAXID_B("Taxid interactor B"), // 11
    INTERACTION_TYPE("Interaction type(s)"), // 12
    SOURCE("Source database(s)"), // 13
    INTERACTION_ID("Interaction identifier(s)"),// 14
    CONFIDENCE("Confidence value(s)"), // 15
    //MITAB 2.6
    COMPLEX_EXPANSION("Expansion method(s)"), // 16
    BIOROLE_A("Biological role(s) interactor A"), // 17
    BIOROLE_B("Biological role(s) interactor B"), // 18
    EXPROLE_A("Experimental role(s) interactor A"), // 19
    EXPROLE_B("Experimental role(s) interactor B"), // 20
    INTERACTOR_TYPE_A("Type(s) interactor A"), // 21
    INTERACTOR_TYPE_B("Type(s) interactor B"), // 22
    XREFS_A("Xref(s) interactor A"), // 23
    XREFS_B("Xref(s) interactor B"), // 24
    XREFS_I("Interaction Xref(s)"), // 25
    ANNOTATIONS_A("Annotation(s) interactor A"), // 26
    ANNOTATIONS_B("Annotation(s) interactor B"), // 27
    ANNOTATIONS_I("Interaction annotation(s)"), // 28
    HOST_ORGANISM("Host organism(s)"), // 29
    PARAMETERS_I("Interaction parameter(s)"), // 30
    CREATION_DATE("Creation date"), // 31
    UPDATE_DATE("Update date"), // 32
    CHECKSUM_A("Checksum(s) interactor A"), // 33
    CHECKSUM_B("Checksum(s) interactor B"), // 34
    CHECKSUM_I("Interaction Checksum(s)"), // 35
    NEGATIVE("Negative"), //36
    // MITAB 2.7
    FEATURES_A("Feature(s) interactor A"), //37
    FEATURES_B("Feature(s) interactor B"), //38
    STOICHIOMETRY_A("Stoichiometry(s) interactor A"), //39
    STOICHIOMETRY_B("Stoichiometry(s) interactor B"), //40
    PARTICIPANT_IDENT_MED_A("Identification method participant A"), //41
    PARTICIPANT_IDENT_MED_B("Identification method participant B"), //42
    MITAB_LENGTH("length");


    /////////////////////////////////
    // Constructor

    private final String name;

    private MitabColumnName(String name) {
        this.name = name;
    }

    /////////////////////////////////
    // Object's overload.

    @Override
    public String toString() {
        return name;
    }
}
