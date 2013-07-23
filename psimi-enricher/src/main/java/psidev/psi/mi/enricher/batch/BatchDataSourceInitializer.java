package psidev.psi.mi.enricher.batch;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * This class will initialize the batch database using external scripts containing sql commands to create the tables necessary for running Spring batch jobs.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/12</pre>
 */

public class BatchDataSourceInitializer implements org.springframework.beans.factory.InitializingBean, org.springframework.beans.factory.DisposableBean{

    private static final Log logger = LogFactory.getLog(BatchDataSourceInitializer.class);

    private Resource[] initScripts;

    private Resource[] destroyScripts;

    private DataSource dataSource;

    private boolean ignoreFailedCreate = true;
    private boolean ignoreFailedDrop = true;

    private boolean initialized = false;

    /**
     * @throws Throwable
     * @see Object#finalize()
     */
    protected void finalize() throws Throwable {
        super.finalize();
        initialized = false;
        logger.debug("finalize called");
    }

    public void destroy() {
        if (destroyScripts == null) return;
        for (int i = 0; i < destroyScripts.length; i++) {
            Resource destroyScript = initScripts[i];
            try {
                doExecuteScript(destroyScript);
            }
            catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.warn("Could not execute destroy script [" + destroyScript + "]", e);
                } else {
                    logger.warn("Could not execute destroy script [" + destroyScript + "]");
                }
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(dataSource);
        initialize();
    }

    /**
     * Run the initialize scripts
     */
    private void initialize() {
        if (!initialized) {
            destroy();
            if (initScripts != null) {
                for (int i = 0; i < initScripts.length; i++) {
                    Resource initScript = initScripts[i];
                    doExecuteScript(initScript);
                }
            }
            initialized = true;
        }
    }

    /**
     * execute the scripts
     * @param scriptResource
     */
    private void doExecuteScript(final Resource scriptResource) {
        if (scriptResource == null || !scriptResource.exists())
            throw new IllegalStateException("Script is null or resource does not exist: "+scriptResource );

        TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        transactionTemplate.execute(new TransactionCallback() {

            @SuppressWarnings("unchecked")
            public Object doInTransaction(TransactionStatus status) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String[] scripts;
                try {
                    scripts = StringUtils.delimitedListToStringArray(stripComments(IOUtils.readLines(scriptResource
                            .getInputStream())), ";");
                }
                catch (IOException e) {
                    throw new BeanInitializationException("Cannot load script from [" + scriptResource + "]", e);
                }
                for (int i = 0; i < scripts.length; i++) {
                    String script = scripts[i].trim();
                    if (StringUtils.hasText(script)) {
                        try {
                            jdbcTemplate.execute(script);
                        }
                        catch (DataAccessException e) {
                            if (ignoreFailedDrop && script.toLowerCase().startsWith("drop")) {
                                logger.debug("DROP script failed (ignoring): " + script);
                            } else if (ignoreFailedCreate) {
                                logger.debug("Ignoring failed create. Database probably exists");
                                break;
                            } else {
                                throw e;
                            }
                        }
                    }
                }
                return null;
            }

        });

    }

    private String stripComments(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (String line : list) {
            if (!line.startsWith("//") && !line.startsWith("--")) {
                buffer.append(line + "\n");
            }
        }
        return buffer.toString();
    }

    public void setInitScripts(Resource[] initScripts) {
        this.initScripts = initScripts;
    }

    public void setDestroyScripts(Resource[] destroyScripts) {
        this.destroyScripts = destroyScripts;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setIgnoreFailedDrop(boolean ignoreFailedDrop) {
        this.ignoreFailedDrop = ignoreFailedDrop;
    }

}
