package psidev.psi.mi.jami.datasource;

import java.io.*;

/**
 * The openedInputStream contains an inputStream that is wrapping an opened inputStream and the first line that was read to identify the molecular interaction source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class OpenedInputStream {

    private InputStream stream;
    private File file;

    private MolecularInteractionSource source;

    public OpenedInputStream(File file, MolecularInteractionSource source) throws FileNotFoundException {
        this.file = file;
        stream = new FileInputStream(file);
        this.source = source;
    }

    public InputStream getStream() {
        return stream;
    }

    public MolecularInteractionSource getSource() {
        return source;
    }

    public void close(){
        if (file != null){
            file.delete();
        }
    }
}
