import React from "react";

class Section1 extends React.Component{
  constructor(props){
    super(props)
    this.state={
      count:0,
    };
  }
  componentDidMount(){
    console.log('');
  }
  componentDidUpdate(){
    console.log();
  }
  componentWillUnmount(){
    console.log();
  }

  render(){
    return(
      <div>
        <h2>클릭횟수: {this.state.count}</h2>
        <button onClick={()=>this.setState({count:this.state.count+1})}>
          clik
        </button>
      </div>
    )
  }

}

function App() {
  return (
    <div className="App">
  
    </div>
  );
}

export default App;
