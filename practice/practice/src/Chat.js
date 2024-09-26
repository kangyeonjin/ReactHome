import { useEffect, useState } from "react";
import io from 'socket.io-client';
// import { v4 as uuidv4 } from 'uuid';  //uuid생성 라이브러리
import { useNavigate } from "react-router-dom";
import ChatComponent from './ChatComponent';

// const socket = io('ws://localhost:8080/ws/chat') //소켓연결

useEffect(() => {
    const socket = io('http://localhost:8080/ws/chat'); // 소켓 서버에 연결

    const handleMessage = (msg) => {
        console.log('Received message:', msg);
        if (msg && msg.sender && msg.message) {
            setChat((prevChat) => [...prevChat, { sender: msg.sender, message: msg.message }]);
        }
    };

    socket.on('chat message', handleMessage);

    return () => {
        socket.off('chat message', handleMessage);
        socket.disconnect(); // 컴포넌트가 언마운트될 때 소켓 연결 해제
    };
}, []);

function Chat (){

    const [message, setMessage] = useState('');
    const [chat, setChat] = useState([]);  //chat상태배열초기화
    // const [userId] = useState(uuidv4()); // 고유한 사용자 ID 생성
    const [name, setName] = useState('');
    const [roomId] = useState('defaultRoom');
    // const [room, setRoom] = useState('default'); //기본방설정
    const navigate = useNavigate();

    //채팅메시지 수신
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

    //메세지 자동저장기능
    useEffect(() => {
        const intervalId = setInterval(() => {
            if (message.trim() !== '' && name.trim() !== '') { // 빈 문자열 체크 추가
                const chatMessage = createChatMessage();
                saveMessage(chatMessage);
            }
        }, 10000);
        return () => clearInterval(intervalId);
    }, [message, name, roomId]);

    //메시지 생성함수
    const createChatMessage = () => ({
                    message,
                    sender: name,
                    timestamp: new Date().toISOString(), // 현재 시간 저장
                    roomId,
                    receiver: '', // 수신자 정보가 필요하다면 설정
                    });
                
    //메시지 저장 함수
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

    //메시지 전송함수
    const sendMessage = async (e) => {
        e.preventDefault();
        if (message && name) {
            const chatMessage = createChatMessage();
            try{
            socket.emit('chat message', chatMessage); //소켓으로 메세지 전송
            await saveMessage(chatMessage); //메시지 저장
            setMessage('');  //메세지 입력필드 초기화
            }catch(error){
                console.error('error sending message', error);
            }
        }else{
            console.error('please enter both name and message');
        }
    };
        

    return (
        <>
            <h1>Chat Room</h1>
            <ChatComponent chat={chat} /> {/* ChatComponent에 chat prop 전달 */}

            <form onSubmit={sendMessage}>
                <input type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Enter your name" />

                <input type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Enter message" />

                <button type="submit">Send</button>
                <br /><br />
                <button type="button" onClick={() => navigate('/chatdata', { state: { chat } })}>Data</button>
            </form>
        </>
    );
}


export default Chat;