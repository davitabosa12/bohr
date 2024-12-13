package br.ufc.mdcc.bohr.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.AoCSuite;

public class CustomReportGenerator {
	private final static String DEFAULT_REPORT_PATH = "./atomsreports";
	private String reportPath;

	public CustomReportGenerator(String reportPath) {
		// check if null
		if (reportPath == null) {
			throw new NullPointerException("reportPath cannot be null");
		}
		if (reportPath.equals("")) {
			this.reportPath = prepareReportPath(getReportName().toString());
		} else {
			this.reportPath = prepareReportPath(reportPath);
		}
	}

	private String prepareReportPath(String rawReportPath) {
		StringBuilder sb = new StringBuilder();
		// check if has directories
		String fileSeparator = System.getProperty("file.separator");
		if (!rawReportPath.contains(fileSeparator)) {
			sb.append(DEFAULT_REPORT_PATH);
			sb.append(fileSeparator);
		}
		sb.append(rawReportPath);
		if (!rawReportPath.endsWith(".csv")) {
			sb.append(".csv");
		}
		return sb.toString();
	}

	public void generateCSVFile(Collection<AoCSuite> aocSuiteList) {
		ArrayList<List<String>> rows = new ArrayList<>();

		for (AoCSuite suite : aocSuiteList) {
			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				String snippet = aocInfo.getSnippet();
				snippet = snippet.replaceAll("\\r\\n|\\r|\\n|\\t", " ");

				if (snippet.contains(",")) {
					snippet = snippet.replaceAll(",", "");
					aocInfo.setSnippet(snippet);
				}

				rows.add(Arrays.asList(aocInfo.getFilePath(), suite.getClassQualifiedName(),
						aocInfo.getAtomOfConfusion().getFullName(),
						snippet,
						String.valueOf(aocInfo.getLineNumber())));
			}
		}

		writeCSV(rows);
	}

	private void writeCSV(ArrayList<List<String>> rows) {
		createReportDirectory();

		FileWriter csvWriter = null;
		try {
			csvWriter = new FileWriter(this.reportPath);
			csvWriter.append("Path");
			csvWriter.append(",");
			csvWriter.append("Class");
			csvWriter.append(",");
			csvWriter.append("Atom");
			csvWriter.append(",");
			csvWriter.append("Snippet");
			csvWriter.append(",");
			csvWriter.append("Line");
			csvWriter.append("\n");

			for (List<String> rowData : rows) {
				csvWriter.append(String.join(",", rowData));
				csvWriter.append("\n");
			}

			csvWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (csvWriter != null) {
				try {
					csvWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createReportDirectory() {
		File directory = new File(this.reportPath).getParentFile();
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	private StringBuffer getReportName() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		dateFormat.setTimeZone(calendar.getTimeZone());

		StringBuffer reportName = new StringBuffer("atoms-report-");
		reportName.append(dateFormat.format(calendar.getTime()));
		reportName.append(".csv");
		return reportName;
	}
}
