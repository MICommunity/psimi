package psidev.psi.mi.jami.datasource;

import java.io.*;

/**
 * The openedInputStream contains a temporary file and the type of dataSourceFile it represents
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class OpenedInputStream {

    private InputStream stream;
    private File file;

    private MIFileSource source;

    public OpenedInputStream(File file, MIFileSource source) throws FileNotFoundException {
        this.file = file;
        stream = new FileInputStream(file);
        this.source = source;
    }

    public InputStream getStream() {
        return stream;
    }

    public MIFileSource getSource() {
        return source;
    }

    public void close(){
        if (stream != null){
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file != null){
            file.delete();
        }
    }
}
