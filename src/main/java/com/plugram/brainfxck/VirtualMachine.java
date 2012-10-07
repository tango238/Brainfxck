package com.plugram.brainfxck;

/**
 * The virtual machine of brainfack.<br>
 * This doesn't implements the opcode ','.
 * 
 * @see http://www.kmonos.net/alang/etc/brainfuck.php
 * 
 * @author Go Tanaka
 */
public class VirtualMachine {

	private static final int MAX_SIZE = 100;
	
	/** the program counter */
	private int pc = 0;

	/** the source code(instructions) */
	private String source = "";

	/** the address of data */
	private int address = 0;
	
	/** the data */
	private byte[] data = new byte[MAX_SIZE];

	public VirtualMachine() {
	}
	
	public VirtualMachine(String source) {
		this.source = source;
	}
	
	/**
	 * Executes the program.
	 */
	public synchronized void start() {
		int length = source.length();
		while(pc < length){
			char c = source.charAt(pc);
			switch(c){
				case '>':
					next();
					break;
				case '<':
					prev();
					break;
				case '+':
					plus();
					break;
				case '-':
					minus();
					break;
				case '.':
					print();
					break;
				case '[':
					loopStart();
					break;
				case ']':
					loopEnd();
					break;
			}
		}
	}

	/**
	 * Returns the address.
	 * @return
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * @param address
	 */
	public void setAddress(int address) {
		this.address = address;
	}

	/**
	 * Returns the value that the address refers to.
	 * @return
	 */
	public byte getValue(int address) {
		return data[address];
	}

	/**
	 * Sets the value that the address refers to.
	 * @param value
	 */
	public void setValue(int address, byte value) {
		this.data[address] = value;
	}
	
	/**
	 * Returns current program counter.
	 * @return
	 */
	public int currentPc() {
		return pc;
	}
	
	/**
	 * Returns next opcode.
	 * @return
	 */
	public char getNextOpcode(){
		return source.charAt(pc);
	}

	/**
	 * Returns the source code.
	 * @return
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Sets the source code.
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * The opcode of 「&gt;」.
	 */
	public void next() {
		this.address++;
		this.pc++;
	}

	/**
	 * The opcode of 「&lt;」.
	 */
	public void prev() {
		this.address--;
		this.pc++;
	}

	/**
	 * The opcode of 「+」.
	 */
	public void plus() {
		data[address]++;
		this.pc++;
	}
	
	/**
	 * The opcode of 「-」.
	 */
	public void minus() {
		data[address]--;
		this.pc++;
	}

	/**
	 * The opcode of 「.」.<br>
	 * This will prints out the value as character.
	 */
	public void print() {
		System.out.print((char)data[address]);
		this.pc++;
	}
	
	public void loopStart(){
		if(data[address] == (byte)0){
			gotoLoopEnd();
		}
		this.pc++;
	}

	private void gotoLoopEnd() {
		try {
			while(source.charAt(pc) != ']'){
				this.pc++;
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.err.println("loopStart: Execution error.");
		}
	}

	public void loopEnd() {
		try {
			while(source.charAt(pc) != '['){
				this.pc--;
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.err.println("loopEnd: Execution error.");
		}
	}
}
