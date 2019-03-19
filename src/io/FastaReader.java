package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.input.CountingInputStream;

public class FastaReader {
	private String filename;
	private HashMap<String, FileReader> readerMap = new HashMap<String, FileReader>();
	private HashMap<String,Long> starts = new HashMap<String,Long>();
	private HashMap<String,Long> ends= new HashMap<String,Long>();
	public FastaReader(String filename){
		this.filename = filename;
	}
	
}
