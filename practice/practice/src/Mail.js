// eslint-disable-next-line no-unused-vars
import { useLocation } from "react-router-dom";
// import axios from 'axios';
// import { useState } from "react";

function Mail(){

    // const [to, setTo] = useState('');
    // const [subject, setSubject] = useState('');
    // const [text, setText] = useState('');
  
    // const handleSubmit = async (e) => {
    //   e.preventDefault();
    //   try {
    //     const response = await axios.post('http://localhost:3001/send-email', {
    //       to,
    //       subject,
    //       text
    //     });
    //     alert('Email sent successfully: ' + response.data);
    //   } catch (error) {
    //     alert('Error sending email: ' + error.message);
    //   }
    // };
  
    // return (
    //   <div>
    //     <h1>Send Email</h1>
    //     <form onSubmit={handleSubmit}>
    //       <div>
    //         <label>To:</label>
    //         <input type="email" value={to} onChange={(e) => setTo(e.target.value)} required />
    //       </div>
    //       <div>
    //         <label>Subject:</label>
    //         <input type="text" value={subject} onChange={(e) => setSubject(e.target.value)} required />
    //       </div>
    //       <div>
    //         <label>Text:</label>
    //         <textarea value={text} onChange={(e) => setText(e.target.value)} required />
    //       </div>
    //       <button type="submit">Send</button>
    //     </form>
    //   </div>
    // );
}


export default Mail;