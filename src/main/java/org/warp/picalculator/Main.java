package org.warp.picalculator;

import org.warp.picalculator.device.Keyboard;
import org.warp.picalculator.device.Keyboard.Key;
import org.warp.picalculator.gui.DisplayManager;
import org.warp.picalculator.gui.screens.LoadingScreen;
import org.warp.picalculator.gui.screens.Screen;

import com.pi4j.system.SystemInfo.BoardType;
import com.pi4j.wiringpi.Gpio;

public class Main {
	public static Main instance;
	public static String[] args;
	public Main(String[] args) throws InterruptedException {
		this(new LoadingScreen(), args);
	}

	public Main(Screen screen, String[] args) {
		System.out.println("WarpPI Calculator");
		instance = this;
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		Thread.currentThread().setName("Main thread");
		Main.args = args;
		beforeStart();
		new DisplayManager(screen);
		afterStart();
		DisplayManager.INSTANCE.waitForExit();
		Utils.out.println(1, "Shutdown...");
		beforeShutdown();
		Utils.out.println(1, "");
		Utils.out.println(1, "Closed.");
		System.exit(0);
	}

	public void beforeStart() {
		boolean isRaspi = false;
		try {
			isRaspi = com.pi4j.system.SystemInfo.getBoardType() != BoardType.UNKNOWN;
		} catch (Exception e) {}
		if (Utils.isRunningOnRaspberry() && !Utils.isInArray("-noraspi", args) && isRaspi) {
			Gpio.wiringPiSetupPhys();
			Gpio.pinMode(12, Gpio.PWM_OUTPUT);
		} else {
			StaticVars.screenPos = new int[] { 0, 0 };
			StaticVars.debugOn = true;
		}
		Utils.debugThirdScreen = StaticVars.debugOn & false;
		for (String arg : args) {
			if (arg.contains("headless")) {
				Utils.headlessOverride = true;
			}
			if (arg.contains("headless-8")) {
				Utils.headlessOverride = true;
				Utils.forceEngine = "console-8";
			}
			if (arg.contains("headless-256")) {
				Utils.headlessOverride = true;
				Utils.forceEngine = "console-256";
			}
			if (arg.contains("headless-24bit")) {
				Utils.headlessOverride = true;
				Utils.forceEngine = "console-24bit";
			}
			if (arg.contains("cpu")) {
				Utils.forceEngine = "cpu";
			}
			if (arg.contains("gpu")) {
				Utils.forceEngine = "gpu";
			}
			if (arg.contains("ms-dos")) {
				Utils.headlessOverride = true;
				Utils.msDosMode = true;
			}
		}
		Keyboard.startKeyboard();
	}

	public void afterStart() {
		DisplayManager.INSTANCE.setBrightness(0.2f);
	}

	public void beforeShutdown() {
		Keyboard.stopKeyboard();
	}

	public static void main(String[] args) throws InterruptedException {
		/*
		 * TEST: Comparing BigIntegerMath.divisors() vs programmingpraxis' Number.getFactors() function
		 * 
		long time1;
		long time2;
		final int max = 10000;
		final long HCN = 720720L;
		final long LCN = 104911L;
		final BigInteger[] bigintegers = new BigInteger[max];
		bigintegers[0] = BigInteger.valueOf(HCN);
		for (int i = 0; i < max; i++) {
			bigintegers[i] = bigintegers[0];
		}
		final Number[] numbers = new Number[max];
		final MathContext mc = new MathContext();
		numbers[0] = new Number(mc, HCN);
		for (int i = 0; i < max; i++) {
			numbers[i] = numbers[0];
		}
		Vector<BigInteger> empty = null;
		LinkedList<BigInteger> empty2 = null;
		
		time1 = System.currentTimeMillis();
		for(int i = 0; i < max; i++) {
			empty = BigIntegerMath.divisors(bigintegers[i]);
		}
		time2 = System.currentTimeMillis();
		System.out.println("BigIntegerMath HCN: "+(time2-time1)+" ("+empty.toString()+")");
		
		
		bigintegers[0] = BigInteger.valueOf(LCN);
		for (int i = 0; i < max; i++) {
			bigintegers[i] = bigintegers[0];
		}
		
		time1 = System.currentTimeMillis();
		for(int i = 0; i < max; i++) {
			empty = BigIntegerMath.divisors(bigintegers[i]);
		}
		time2 = System.currentTimeMillis();
		System.out.println("BigIntegerMath LCN: "+(time2-time1)+" ("+empty.toString()+")");
		
		time1 = System.currentTimeMillis();
		for(int i = 0; i < max; i++) {
			empty2 = numbers[i].getFactors();
		}
		time2 = System.currentTimeMillis();
		System.out.println("BigIntegerMath HCN: "+(time2-time1)+" ("+empty2.toString()+")");
		
		numbers[0] = new Number(mc, LCN);
		for (int i = 0; i < max; i++) {
			numbers[i] = numbers[0];
		}
		time1 = System.currentTimeMillis();
		for(int i = 0; i < max; i++) {
			empty2 = numbers[i].getFactors();
		}
		time2 = System.currentTimeMillis();
		System.out.println("BigIntegerMath LCN: "+(time2-time1)+" ("+empty2.toString()+")");
		if(true) {
			System.exit(0);;
		}
		*/
		new Main(args);
	}
}
