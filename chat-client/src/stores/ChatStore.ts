import axios from "axios";
import { makeAutoObservable } from "mobx";

interface Message {
	id: number;
	userId: number;
	nickname: string;
	content: string;
	timestamp: string;
}

class ChatStore {
	messages: Message[] = [];
	nickname: string = "";
	messageInput: string = "";
	userId: number | null = null;

	constructor() {
		console.log("ChatStore created");
		makeAutoObservable(this);
	}

	setNickname(nickname: string) {
		this.nickname = nickname;
	}

	setMessageInput(messageInput: string) {
		this.messageInput = messageInput;
	}

	setUserId(userId: number) {
		this.userId = userId;
	}

	addMessage(message: Message) {
    this.messages.push(message);
	}

	connectToMessageStream() {
    const eventSource = new EventSource("http://localhost:8080/api/messages/stream");

    eventSource.onmessage = (event) => {
      const newMessage: Message = JSON.parse(event.data);

			if (newMessage.userId === this.userId) {
				return;
			}

      this.addMessage(newMessage);
    };

    eventSource.onerror = (error) => {
      console.error("EventSource failed:", error);
      eventSource.close();
    };

    return eventSource; 
	}

	async sendMessage() {
		if (!this.nickname || !this.messageInput) {
			return;
		}

		const response = await axios.post("http://localhost:8080/api/messages", {
			userId: this.userId,
			nickname: this.nickname,
			content: this.messageInput,
		});

		this.addMessage(response.data);
		this.setMessageInput("");
	}
}

export const chatStore = new ChatStore();