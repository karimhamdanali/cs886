package ca.uwaterloo.bhp.cfg;

public enum FeatureName {
	EQUAL("=="),
	NEW("new"),
	//THIS("this"),
	//VARIABLES("vars"),
	ASSIGNMENTS("="),
	DEREFERNCES("."),
	FIELDS("fields"),
	FIELDS_WRITTEN("fields written"),
	INVOCATIONS("invocations"),
	GOTO("goto"),
	IF("if"),
	LOCAL_VARIABLES("local variables"),
	//PARAMETERS("params"),
	//RETURNS("return statements"),
	STATEMENTS("statements"),
	THROWS("throw statements");
	
	private String name;
	
	private FeatureName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return "< " + this.name + " >";
	}

}
