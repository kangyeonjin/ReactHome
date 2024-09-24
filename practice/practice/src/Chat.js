import { useEffect, useState } from "react";
import io from 'socket.io-client';
// import { v4 as uuidv4 } from 'uuid';  //uuid생성 라이브러리
import { useNavigate } from "react-router-dom";

const socket = io('ws://localhost:8080')

function Chat (){

    const [message, setMessage] = useState('');
    const [chat, setChat] = useState([]);  //chat상태배열초기화
    // const [userId] = useState(uuidv4()); // 고유한 사용자 ID 생성
    const [name, setName] = useState('');
    const [roomId] = useState('defaultRoom');
    // const [room, setRoom] = useState('default'); //기본방설정
    const navigate = useNavigate();

    useEffect(() => {

        // socket.emit('join room', room); //방에 참여

        const handleMessage = (msg) => {
            console.log('Received message:', msg);
            if(msg && msg.name && msg.message){
            // setChat((prevChat) => [...prevChat, msg]);
            setChat((prevChat) => [...prevChat, { sender: msg.sender, message: msg.message, timestamp: msg.timestamp }]);
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

    //자동저장기능
    useEffect(()=> {
        const intervalId = setInterval(()=>{
            if(message && name){
                const chatMessage ={
                    message,
                    sender: name,
                    timestamp: new Date().toISOString(), // 현재 시간 저장
                    roomId,
                    receiver: '', // 수신자 정보가 필요하다면 설정
                    };
                saveMessage(chatMessage);
            }
        }, 10000); //10초마다 저장
        return () => clearInterval(intervalId);
    },[message, name, roomId]);

    const saveMessage = async (chatMessage) => {
        try {
            const response = await fetch('http://localhost:8080/chat/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(chatMessage),
            });

            if (!response.ok) {
                throw new Error('Failed to save message');
            }

            const data = await response.json();
            console.log('Message auto-saved:', data);
        } catch (error) {
            console.error('Error saving message:', error);
        }
    };

    const sendMessage = async (e) => {
        e.preventDefault();
        if (message && name) {
            const chatMessage = {
                message,
                sender: name,
                timestamp: new Date().toISOString(), // 현재 시간 저장
                roomId,
                receiver: '', // 수신자 정보가 필요하다면 설정 
                };
            socket.emit('chat message', chatMessage); // name과 message를 함께 보냄

            // 메시지를 즉시 저장
            await saveMessage(chatMessage);

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