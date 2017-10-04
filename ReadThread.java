package link1;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class ReadThread implements Runnable
{
	private Thread thr;
	//private NetworkUtil nc;

        int number;
        DatagramSocket dSocket;
        ArrayList<LSP> lsp_list;

        public ReadThread(DatagramSocket dSocket, int number, ArrayList<LSP> lsp_list)  
	{
                this.dSocket = dSocket;
                this.number = number;
                this.lsp_list = lsp_list;

		this.thr = new Thread(this);
		thr.start();
	}
        /* public ReadThread(NetworkUtil nc, Data dd, ClientSubscription b)  // for server
	{
                d = dd;
       
                client_sub = b;
		this.nc = nc;
		this.thr = new Thread(this);

		thr.start();
	} */

	public void run() 
        {
            System.out.println("Read thread of router started");

            while(true)
            {
                try
                {
                     //byte data[] = new byte[ 100 ]; // set up packet
                    byte data[] = new byte[ 2000 ]; // set up packet
                    DatagramPacket receivePacket =  new DatagramPacket( data, data.length );
                    dSocket.receive( receivePacket ); // wait to receive packet

                    int byteCount = receivePacket.getLength();
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
                    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
                    Object o = is.readObject();
                     is.close();

                     LSP lsp = (LSP)o;
                    System.out.println("paitese in router "+ number +" er read : "+ lsp.id );
                    for(int i=1; i < lsp.router_connection.length; i++)
                    {
                        System.out.print(lsp.router_connection[i]);
                    }
                    System.out.println();
                    System.out.println(lsp.l_time);
                    System.out.println("nijer prev time : " + lsp_list.get(lsp.id - 1).l_time );

                    LSP temp = lsp_list.get(lsp.id - 1);
                    
                    if( lsp.l_time > temp.l_time )
                    {
                        lsp_list.remove(temp);
                        lsp_list.add(lsp.id - 1, lsp );
                        System.out.println("LSP changedddd");

                        // jehetu changed akhon abar write call kora lagbe !! for next hop !! 

                    }
                    else
                    {
                        System.out.println("LSP nott changedddd");
                    }
                   // System.out.println( "paitese: " +  new String( receivePacket.getData(), 0, receivePacket.getLength() )  );
                }
                catch(Exception e)
                {
                        System.out.println ("inside read problem " + e.toString() );
                }
            }

            //nc.closeConnection();
          
	}
}



