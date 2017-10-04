/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package link1;

/**
 *
 * @author Farhan
 */
import java.util.ArrayList;
import java.io.*;
import java.lang.Byte;
import java.net.*;
//import java.lang.String;

public class Router {
    int id;
    int state;   // 0 = off, 1 = on
     int d[]  ;    // distance
     int dC[] ;     // visited kina ei jonno
     int p[] ;    // predecessor node
     int next_hop[];
     //int route_network[];
     int table[];    // network connectivity


     ArrayList <Integer> route_network;
     ArrayList<LSP> lsp_list;
     DatagramSocket dSocket;

     int socket_num;

     Router(int router_numbers, int num, int socket_num)
     {
         id = num;
         this.socket_num = socket_num;

         d  = new int[ router_numbers + 1  ];    // distance
         dC = new int[ router_numbers +1  ];     // visited kina ei jonno
         p = new int[  router_numbers +1  ];
         next_hop = new int[  router_numbers +1  ];

         route_network = new ArrayList<Integer>();
         lsp_list = new  ArrayList<LSP>();

          try{
                 dSocket = new DatagramSocket(this.socket_num);

            }
            catch(SocketException e)
            {
                System.out.println("jamela in socket "+ e.toString() );
            }
     }

      void set_table( int network_num)
     {
        table = new int [network_num + 1];
     }
     void create_network_table(Network N[] )
     {
        for(int i=1; i <= next_hop.length; i++  )
        {
            for( int j=1; j < N.length; j++)
            {
                if( N[j].router == i)
                {
                    table[j] = next_hop[i];
                }
            }
        }
        for( int i=1; i < table.length; i++)
        {
            System.out.print( table[i] + " ");
        }
        System.out.println();
     }

    void thread_start()
    {
            new ReadThread(dSocket, id, lsp_list );
             new WriteThread(dSocket, id, lsp_list );
       
    }

    void send_packet()
    {
        //LSP l = new LSP(number);
        LSP l = lsp_list.get(id - 1);   // ArrayList 0 theke start hoise
        l.l_time += 100;
        try{

         ByteArrayOutputStream byteStream = new ByteArrayOutputStream(2000);
           ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
           os.flush();
           os.writeObject(l);
           os.flush();


        //String msg = "farhan";
        //byte data[] = msg.getBytes(); // convert to bytes
           byte data[] = byteStream.toByteArray();
        // create sendPacket
      
               DatagramPacket sendPacket = new DatagramPacket( data, data.length, InetAddress.getLocalHost(), 5002 );
              dSocket.send( sendPacket ); // send packet
        }
         catch(IOException e)
        {
                System.out.println ("inside send packet problem " + e.toString() );
        }
    }

}
