import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import chatReducer from './reducers/chatReducer';

const store = createStore(chatReducer, applyMiddleware(thunk));

export default store;