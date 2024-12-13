package br.ufc.mdcc.bohr.model;

public class AoCInfo {

	private AoC atomOfConfusion;

	private int lineNumber;

	private String snippet;
	private String filePath = "";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public AoCInfo(AoC atomOfConfusion, int lineNumber, String snippet) {
		super();
		this.atomOfConfusion = atomOfConfusion;
		this.lineNumber = lineNumber;
		this.snippet = snippet;
	}

	public AoCInfo(AoC atomOfConfusion, int lineNumber, String snippet, String filePath) {
		super();
		this.atomOfConfusion = atomOfConfusion;
		this.lineNumber = lineNumber;
		this.snippet = snippet;
		this.filePath = filePath;
	}

	public AoC getAtomOfConfusion() {
		return atomOfConfusion;
	}

	public void setAtomOfConfusion(AoC atomOfConfusion) {
		this.atomOfConfusion = atomOfConfusion;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

}
