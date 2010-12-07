package ca.uwaterloo.bhp.weka;

import java.io.IOException;
import java.util.Collection;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import ca.uwaterloo.bhp.cfg.ExecutionPath;

public class ArffWriter extends ArffSaver {

	private static final long serialVersionUID = 1251197729530350333L;
	
	private Collection<ExecutionPath> executionPaths;
	private Instances instances;

	public ArffWriter(String dir, String className, int methodName, Collection<ExecutionPath> executionPaths) {
		super();
		setFileExtension(Instances.FILE_EXTENSION);
		setDir(dir);
		setFilePrefix(className);
		setDirAndPrefix(String.valueOf(methodName), "");
		this.executionPaths = executionPaths;
		
		// Create the instances based on the execution paths
		createInstances();
	}
	
	private void createInstances() {
		String relationName = retrieveFile().getName();		
		FastVector attributeInfo = ExecutionPath.getAttributes(executionPaths.iterator().next().features());
		int capacity = executionPaths.size();
		
		instances = new Instances(relationName, attributeInfo, capacity);
		instances.setClass((Attribute)attributeInfo.lastElement());
		
		for(ExecutionPath path : executionPaths) {
			Instance instance = new Instance(1, path.featuresToArray());
			instance.setDataset(instances);
			instance.setClassMissing();
			instances.add(instance);
		}
		
		setInstances(instances);
	}
	
	public void write() throws IOException {
		writeBatch();
	}
	
	public Instances instances() {
		return instances;
	}
}