import React from "react";
import { useState, useEffect} from 'react';
import {useLocation} from 'react-router-dom';

function Etc(){

    const location = useLocation();
    // const [savedValue, setSavedValue] = useState('');
    const [inputList, setInputList] = useState([]);

    // useEffect(()=>{
    //     if(location.state?.inputValue){
    //         setSavedValue(location.state.inputValue); //전달 받은 값 저장
    //     }
    // },[location.state]);

    useEffect(()=>{
        console.log('location state', location.state);
        if(location.state?.inputList){
        setInputList(location.state.inputList);
        }
    },[location.state]);

return(
    <div>
    <h1>기타</h1>
    {/* {savedValue ? <p>저장된값:{savedValue}</p>:<p>저장된값이 없음</p>} */}
    {inputList.length>0 ? (
        <table border="1">
            <thead>
                <tr>
                    <th>입력값</th>
                </tr>
            </thead>
            <tbody>
                {inputList.map((value, index)=>(
                    <tr key={index}>
                        <td>{value}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    ):(<p>저장된 값이 없음</p>)}
    
    </div>
);

}

export default Etc;