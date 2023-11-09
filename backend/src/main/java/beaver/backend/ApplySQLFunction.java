package beaver.backend;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class ApplySQLFunction implements FunctionContributor {

    private static final String FUNCTION_NAME = "haversine";
    private static final String FUNCTION_PATTERN = "haversine(?1, ?2, ?3, ?4, ?5)";

    @Override
    public void contributeFunctions(final FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
                .registerPattern(FUNCTION_NAME, FUNCTION_PATTERN,
                        functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(StandardBasicTypes.DOUBLE));
    }
}
