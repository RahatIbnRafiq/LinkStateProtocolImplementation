/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package link1;

import java.io.*;

import java.util.*;

/**
 *
 * @author Farhan
 */


public class Main {

    /**
     * @param args the command line arguments
     */
     public static Router[]  r ;
     public static int router_con[][];
     public static Network[] network_con;

    public static int  check_router(int position)
    {
        if ( r[position].state == 1  )
            return 1;
        else
            return 0;
    }
    public static void generate_LSP(int router_num)
    {
        for(int i=1; i<=router_num; i++)
        {
             LSP l = new LSP(i);
              l.set_router_conn(router_con[i]);
              l.socket_num = r[i].socket_num;

             for(int j=1; j <= router_num; j++)
             {
                r[j].lsp_list.add(l);
             }
        }
    }

    public static int router_to_router( int s, int d, Packet  p ){
        System.out.print( " > router" + d );
        if( r[d].table[p.dest ] == 0 )
        {
            int j = router_to_network( d, p.dest, p);

        }
        else
        {
            int i = router_to_router( d, r[d].table[p.dest ], p );
        }
        return 1;
    }

    public static int network_to_router( int net, int route, Packet p ){
        System.out.print("netwrk "+ net +" > " +"router "+ route );
        int i = router_to_router( route, r[route].table[p.dest ], p );
        return 1;
    }

    public static int router_to_network( int route, int  net, Packet p ){
         System.out.println( " >  netwrk "+ net );
        return 1;
    }

    public static void dijkstra(int graph[][], Router rt, int num)
    {
        System.out.println("graph length " + graph.length);

            int i;

            for(  i = 1; i <  graph.length; i++ )   // initialization
            {
                 rt.d[ i ] = 100;  // 100 raksi infinity er jonno
                 rt.p[ i ]  = -1;
                 rt.dC[ i ] = 100;
            }

             rt.d[ num]  = 0;       // first node ke "0" banailam
             rt.dC[ num ] = 0;

            int  min = 200, pos = num; // min  200 raksi ar pos hoilo source "1st node"
            i = 1;

            while( i < graph.length )
            {
                    //extract minimum
                    for( int j = 1; j  <  rt.dC.length; j++ )
                    {
                            if( min >  rt.d[ j ] &&  rt.dC[ j ] != -1 )
                            {
                                    min =  rt.d[ j ];
                                    pos = j;
                            }
                    }
                    //System.out.println("pos    " + pos);

                     rt.dC[ pos ] = -1;     // unavailable kore dilam

                   if( check_router(pos) == 1 )  // router on naki check korlam
                   {
                        //relax
                        for( int j = 1; j  <  graph.length; j++ )
                        {
                             if( graph[ pos ][ j ] != 0  && rt.d[ j ] > graph[pos][j] + rt.d[pos] )
                             {
                                     if( check_router(j) == 1 )
                                     {
                                       //System.out.println("dhuksseee j =  " + j);

                                        rt.d[ j ] = graph[ pos ][ j ] +  rt.d[ pos ];
                                         rt.p[ j ] = pos;

                                         // next hop calculate kortesi !!

                                         int temp_pos=0, temp_hop;

                                         temp_hop = rt.p[ j ];

                                         while(temp_hop != num )
                                         {
                                             temp_pos = temp_hop;
                                            temp_hop =  rt.p[ temp_pos ];
                                         }

                                         if(temp_pos ==0){
                                             rt.next_hop[j] = j;

                                         }
                                         else
                                            rt.next_hop[j] = temp_pos;

                                     }
                                }
                        }
                   }

                    i++;
                    min = 200;
             }

             System.out.print( "Parent: ");
             for( int  j = 1; j <  rt.p.length; j++ ){
                        System.out.print(  rt.p[ j ] + " " );
                }
                System.out.print( "\n" );
                System.out.print( "NextHP: ");
             for( int  j = 1; j <  rt.next_hop.length; j++ ){
                        System.out.print(  rt.next_hop[ j ] + " " );
                }
                System.out.print( "\n" );
                 System.out.print( "distan: ");
                for( int j = 1; j <  rt.d.length; j++ ){
                        System.out.print(  rt.d[ j ] + " " );
                }
                System.out.print( "\n" );

    }
   
    public static void user_on_off(Router rt[])
   {
        int choice;
        Scanner scan = new Scanner( System.in);
        while( true )
        {
            /*
            System.out.println("enter 1 router for state change/ 2 for network / 3 lsp / 4 ping :  ");
            choice = scan.nextInt();
            if( choice == -1)
            {
                break;
            }
            else if(choice == 2)   //network on /off
            {
               System.out.println("enter network to on off:  ");
               int c = scan.nextInt();

               if(network_con[c].state == 1 )
               {
                    network_con[c].state = 0;
               }
               else
                   network_con[c].state =1;
            }
            else if( choice == 3)
            {           
               // LSP l = new LSP(10);

                r[1].send_packet();
                 r[3].send_packet();

           }
             else if( choice == 4)   // ping
            {
                int t1 , t2;
                System.out.print("Enter source network: ");
                t1 = scan.nextInt();

                 System.out.print("Enter destination network: ");
                 t2 = scan.nextInt();

                Packet pt = new Packet(t1, t2, 100 );

                int t = network_to_router( t1, network_con[t1].router ,  pt);
           }
            else    // router on/off
            {
                if( rt[choice].state == 1)
                    rt[choice].state = 0;
                else
                    rt[choice].state = 1;

                for( int i=1; i <  r.length; i++)
                  {
                    
                      System.out.println("Router table for : " + i );
                         dijkstra (router_con, r[i], i);
                     
                  }
            }
            */
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here

        int router_num, router_to_router, network_num;
       // int router_con[][]; // = new int [6][6];  // 1 beshi dilam
       // int network_con[] ; // = new int [10];

        System.out.println("starting!!");
        File f = new File("input.txt");
        Scanner scn = new Scanner(f);

        int i, j, k, socket_num=5000;

        router_num = scn. nextInt();
        router_con = new int [router_num + 1][router_num + 1] ;   // 2d array

         r = new Router[router_num + 1];

        for( i=1; i <=  router_num; i++)  // Router initialization
        {
            r[i] = new Router(router_num, i, ++socket_num);
            r[i].state = 1;
        } 

        router_to_router = scn.nextInt();
         System.out.println( router_to_router);
        for( k=1;  k <=router_to_router; k++)
        {
            i = scn.nextInt();
            j = scn.nextInt();
            router_con[i][j] = 1;
             router_con[j][i] = 1;

            System.out.print(i + " ");
            System.out.println(j);

        }

         generate_LSP(router_num);   // eita aage kora lagbe because ReadThread is called after set_socket func

        for( i=1; i <=  router_num; i++)  // Router socket initialization , Read and write thread o chalu hoye jabe
        {
            r[i].thread_start();
            //r[i].set_socket(++socket_num);
        }

        network_num = scn.nextInt();
        network_con = new Network [network_num + 1];       // 9 input
        System.out.println(network_num);

         for( i=1; i <=  router_num; i++)  // router's  network_table
         {
            r[i].set_table(network_num);
          }

        for( k=1;  k <=network_num; k++)
        {
            i = scn.nextInt();
            j = scn.nextInt();

            network_con[i] = new Network(j, i);

            r[j].route_network.add(i);

            System.out.print(i + " ");
            System.out.println(network_con[i].router);

        }

          for( i=1; i <= router_num; i++)
          {
                for( j=1; j <= router_num; j++)
                {
                    System.out.print( router_con[i][j]  + " "  );
                }
                System.out.println();
          }

          for( i=1; i <= router_num; i++)
          {
              System.out.println("Router table for : " + i );
                 dijkstra (router_con, r[i], i);

                 r[i].create_network_table(network_con );
          }

        //generate_LSP(router_num);

        user_on_off(r);

    }

}
