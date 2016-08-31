package lab04;

import lab04.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;

public class List {
	private File file;
	private Scanner inF;
	private String fileN;
	private int size, index;
	private Element element;

	public List() { }

	public List(String fileN) {
		create(fileN);
	}

	public int create(String fileN) {
		int error = 0;
		index = 0;
		size = 0;
		this.fileN = fileN; // set file name

		try { // open file with specificed name and scan it
			file = new File(fileN);
			inF = new Scanner(new BufferedReader(new FileReader(file)));
		} catch(FileNotFoundException e) { // if file doesn't exist, create it
			try {
				file.createNewFile();
			} catch(IOException i) {
				error = 1;
			} finally {
				return error;
			}
		}

		while(inF.hasNext()) { // determine number of elements
			try {
				readElement();
				size++;
			} catch(InputMismatchException e) {
				error = 2;
				inF.close();
				return error;
			}
		}

		inF.close(); // should be either empty or garbage
		return error;
	}

	public void destroy() {
		file = null;
		inF = null;
		fileN = null;
		element = null;
		size = -1;
		index = -1;
	}

	public boolean search(String searchStr) throws FileNotFoundException {
		boolean result = false;
		reset();
		while(!atEnd() && !searchStr.equalsIgnoreCase(element.getKey())) {
			getNext(); // traverse until atEnd or until search value = the current element
		}
		if(!atEnd())
			result = true;
		return result;
	}

	public boolean delete(String searchStr) throws IOException {
		boolean result;
		File tmpF = new File("list.tmp");
		List tmpL = new List(tmpF.getName()); // create temp list using file name "list.tmp"

		result = search(searchStr);

		if(result) { // if search successful
			reset(); // reset then add all entries prior to target element to temp list
			while(!atEnd() && !searchStr.equalsIgnoreCase(element.getKey())) {
				tmpL.add(element);
				getNext();
			}

			while(!atEnd() && getNext()) { // add all remaining elements to temp list--getNext is in pretest in order to skip target element
				tmpL.add(element);
			}

			size--;

			inF.close(); // IOExceptions otherwise
			// replace original file with temp file by copying
			Files.copy(tmpF.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			// delete temp file
			Files.deleteIfExists(tmpF.toPath());
		}

		return result;
	}

	public void add(Element element) throws IOException {
		PrintWriter outF;

		// append element's fields to the file
		outF = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		outF.printf("%s\n%d\n%d\n%s\n", element.getLocation(), element.getTimeBegin().getTime(), element.getTimeEnd().getTime(), element.getComment());
		outF.close();

		size++;
	}

	public Element retrieve() {
		if(isEmpty() || atEnd())
			return null;
		return element.clone();
	}

	public void reset() throws FileNotFoundException { // close and reopen scanner
		index = 0;
		inF.close();
		inF = new Scanner(new BufferedReader(new FileReader(file)));
		readElement(); // prep the first element so a retrieve() following a reset() has something to return
	}

	public boolean getNext() {
		boolean result = false;
		index++;
		if(!atEnd() && !isEmpty()) {
			result = true;
			readElement();
		}
		return result;
	}

	public void readElement() throws InputMismatchException, NoSuchElementException {
		Long begin, end;
		Date beginD, endD;
		String location, comment;
 
		// read four lines corresponding to an element then instantiate element using them
		location = inF.nextLine();
		begin = inF.nextLong();
		end = inF.nextLong();
		inF.nextLine();
		comment = inF.nextLine();

		beginD = new Date(begin);
		endD = new Date(end);

		element = new Element(location, beginD, endD, comment);
	}

	public boolean atEnd() {
		boolean result = false;
		if(index == size)
			result = true;
		return result;
	}

	public boolean isEmpty() {
		boolean result;
		if(size == 0)
			result = true;
		else
			result = false;
		return result;
	}
}