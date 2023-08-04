import React, {useEffect, useState} from 'react';
import './App.css';
import {LoginForm, UserCredentials} from "./components/LoginForm";
import {InventoryApiClient, Product} from "./services/InventoryApiClient";
import {ProductList} from "./components/ProductList";



const App = () => {
    const [credentials, setCredentials] = useState<UserCredentials>( );
    const [products, setProducts] = useState<Product[]>( [] );

    const client = new InventoryApiClient();

    useEffect(() => {
        refreshList();
    }, [credentials])

    function login(formData: UserCredentials | undefined) {
        setCredentials(formData);
    }

    function logout() {
        setCredentials(undefined);
        setProducts([]);
    }

    function refreshList() {
        if (credentials) {
            client.getProducts(credentials).then(products => {
                setProducts(products)
            });
        }
    }

    return (
        <div className="App">
          <h1>Inventory Management</h1>
            {!credentials && <LoginForm  onSubmit={login}/> }
            {credentials &&
                <>
                    <p>Logged in as {credentials.name}</p>
                    <button onClick={refreshList}>Refresh List</button>
                    <button onClick={logout}>Logout</button>
                    <hr/>
                    <ProductList  products={products} credentials={credentials} onUpdate={refreshList}></ProductList>
                </>
            }
        </div>
  );
}

export default App;
