package in.abhinav.pilight.pilight;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by aravind on 20/05/17.
 */

public class Controller extends AsyncTask<Void, Void, String> {

    Socket mSocket;

    String dstAddress;
    int dstPort;
    String cmd;
    String response = "";

    public interface controllerResponse {
        void sendResponse(String response);
    }

    public controllerResponse delegate = null;

    Controller(String address, int port, String cmd, controllerResponse delegate) {
        this.dstAddress = address;
        this.dstPort = port;
        this.cmd = cmd;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Void... arg0) {

        try {

            this.mSocket = new Socket(dstAddress, dstPort);

            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(this.mSocket.getOutputStream())),
                    true);
            out.println(this.cmd);

            BufferedReader input = new BufferedReader(new InputStreamReader(this.mSocket.getInputStream()));
            response = input.readLine();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (this.mSocket != null) {
                try {
                    this.mSocket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.sendResponse(response);
        super.onPostExecute(result);
    }

}
