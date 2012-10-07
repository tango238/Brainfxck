package com.plugram.brainfxck;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class VirtualMachineTest {

	@Test
	public void testInitializeVM() throws Exception {
		VirtualMachine vm = new VirtualMachine();
		assertThat(vm.getAddress(), is(0));
		assertThat(vm.getValue(0), is((byte)0));
	}
	
	@Test
	public void testNextPrev() {
		VirtualMachine vm = new VirtualMachine();
		vm.next();
		assertThat(vm.getAddress(), is(1));
		vm.next();
		assertThat(vm.getAddress(), is(2));
		vm.prev();
		assertThat(vm.getAddress(), is(1));
		vm.prev();
		assertThat(vm.getAddress(), is(0));
	}

	
	@Test
	public void testPlusMinus() throws Exception {
		VirtualMachine vm = new VirtualMachine();
		vm.plus();
		assertThat(vm.getValue(0), is((byte)1));
		vm.minus();
		assertThat(vm.getValue(0), is((byte)0));
		vm.next();
		vm.plus();
		assertThat(vm.getValue(1), is((byte)1));
	}
	
	@Test
	public void testPrint() throws Exception {
		VirtualMachine vm = new VirtualMachine();
		vm.next();
		for(int i = 0; i< 65; i++){
			vm.plus();
		}
		vm.print();
	}
	
	@Test
	public void testLoopStartEnd() throws Exception {
		VirtualMachine vm = new VirtualMachine();
		vm.setSource("[+++]-");
		vm.loopStart();
		assertThat(vm.currentPc(), is(5));
		assertThat(vm.getNextOpcode(), is('-'));
		vm.loopEnd();
		assertThat(vm.currentPc(), is(0));
		assertThat(vm.getNextOpcode(), is('['));
	}
	
	@Test
	public void testStart() throws Exception {
		VirtualMachine vm = new VirtualMachine("+++++++++[>++++++++<-]>.");
		vm.start();
	}
}
