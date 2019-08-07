package it.cavallium.warppi.math.functions.trigonometry;

import it.cavallium.warppi.gui.expression.blocks.Block;
import it.cavallium.warppi.math.Function;
import it.cavallium.warppi.math.FunctionSingle;
import it.cavallium.warppi.math.MathContext;
import it.cavallium.warppi.util.Error;
import it.cavallium.warppi.util.Errors;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class Cosine extends FunctionSingle {

	public Cosine(final MathContext root, final Function value) {
		super(root, value);
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof Cosine) {
			final FunctionSingle f = (FunctionSingle) o;
			if (parameter.equals(f.getParameter())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public FunctionSingle clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionSingle clone(MathContext c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectArrayList<Block> toBlock(final MathContext context) throws Error {
		// TODO Auto-generated method stub
		throw new Error(Errors.NOT_IMPLEMENTED, "Unknown function " + getClass().getSimpleName());
	}

	@Override
	public <T> T accept(final Function.Visitor<T> visitor) {
		return visitor.visit(this);
	}

}
