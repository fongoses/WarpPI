package it.cavallium.warppi.math.functions;

import it.cavallium.warppi.Error;
import it.cavallium.warppi.gui.expression.blocks.Block;
import it.cavallium.warppi.math.Function;
import it.cavallium.warppi.math.MathContext;
import it.cavallium.warppi.math.rules.Rule;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class EmptyNumber implements Function {

	public EmptyNumber(MathContext root) {
		this.root = root;
	}

	private final MathContext root;

	@Override
	public ObjectArrayList<Function> simplify(Rule rule) throws Error, InterruptedException {
		return rule.execute(this);
	}

	@Override
	public MathContext getMathContext() {
		return root;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof EmptyNumber;
	}

	@Override
	public Function clone() {
		return new EmptyNumber(root);
	}

	@Override
	public Function setParameter(int index, Function var) throws IndexOutOfBoundsException {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public Function getParameter(int index) throws IndexOutOfBoundsException {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public ObjectArrayList<Block> toBlock(MathContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}