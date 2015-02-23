package psidev.psi.mi.jami.xml.io.parser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper of inputStream
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/14</pre>
 */

public class NonCloseableInputStreamWrapper extends InputStream {
    private InputStream delegate;

    public NonCloseableInputStreamWrapper(InputStream delegate){
        super();
        if (delegate == null){
            throw new IllegalArgumentException("The delegate inputstream cannot be null");
        }
        this.delegate = delegate;
    }
    @Override
    public int read() throws IOException {
        return this.delegate.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.delegate.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.delegate.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return this.delegate.skip(n);
    }

    @Override
    public int available() throws IOException {
        return this.delegate.available();
    }

    @Override
    public void close() throws IOException {
        // do not close
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.delegate.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.delegate.reset();
    }

    @Override
    public boolean markSupported() {
        return this.delegate.markSupported();
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }
}
