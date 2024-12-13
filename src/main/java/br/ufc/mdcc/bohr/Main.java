package br.ufc.mdcc.bohr;

import java.util.Collection;
import java.util.List;

import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.AoCSuite;

public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
			// no args
			System.out.println("No file path provided, exiting.");
			return;
		}
		String reportPath;
		try {
			reportPath = args[1];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No report path provided, saving on ./atomsreports");
			reportPath = "";
		}
		String projpath = args[0];
		System.out.println("Running BOHR on " + projpath);
		BohrAPI.findAoC(projpath, reportPath);
	}
}
