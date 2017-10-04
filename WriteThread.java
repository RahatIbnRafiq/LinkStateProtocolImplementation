package link1;


import java.util.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class WriteThread implements Runnable
{
	
	private Thread thr;

         int number;
        DatagramSocket dSocket;
        ArrayList<LSP> lsp_list;

        WriteThread(DatagramSocket dSocket, int number, ArrayList<LSP> lsp_list){
             this.dSocket = dSocket;
            this.number = number;
            this.lsp_list = lsp_list;

            this.thr = new Thread(this);
            thr.start();
        }
	/*public WriteThread(NetworkUtil nc,String name, Data y)   // for Server
	{
                d = y;
		this.nc = nc;
		this.name=name;
		this.thr = new Thread(this);
		thr.start();
	} */

	public void run() 
	{
            // eita while true er vitor thakbe

            while(true)
            {
                try{
                     Thread.sleep(2000);
                }
                catch( Exception e)
                {
                }

                 LSP prev_lsp = lsp_list.get(number - 1);   // ArrayList 0 theke start hoise
                 
                 LSP lsp = new LSP(number);
                 lsp.l_time = prev_lsp.l_time + 100;
                 lsp.router_connection = prev_lsp.router_connection;
                 //lsp.l_time += 100;

                for( int i=0; i< lsp_list.size(); i++)
                {
                    if( lsp_list.get(number-1).router_connection[i+1] == 1 )
                    {
                        try{

                               ByteArrayOutputStream byteStream = new ByteArrayOutputStream(2000);
                               ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
                               os.flush();
                               os.writeObject(lsp);
                               os.flush();

                                byte data[] = byteStream.toByteArray();
                                // create sendPacket

                               DatagramPacket sendPacket = new DatagramPacket( data, data.length, InetAddress.getLocalHost(),
                                       lsp_list.get(i).socket_num );

                               dSocket.send( sendPacket ); // send packet
                                System.out.println( number +" write thread likse for " + (i+1) + " !!! port number: " + lsp_list.get(i).socket_num);

                          }
                         catch(Exception e)
                        {
                                System.out.println (" exception write thread: " + e.toString() );
                        }
                    }
                }
            }
	}
}



