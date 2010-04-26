package psidev.psi.mi.validator.extension;

/**
 * Validator context.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class Mi25ValidatorContext {

    private static ThreadLocal<Mi25ValidatorContext> instance = new
            ThreadLocal<Mi25ValidatorContext>() {
                @Override
                protected Mi25ValidatorContext initialValue() {
                    return new Mi25ValidatorContext();
                }
            };

    private Mi25ValidatorConfig validatorConfig;

    private Mi25ValidatorContext() {
        this.validatorConfig = new Mi25ValidatorConfig();
    }

    public static Mi25ValidatorContext getCurrentInstance() {
        return instance.get();
    }

    public Mi25ValidatorConfig getValidatorConfig() {
        return validatorConfig;
    }
}
