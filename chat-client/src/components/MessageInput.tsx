import { observer } from "mobx-react-lite";
import { chatStore } from "../stores/ChatStore"
import "../styles/Chat.css"
import { useState } from "react";

const MessageInput: React.FC = observer(() => {
  const [message, setMessage] = useState("");

	const handleSend = () => {
    if (message.trim() === "") return; // 빈 메시지 방지
    chatStore.sendMessage(message);
    setMessage("");
	};

  const handleKeyPress = (event: React.KeyboardEvent) => {
    if (event.key === "Enter" && !event.shiftKey) { // Shift + Enter는 제외
      event.preventDefault(); // Enter의 기본 동작(새 줄 추가) 방지
      handleSend();
    }
  };

	return (
		<div className="chat-input">
      <input
        type="text"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyPress}
        placeholder="Type a message..."
      />
      <button onClick={handleSend}>Send</button>
    </div>
	)
})

export default MessageInput;