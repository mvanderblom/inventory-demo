import {Product} from "../services/InventoryApiClient";
import {UserCredentials} from "./LoginForm";
import React, {useEffect, useState} from "react";
import {ProductForm} from "./ProductForm";

export interface ProductListProps{
    products: Product[]
    credentials: UserCredentials
    onUpdate: () => void
}

export function ProductList({ products, credentials, onUpdate}: ProductListProps) {
    const [product, setProduct] = useState<Product | undefined>( undefined)

    useEffect(onUpdate, [product, onUpdate])

    return (
        <>
            <button onClick={() => setProduct({id: null, inventory: 0, name: ""})}>New Product</button>
            <table border={1}>
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
                <ProductForm product={product} credentials={credentials} onUpdate={() => setProduct(undefined)}/>
            }
        </>
    );
}