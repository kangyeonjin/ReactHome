import { Children, useState } from "react";
import CombineComponents from "./components/CombineComponents";
import {CalculatorContext, TodoContext} from "./context/Mycontext"
// import Calculator from "./components/Calculator";
// import CalculatorContext from "./context/Mycontext";

  const AppProvider = ({contexts, children}) =>{
    return contexts.reduce((
      prev,
      {context: Context, value}) =>
        <Context value={value}>{prev}</Context>,
      children
    );
  };

  function App(){
    const [result, setResult] = useState(0);
    const [todos, setTodos] = useState([]);
    
  const add =(value)=> setResult(result+value);
  const subtract =(value)=> setResult(result-value);
  const multiply =(value)=> setResult(result*value);
  const divide =(value)=> setResult(result/value);
  const reset = () => setResult(0);

  const onAddTodo =(content) =>{
    const newTodo ={
      id: todos.length,
      content,
      isDone: false
    }
    setTodos([...todos, newTodo]);
  
  }

  const onDeleteTodo = (id) =>{
    const newTodo = todos.filter(todo=>todo.id !== id);
    setTodos(newTodo);
  }

  const onToggleTodo =(id) =>{
    const newTodo = todos.map(todo => todo.id === id?{...todo, isDone: !todo.isDone}:todo);
    setTodos(newTodo);
  }

  return(
    <AppProvider contexts={[
      {context:CalculatorContext.Provider, value:{result, add, subtract, multiply,divide,reset}},
      {context: TodoContext.Provider, value:{todos, onAddTodo,onDeleteTodo,onToggleTodo}}
    ]}>
      <CombineComponents/>
    </AppProvider>
  );

  }
  
// function App() {
  // const [result, setResult] = useState(0);
  // const [todos, setTodos] = useState([]);

  // const add =(value)=> setResult(result+value);
  // const subtract =(value)=> setResult(result-value);
  // const multiply =(value)=> setResult(result*value);
  // const divide =(value)=> setResult(result/value);
  // const reset = () => setResult(0);

  // const onAddTodo =(content) =>{
  //   const newTodo ={
  //     id: todos.length,
  //     content,
  //     isDone: false
  //   }
  //   setTodos([...todos, newTodo]);
  
  // }

  // const onDeleteTodo = (id) =>{
  //   const newTodo = todos.filter(todo=>todo.id !== id);
  //   setTodos(newTodo);
  // }

  // const onToggleTodo =(id) =>{
  //   const newTodo = todos.map(todo => todo.id === id?{...todo, isDone: !todo.isDone}:todo);
  //   setTodos(newTodo);
  // }

//   return (
//     <CalculatorContext.Provider value={{result, add, subtract, multiply, divide, reset}}>
//       <TodoContext.Provider value={{todos, onAddTodo, onDeleteTodo, onToggleTodo}}>
//         <CombineComponents/>
//               </TodoContext.Provider>
//     </CalculatorContext.Provider>

//   );
// }

export default App;