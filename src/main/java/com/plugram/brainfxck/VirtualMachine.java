package com.plugram.brainfxck;

import java.io.IOException;

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
	
	public void start() {
		start(false);
	}
	/**
	 * Executes the program.
	 */
	public synchronized void start(boolean debug) {
		int length = source.length();
		while(pc < length){
			char c = source.charAt(pc);
			if(debug) System.out.print(c);
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
				case ',':
					read();
					break;
				case '[':
					loopStart();
					break;
				case ']':
					loopEnd();
					break;
				default:
					pc++;
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
	
	public void setPc(int pc){
		this.pc = pc;
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
	 * This will print out the value as character.
	 */
	public void print() {
		System.out.print((char)data[address]);
		this.pc++;
	}
	
	/**
	 * The opcode of 「,」.<br>
	 * This will wait out the value as character.
	 */
	public void read() {
		try {
			System.out.print("input -> ");
			int ch = System.in.read();
			data[address] = (byte) ch;
		} catch (IOException e) {
			System.err.println("read: Execution error.");
		} finally {
			this.pc++;
		}
	}
	
	/**
	 * The opcode of 「[」
	 */
	public void loopStart(){
		if(data[address] == (byte) 0){
			gotoLoopEnd();
		}
		this.pc++;
	}
	
	private void gotoLoopEnd() {
		try {
			int nest = 0;
			while(true) {
				char ch = source.charAt(this.pc);
				if (ch == '[') {
					nest++;
				} else if (ch == ']') {
					nest--;
					if(nest == 0) break;
				}
				this.pc++;
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.err.println("loopStart: Execution error.");
		}
	}

	/**
	 * The opcode of 「]」
	 */
	public void loopEnd() {
		try {
			int nest = 0;
			while(true) {
				char ch = source.charAt(this.pc);
				if (ch == ']') {
					nest++;
				} else if (ch == '[') {
					nest--;
					if (nest == 0) break;
				}
				this.pc--;
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.err.println("loopEnd: Execution error.");
		}
	}
}
