package src;
import java.util.ArrayList;

import com.sun.media.jfxmediaimpl.platform.Platform;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Plot extends Application  {

 private static int numberOfBooks;
 private static ArrayList<Book> booksTolstoy;
 private static ArrayList<Book> booksShakes;
 

 public void init(ArrayList<Book> BooksTolstoy,ArrayList<Book> BooksShakes ){
	 Plot.booksTolstoy=BooksTolstoy;
	 Plot.booksShakes=BooksShakes;
	 launch();
 }
	
 @Override public void start(Stage stage) throws Exception {
	Stage stage1 = new Stage();
	draw(stage,booksTolstoy);
	draw(stage1,booksShakes);
}
 
 public void draw(Stage stage,ArrayList<Book> Books){
	 
	 stage.setTitle("Metrics Summary");
	    final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
	    bc.setTitle("Metric Summary");
	    xAxis.setLabel("Novels");
	    yAxis.setLabel("Value");
	 
	    Scene scene= new Scene(bc,800,800);
	     for(Book b:Books){
	     
	    	 XYChart.Series series1 = new XYChart.Series();
	         series1.setName(b.filename);       
	         series1.getData().add(new XYChart.Data("Punctuation Density", b.getPunctuationDensity()));
	         series1.getData().add(new XYChart.Data("Average Sentence Length", b.getAverageSentenceLengthChars()));
	         series1.getData().add(new XYChart.Data("Average Paragraph Length", b.getAverageParaLengthChars()));
	         
	         bc.getData().add(series1);
	     }
	 
	        stage.setScene(scene);
	        stage.show();
 }
}