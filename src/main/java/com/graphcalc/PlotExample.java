// package com.graphcalc;

//     //import require classes and packages  
//     import java.awt.*;  
//     import javax.swing.*;

// import org.mariuszgromada.math.mxparser.Function;

// import java.awt.geom.*;
// import java.util.List;  
      
//     //Extends JPanel class  
//     public class PlotExample extends JPanel{  
//         //initialize coordinates  
//         double[] cord = new double[5];  
//         int margin = 60;  
          
//         protected void paintComponent(Graphics grf){  
//             //create instance of the Graphics to use its methods  
//             super.paintComponent(grf);  
//             Graphics2D graph = (Graphics2D)grf;  
            
//             //Sets the value of a single preference for the rendering algorithms.  
//             graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  

//             // FunctionManager f = new FunctionManager(5, 1);
//             // f.addFunction(new Function("f(x)=x"));
//             // int midPoint = (2*f.getRange() - 1) / 2;
//             // List<Double> aux = f.getYValuesOf(f.getFunctionsList().get(0));
            
//             // for (int i = 0; i < cord.length; i++) {
//             //     cord[i] = aux.get(midPoint + i);
//             // }
            
              
//             // get width and height  
//             int width = getHeight();  
//             int height = getHeight();  
              
//             // draw graph  
//             graph.draw(new Line2D.Double(margin, margin, margin, height-margin));  
//             graph.draw(new Line2D.Double(margin, height-margin, width-margin, height-margin));  
              
//             //find value of x and scale to plot points  
//             //double x = (double)(width - 2*margin)/(f.getRange() - 1);  
//             double scale = (double)(height - 2*margin)/getMax();  
              
//             //set color for points  
//             graph.setColor(Color.RED);
              
//             // set points to the graph  
//             for(int i=0; i<cord.length; i++){  
//                 double x1 = margin+i*x;  
//                 double y1 = height-margin-scale*cord[i];  
//                 graph.fill(new Ellipse2D.Double(x1-1, y1-2, 4, 4));  
//             }  
//         }  
          
//         //create getMax() method to find maximum value  
//         private double getMax(){  
//             double max = -Integer.MAX_VALUE;  
//             for(int i=0; i<cord.length; i++){  
//                 if(cord[i]>max)  
//                     max = cord[i];  
                 
//             }  
//             return max;  
//         }         
          
//         //main() method start  
//         public static void main(String args[]){  
//             //create an instance of JFrame class  
//             JFrame frame = new JFrame();  
//             frame.getContentPane().setLayout(new BorderLayout());
//             // set size, layout and location for frame.  
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//             frame.add(new PlotExample(), BorderLayout.CENTER);  
//             frame.setSize(400, 400);  
//             frame.setLocationRelativeTo(null);
//             frame.setVisible(true);  
//         }  
//     }  