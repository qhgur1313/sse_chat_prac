import React, { useEffect, useRef } from "react";
import { observer } from "mobx-react-lite";
import { useChatStore } from "../ChatStoreContext";
import MessageInput from "./MessageInput";
import "../styles/Chat.css";
import { Message } from "../stores/ChatStore";

const Chat: React.FC = observer(() => {
  const chatStore = useChatStore();
  const messagesEndRef = useRef<HTMLDivElement | null>(null);
  const messages = chatStore.getMessages();

  useEffect(() => {
    chatStore.loadMessages();
    chatStore.connectToMessageStream();
  }, []);

  // 메시지가 추가될 때 스크롤을 아래로 내리기
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages.length]);

  const messageGroups: Message[][] = [];
  let currentGroup: Message[] = [];
  messages.map(msg => {
    if (currentGroup.length === 0) {
      currentGroup.push(msg);
    } else {
      const lastMessage = currentGroup[currentGroup.length - 1];
      if (msg.userId === lastMessage.userId && msg.timestamp === lastMessage.timestamp) {
        currentGroup.push(msg);
      } else {
        messageGroups.push(currentGroup);
        currentGroup = [msg];
      }
    }
  });
  if (currentGroup.length > 0) {
    messageGroups.push(currentGroup);
  }

  return (
    <div className="chat-container">
      <div className="chat-messages">
        {
          messageGroups.map(group => {
            const myMessage = group[0].userId === chatStore.getUserId();
            return (
              <div className="message-group">
                <div className={`${myMessage ? "my-message" : "other-message"}`}>
                  {!myMessage ? <div className="nickname">{group[0].nickname}</div> : null}
                  <div className="message-timestamp">
                    {myMessage ? <div className="timestamp">{(group[0].timestamp)}</div> : null}
                  <div className="message-group-content">
                    {
                      group.map(msg => (
                        <div className="message">
                          <div className="message-content">{msg.content}</div>
                        </div>
                      ))
                    }
                  </div>
                    {!myMessage ? <div className="timestamp">{(group[0].timestamp)}</div> : null}
                  </div>
                </div>
              </div>
            )
          })
        }
        <div ref={messagesEndRef} />
      </div>
      <MessageInput />
    </div>
  );
});

export default Chat;
