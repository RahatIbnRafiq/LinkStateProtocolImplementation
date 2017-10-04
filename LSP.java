/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package link1;

/**
 *
 * @author Farhan
 */

import java.io.*;
import java.util.Date;


public class LSP implements Serializable {
    int id;
    Date d;
    long l_time;
    
    int router_connection[];
    int socket_num;

     LSP(int num )
    {
        id = num;
        d=new Date();
        l_time = d.getTime();
        System.out.println(l_time);
    }
    void set_router_conn(int conn[]){       // index 1 theke start kora hoise
        router_connection = conn;
    }


}
