const express = require('express');
const http = require('http');
const {Server} = require('socket.io');
const cors = require('cors');

const app= express();
const server = http.createServer(app);
const path = require('path');


// CORS 미들웨어 추가
app.use(cors({
    origin: "http://localhost:3000", // 클라이언트의 URL
    methods: ["GET", "POST"],
    allowedHeaders: ["Content-Type"]
}));


// 정적 파일 제공 설정
// app.use(express.static(path.join(__dirname, 'build')));
app.use(express.static(path.join(__dirname, 'public')));

// 기본 라우트 처리
app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

const io = new Server(server, {
    cors: {
      origin: "http://localhost:3000", // 클라이언트가 실행되는 주소
      methods: ["GET", "POST"]
    }
  });

io.on('connection', (socket) =>{
    console.log('a user connected');

    socket.on('chat message', (msg)=>{
        io.emit('chat message', msg); //모든 클라이언트에게 메세지 전송
    });

    socket.on('disconnect', ()=>{
        console.log('user disconnected');
    })
})

server.listen(5000, ()=>{
    console.log('listening on *:5000');
})