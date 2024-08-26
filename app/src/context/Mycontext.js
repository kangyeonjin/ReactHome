import { createContext } from "react";

// const CalculatorContext = createContext("초기값");
export const CalculatorContext = createContext({
    result :0,
    add: ()=>{},
    subtract: () =>{},
    multiply: () =>{},
    divide: () =>{},
    reset:()=>{}
});

export const TodoContext = createContext({
    todos:[],
    onAddTodo: ()=>{},
    onDeleteTodo: ()=>{},
    ontoggleTodo: ()=>{}
});