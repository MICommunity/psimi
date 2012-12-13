package psidev.psi.mi.xml.xmlindex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provide indexing facilities for PSI-MI XML content.
 * <p/>
 * The generated index contains the begin and end position of interactions, experiments, interactors, participants and
 * features.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlIndexer {

    public static final Log log = LogFactory.getLog( PsimiXmlIndexer.class );

    /**
     * Indexes references component of the given file. that is experiments, interaction, interactor, feature and
     * participant so that we know where they are in the file and we can jump in the right position should we want to
     * extract one of them.
     *
     * @param f the file to index.
     * @return a PsimiXmlFileIndex that stores the mapping between object ids and their position.
     * @throws IOException
     */
    public PsimiXmlFileIndex build( File f ) throws IOException {

        // TODO buffer the reading of the file and provide a method nextChar() instead of calling read()
        long start = System.currentTimeMillis();
        PsimiXmlFileIndex index = new PsimiXmlFileIndex( f );

        long length = f.length();
        if ( log.isInfoEnabled() ) {
            log.info( "length = " + length );
        }

        FileInputStream fis = new FileInputStream( f );
        Reader r = new BufferedReader( new InputStreamReader( fis ) );

        long offset = 0L;

        // set position in the file
        fis.getChannel().position( offset );

        char[] c = new char[1];

        int idx = 0;
        int read;
        boolean recording = false;

        char x = ' ';
        StringBuilder sb = null;


        Stack<Integer> idStack = new Stack<Integer>();
        int currentId = -1;

        while ( -1 != ( read = r.read( c, 0, 1 ) ) ) {
            idx += read;
            x = c[0];

            if ( recording ) {
                if ( !isAlphabeticalChar( x ) ) {
                    if ( x == '/' ) {

                        // search for '>' and that's our position
                        while ( -1 != ( read = r.read( c, 0, 1 ) ) ) {
                            idx += read;
                            x = c[0];
                            if ( isAlphabeticalChar( x ) ) {
                                sb.append( x );
                            } else if ( x == '>' ) {
                                break;
                            }
                        }

                        long position = offset + idx;

                        if ( "experimentDescription".equalsIgnoreCase( sb.toString() ) ) {

                            currentId = idStack.pop();
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": end:" + position );
                            }
                            InputStreamRange range = index.getExperimentPosition( currentId );
                            range.setToPosition( position );

                        } else if ( "interactor".equalsIgnoreCase( sb.toString() ) ) {

                            currentId = idStack.pop();
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": end:" + position );
                            }
                            InputStreamRange range = index.getInteractorPosition( currentId );
                            range.setToPosition( position );

                        } else if ( "interaction".equalsIgnoreCase( sb.toString() ) ) {

                            currentId = idStack.pop();
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": end:" + position );
                            }
                            InputStreamRange range = index.getInteractionPosition( currentId );
                            range.setToPosition( position );

                        } else if ( "participant".equalsIgnoreCase( sb.toString() ) ) {

                            currentId = idStack.pop();
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": end:" + position );
                            }
                            InputStreamRange range = index.getParticipantPosition( currentId );
                            range.setToPosition( position );

                        } else if ( "feature".equalsIgnoreCase( sb.toString() ) ) {

                            currentId = idStack.pop();
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": end:" + position );
                            }
                            InputStreamRange range = index.getFeaturePosition( currentId );
                            range.setToPosition( position );
                        }

                        // it was a closing tag (<abc/> or </abc> ... problem will occur with <abc />)
                        recording = false;

                    } else if ( x == '!' ) {

                        // TODO BUGFIX, this should only get executed if the rprevious char was '<'.
                        // This is the beginning of a comments.
                        // Now fast forward until the end of the comment: '-->'
                        recording = false;

                        char c1 = ' ',
                                c2 = ' ',
                                c3 = ' ';
                        log.debug( "Skiping comment" );
                        while ( -1 != ( read = r.read( c, 0, 1 ) ) ) {
                            idx += read;

                            c1 = c2;
                            c2 = c3;
                            c3 = c[0];

                            if ( c1 == '-' && c2 == '-' && c3 == '>' ) {
                                // found it
                                log.debug( "End of comment" );
                                break;
                            }
                        }

                    } else {
                        // check what start tag it is

                        long position = offset + idx - sb.length() - 2; // -1 as the char '<' is not in the buffer

                        if ( "experimentDescription".equalsIgnoreCase( sb.toString() ) ) {

                            int[] result = getId( r, c );
                            currentId = result[0];
                            idStack.push( currentId );
                            idx += result[1];
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": " + position );
                            }
                            InputStreamRange range = new InputStreamRange();
                            range.setFromPosition( position );
                            index.addExperiment( currentId, range );

                        } else if ( "interactor".equalsIgnoreCase( sb.toString() ) ) {

                            int[] result = getId( r, c );
                            currentId = result[0];
                            idStack.push( currentId );
                            idx += result[1];
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": " + position );
                            }
                            InputStreamRange range = new InputStreamRange();
                            range.setFromPosition( position );
                            index.addInteractor( currentId, range );

                        } else if ( "interaction".equalsIgnoreCase( sb.toString() ) ) {

                            int[] result = getId( r, c );
                            currentId = result[0];
                            idStack.push( currentId );
                            idx += result[1];
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": " + position );
                            }
                            InputStreamRange range = new InputStreamRange();
                            range.setFromPosition( position );
                            index.addInteraction( currentId, range );

                        } else if ( "participant".equalsIgnoreCase( sb.toString() ) ) {

                            int[] result = getId( r, c );
                            currentId = result[0];
                            idStack.push( currentId );
                            idx += result[1];
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": " + position );
                            }
                            InputStreamRange range = new InputStreamRange();
                            range.setFromPosition( position );
                            index.addParticipant( currentId, range );

                        } else if ( "feature".equalsIgnoreCase( sb.toString() ) ) {

                            int[] result = getId( r, c );
                            currentId = result[0];
                            idStack.push( currentId );
                            idx += result[1];
                            if ( log.isDebugEnabled() ) {
                                log.debug( "'" + sb.toString() + "' id=" + currentId + ": " + position );
                            }
                            InputStreamRange range = new InputStreamRange();
                            range.setFromPosition( position );
                            index.addFeature( currentId, range );
                        }

                        recording = false;
                    }
                } else {
                    // add alphabetical char
                    sb.append( x );
                }
            }

            if ( x == '<' ) {
                // start recording
                recording = true;
                sb = new StringBuilder( 50 );
            }

        } // while can read

        long stop = System.currentTimeMillis();

        if ( log.isInfoEnabled() ) {
            log.info( "Time elapsed: " + ( stop - start ) + "ms" );
        }

        r.close();

        return index;
    }

    /**
     * Captures the identifier from a String looking like: id="1"
     */
    private static final Pattern ID_PATTERN = Pattern.compile( "id(?:\\s*)=(?:\\s*)\"(\\d*)\"", Pattern.CANON_EQ );

    private int[] getId( Reader r, char[] c ) throws IOException {

        int id = -1;
        int idx = 0;
        int read;

        StringBuilder sb = new StringBuilder( 20 );
        while ( -1 != ( read = r.read( c, 0, 1 ) ) ) {

            idx += read;

            char x = c[0];
            if ( x == '>' ) {
                // completed the tag, extract the id
                Matcher matcher = ID_PATTERN.matcher( sb.toString() );
                if ( matcher.matches() ) {
                    String strId = matcher.group( 1 );
                    id = Integer.parseInt( strId );
                }

                break; // stop here
            } else {
                sb.append( x );
            }
        }

        return new int[]{id, idx};
    }

    private boolean isAlphabeticalChar( char c ) {
        return ( ( c >= 'a' && c <= 'z' ) || ( ( c >= 'A' && c <= 'Z' ) ) );
    }
}
