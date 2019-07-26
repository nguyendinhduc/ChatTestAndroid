package com.t3h.testclient.socket;

import com.t3h.testclient.model.MessageChatResponse;

public interface ReciverMessage {
    void recieve(MessageChatResponse response);
}
