package de.hpi.bpt.process.petri.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import de.hpi.bpt.process.Gateway;
import de.hpi.bpt.process.GatewayType;
import de.hpi.bpt.process.Process;
import de.hpi.bpt.process.Task;
import de.hpi.bpt.process.checks.structural.ProcessStructureChecker;
import de.hpi.bpt.process.petri.Marking;
import de.hpi.bpt.process.petri.PetriNet;
import de.hpi.bpt.process.petri.Place;
import de.hpi.bpt.process.petri.Transition;
import de.hpi.bpt.process.petri.unf.OccurrenceNet;
import de.hpi.bpt.process.petri.unf.ProperUnfolding;
import de.hpi.bpt.process.petri.unf.Unfolding;
import de.hpi.bpt.process.petri.unf.UnfoldingSetup;
import de.hpi.bpt.process.petri.unf.Utils;
import de.hpi.bpt.process.petri.unf.order.EsparzaAdequateOrderForArbitrarySystems;
import de.hpi.bpt.process.petri.unf.order.EsparzaTotalAdequateOrderForSafeSystems;
import de.hpi.bpt.process.petri.unf.order.UnfoldingAdequateOrder;
import de.hpi.bpt.process.petri.util.TransformationException;
import de.hpi.bpt.process.serialize.Process2DOT;
import de.hpi.bpt.utils.IOUtils;


public class UnfoldingTest extends TestCase {
	public void test1() throws TransformationException, FileNotFoundException {
		Process p = new Process();
		
		Task ti = new Task("I");
		Task to = new Task("O");
		Task ta = new Task("A");
		Task tb = new Task("B");
		Task tc = new Task("C");
		Task td = new Task("D");
	
		Gateway s1 = new Gateway(GatewayType.AND);
		Gateway j2 = new Gateway(GatewayType.XOR);
		Gateway s2 = new Gateway(GatewayType.XOR);
		Gateway j1 = new Gateway(GatewayType.AND);
		
		p.addTask(ti);
		p.addTask(to);
		p.addTask(ta);
		p.addTask(tb);
		p.addTask(tc);
		p.addTask(td);
		p.addGateway(s1);
		p.addGateway(s2);
		p.addGateway(j1);
		p.addGateway(j2);
		
		p.addControlFlow(ti,s1);
		p.addControlFlow(s1,ta);
		p.addControlFlow(s1,s2);
		p.addControlFlow(ta,j1);
		p.addControlFlow(s2,tb);
		p.addControlFlow(s2,tc);
		p.addControlFlow(tb,j2);
		p.addControlFlow(tc,j2);
		p.addControlFlow(j2,td);
		p.addControlFlow(td,j1);
		p.addControlFlow(j1,to);
		
		List<String> errors = ProcessStructureChecker.checkStructure(p);
		if (errors.size()>0)
			for (String e : errors) System.err.println(e);
		
		Utils.toFile("model.dot", Process2DOT.convert(p));
		
		PetriNet net = Utils.process2net(p);
		int cp = 1; int ct = 1;
		for (Place place : net.getPlaces()) place.setName("p"+cp++);
		for (Transition trans : net.getTransitions()) trans.setName("t"+ct++);
		Utils.addInitialMarking(net);
		IOUtils.toFile("net.dot", net.toDOT());
		
		UnfoldingSetup conf = new UnfoldingSetup();
		//conf.MAX_EVENTS = 5;
		Unfolding bp = new Unfolding(net,conf);

		OccurrenceNet bpnet = bp.getOccurrenceNet();
		IOUtils.toFile("unf.dot", bpnet.toDOT());
	}
	
	public void test1a() throws TransformationException, FileNotFoundException {
		Process p = new Process();
		
		Task ti = new Task("I");
		Task to = new Task("O");
		Task ta = new Task("A");
		Task tb = new Task("B");
		Task tc = new Task("C");
		Task td = new Task("D");
	
		Gateway s1 = new Gateway(GatewayType.AND);
		Gateway j2 = new Gateway(GatewayType.XOR);
		Gateway s2 = new Gateway(GatewayType.XOR);
		Gateway j1 = new Gateway(GatewayType.AND);
		
		p.addTask(ti);
		p.addTask(to);
		p.addTask(ta);
		p.addTask(tb);
		p.addTask(tc);
		p.addTask(td);
		p.addGateway(s1);
		p.addGateway(s2);
		p.addGateway(j1);
		p.addGateway(j2);
		
		p.addControlFlow(ti,s1);
		p.addControlFlow(s1,ta);
		p.addControlFlow(s1,s2);
		p.addControlFlow(ta,j1);
		p.addControlFlow(s2,tb);
		p.addControlFlow(s2,tc);
		p.addControlFlow(tb,j2);
		p.addControlFlow(tc,j2);
		p.addControlFlow(j2,td);
		p.addControlFlow(td,j1);
		p.addControlFlow(j1,to);
		
		List<String> errors = ProcessStructureChecker.checkStructure(p);
		if (errors.size()>0)
			for (String e : errors) System.err.println(e);
		
		Utils.toFile("model1a.dot", Process2DOT.convert(p));
		
		PetriNet net = Utils.process2net(p);
		int cp = 1; int ct = 1;
		for (Place place : net.getPlaces()) place.setName("p"+cp++);
		for (Transition trans : net.getTransitions()) trans.setName("t"+ct++);
		Utils.addInitialMarking(net);
		IOUtils.toFile("net1a.dot", net.toDOT());
		
		UnfoldingSetup setup = new UnfoldingSetup();
		setup.ADEQUATE_ORDER = new EsparzaAdequateOrderForArbitrarySystems();
		Unfolding bp = new Unfolding(net,setup);
		
		OccurrenceNet bpnet = bp.getOccurrenceNet();
		IOUtils.toFile("unf1a.dot", bpnet.toDOT());
	}

	public void test1b() throws TransformationException, FileNotFoundException {
		Process p = new Process();
		
		Task ti = new Task("I");
		Task to = new Task("O");
		Task ta = new Task("A");
		Task tb = new Task("B");
		Task tc = new Task("C");
		Task td = new Task("D");
	
		Gateway s1 = new Gateway(GatewayType.AND);
		Gateway j2 = new Gateway(GatewayType.XOR);
		Gateway s2 = new Gateway(GatewayType.XOR);
		Gateway j1 = new Gateway(GatewayType.AND);
		
		p.addTask(ti);
		p.addTask(to);
		p.addTask(ta);
		p.addTask(tb);
		p.addTask(tc);
		p.addTask(td);
		p.addGateway(s1);
		p.addGateway(s2);
		p.addGateway(j1);
		p.addGateway(j2);
		
		p.addControlFlow(ti,s1);
		p.addControlFlow(s1,ta);
		p.addControlFlow(s1,s2);
		p.addControlFlow(ta,j1);
		p.addControlFlow(s2,tb);
		p.addControlFlow(s2,tc);
		p.addControlFlow(tb,j2);
		p.addControlFlow(tc,j2);
		p.addControlFlow(j2,td);
		p.addControlFlow(td,j1);
		p.addControlFlow(j1,to);
		
		List<String> errors = ProcessStructureChecker.checkStructure(p);
		if (errors.size()>0)
			for (String e : errors) System.err.println(e);
		
		Utils.toFile("model1b.dot", Process2DOT.convert(p));
		
		PetriNet net = Utils.process2net(p);
		int cp = 1; int ct = 1;
		for (Place place : net.getPlaces()) place.setName("p"+cp++);
		for (Transition trans : net.getTransitions()) trans.setName("t"+ct++);
		Utils.addInitialMarking(net);
		IOUtils.toFile("net1b.dot", net.toDOT());
		
		UnfoldingSetup setup = new UnfoldingSetup();
		setup.ADEQUATE_ORDER = new EsparzaTotalAdequateOrderForSafeSystems();
		Unfolding bp = new Unfolding(net,setup);
				
		OccurrenceNet bpnet = bp.getOccurrenceNet();
		IOUtils.toFile("unf1b.dot", bpnet.toDOT());
	}

	
	public void test2() throws TransformationException, FileNotFoundException {
		Process p = new Process();
		
		Task ti = new Task("I");
		Task to = new Task("O");
		Task ta = new Task("A");
		Task tb = new Task("B");
	
		Gateway s1 = new Gateway(GatewayType.XOR);
		Gateway j1 = new Gateway(GatewayType.XOR);
		
		p.addTask(ti);
		p.addTask(to);
		p.addTask(ta);
		p.addTask(tb);
		p.addGateway(s1);
		p.addGateway(j1);
		
		p.addControlFlow(ti,j1);
		p.addControlFlow(j1,ta);
		p.addControlFlow(ta,s1);
		p.addControlFlow(s1,tb);
		p.addControlFlow(tb,j1);
		p.addControlFlow(s1,to);
		
		List<String> errors = ProcessStructureChecker.checkStructure(p);
		if (errors.size()>0)
			for (String e : errors) System.err.println(e);
		
		Utils.toFile("model2.dot", Process2DOT.convert(p));
		
		PetriNet net = Utils.process2net(p);
		int cp = 1; int ct = 1;
		for (Place place : net.getPlaces()) place.setName("p"+cp++);
		for (Transition trans : net.getTransitions()) trans.setName("t"+ct++);
		Utils.addInitialMarking(net);
		IOUtils.toFile("net2.dot", net.toDOT());
		
		Unfolding bp = new Unfolding(net);
		
		OccurrenceNet bpnet = bp.getOccurrenceNet();
		IOUtils.toFile("unf2.dot", bpnet.toDOT());
	}
	
	public void test2a() throws TransformationException, FileNotFoundException {
		Process p = new Process();
		
		Task ti = new Task("I");
		Task to = new Task("O");
		Task ta = new Task("A");
		Task tb = new Task("B");
	
		Gateway s1 = new Gateway(GatewayType.XOR);
		Gateway j1 = new Gateway(GatewayType.XOR);
		
		p.addTask(ti);
		p.addTask(to);
		p.addTask(ta);
		p.addTask(tb);
		p.addGateway(s1);
		p.addGateway(j1);
		
		p.addControlFlow(ti,j1);
		p.addControlFlow(j1,ta);
		p.addControlFlow(ta,s1);
		p.addControlFlow(s1,tb);
		p.addControlFlow(tb,j1);
		p.addControlFlow(s1,to);
		
		List<String> errors = ProcessStructureChecker.checkStructure(p);
		if (errors.size()>0)
			for (String e : errors) System.err.println(e);
		
		Utils.toFile("model2a.dot", Process2DOT.convert(p));
		
		PetriNet net = Utils.process2net(p);
		int cp = 1; int ct = 1;
		for (Place place : net.getPlaces()) place.setName("p"+cp++);
		for (Transition trans : net.getTransitions()) trans.setName("t"+ct++);
		Utils.addInitialMarking(net);
		IOUtils.toFile("net2a.dot", net.toDOT());
		
		UnfoldingSetup setup = new UnfoldingSetup();
		setup.ADEQUATE_ORDER = new EsparzaAdequateOrderForArbitrarySystems();
		Unfolding bp = new Unfolding(net,setup);
		
		OccurrenceNet bpnet = bp.getOccurrenceNet();
		IOUtils.toFile("unf2a.dot", bpnet.toDOT());
	}
	
	public void test2b() throws TransformationException, FileNotFoundException {
		Process p = new Process();
		
		Task ti = new Task("I");
		Task to = new Task("O");
		Task ta = new Task("A");
		Task tb = new Task("B");
	
		Gateway s1 = new Gateway(GatewayType.XOR);
		Gateway j1 = new Gateway(GatewayType.XOR);
		
		p.addTask(ti);
		p.addTask(to);
		p.addTask(ta);
		p.addTask(tb);
		p.addGateway(s1);
		p.addGateway(j1);
		
		p.addControlFlow(ti,j1);
		p.addControlFlow(j1,ta);
		p.addControlFlow(ta,s1);
		p.addControlFlow(s1,tb);
		p.addControlFlow(tb,j1);
		p.addControlFlow(s1,to);
		
		List<String> errors = ProcessStructureChecker.checkStructure(p);
		if (errors.size()>0)
			for (String e : errors) System.err.println(e);
		
		Utils.toFile("model2b.dot", Process2DOT.convert(p));
		
		PetriNet net = Utils.process2net(p);
		int cp = 1; int ct = 1;
		for (Place place : net.getPlaces()) place.setName("p"+cp++);
		for (Transition trans : net.getTransitions()) trans.setName("t"+ct++);
		Utils.addInitialMarking(net);
		IOUtils.toFile("net2b.dot", net.toDOT());
		
		UnfoldingSetup setup = new UnfoldingSetup();
		setup.ADEQUATE_ORDER = new EsparzaTotalAdequateOrderForSafeSystems();
		Unfolding bp = new Unfolding(net,setup);
		
		OccurrenceNet bpnet = bp.getOccurrenceNet();
		IOUtils.toFile("unf2b.dot", bpnet.toDOT());
	}
	
	public void test2Esparza() throws TransformationException, FileNotFoundException {
		PetriNet net = new PetriNet();
		
		Place p1 = new Place("p1");
		Place p2 = new Place("p2");
		Place p3 = new Place("p3");
		Place p4 = new Place("p4");
		
		net.addVertex(p1);
		net.addVertex(p2);
		net.addVertex(p3);
		net.addVertex(p4);
		
		Transition t1a = new Transition("t1a");
		Transition t1b = new Transition("t1b");
		Transition t2a = new Transition("t2a");
		Transition t2b = new Transition("t2b");
		Transition t3a = new Transition("t3a");
		Transition t3b = new Transition("t3b");
		
		net.addVertex(t1a);
		net.addVertex(t2a);
		net.addVertex(t3a);
		net.addVertex(t1b);
		net.addVertex(t2b);
		net.addVertex(t3b);
		
		net.addFlow(p1, t1a);
		net.addFlow(p1, t1b);
		net.addFlow(t1a, p2);
		net.addFlow(t1b, p2);
		net.addFlow(p2, t2a);
		net.addFlow(p2, t2b);
		net.addFlow(t2a, p3);
		net.addFlow(t2b, p3);
		net.addFlow(p3, t3a);
		net.addFlow(p3, t3b);
		net.addFlow(t3a, p4);
		net.addFlow(t3b, p4);
		
		Utils.addInitialMarking(net);
		IOUtils.toFile("netEsp.dot", net.toDOT());
		
		Unfolding unf = new Unfolding(net);
		OccurrenceNet bpnet = unf.getOccurrenceNet();
		IOUtils.toFile("unfMcMil.dot", bpnet.toDOT());
		
		UnfoldingSetup setup = new UnfoldingSetup();
		setup.ADEQUATE_ORDER = new UnfoldingAdequateOrder();
		unf = new Unfolding(net,setup);
		bpnet = unf.getOccurrenceNet();
		IOUtils.toFile("unfUnf.dot", bpnet.toDOT());
		
		setup.ADEQUATE_ORDER = new EsparzaAdequateOrderForArbitrarySystems();
		unf = new Unfolding(net,setup);
		bpnet = unf.getOccurrenceNet();
		IOUtils.toFile("unfEspArbitrary.dot", bpnet.toDOT());
		
		//setup.ADEQUATE_ORDER = new EsparzaTotalAdequateOrderForSafeSystems();
		unf = new ProperUnfolding(net);
		bpnet = unf.getOccurrenceNet();
		IOUtils.toFile("unfEspSafe.dot", bpnet.toDOT());
	}
	
	public void testPhilosophers() throws TransformationException, FileNotFoundException {
		PetriNet net = new PetriNet();
		
		Place p1 = new Place("p1");
		Place p2 = new Place("p2");
		Place p3 = new Place("p3");
		Place p4 = new Place("p4");
		Place p5 = new Place("p5");
		Place p6 = new Place("p6");
		Place p7 = new Place("p7");
		Place p8 = new Place("p8");
		
		Transition t1 = new Transition("t1");
		Transition t2 = new Transition("t2");
		Transition t3 = new Transition("t3");
		Transition t4 = new Transition("t4");
		Transition t5 = new Transition("t5");
		Transition t6 = new Transition("t6");
		Transition t7 = new Transition("t7");
		Transition t8 = new Transition("t8");
		
		net.addFlow(t5, p1);
		net.addFlow(p1, t2);
		net.addFlow(t5, p4);
		net.addFlow(p5, t5);
		net.addFlow(t1, p5);
		net.addFlow(p4, t1);
		net.addFlow(p1, t1);
		net.addFlow(t2, p6);
		net.addFlow(p6, t6);
		net.addFlow(t6, p1);
		net.addFlow(t6, p2);
		net.addFlow(p2, t2);
		net.addFlow(p2, t3);
		net.addFlow(t3, p7);
		net.addFlow(p7, t7);
		net.addFlow(t7, p2);
		net.addFlow(t7, p3);
		net.addFlow(p3, t3);
		net.addFlow(p4, t4);
		net.addFlow(t4, p8);
		net.addFlow(p8, t8);
		net.addFlow(t8, p4);
		net.addFlow(t8, p3);
		net.addFlow(p3, t4);
		
		Marking M0 = new Marking(net);
		M0.put(p1, 1);
		M0.put(p2, 1);
		M0.put(p3, 1);
		M0.put(p4, 1);
		M0.apply();
		
		IOUtils.toFile("netPhilosophers.dot", net.toDOT());
		
		UnfoldingSetup setup = new UnfoldingSetup();
		setup.ADEQUATE_ORDER = new EsparzaTotalAdequateOrderForSafeSystems();
		setup.MAX_EVENTS = 8;
		
		Unfolding unf = new Unfolding(net,setup);
		OccurrenceNet bpnet = unf.getOccurrenceNet();
		IOUtils.toFile("unfPhilosophers.dot", bpnet.toDOT());
	}
		
}
