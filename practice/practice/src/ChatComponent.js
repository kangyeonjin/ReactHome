import React, { useEffect, useState } from 'react';

const ChatComponent = () => {
    const [messages, setMessages] = useState([]);
    const [socket, setSocket] = useState(null);

    useEffect(() => {
        // WebSocket 연결
        const socketInstance = new WebSocket('ws://localhost:8080/ws/chat');
        setSocket(socketInstance);

        // WebSocket 연결이 성공적으로 열리면 호출되는 함수
        socketInstance.onopen = () => {
            console.log('WebSocket 연결 성공');
            socketInstance.send('안녕하세요! 웹소켓에 연결되었습니다.');
        };

        // 서버에서 메시지를 수신하면 호출되는 함수
        socketInstance.onmessage = (event) => {
            console.log('받은 메시지:', event.data);
            setMessages((prevMessages) => [...prevMessages, event.data]);
        };

        // WebSocket 연결이 종료되면 호출되는 함수
        socketInstance.onclose = () => {
            console.log('WebSocket 연결 종료');
        };

        // WebSocket에서 오류가 발생하면 호출되는 함수
        socketInstance.onerror = (error) => {
            console.error('WebSocket 오류:', error);
        };

        // 컴포넌트가 언마운트될 때 WebSocket 연결 종료
        return () => {
            socketInstance.close();
        };
    }, []);

    return (
        <div>
            <h2>채팅 메시지</h2>
            <ul>
                {messages.map((msg, index) => (
                    <li key={index}>{msg}</li>
                ))}
            </ul>
        </div>
    );
};

export default ChatComponent;