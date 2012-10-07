package com.plugram.brainfxck;

/**
 * Prints out 'Hello World!'.
 * 
 * @author Go Tanaka
 */
public class HelloWorld {

	public static void main(String[] args) {
		new HelloWorld().start();
	}

	private void start() {
		String source = ">+++++++++[<++++++++>-]<.>+++++++[<++++>-]<+.+++++++..+++.[-]>++++++++[<++++>-]<.>+++++++++++[<+++++>-]<.>++++++++[<+++>-]<.+++.------.--------.[-]>++++++++[<++++>-]<+.[-]++++++++++.";
		VirtualMachine vm = new VirtualMachine(source);
		vm.start(); // prints out 'Hello World!'
	}
}
