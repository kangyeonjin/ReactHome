import { useEffect, useState } from "react";
import io from 'socket.io-client';
// import { v4 as uuidv4 } from 'uuid';  //uuid생성 라이브러리
import { useNavigate } from "react-router-dom";

const socket = io('ws://localhost:5000')

function Chat (){

    const [message, setMessage] = useState('');
    const [chat, setChat] = useState([]);  //chat상태배열초기화
    // const [userId] = useState(uuidv4()); // 고유한 사용자 ID 생성
    const [name, setName] = useState('');
    // const [room, setRoom] = useState('default'); //기본방설정
    const navigate = useNavigate();

    useEffect(() => {

        // socket.emit('join room', room); //방에 참여

        const handleMessage = (msg) => {
            console.log(msg);
            if(msg && msg.name && msg.message){
            // setChat((prevChat) => [...prevChat, msg]);
            setChat((prevChat) => [...prevChat, { name: msg.name, message: msg.message }]);
            }else{
                console.error('Invalid message object :', msg);
            }
        };
    
        socket.on('chat message', handleMessage);
    
        return () => {
            socket.off('chat message', handleMessage);
        };
    }, []);
    // }, [room]);

    // useEffect(()=>{
    //     socket.on('chat message', (msg)=>{
    //         setChat((prevChat)=>[...prevChat, msg]);  //chat에 새 메세지 추가
    //     });

    //     return() =>{
    //         socket.off('chat message');
    //     };
    // },[]);

    const sendMessage =(e) => {
        e.preventDefault();
        if(message && name){
            console.log({name, message});
            socket.emit('chat message',{name, message}); //name과 message를 함께보냄
            setMessage('');
        }
    };
    return(
<>
{/* <h1>Chat Room:{room}</h1> */}
<h1>chat</h1>
<div>
{chat.length > 0 && chat.map((msg, index) => (  // chat 상태 사용
        <p key={index}><strong>{msg.name}:</strong>{msg.message}</p>
        ))}
</div>

<form onSubmit={sendMessage}>
<input type="text" value={name} 
    onChange={(e) => setName(e.target.value)}
    placeholder="Enter your name"/>
    <input type="text" value={message} 
    onChange={(e)=>setMessage(e.target.value)}
    placeholder="Enter message"/>
    <button type="submit">Send</button>
    <br/><br/>
    <button type="button" onClick={() => navigate('/chatdata', { state: { chat } })}>Data</button>
</form>
</>
    );
}

export default Chat;