import axios from "axios";
import { makeAutoObservable } from "mobx";

export interface Message {
	id: number;
	userId: number;
	nickname: string;
	content: string;
	timestamp: string;
}

class ChatStore {
	private messages: Message[] = [];
	private nickname: string = "";
	private userId: number | null = null;

	constructor() {
		makeAutoObservable(this);
	}

	setNickname(nickname: string) {
		this.nickname = nickname;
	}

	setUserId(userId: number) {
		this.userId = userId;
	}

	getUserId() {
		return this.userId;
	}

	addMessage(message: Message) {
    this.messages.push(message);
	}

	getMessages() {
		return this.messages;
	}

	getNickname() {
		return this.nickname;
	}

	async loadMessages() {
		try {
			const response = await axios.get("http://localhost:8080/api/messages");
			this.messages = response.data;
		} catch (error) {
			console.error("Failed to load messages:", error);
		}
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

	async sendMessage(message: string) {
		if (!this.nickname) {
			return;
		}

		const response = await axios.post("http://localhost:8080/api/messages", {
			userId: this.userId,
			nickname: this.nickname,
			content: message,
		});

		this.addMessage(response.data);
	}
}

export const chatStore = new ChatStore();