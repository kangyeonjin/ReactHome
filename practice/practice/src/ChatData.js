import { useLocation } from "react-router-dom";


function ChatData(){

    const location = useLocation();
    const {chat} =location.state||{chat:[]};

    return(
        <div>
            <h1>chat data</h1>
            <div>
                {chat.length > 0 && chat.map((msg, index)=>(
                    <p key={index}><strong>{msg.name}:</strong>{msg.message}</p>
                ))}
            </div>
        </div>
    );

}

export default ChatData;