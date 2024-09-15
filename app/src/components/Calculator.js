import { useState } from "react";
import CalculatorContext from "../context/Mycontext";

const Calculator = () => {
    const [inputValue, setInputValue] = useState("");
    return (
        <CalculatorContext.Consumer>
            {({result, add, subtract, multiply, divide, reset})=>(
                <>
                <div>결과: {result}</div>
                <input type="number" value={inputValue}
                onChange={(e)=> setInputValue(e.target.value)}/>

                <div>
                    <button onClick={()=>add(Number(inputValue))}>+</button>
                    <button onClick={()=>subtract(Number(inputValue))}>-</button>
                    <button onClick={()=>divide(Number(inputValue))}>/</button>
                    <button onClick={()=>multiply(Number(inputValue))}>*</button>
                    <button onClick={()=>{
                        reset(0);
                    setInputValue("");}}>c</button>
                </div>
                </>
            )}
        </CalculatorContext.Consumer>
    )
}

export default Calculator;