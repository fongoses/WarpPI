package it.cavallium.warppi.math.rules.dsl.patterns;

import it.cavallium.warppi.math.Function;
import it.cavallium.warppi.math.MathContext;
import it.cavallium.warppi.math.functions.trigonometry.ArcTangent;
import it.cavallium.warppi.math.rules.dsl.Pattern;
import it.cavallium.warppi.math.rules.dsl.VisitorPattern;

import java.util.Map;
import java.util.Optional;

/**
 * Matches and generates the arctangent of another pattern.
 */
public class ArcTangentPattern extends VisitorPattern {
	private final Pattern argument;

	public ArcTangentPattern(final Pattern argument) {
		this.argument = argument;
	}

	@Override
	public Optional<Map<String, Function>> visit(final ArcTangent arcTangent) {
		return argument.match(arcTangent.getParameter());
	}

	@Override
	public Function replace(final MathContext mathContext, final Map<String, Function> subFunctions) {
		return new ArcTangent(
				mathContext,
				argument.replace(mathContext, subFunctions)
		);
	}
}
