export const SEND_MESSAGE = 'SEND_MESSAGE';
export const UPDATE_CHAT_HISTORY = 'UPDATE_CHAT_HISTORY';

export const sendMessage = (message) => ({
    type: SEND_MESSAGE,
    payload: message,
});

export const updateChatHistory = (messages) => ({
    type: UPDATE_CHAT_HISTORY,
    payload: messages,
});

//서버에서 채팅기록을 가져옴
export const fetchChatHistory = () => async (dispatch) => {
    try {
        const response = await fetch('http://localhost:8080/chat/history');
        if (!response.ok) {
            throw new Error('Failed to fetch chat history');
        }
        const chatHistory = await response.json();
        dispatch({
            type: 'SET_CHAT_HISTORY',
            payload: chatHistory,
        });
    } catch (error) {
        console.error('Error fetching chat history:', error);
    }
};