package src;
import java.util.ArrayList;

import com.sun.media.jfxmediaimpl.platform.Platform;

import javafx.application.Application;
import javafx.scene.Node;
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
 private static ArrayList<Author> authorAverage;
 

 public void init(ArrayList<Book> BooksTolstoy,ArrayList<Book> BooksShakes ,ArrayList<Author> AuthorAverage){
	 this.booksTolstoy=BooksTolstoy;
	 this.booksShakes=BooksShakes;
	 this.authorAverage=AuthorAverage;
	 launch();
 }
	
 @Override public void start(Stage stage) throws Exception {
	Stage stage1 = new Stage();
	drawSimilarity(stage,booksTolstoy);
	drawSimilarity(stage1,booksShakes);
	
	Stage stage2=new Stage();
	drawDifference(stage2,authorAverage);
}
 
 private void drawDifference(Stage stage, ArrayList<Author> author) {
	// TODO Auto-generated method stub
	 	stage.setTitle("Metrics Summary");
	    final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
	    bc.setTitle("Metric Summary");
	    xAxis.setLabel("AUthors");
	    yAxis.setLabel("Value");
	     
	    Scene scene= new Scene(bc,800,800);
        
	     for(Author a:author){
	     
	    	 XYChart.Series series1 = new XYChart.Series();
	         series1.setName(a.name);       
	         series1.getData().add(new XYChart.Data("Punctuation Density", a.averagePunctuationDensity));
	         series1.getData().add(new XYChart.Data("Average Sentence Length", a.averageSentenceLength));
	         series1.getData().add(new XYChart.Data("Average Paragraph Length", a.averageParagraphLength));
	         
	         bc.getData().add(series1);
	     }
	 
	        stage.setScene(scene);
	        stage.show();
	
}

public void drawSimilarity(Stage stage,ArrayList<Book> Books){
	 
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