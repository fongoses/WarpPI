package org.warp.picalculator.math.functions;

import java.util.ArrayList;

import org.warp.picalculator.Error;
import org.warp.picalculator.Errors;
import org.warp.picalculator.Utils;
import org.warp.picalculator.device.graphicengine.Display;
import org.warp.picalculator.math.Calculator;
import org.warp.picalculator.math.MathematicalSymbols;
import org.warp.picalculator.math.rules.ExpandRule1;
import org.warp.picalculator.math.rules.ExpandRule5;

public class Negative extends AnteriorFunction {

	public Negative(Calculator root, Function value) {
		super(root, value);
	}

	@Override
	public Function NewInstance(Calculator root, Function value) {
		return new Negative(root, value);
	}

	@Override
	public String getSymbol() {
		return MathematicalSymbols.MINUS;
	}
	
	@Override
	public void generateGraphics() {
		variable.setSmall(small);
		variable.generateGraphics();
		
		height = getVariable().getHeight();
		width = Display.Render.glGetStringWidth(Utils.getFont(small), "-") + getVariable().getWidth();
		line = getVariable().getLine();
	}

	@Override
	protected boolean isSolvable() {
		if (variable instanceof Number) return true;
		if (ExpandRule1.compare(this)) return true;
		if (ExpandRule5.compare(this)) return true;
		return false;
	}
	
	@Override
	public ArrayList<Function> solve() throws Error {
		if (variable == null) {
			throw new Error(Errors.SYNTAX_ERROR);
		}
		ArrayList<Function> result = new ArrayList<>();
		if (ExpandRule1.compare(this)) {
			result = ExpandRule1.execute(this);
		} else if (ExpandRule5.compare(this)) {
			result = ExpandRule5.execute(this);
		} else if (variable.isSolved()) {
			try {
				Number var = (Number) getVariable();
				result.add(var.multiply(new Number(root, "-1")));
			} catch(NullPointerException ex) {
				throw new Error(Errors.ERROR);
			} catch(NumberFormatException ex) {
				throw new Error(Errors.SYNTAX_ERROR);
			} catch(ArithmeticException ex) {
				throw new Error(Errors.NUMBER_TOO_SMALL);
			}
		} else {
			ArrayList<Function> l1 = new ArrayList<>();
			if (variable.isSolved()) {
				l1.add(variable);
			} else {
				l1.addAll(variable.solveOneStep());
			}
			
			for (Function f : l1) {
				result.add(new Negative(root, f));
			}
		}
		return result;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getLine() {
		return line;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Negative) {
			return ((Negative)o).variable.equals(variable);
		}
		return false;
	}
}
