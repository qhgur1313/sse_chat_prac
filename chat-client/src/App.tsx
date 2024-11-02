import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Chat from './components/Chat';
import LandingPage from './components/LandingPage';
import { ChatStoreProvider } from './ChatStoreContext';

function App() {
  return (
    <ChatStoreProvider>
      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/chat" element={<Chat />} />
        </Routes>
      </Router>
    </ChatStoreProvider>
  );
}

export default App;
