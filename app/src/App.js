import React from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Main from "./Main";
import Login from './Login'
import Etc from './Etc'

function App(){
return(
  <Router>
    <Routes>
      <Route path="/" element={<Main/>}/>
      {/* <Route path="/Main" element={<Main/>}/> */}
      <Route path="/Login" element={<Login/>}/>
      <Route path="/Etc" element={<Etc/>}/>
  </Routes>
</Router>
  );
}

export default App;