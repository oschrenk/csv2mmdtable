package com.oschrenk.csv2mmdtable.ui.cmd;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

import com.oschrenk.csv2mmdtable.core.Csv2MmdTable;

/**
 * Creates a {@link Csv2MmdTable} reading the given csv file, rewriting it as a
 * Multimarkdown formatted table with header to the <code>stdout</code>
 *
 * @author Oliver Schrenk <oliver.schrenk@gmail.com>
 */
public class CommandLine {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
		Cli<StartupArguments> cli = null;
		StartupArguments arguments = null;

		File path;
		char delimiter;
		Charset charset = null;

		try {
			cli = CliFactory.createCli(StartupArguments.class);
			arguments = cli.parseArguments(args);

			path = arguments.getPath();
			if (!path.canRead()) {
				System.err
						.printf("Can't read from the given path \"%s\" due to access restrictions.\n",
								path);
				return;
			}

			delimiter = arguments.getDelimiter();

			charset = Charset.forName(arguments.getCharset());

		} catch (ArgumentValidationException e) {
			System.err.println(e);
			return;
		} catch (SecurityException e) {
			System.err.println(e);
			return;
		} catch (IllegalCharsetNameException e) {
			System.err.printf("Charset \"%s\" not supported.\n",
					arguments.getCharset());
			return;
		} catch (UnsupportedCharsetException e) {
			System.err.printf("Charset \"%s\" not supported.\n",
					arguments.getCharset());
			return;
		}

		try {
			new Csv2MmdTable(path, delimiter, charset).process(System.out);
		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
