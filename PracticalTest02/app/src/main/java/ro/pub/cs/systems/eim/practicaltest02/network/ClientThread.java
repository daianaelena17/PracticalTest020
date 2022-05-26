package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private final String address;
    private final int port;
    private String key;
    private String value;
    private TextView result;
    private String req_type;

    private Socket socket;

    public ClientThread(String clientAddress, int clientport, String key, String value, String informationType, TextView result) {
        address = clientAddress;
        port = clientport;
        this.key = key;
        this.value = value;
        this.result = result;
        this.req_type = informationType;
    }


    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            printWriter.println(key);
            printWriter.flush();
            if (value != null && !value.isEmpty()) {
                printWriter.println(value);
                printWriter.flush();
            } else {
                printWriter.println("");
                printWriter.flush();
            }
            printWriter.println(req_type);
            printWriter.flush();

            String info;
            while ((info = bufferedReader.readLine()) != null) {
                final String finalizedWeatherInformation = info;
                result.post(new Runnable() {
                   @Override
                    public void run() {
                       result.setText(finalizedWeatherInformation);
                   }
                });
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}
