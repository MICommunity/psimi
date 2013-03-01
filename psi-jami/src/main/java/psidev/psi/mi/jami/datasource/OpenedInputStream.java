package psidev.psi.mi.jami.datasource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The openedInputStream is an inputStream that is wrapping an opened inputStream and the first line that was read to identify the molecular interaction source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class OpenedInputStream extends InputStream {

    private String firstLine;
    private String nextLine;
    private BufferedReader reader;
    private boolean hasReadFirstLine=false;
    private InputStream firstLineInputStream;
    private InputStream nextLineInputStream;

    private MolecularInteractionSource source;

    public OpenedInputStream(String firstLine, BufferedReader openedStream, MolecularInteractionSource source){
        this.firstLine = firstLine;
        if (firstLine == null){
            hasReadFirstLine = true;
        }
        if (openedStream == null){
            throw new IllegalArgumentException("The opened reader cannot be null");
        }
        this.reader = openedStream;
        this.source = source;
    }

    @Override
    public int read() throws IOException {

        if (hasReadFirstLine){
            if (nextLineInputStream == null){
                nextLineInputStream = readNextLine();
            }

            return nextLineInputStream.read();
        }
        else {
            if (firstLineInputStream == null){
                firstLineInputStream = readLine();
            }

            int charRead = firstLineInputStream.read();
            // we reached the end of firstresults
            if (charRead == -1){
                hasReadFirstLine = true;
                if (nextLineInputStream == null){
                    nextLineInputStream = readNextLine();
                }

                return nextLineInputStream.read();
            }

            return charRead;
        }
    }

    @Override
    public int read(byte[] bytes) throws java.io.IOException {

        return read(bytes, 0, bytes.length);
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws java.io.IOException {
        if (bytes == null){
            throw new NullPointerException("The array of bytes to read for the OpenedInputStream is null.");
        }
        if (off < 0){
            throw new IndexOutOfBoundsException("The off value to read the OpenedInputStream cannot be negative " + off);
        }
        if (len < 0){
            throw new IndexOutOfBoundsException("The len value to read the OpenedInputStream cannot be negative " + len);
        }
        if (len < bytes.length - off ){
            throw new IndexOutOfBoundsException("The len value to read the OpenedInputStream cannot be inferior to bytes.length - off. Length = " + len + ", bytes.length - off = " + Integer.toString(bytes.length - off));
        }
        if (len == 0){
            return 0;
        }

        if (hasReadFirstLine){
            if (nextLineInputStream == null){
                nextLineInputStream = readNextLine();
            }

            int numberCharRead = Math.max(0, nextLineInputStream.read(bytes, off, len));

            // we need to read the next openedStream line because we reached the end of the current one
            while (numberCharRead < len && nextLineInputStream != null){
                nextLineInputStream = readNextLine();

                if (nextLineInputStream != null){
                    int newNumberCharRead = Math.max(0,nextLineInputStream.read(bytes, off + numberCharRead, len - numberCharRead));
                    numberCharRead+=newNumberCharRead;
                }
            }

            if (numberCharRead == 0){
                return -1;
            }

            return numberCharRead;
        }
        else {
            if (firstLineInputStream == null){
                firstLineInputStream = readLine();
            }

            int numberCharRead = Math.max(0, firstLineInputStream.read(bytes, off, len));

            // we reached the end of firstresults
            if (numberCharRead < len){
                hasReadFirstLine = true;
                nextLineInputStream = readNextLine();
            }

            // we need to read the next openedStream line because we reached the end of the current one
            while (numberCharRead < len && nextLineInputStream != null){
                nextLineInputStream = readNextLine();

                if (nextLineInputStream != null){
                    int newNumberCharRead = Math.max(0,nextLineInputStream.read(bytes, off + numberCharRead, len - numberCharRead));
                    numberCharRead+=newNumberCharRead;
                }
            }

            if (numberCharRead == 0){
                return -1;
            }

            return numberCharRead;
        }
    }

    @Override
    public int available() throws java.io.IOException {

        if (hasReadFirstLine){
            if (nextLineInputStream == null){
                nextLineInputStream = readNextLine();
            }

            if (nextLineInputStream != null){
                return nextLineInputStream.available();
            }
        }
        else{
            if (firstLineInputStream == null){
                firstLineInputStream = readLine();
            }
            int available = firstLineInputStream.available();

            if (available == 0){
                if (nextLineInputStream == null){
                    nextLineInputStream = readNextLine();
                }

                if (nextLineInputStream != null){
                    return nextLineInputStream.available();
                }
            }
        }

        return 0;
    }

    private InputStream readLine() throws IOException {

        return new ByteArrayInputStream(firstLine.getBytes());
    }

    private InputStream readNextLine() throws IOException {

        if (nextLine != null){
            nextLine = reader.readLine();

            if (nextLine != null){
                return new ByteArrayInputStream(nextLine.getBytes());
            }
        }

        return null;
    }

    @Override
    public void close() throws IOException {
        reader.close();

        if (firstLineInputStream != null){
            firstLineInputStream.close();
        }
        if (nextLineInputStream != null){
            nextLineInputStream.close();
        }
    }

    public MolecularInteractionSource getSource() {
        return source;
    }
}
