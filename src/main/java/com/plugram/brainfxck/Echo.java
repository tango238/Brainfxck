package com.plugram.brainfxck;

public class Echo {

	public static void main(String[] args) {
		String source = "+[>,.<-]";
		VirtualMachine vm = new VirtualMachine(source);
		vm.start();
	}

}
