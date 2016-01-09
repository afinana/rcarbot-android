package com.middleland.mobile.rccar.bt;

public interface SocketHandler {

    static final String MESSAGE_READ = null;

    String obtainMessage(String messageRead, int bytes, int i,
                         byte[] buffer);

}
 