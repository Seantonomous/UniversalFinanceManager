package ufm.universalfinancemanager.net;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.inject.Inject;

import ufm.universalfinancemanager.util.AppExecutors;

/**
 * Created by smh7 on 3/26/18.
 */

public class LoginManager {
    private AppExecutors mExecutors;

    private Socket socket = null;
    private static final int port = 5000;
    private static final String ip = "54.67.43.213";

    @Inject
    public LoginManager(@NonNull AppExecutors executors) {
        mExecutors = executors;
    }

    public void login(final String username, final String password, final LoginCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PrintWriter out = null;
                BufferedReader in = null;

                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    socket = new Socket(serverAddr, port);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });

                    return;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                    return;
                }

                try {
                    out.println("LOGIN");
                    out.println(username);
                    out.println(password);
                    final String response = in.readLine();

                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (response.equals("SUCCESS"))
                                callback.onSuccessfulLogin();
                            else
                                callback.onFailedLogin();
                        }
                    });
                } catch (IOException e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
        };

        mExecutors.networkIO().execute(runnable);
    }

    public void signup(final String username, final String password, final SignupCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PrintWriter out = null;
                BufferedReader in = null;

                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    socket = new Socket(serverAddr, port);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });

                } catch (IOException e2) {
                    e2.printStackTrace();
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }

                try {
                    out.println("SIGNUP");
                    out.println(username);
                    out.println(password);

                    final String response = in.readLine();
                    if(response.equals("SUCCESS"))
                        //Read in user token
                        //Store in shared preference

                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (response.equals("SUCCESS"))
                                callback.onSuccessfulSignup();
                            else
                                callback.onFailedSignup();
                        }
                    });
                } catch (IOException e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
        };

        mExecutors.networkIO().execute(runnable);
    }
}
