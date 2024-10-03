import {useSelector, useDispatch} from 'react-redux';
import { useEffect} from "react";
import { fetchChatHistory, sendMessage, updateChatHistory } from './actions/chatActions';
// import io from 'socket.io-client';
// import { v4 as uuidv4 } from 'uuid';  //uuid생성 라이브러리
import { useNavigate } from "react-router-dom";
import { io} from 'socket.io-client';

const socket = io('ws://localhost:8080')

function server (){

    // const [message, setMessage] = useState('');
    // const [chat, setChat] = useState([]);  //chat상태배열초기화
    // const [name, setName] = useState('');
    const dispatch = useDispatch();
    const navigate = useNavigate();

    //redux상태가져오기
    const {chat, name, message} = useSelector((state) => ({
        chat: state.chat,
        name: state.name,
        message: state.message,
    }));

    useEffect(() => {

        //기존채팅기록을 가져오는 액션을 호출
        dispatch(fetchChatHistory());

        // socket.emit('join room', room); //방에 참여
        //메세지 수신할때 
        const handleMessage = (msg) => {
            console.log(msg);
            // if(msg && msg.name && msg.message){
            // // setChat((prevChat) => [...prevChat, msg]);
            // setChat((prevChat) => [...prevChat, { name: msg.name, message: msg.message }]);
            // }else{
            //     console.error('Invalid message object :', msg);
            // }

            const decodedMessage = decodeURIComponent(msg.message);

            if (msg && msg.name && msg.message) {
                dispatch(updateChatHistory([...chat, { name: msg.name, message: msg.message }]));
            } else {
                console.error('Invalid message object :', msg);
            }

        };
    
        socket.on('chat message', handleMessage);
    
        return () => {
            socket.off('chat message', handleMessage);
        };
    }, [dispatch, chat]);

    const handleSendMessage = async (e) => {
        e.preventDefault();
        if (message && name) {

            //메시지 uri 인코딩
            const encodedMessage = encodeURIComponent(message);
            const chatMessage = { name, message: encodedMessage };

            console.log(chatMessage);
            // 메시지를 소켓을 통해 전송
            socket.emit('chat message', chatMessage);

            // Redux 액션으로 메시지 전송
            dispatch(sendMessage(chatMessage));

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
                console.log('Message saved:', data);
            } catch (error) {
                console.error('Error saving message:', error);
            }
        }
    };

    return (
        <div>
            <h1>Chat</h1>
            <div>
                {chat.length > 0 &&
                    chat.map((msg, index) => (
                        <p key={index}>
                            <strong>{msg.name}:</strong> {msg.message}
                        </p>
                    ))}
            </div>

            <form onSubmit={handleSendMessage}>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => dispatch({ type: 'SET_NAME', payload: e.target.value })} // Redux로 이름 업데이트
                    placeholder="Enter your name"
                />
                <input
                    type="text"
                    value={message}
                    onChange={(e) => dispatch({ type: 'SET_MESSAGE', payload: e.target.value })} // Redux로 메시지 업데이트
                    placeholder="Enter message"
                />
                <button type="submit">Send</button>
                <br />
                <br />
                <button type="button" onClick={() => navigate('/chatdata', { state: { chat } })}>
                    Data
                </button>
            </form>
        </div>
    );
}


export default server;