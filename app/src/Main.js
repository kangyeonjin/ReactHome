import React from 'react';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom'

function Main(){

    //입력값 저장함
    const [inputValue, setInputValue] = useState(''); //단일 입력값
    const [inputList, setInputList] = useState([]);  //입력값 리스트상태

    const navigate = useNavigate();

    //입력값 업데이트
    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    }

    const addToListAndNavigate = () =>{
    
        if(inputValue.trim()){

            //입력값을 리스트에 추가
            const newList =[...inputList, inputValue];
            setInputList(newList); //상태업데이트
            setInputValue(''); //입력값초기화

            //페이지 이동하면서 list전달
            navigate('/Etc',{state:{inputList:newList}});
        }else{
            alert('값을 입력하세요');  
        }
    }
    

    const goLogin =() =>{
        navigate('/Login');
    
        };

    //etc페이지로 전달
    // const goEtc =() =>{
    //     console.log('Navigating to ETC with', inputList);
    //     if(inputList.length >0){
    //     navigate('/Etc',{state:{inputList}});
    //     }else{
    //         alert('리스트에 값이 없습니다');
    //     }

    // };

    return(
        <div>
        <h1>나오니?</h1>
        <input value={inputValue} onChange={handleInputChange} placeholder='값을 입력하세요'></input> <br/> <br/>
        <button onClick={addToListAndNavigate}>추가</button> {/* 입력값 리스트에 추가 */}
        <button onClick={goLogin}>로그인 </button>
        <br/> <br/>
        {/* <button onClick={goEtc}>기타</button> */}
        </div>
    )

}

export default Main;