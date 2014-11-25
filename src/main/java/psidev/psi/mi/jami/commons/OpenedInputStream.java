package psidev.psi.mi.jami.commons;

import java.io.*;

/**
 * The openedInputStream contains a PushbackReader reader that can read the opened input stream
 * and the type of dataSourceFile it represents.
 *
 * It needs to be closed after being used.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class OpenedInputStream {

    private PushbackReader reader;
    private MIFileType source;

    public OpenedInputStream(PushbackReader reader, MIFileType source) throws FileNotFoundException {
        this.reader = reader;
        this.source = source;
    }

    public PushbackReader getReader() {
        return reader;
    }

    public MIFileType getSource() {
        return source;
    }

    public void close() throws IOException {
        if (this.reader != null){
            reader.close();
        }
    }
}
