const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: './src/index.js', // 엔트리 파일
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js', // 번들링된 파일 이름
        publicPath: '/',
    },
    module: {
        rules: [
            {
                test: /\.js$/, // .js 파일 처리
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react'], // Babel 프리셋
                    },
                },
            },
            {
                test: /\.css$/, // .css 파일 처리
                use: ['style-loader', 'css-loader'],
            },
        ],
    },
    devServer: {
        static: {
            directory: path.join(__dirname, 'dist'), // 정적 파일 디렉토리 설정
        },
        compress: true,
        port: 3000,
        historyApiFallback: true, // React Router 사용 시 필요
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './public/index.html', // 템플릿 HTML 파일
        }),
    ],
    resolve: {
        extensions: ['.js', '.jsx'], // import 시 확장자 생략 가능
    },
};
