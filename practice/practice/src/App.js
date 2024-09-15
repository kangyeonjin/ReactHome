import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import Chat from './Chat';
import ErrorBoundary from './ErrorBoundary';

function App() {

  const navigate = useNavigate();

  const handle =()=>{

    navigate('/chat');
  }

  return (
    <div>
      <h1>hi</h1>
      <button onClick={handle}>chat</button>

    </div>
  );
}

function AppWrapper() {
  return (
    <ErrorBoundary>
    <Router>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/chat" element={<Chat />} />
      </Routes>
    </Router>
    </ErrorBoundary>
  );
}

export default AppWrapper;
// export default App;
