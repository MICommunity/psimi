package psidev.psi.mi.jami.datasource;

/**
 * A datasource error has a label, an error message
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class DataSourceError {

    private String label;
    private String message;

    public DataSourceError(String label, String message){
        this.label = label;
        this.message = message;
    }

    public String getLabel() {
        return label;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceError)) return false;

        DataSourceError that = (DataSourceError) o;

        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
