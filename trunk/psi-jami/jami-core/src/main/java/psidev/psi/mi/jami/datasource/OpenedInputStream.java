package psidev.psi.mi.jami.datasource;

import java.io.*;

/**
 * The openedInputStream contains a temporary file and the type of dataSourceFile it represents.
 *
 * It needs to be closed after being used.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class OpenedInputStream {

    private InputStream stream;
    private File file;

    private MIFileSourceType source;

    public OpenedInputStream(File file, MIFileSourceType source) throws FileNotFoundException {
        this.file = file;
        stream = new FileInputStream(file);
        this.source = source;
    }

    public InputStream getCopiedStream() {
        return stream;
    }

    public MIFileSourceType getSource() {
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
