import { useContext, useState } from "react";
import {CalculatorContext, TodoContext} from "../context/Mycontext";

// const CombineComponents = ()=>{
//     const [inputValue, setInputValue] = useState("");
//     return(
//         <CalculatorContext.Consumer>
//             {({result, add, subtract, multiply, divide, reset})=>
//             <TodoContext.Consumer>
//                 {({todos, onAddTodo, onDeleteTodo, onToggleTodo})=>(
//                     <>
//                     <div>계산결과 :{result}</div>
//                     <input type="number" value={inputValue}
//                     onChange={(e)=>setInputValue(e.target.value)}/>

//                     <div>
//                     <button onClick={()=> add(Number(inputValue))}>
//                             +
//                         </button>
//                         <button onClick={()=> subtract(Number(inputValue))}>
//                             -
//                         </button>
//                         <button onClick={()=> divide(Number(inputValue))}>
//                             /
//                         </button>
//                         <button onClick={()=> multiply(Number(inputValue))}>
//                             *
//                         </button>
//                         <button onClick={()=>{reset(0); setInputValue("");}}>C</button>
//                     </div>

//                     <main>
//                         <div>
//                             <TodoForm onAddTodo={onAddTodo}/>
//                             <hr />
//                             {todos.map((todo)=>(
//                                 <section key = {todo.id}>
//                                     <input type="checkbox"
//                                     onClick={()=>onToggleTodo(todo.id)}/>
//                                     <div style={{textDecoration:todo.isDone
//                                         ?"line-through" :"none"
//                                     }}>{todo.content}</div>
//                                     <button onClick={()=>onDeleteTodo(todo.id)}>
//                                         ❌
//                                     </button>
//                                 </section>
//                             ))}
//                         </div>
//                     </main>
//                     </>
//                 )}
//                 </TodoContext.Consumer>
//                 }
//         </CalculatorContext.Consumer>
//     )
// }

// const TodoForm = ({onAddTodo}) =>{
//     const [inputValue, setInputValue] = useState("");
//     return(
//         <form onSubmit={(e)=>e.preventDefault()}>
//             <input type="text" value={inputValue}
//             placeholder="할일을 작성해주세요"
//             onChange={(e)=> setInputValue(e.target.value)}/>
//             <button type="submit"
//             onClick={()=>
//             {onAddTodo(inputValue);
//                 setInputValue("");
//             }
//             }>추가</button>
//         </form>
//     )
// }

function CombineComponents(){
    const [inputValue, setInputValue] = useState("");
    const {result, add, subtract, multiply, divide, reset} = useContext(CalculatorContext);
    const {todos, onAddTodo, onDeleteTodo, onToggleTodo} = useContext(TodoContext);

    return(
        <>
        <div>계산결과: {result}</div>
        <input type="number" value={inputValue} onChange={(e)=>setInputValue(e.target.value)}/>
        
        <div>
            <button onClick={()=>add(Number(inputValue))}>+</button>
            <button onClick={()=>subtract(Number(inputValue))}>-</button>
            <button onClick={()=>divide(Number(inputValue))}>/</button>
            <button onClick={()=>multiply(Number(inputValue))}>*</button>
            <button onClick={()=>{reset(0); setInputValue("");}}>c</button>
        </div>
        <main>
            <div>
                <TodoForm onAddTodo={onAddTodo}/>
                <hr />
                {todos.map((todo)=>(
                    <section key={todo.id}>
                        <input type="checkbox" onClick={()=>onToggleTodo(todo.id)}/>
                        <div style={{textDecoration:todo.isDone?"line.through": "none"}}>{todo.content}</div>
                        <button onClick={()=> onDeleteTodo(todo.id)}>❌</button>
                    </section>
                ))}
            </div>
        </main>
        </>
    );
}

const TodoForm = ({onAddTodo}) =>{
    const [inputValue, setInputValue] = useState("");
    return(
        <form onSubmit={(e)=>e.preventDefault()}>
            <input type="text" value={inputValue} placeholder="할일작성" onChange={(e)=>setInputValue(e.target.value)}/>
            <button type="submit" onClick={()=>{onAddTodo(inputValue); setInputValue("");}}>추가</button>
        </form>
    );
}

export default CombineComponents;