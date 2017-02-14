package org.warp.picalculator.math.functions;

import java.util.ArrayList;

import org.warp.picalculator.Error;
import org.warp.picalculator.Utils;
import org.warp.picalculator.gui.DisplayManager;
import org.warp.picalculator.gui.graphicengine.cpu.CPUEngine;
import org.warp.picalculator.gui.math.GraphicalElement;
import org.warp.picalculator.math.MathContext;
import org.warp.picalculator.math.Function;
import org.warp.picalculator.math.FunctionOperator;
import org.warp.picalculator.math.MathematicalSymbols;
import org.warp.picalculator.math.rules.FractionsRule1;
import org.warp.picalculator.math.rules.FractionsRule11;
import org.warp.picalculator.math.rules.FractionsRule12;
import org.warp.picalculator.math.rules.FractionsRule2;
import org.warp.picalculator.math.rules.FractionsRule3;
import org.warp.picalculator.math.rules.UndefinedRule2;

public class Division extends FunctionOperator {

	public Division(MathContext root, Function value1, Function value2) {
		super(root, value1, value2);
	}

	@Override
	protected boolean isSolvable() {
		Function variable1 = getParameter1();
		Function variable2 = getParameter2();
		if (FractionsRule1.compare(this)) {
			return true;
		}
		if (FractionsRule2.compare(this)) {
			return true;
		}
		if (FractionsRule3.compare(this)) {
			return true;
		}
		if (FractionsRule11.compare(this)) {
			return true;
		}
		if (FractionsRule12.compare(this)) {
			return true;
		}
		if (UndefinedRule2.compare(this)) {
			return true;
		}
		if (variable1 instanceof Number && variable2 instanceof Number) {
			if (getMathContext().exactMode) {
				try {
					return ((Number)variable1).divide((Number)variable2).isInteger();
				} catch (Error e) {
					return false;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<Function> solve() throws Error {
		Function variable1 = getParameter1();
		Function variable2 = getParameter2();
		ArrayList<Function> result = new ArrayList<>();
		if (FractionsRule1.compare(this)) {
			result = FractionsRule1.execute(this);
		} else if (FractionsRule2.compare(this)) {
			result = FractionsRule2.execute(this);
		} else if (FractionsRule3.compare(this)) {
			result = FractionsRule3.execute(this);
		} else if (FractionsRule11.compare(this)) {
			result = FractionsRule11.execute(this);
		} else if (FractionsRule12.compare(this)) {
			result = FractionsRule12.execute(this);
		} else if (UndefinedRule2.compare(this)) {
			result = UndefinedRule2.execute(this);
		} else if (variable1 instanceof Number && variable2 instanceof Number) {
			result.add(((Number) variable1).divide((Number) variable2));
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Division) {
			final FunctionOperator f = (FunctionOperator) o;
			return getParameter1().equals(f.getParameter1()) && getParameter2().equals(f.getParameter2());
		}
		return false;
	}

	@Override
	public FunctionOperator clone() {
		return new Division(this.getMathContext(), this.getParameter1(), this.getParameter2());
	}
}