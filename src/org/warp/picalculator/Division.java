package org.warp.picalculator;

import static org.warp.engine.Display.Render.getStringWidth;
import static org.warp.engine.Display.Render.glColor3f;
import static org.warp.engine.Display.Render.glDrawStringLeft;
import static org.warp.engine.Display.Render.glFillRect;

import org.nevec.rjm.NumeroAvanzatoVec;
import org.warp.device.PIDisplay;
import org.warp.engine.Display;

public class Division extends FunctionTwoValuesBase {

	public Division(FunctionBase value1, FunctionBase value2) {
		super(value1, value2);
	}

	@Override
	public String getSymbol() {
		return MathematicalSymbols.DIVISION;
	}

	@Override
	public Number solve() throws Error {
		if (variable2 == null || variable1 == null) {
			return new Number("0");
		}
		if (variable2.solve().getTerm().compareTo(NumeroAvanzatoVec.ZERO) == 0) {
			throw new Error(Errors.DIVISION_BY_ZERO);
		}
		return variable1.solve().divide(variable2.solve());
	}

	public boolean hasMinus() {
		String numerator = variable1.toString();
		if (numerator.startsWith("-")) {
			return true;
		}
		return false;
	}

	public void draw(int x, int y, boolean small, boolean drawMinus) {
		boolean beforedrawminus = this.drawMinus;
		this.drawMinus = drawMinus;
		draw(x, y);
		this.drawMinus = beforedrawminus;
	}

	private boolean drawMinus = true;

	@Override
	public void generateGraphics() {
		variable1.setSmall(true);
		variable1.generateGraphics();
		
		variable2.setSmall(true);
		variable2.generateGraphics();
		
		width = calcWidth();
		height = calcHeight();
		line = variable1.getHeight() + 1;
	}
	
	@Override
	public void draw(int x, int y) {
//		glColor3f(255, 127-50+new Random().nextInt(50), 0);
//		glFillRect(x,y,width,height);
//		glColor3f(0, 0, 0);
		
		Object var1 = variable1;
		Object var2 = variable2;
		boolean minus = false;
		int minusw = 0;
		int minush = 0;
		String numerator = ((Function) var1).toString();
		if (numerator.startsWith("-") && ((Function) var1) instanceof Number && ((Number) var1).term.isBigInteger(true)) {
			minus = true;
			numerator = numerator.substring(1);
		}
		int w1 = 0;
		int h1 = 0;
		if (minus) {
			w1 = getStringWidth(numerator);
			h1 = Utils.getFontHeight(small);
		} else {
			w1 = ((Function) var1).getWidth();
			h1 = ((Function) var1).getHeight();
		}
		int w2 = ((Function) var2).getWidth();
		int maxw;
		if (w1 > w2) {
			maxw = 1 + w1;
		} else {
			maxw = 1 + w2;
		}
		if (minus && drawMinus) {
			minusw = getStringWidth("-") + 1;
			minush = Utils.getFontHeight(small);
			if (small) {
				Display.Render.setFont(PIDisplay.fonts[1]);
			} else {
				Display.Render.setFont(PIDisplay.fonts[0]);
			}
			glDrawStringLeft(x+1, y + h1 + 1 + 1 - (minush / 2), "-");
			glDrawStringLeft((int) (x+1 + minusw + 1 + (maxw - w1) / 2d), y, numerator);
		} else {
			((Function) var1).draw((int) (x+1 + minusw + (maxw - w1) / 2d), y);
		}
		((Function) var2).draw((int) (x+1 + minusw + (maxw - w2) / 2d), y + h1 + 1 + 1 + 1);
		glColor3f(0, 0, 0);
		glFillRect(x+1+ minusw, y + h1 + 1, maxw, 1);
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	protected int calcHeight() {

		boolean minus = false;
		String numerator = variable1.toString();
		if (numerator.startsWith("-") && variable1 instanceof Number && ((Number) variable1).term.isBigInteger(true)) {
			minus = true;
			numerator = numerator.substring(1);
		}
		int h1 = 0;
		if (minus) {
			h1 = Utils.getFontHeight(small);
		} else {
			h1 = variable1.getHeight();
		}
		int h2 = variable2.getHeight();
		return h1 + 3 + h2;
	}

	@Override
	public int getLine() {
		return line;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	protected int calcWidth() {
		boolean minus = false;
		String numerator = variable1.toString();
		if (numerator.startsWith("-") && variable1 instanceof Number && ((Number) variable1).term.isBigInteger(true)) {
			minus = true;
			numerator = numerator.substring(1);
		}
		int w1 = 0;
		if (minus) {
			w1 = getStringWidth(numerator);
		} else {
			w1 = variable1.getWidth();
		}
		int w2 = variable2.getWidth();
		int maxw = 0;
		if (w1 > w2) {
			maxw = w1+1;
		} else {
			maxw = w2+1;
		}
		if (minus && drawMinus) {
			return 1 + getStringWidth("-") + 1 + maxw;
		} else {
			return 1 + maxw;
		}
	}
}