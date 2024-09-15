import { useEffect, useState } from "react";
import io from 'socket.io-client';

const socket = io('http://localhost:5000')

function Chat (){

    const [message, setMessage] = useState('');
    const [chat, setChat] = useState([]);  //chat상태배열초기화

    useEffect(()=>{
        socket.on('chat message', (msg)=>{
            setChat((prevChat)=>[...prevChat, msg]);  //chat에 새 메세지 추가
        });

        return() =>{
            socket.off('chat message');
        };
    },[]);

    const sendMessage =(e) => {
        e.preventDefault();
        if(message){
            socket.emit('chat message', message);
            setMessage('');
        }
    };


    return(
<>
<h1>page</h1>
<div>
{chat.length > 0 && chat.map((msg, index) => (  // chat 상태 사용
        <p key={index}>{msg}</p>
        ))}
</div>

<form onSubmit={sendMessage}>
    <input type="text" value={message} 
    onChange={(e)=>setMessage(e.target.value)}
    placeholder="Enter message"/>
    <button type="submit">Send</button>
</form>
</>
    );
}

export default Chat;