import React, { createContext, useContext } from "react";
import { chatStore } from "./stores/ChatStore";

const ChatStoreContext = createContext(chatStore);

interface ChatStoreProviderProps {
  children: React.ReactNode;
}

export const ChatStoreProvider: React.FC<ChatStoreProviderProps> = ({ children }) => {
	return (
		<ChatStoreContext.Provider value={chatStore}>
			{children}
		</ChatStoreContext.Provider>
	);
}

export const useChatStore = () => {
	const store = useContext(ChatStoreContext);
	if (!store) {
		throw new Error("useChatStore must be used within a ChatStoreProvider");
	}
	return store;
}