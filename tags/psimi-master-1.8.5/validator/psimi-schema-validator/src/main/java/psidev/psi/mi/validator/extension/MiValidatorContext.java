package psidev.psi.mi.validator.extension;

/**
 * MiValidator context.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class MiValidatorContext {

    private static ThreadLocal<MiValidatorContext> instance = new
            ThreadLocal<MiValidatorContext>() {
                @Override
                protected MiValidatorContext initialValue() {
                    return new MiValidatorContext();
                }
            };

    private MiValidatorConfig validatorConfig;

    private MiValidatorContext() {
        this.validatorConfig = new MiValidatorConfig();
    }

    public static MiValidatorContext getCurrentInstance() {
        return instance.get();
    }

    public MiValidatorConfig getValidatorConfig() {
        return validatorConfig;
    }
}
