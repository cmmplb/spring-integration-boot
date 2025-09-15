package io.github.cmmplb.cache.domain.dto;

import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-30 10:24:39
 * @since jdk 1.8
 */

public class RedisMessage extends DefaultMessage {

    private static final long serialVersionUID = -4609407855209805841L;

    public final Object msgBody;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public RedisMessage(byte[] channel, byte[] body) {
        super(channel, body);
        msgBody = new Jackson2JsonRedisSerializer(Object.class).deserialize(body);
    }

    public static class MessageBody implements Serializable {
        private static final long serialVersionUID = 14363855843683360L;
        public final Serializable sessionId;
        public final String nodeId;
        public String msg = "";

        public MessageBody(Serializable sessionId, String nodeId) {
            this.sessionId = sessionId;
            this.nodeId = nodeId;
        }

        public MessageBody(Serializable sessionId, String nodeId, String msg) {
            this.sessionId = sessionId;
            this.nodeId = nodeId;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "MessageBody{" +
                    "sessionId='" + sessionId + '\'' +
                    ", nodeId='" + nodeId + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
