package io.devtab.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

@Component
public class SocketIoController {

    private final SocketIOServer socketIOServer;

    public SocketIoController(SocketIOServer server) {
        this.socketIOServer = server;

        // 소켓 이벤트 리스너 등록
        server.addConnectListener(listenConnected());
        server.addDisconnectListener(listenDisconnected());
    }

    /**
     * 클라이언트 연결 리스너
     */
    public ConnectListener listenConnected() {
        return (client) -> {
            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        };
    }

    /**
     * 클라이언트 연결 해제 리스너
     */
    public DisconnectListener listenDisconnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            client.disconnect();
        };
    }
}
