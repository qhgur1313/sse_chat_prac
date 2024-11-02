import { observer } from "mobx-react-lite";
import { chatStore } from "../stores/ChatStore"
import "../styles/Chat.css"

const MessageInput: React.FC = observer(() => {
	const handleSend = () => {
    if (chatStore.messageInput.trim() === "") return; // 빈 메시지 방지
    chatStore.sendMessage();
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
        value={chatStore.messageInput}
        onChange={(e) => chatStore.setMessageInput(e.target.value)}
        onKeyDown={handleKeyPress}
        placeholder="Type a message..."
      />
      <button onClick={handleSend}>Send</button>
    </div>
	)
})

export default MessageInput;