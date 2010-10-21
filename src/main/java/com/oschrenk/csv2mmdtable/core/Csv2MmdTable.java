package com.oschrenk.csv2mmdtable.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import com.csvreader.CsvReader;

/**
 * {@link Csv2MmdTable} reafs from a given csv file, rewriting it as a
 * Multimarkdown formatted table with headers, writing it to the OutputStream
 *
 * @author Oliver Schrenk <oliver.schrenk@gmail.com>
 */
public class Csv2MmdTable {

	private static final boolean AUTO_FLUSH = true;

	/** The path. */
	private final File path;

	/** The delimiter. */
	private final char delimiter;

	/** The charset. */
	private final Charset charset;

	/**
	 * Instantiates a new csv2 mmd table.
	 *
	 * @param path
	 *            the path
	 * @param delimiter
	 *            the delimiter
	 * @param charset
	 *            the charset
	 */
	public Csv2MmdTable(final File path, final char delimiter,
			final Charset charset) {
		this.path = path;
		this.delimiter = delimiter;
		this.charset = charset;
	}

	/**
	 * Process.
	 *
	 * @param out
	 *            the out
	 * @return the csv2 mmd table
	 */
	public void process(final OutputStream out) {
		PrintStream p = new PrintStream(out, AUTO_FLUSH);

		CsvReader csv;
		try {
			csv = new CsvReader(new FileInputStream(path), delimiter, charset);
			csv.readHeaders();
			int headerCount = csv.getHeaderCount();

			// write header
			p.append("| ");
			for (int i = 0; i < headerCount; i++) {
				p.append(csv.getHeader(i));
				if (i != headerCount - 1)
					p.append(" | ");
				else
					p.append(" |");
			}
			p.append("\n");

			// left align all headers
			p.append("| ");
			for (int i = 0; i < headerCount; i++) {
				p.append(":----");
				if (i != headerCount - 1)
					p.append(" | ");
				else
					p.append(" |");
			}
			p.append("\n");

			// write other fields
			while (csv.readRecord()) {
				p.append("| ");
				for (int i = 0; i < headerCount; i++) {
					p.append(csv.get(i));
					if (i != headerCount - 1)
						p.append(" | ");
					else
						p.append(" |");
				}
				p.append("\n");
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
