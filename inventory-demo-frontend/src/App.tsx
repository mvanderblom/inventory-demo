import React, {useState} from 'react';
import './App.css';
import {LoginForm} from "./components/LoginForm";
import {ProductList} from "./components/ProductList";
import {UserCredentials} from "./services/Model";


const App = () => {
    const [credentials, setCredentials] = useState<UserCredentials>( );

    return (
        <div className="App">
          <h1>Inventory Management System</h1>
            {!credentials && <LoginForm  onSubmit={setCredentials}/> }
            {credentials &&
                <>
                    <p>Logged in as {credentials.name}</p>
                    <button onClick={() => setCredentials(undefined)}>Logout</button>
                    <hr/>
                    <ProductList credentials={credentials}></ProductList>
                </>
            }
        </div>
  );
}

export default App;
