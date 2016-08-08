import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Plot extends Application  {

 private static int numberOfBooks;
 private static ArrayList<Book> Books;
 private static boolean sameAuthor;


 public void init(ArrayList<Book> Books, boolean sameAuthor){
	 this.Books=Books;
	 sameAuthor=sameAuthor;
	 launch();
 }
	
 @Override public void start(Stage stage) throws Exception {
       
	   drawChart(stage,"Punctuation Density");
 }
	 
 
private void drawChart(Stage stage,String metric)
{
	stage.setTitle("Metrics Summary");
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
    bc.setTitle(metric);
    if(this.sameAuthor)
    xAxis.setLabel("Novels");
    else
    xAxis.setLabel(this.Books.get(0).author);
    yAxis.setLabel("Value");
 
    Scene scene= new Scene(bc,800,600);;
     for(Book b:this.Books){
       XYChart.Series series1 = new XYChart.Series();
    	series1.setName("2003");       
        series1.getData().add(new XYChart.Data("austria", 25601.34));
        series1.getData().add(new XYChart.Data("brazil", 20148.82));
        bc.getData().add(series1);
     }
        
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("2004");
//        series2.getData().add(new XYChart.Data("austria", 57401.85));
//        series2.getData().add(new XYChart.Data("brazil", 41941.19));
//        
//        
//        XYChart.Series series3 = new XYChart.Series();
//        series3.setName("2005");
//        series3.getData().add(new XYChart.Data("austria", 45000.65));
//        series3.getData().add(new XYChart.Data("brazil", 44835.76));
//       
        
          
        //All(series1, series2, series3);
        stage.setScene(scene);
        stage.show();
    
}
}