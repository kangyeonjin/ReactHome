import { SEND_MESSAGE, UPDATE_CHAT_HISTORY } from '../actions/chatActions';

const initialState = {
    chat: [],
    name: '',
    message: '',
};

const chatReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEND_MESSAGE:
            return {
                ...state,
                chat: [...state.chat, action.payload],
                message: '',
            };
        case UPDATE_CHAT_HISTORY:
            return {
                ...state,
                chat: action.payload,
            };
        default:
            return state;
    }
};

export default chatReducer;