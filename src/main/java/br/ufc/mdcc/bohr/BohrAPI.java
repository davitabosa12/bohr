package br.ufc.mdcc.bohr;

import java.util.Collection;
import java.util.List;

import br.ufc.mdcc.bohr.model.AoCSuite;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.CustomReportGenerator;
import br.ufc.mdcc.bohr.util.ReportGenerator;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.SpoonException;
import spoon.compiler.SpoonFile;
import spoon.support.compiler.FileSystemFile;
import spoon.support.compiler.FileSystemFolder;
import spoon.support.compiler.FilteringFolder;

public class BohrAPI {

	private static Launcher spoon;

	public static Collection<AoCSuite> findAoC(String sourceCodePath, boolean generateReport) {
		build(sourceCodePath);
		configure();
		process();

		if (generateReport) {
			ReportGenerator.generateCSVFile(Dataset.list());
		}

		return Dataset.list();
	}

	public static Collection<AoCSuite> findAoC(String sourceCodePath, String reportPath) {
		build(sourceCodePath);
		configure();
		process();
		CustomReportGenerator reportGenerator = new CustomReportGenerator(reportPath);
		reportGenerator.generateCSVFile(Dataset.list());
		return Dataset.list();
	}

	public static Collection<AoCSuite> findAoC(String sourceCodePath, String[] finders, boolean generateReport) {
		build(sourceCodePath);
		configure(finders);
		process();

		if (generateReport) {
			ReportGenerator.generateCSVFile(Dataset.list());
		}

		return Dataset.list();
	}

	private static void build(String sourceCodePath) {
		spoon = new Launcher();
		spoon.getEnvironment().setCommentEnabled(false);
		// filter test folder
		try {
			spoon.getModelBuilder().addInputSource(new FileSystemFolder(sourceCodePath));
		} catch (SpoonException e) {
			spoon.getModelBuilder().addInputSource(new FileSystemFile(sourceCodePath));
		}
		spoon.buildModel();
	}

	private static void configure() {
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.InfixOperatorPrecedenceFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.PreAndPostIncrementDecrementFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.ConditionalOperatorFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.OmittedCurlyBracesFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.LogicAsControlFlowFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.ArithmeticAsLogicFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.ChangeOfLiteralEncodingFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.TypeConversionFinder");
		spoon.addProcessor("br.ufc.mdcc.bohr.finder.RepurposedVariablesFinder");
	}

	private static void configure(String[] finders) {
		for (String finder : finders) {
			spoon.addProcessor(finder);
		}
	}

	private static void process() {
		spoon.process();
	}

	public static void clean() {
		Dataset.clear();
	}

}
