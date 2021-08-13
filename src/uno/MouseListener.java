/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter{

    private int x=0;
    private int y=0;
    
    @Override
        public void mouseClicked(MouseEvent e) {
            int [] coords = new int[2];
             mouseClickedGetCoords(e);
             returnX();
             returnY();
             repaint();
        }

        public void mouseClickedGetCoords(MouseEvent e) {
             this.x = e.getX();
             this.y = e.getY();
             System.out.println(this.x+","+this.y);

        }
        public int returnX()
        {
            return this.x;
        }
        public int returnY()
        {
            return this.y;
        }

    private void repaint() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
