import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import Chat from './Chat';
import ErrorBoundary from './ErrorBoundary';
import ChatData from './ChatData';
import Mail from './Mail';
import React from 'react';

function App() {

  const navigate = useNavigate();

  const handle =()=>{

    navigate('/chat');
  }

  const mail =() =>{

    navigate('/mail');
  }

  return (
    <div>
      <h1>hi</h1>
      <button onClick={handle}>chat</button><br/><br/>
      <button onClick={mail}>mail</button><br/>

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
        <Route path='/chatdata' element={<ChatData/>}/>
        <Route path='/mail' element={<Mail/>}/>
      </Routes>
    </Router>
    </ErrorBoundary>
  );
}

export default AppWrapper;
// export default App;
