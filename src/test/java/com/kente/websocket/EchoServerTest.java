package com.kente.websocket;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class EchoServerTest {
    @OnMessage
    public String echo(String incomingMessage) {
        System.out.println("Hi");
        return "I got this (" + incomingMessage + ")"
                + "so I am sending it back !";
    }
}
