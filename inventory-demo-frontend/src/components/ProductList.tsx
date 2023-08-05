import React, {useEffect, useState} from "react";
import "./ProductList.css"
import inventoryApiClient from "../services/InventoryApiClient";
import {ProductForm} from "./ProductForm";
import {Product, UserCredentials} from "../services/Model";

export interface ProductListProps{
    credentials: UserCredentials
}

export function ProductList({ credentials }: ProductListProps) {
    const [products, setProducts] = useState<Product[]>( [] );
    const [product, setProduct] = useState<Product | undefined>( undefined)

    useEffect(() => {
        inventoryApiClient.getProducts(credentials).then(products => {
            setProducts(products)
        });
    }, [credentials])

    const refreshList = () => {
        inventoryApiClient.getProducts(credentials).then(products => {
            setProducts(products)
        });
    }

    const handleUpdate = () => {
        setProduct(undefined)
        refreshList()
    };

    return (
        <div >
            <button onClick={() => setProduct({id: null, inventory: 0, name: ""})}>New Product</button>
            <button onClick={refreshList}>Refresh List</button>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Inventory</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {products.filter(it => it.id != null).map(it => (
                        <tr key={it.id}>
                            <td>{it.name}</td>
                            <td>{it.inventory}</td>
                            <td>
                                <button onClick={() => setProduct(it)}>select</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {product &&
                <ProductForm product={product} credentials={credentials} onUpdate={handleUpdate}/>
            }
        </div>
    );
}