package br.com.chatBotCalculadoraCientifica.view;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;


import com.deriv.calculator.Calculator;

//import java.util.Scanner;
//
//import org.jdice.calc.Calculator;
//
//import javacalculus.core.CALC;
//import javacalculus.core.CalcParser;
//import javacalculus.evaluator.CalcSUB;
//import javacalculus.struct.CalcDouble;
//import javacalculus.struct.CalcObject;
//import javacalculus.struct.CalcSymbol;

//import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
//import org.apache.commons.math.ode.AbstractIntegrator;
//import org.apache.commons.math.ode.DerivativeException;
//import org.mariuszgromada.math.mxparser.*;

public class TesteCalculo {

	public static void main(String[] args) throws Exception {
		
//		// soma
//		Expression e = new Expression("2+3+4");
//		System.out.println("Resultado " + e.calculate());
//		
//		//subtracao
//		Expression e1 = new Expression("2-3-4");
//		System.out.println("Resultado " + e1.calculate());
//		
//		//raiz
//		Expression e2 = new Expression("sqrt(4)");
//		System.out.println("Resultado " + e2.calculate());
//		
//		//geral
//		Expression e3 = new Expression(" 2^2 -(4* sqrt(4))");
//		System.out.println("Resultado " + e3.calculate());
//		
//		// sin
//		Expression e4 = new Expression("  sin(0)");
//		System.out.println("Resultado " + e4.calculate());
//		
		// cos
//		Expression e5 = new Expression("  cos(pi)");
//		System.out.println("Resultado " + e5.calculate());
//	
//		// log
//		Expression e6 = new Expression("  log2(4) + tan(pi) ");
//	    System.out.println("Resultado com, log " + e6.calculate());
//				
//	    // divisao
//	 	Expression e7 = new Expression(" 2/2");
//	 	   System.out.println("Resultado divisao " + e7.calculate());
//		
		// integral polinomios
//	    Expression et = new Expression("2 * int( sqrt(1-x^2), x, -1, 1 )");
//		System.out.println("Resultado integral " + et.calculate());
//		
//		
		// integral polinomios
	    Expression et2 = new Expression("int(sin(3*x^2) + 1*x^2, x, -0, 2 )");
		System.out.println("Resultado integral2 " + et2.calculate());
	    
		
		//derivada polinomios
 
//		Expression ed = new Expression("der(tan(2*x*pi)+2*x^4,x)",new Argument("x=1"));
//		System.out.println("Resultado derivada 2 " + ed.calculate());
		
		//derivada polinomios
//		Expression ed2 = new Expression("der(2*x^2,x,1)");
//		System.out.println("Resultado derivada " + ed2.calculate());
		

		
		Calculator calc = new Calculator();
		String a = calc.differentiate("ln(x^2)/e^(x^2) + (3*x)^4x", "x").get().toString();
		System.out.println(a);
		
		Calculator calc2 = new Calculator();
		String a1 = calc2.differentiate("ln(x^2) + tan(3*x^4) + 2", "x").get().toString();
		System.out.println(a1);
	}
}
