package com.graphcalc;

    //import require classes and packages  
    import java.awt.*;  
    import javax.swing.*;  
    import java.awt.geom.*;  
      
    //Extends JPanel class  
    public class PlotExample extends JPanel{  
        //initialize coordinates  
        int[] cord = {65, 20, 40, 80};  
        int margin = 60;  
          
        protected void paintComponent(Graphics grf){  
            //create instance of the Graphics to use its methods  
            super.paintComponent(grf);  
            Graphics2D graph = (Graphics2D)grf;  
              
            //Sets the value of a single preference for the rendering algorithms.  
            graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
              
            // get width and height  
            int width = getWidth();  
            int height = getHeight();  
              
            // draw graph  
            graph.draw(new Line2D.Double(margin, margin, margin, height-margin));  
            graph.draw(new Line2D.Double(margin, height-margin, width-margin, height-margin));  
              
            //find value of x and scale to plot points  
            double x = (double)(width-2*margin)/(cord.length - 1);  
            double scale = (double)(height-2*margin)/getMax();  
              
            //set color for points  
            graph.setPaint(Color.RED);  
              
            // set points to the graph  
            for(int i=0; i<cord.length; i++){  
                double x1 = margin+i*x;  
                double y1 = height-margin-scale*cord[i];  
                graph.fill(new Ellipse2D.Double(x1-2, y1-2, 4, 4));  
            }  
        }  
          
        //create getMax() method to find maximum value  
        private int getMax(){  
            int max = -Integer.MAX_VALUE;  
            for(int i=0; i<cord.length; i++){  
                if(cord[i]>max)  
                    max = cord[i];  
                 
            }  
            return max;  
        }         
          
        //main() method start  
        public static void main(String args[]){  
            //create an instance of JFrame class  
            JFrame frame = new JFrame();  
            // set size, layout and location for frame.  
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            frame.add(new PlotExample());  
            frame.setSize(400, 400);  
            frame.setLocation(200, 200);  
            frame.setVisible(true);  
        }  
    }  