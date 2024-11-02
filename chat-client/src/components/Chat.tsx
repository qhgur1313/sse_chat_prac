import { observer } from "mobx-react-lite";
import { useEffect, useRef } from "react";
import MessageInput from "./MessageInput";
import "../styles/Chat.css";
import { useChatStore } from "../ChatStoreContext";

const Chat: React.FC = observer(() => {
  const chatStore = useChatStore();
	useEffect(() => {
    chatStore.connectToMessageStream();
	}, []);

  const messagesEndRef = useRef<HTMLDivElement | null>(null);

  // 메시지가 추가될 때 스크롤을 아래로 내리기
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [chatStore.messages.length]);

	return (
    <div className="chat-container">
      <div className="chat-messages">
        {chatStore.messages.map((message, index) => (
          <div
            key={index}
            className={`chat-message ${
              message.nickname === chatStore.nickname ? "sent" : "received"
            }`}
          >
            <span className="nickname">{message.nickname}</span>
            <div className={`chat-bubble ${message.nickname === chatStore.nickname ? "sent" : "received"}`}>
              {message.content}
            </div>
          </div>
        ))}
        <div ref={messagesEndRef} />
      </div>
      <MessageInput />
    </div>
	)
})

export default Chat;