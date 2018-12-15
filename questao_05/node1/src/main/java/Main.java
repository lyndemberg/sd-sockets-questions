import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",10000));
        
        while(true){
            Socket accept = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(accept.getInputStream());
            Request request = (Request) in.readObject();

            if(request.op.equals("sum")){
                executeSum(request);
            }


            accept.close();
        }


    }

    private static void executeSum(Request r) throws IOException {
        File sum_file = new File("/home/lyndemberg/sum.txt");
        if(sum_file.exists()){
            int result = r.x + r.y;
            FileWriter fr = new FileWriter(sum_file,true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println(result);
            br.close();
            fr.close();
        }else {
            System.out.println("Arquivo sum.txt não existe");
        }
        System.out.println(r.toString());
        System.out.println("Node1 escreveu o resultado em sum.txt");
    }
}
