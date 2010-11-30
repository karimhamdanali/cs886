package ca.uwaterloo.bhp.cfg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import soot.ClassProvider;
import soot.G;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.toolkits.graph.BriefBlockGraph;

public class DataPreprocessor {
	
	private static NoSearchingClassProvider _provider;
	private static Scene _scene;
	
	public static void run(String inputDirectory) throws IOException {
		// Initialize some parameters for Soot
		init();
		
		// Load the classes
		Collection<SootClass> inputClasses = loadClasses(inputDirectory);
		
		// Construct the control flow graph for the input lasses
		Collection<BriefBlockGraph> cfgList = CfgGenerator.generate(inputClasses);
		
	}
	
	private static SootClass loadClassByCanonicalName(String className) {
		_scene.loadClassAndSupport(className);
	    _scene.loadNecessaryClasses();
		SootClass sc = _scene.loadClass(className, SootClass.BODIES);
		sc.setApplicationClass();
		
		return sc;
	}
	
	private static Collection<SootClass> loadSootClasses() {
		// Load SootClasses for the input files
		Collection<SootClass> classes = new ArrayList<SootClass>();
	    for(String className : _provider.getClassNames()) {
	    	_scene.loadClass(className, SootClass.SIGNATURES);
	    	//_scene.loadClassAndSupport(className);
	    	SootClass sc = _scene.loadClass(className, SootClass.BODIES);
	    	sc.setApplicationClass();
	    	classes.add(sc);
	    }
		
	    //addCommonDynamicClasses(_scene, _provider);
		_scene.loadNecessaryClasses();
		
		return classes;
	}
	
	private static Collection<SootClass> loadClasses(String inputDirectory) throws IOException {
		// Fetch the input files
		for(File file : new File(inputDirectory).listFiles()) {
    		if(file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) {
    	        System.out.println("Adding archive: " + file.getName());
    	        _provider.addArchive(file);
    		} else if (file.getName().endsWith(".java") || file.getName().endsWith(".class")) {
    			System.out.println("Adding file: " + file.getName());
    	        _provider.addClass(file);
    		}
    	}
		
		return loadSootClasses();
	}
	
	private static void init() {
		// Reset Soot
		G.reset();
		
		// Create the Soot-related members
		_provider = new NoSearchingClassProvider();
		_scene = Scene.v();

		// Set the class provider for Soot
		soot.SourceLocator.v().setClassProviders(Collections.singletonList((ClassProvider) _provider));
	}
}
