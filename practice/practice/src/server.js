const express = require('express');
const http = require('http');
const {Server} = require('socket.io');
const cors = require('cors');
const mongoose = require('mongoose');

const app= express();
const server = http.createServer(app);
const path = require('path');
const io = new Server(server);  // 여기서 socketIo를 Server로 변경

mongoose.connect('mongodb://localhost:27017/chat', {
    userNewUrlParser:true,
    useUnifiedTopology: true
});

const chatSchema = new mongoose.Schema({
    name: String,
    message: String,
    timestamp:{type: Date, default: Date.now}
});

const Chat = mongoose.model('Chat', chatSchema);

// const corsOptions = {
//     origin: "http://localhost:3000",
//     methods: ["GET", "POST"],
//     allowedHeaders: ["Content-Type"]
// };
// app.use(cors(corsOptions));

//CORS 미들웨어 추가
app.use(cors({
    origin: "http://localhost:3000", // 클라이언트의 URL
    methods: ["GET", "POST"],
    allowedHeaders: ["Content-Type"]
}));


// 정적 파일 제공 설정
app.use(express.static(path.join(__dirname, 'build')));
app.use(express.static(path.join(__dirname, 'public')));

// // 기본 라우트 처리
app.get('*', (req, res) => {
    // res.sendFile(path.join(__dirname, 'build', 'index.html'));
    res.sendFile(path.resolve(__dirname, 'build', 'index.html'));
});

// const io = new Server(server, {
//     cors: {
//       origin: "http://localhost:3000", // 클라이언트가 실행되는 주소
//       methods: ["GET", "POST"]
//     }
//   });

io.on('connection', (socket) =>{
    console.log('a user connected');

    //클라이언트가 방에 참여
    socket.on('join room', (room)=>{
        socket.join(room);
        console.log('User joined room: ${room}'); //템플릿 리터럴사용
    })

    socket.on('chat message', (msg)=>{
        console.log(msg);
        // const room = msg.room;
        const chatMessage = new Chat({name:msg.name, message:msg.message});
        chatMessage.save().then(()=>{
            io.emit('chat message', msg);
        });
        // io.emit('chat message', msg); //모든 클라이언트에게 메세지 전송
        // io.to(room).emit('chat message', msg.message); //특정방에 메세지전송
    });

    socket.on('disconnect', ()=>{
        console.log('user disconnected');
    })
})

server.listen(5000, ()=>{
    console.log('listening on *:5000');
})