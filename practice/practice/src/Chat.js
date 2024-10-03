// Chat.js
import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const Chat = () => {
    const [chatMessages, setChatMessages] = useState([]);
    const [message, setMessage] = useState('');
    const [sender, setSender] = useState(''); // 발신자 정보
    const [roomId, setRoomId] = useState('room1'); // 대화방 ID

    useEffect(() => {
        // WebSocket 연결
        const socket = new SockJS('http://localhost:8080/ws/chat'); // 서버 WebSocket 엔드포인트와 연결
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            // 서버로부터 메시지 수신
            stompClient.subscribe(`/topic/room/${roomId}`, (messageOutput) => {
                const message = JSON.parse(messageOutput.body);
                setChatMessages((prevMessages) => [...prevMessages, message]);
            });
        });

        // 컴포넌트 언마운트 시 WebSocket 연결 해제
        return () => {
            stompClient.disconnect();
        };
    }, [roomId]);

    const sendMessage = () => {
        const socket = new SockJS('http://localhost:8080/ws/chat');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            // 클라이언트에서 서버로 메시지 전송
            stompClient.send('/app/chat.send', {}, JSON.stringify({
                sender,
                roomId,
                message
            }));
            setMessage(''); // 메시지 전송 후 입력 필드 비우기
        });
    };

    return (
        <div>
            <h2>Chat Room: {roomId}</h2>
            <div>
                <input
                    type="text"
                    value={sender}
                    onChange={(e) => setSender(e.target.value)}
                    placeholder="Your Name"
                />
            </div>
            <div>
                <input
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Enter your message"
                />
                <button onClick={sendMessage}>Send</button>
            </div>
            <div>
                {chatMessages.map((msg, index) => (
                    <div key={index}>
                        <strong>{msg.sender}</strong>: {msg.message}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Chat;
