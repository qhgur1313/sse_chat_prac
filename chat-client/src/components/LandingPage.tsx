import axios from "axios";
import { useState } from "react";
import "../styles/LandingPage.css";
import { useChatStore } from "../ChatStoreContext";
import { useNavigate } from "react-router-dom";

const LandingPage: React.FC = () => {
	const [nickname, setNickname] = useState("");
	const chatStore = useChatStore();
	const navigate = useNavigate();
	
	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		if (!nickname) {
			return;
		}
		
		try {
			const response = await axios.post("http://localhost:8080/api/users", {
				nickname,
			});

			chatStore.setUserId(response.data.id);
			chatStore.setNickname(nickname);

			navigate("/chat");
		} catch (error) {
			console.error(error);
		}
	};

	return (
		<div className="landing-container">
      <h2>Enter your nickname to join the chat</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          placeholder="Enter your nickname"
        />
        <button type="submit">Join Chat</button>
      </form>
    </div>
	)
}

export default LandingPage;