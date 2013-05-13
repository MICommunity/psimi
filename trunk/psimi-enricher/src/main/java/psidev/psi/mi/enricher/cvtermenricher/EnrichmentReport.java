package psidev.psi.mi.enricher.cvtermenricher;

/**
 * "Identity" is a value which being used to identifier the object
 * "IdentityType" is the field of the value.
 * Ideally the identityType will be (in order of preference) the identifier, fullname or shortname.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 15:29
 */
public class EnrichmentReport {
    private String identityType = null;
    private String identity = null;

    public EnrichmentReport(){

    }


    public String getIdentity() {return identity;}
    public void setIdentity(String identity) {this.identity = identity;}

    public String getIdentityType() {return identityType;}
    public void setIdentityType(String method) {this.identityType = method;}




}
