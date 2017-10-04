/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Farhan
 */

package link1;

import java.io.*;

public class Network  {
    int id;
    int state;   // 0 = off, 1 = on
    int router;

    //PipedInputStream pis;
   //  PipedOutputStream pos;

    Network(int x, int num)
    {
        id = num;
        router = x;
        state = 1;

       // pos = new  PipedOutputStream();

     
    }
    
    
    

}
