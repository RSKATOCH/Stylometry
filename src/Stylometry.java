import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

class Stylometry{
	
	private final static int BookCount = 3;
	
	public static void main(String args[]) throws IOException {
		Book b1=new Book("C:/Users/abiak/Downloads/War.txt");
		Book b2=new Book("C:/Users/abiak/Downloads/a.txt");
		ArrayList<Book> books = new ArrayList<Book>();
		books.add(b1);
		books.add(b2);
		Plot p=new Plot();
		p.init(books,true);
	}

	
}
