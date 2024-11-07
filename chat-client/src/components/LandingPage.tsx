import axios from "axios";
import { useState } from "react";
import "../styles/LandingPage.css";
import { useChatStore } from "../ChatStoreContext";
import { useNavigate } from "react-router-dom";

const LandingPage: React.FC = () => {
	const [nickname, setNickname] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [isSignUp, setIsSignUp] = useState(false);
	const chatStore = useChatStore();
	const navigate = useNavigate();
	
	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		const url = isSignUp ? "http://localhost:8080/api/auth/signup" : "http://localhost:8080/api/auth/login";
		
		try {
			const response = await axios.post(url, {
				email,
				nickname: isSignUp ? nickname : null,
				password,
			});

			if (isSignUp) {
				chatStore.setUserId(response.data.id);
				chatStore.setNickname(nickname);
			} else {
				const { token, id, nickname } = response.data;
				localStorage.setItem("jwtToken", token);
				chatStore.setUserId(id);
				chatStore.setNickname(nickname);
			}

			navigate("/chat");
		} catch (error) {
			console.error(error);
		}
	};

	return (
		<div className="landing-container">
      <h2>{isSignUp ? "Sign Up" : "Log In"}</h2>
      <form onSubmit={handleSubmit}>
				<input 
					type="email"
					value={email}
					onChange={(e) => setEmail(e.target.value)}
					placeholder="Enter your email"
				/>
				{isSignUp && 
				<input
					type="text"
					value={nickname}
					onChange={(e) => setNickname(e.target.value)}
					placeholder="Enter your nickname"
				/>}
				<input
					type="password"
					value={password}
					onChange={(e) => setPassword(e.target.value)}
					placeholder="Enter your password"
				/>
				<button type="submit">{isSignUp ? "Sign Up" : "Log In"}</button>
      </form>
			<div className="toggle-link">
				{isSignUp ? (
					<p>
						Already have an account?{" "}
						<span onClick={() => setIsSignUp(false)}>Log In</span>
					</p>
				) : (
					<p>
						Don't have an account?{" "}
						<span onClick={() => setIsSignUp(true)}>Sign Up</span>
					</p>
				)}
			</div>
    </div>
	)
}

export default LandingPage;