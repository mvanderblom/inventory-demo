import {InventoryApiClient, Product} from "../services/InventoryApiClient";
import {UserCredentials} from "./LoginForm";
import React, {useEffect, useState} from "react";

export interface ProductFormProps {
    product: Product
    credentials: UserCredentials
    onUpdate: () => void
}

export interface Reservation {
    amount: number | undefined
    seconds: number | undefined
}

export function ProductForm({ product, credentials, onUpdate }: ProductFormProps) {

    const client = new InventoryApiClient();

    const [productModel, setProductModel] = useState<Product>( product )
    const [reservation, setReservation] = useState<Reservation>({amount: 0, seconds: 0})

    useEffect(() => {
        setProductModel({...product})
    }, [product])


    function changeName(event: React.ChangeEvent<HTMLInputElement>) {
        const {value} = event.target;
        setProductModel({...productModel, name: value});
    }

    function changeInventory(event: React.ChangeEvent<HTMLInputElement>) {
        const {value} = event.target;
        setProductModel(product = {...productModel, inventory: parseInt(value)});
    }

    function create(event: React.FormEvent<HTMLButtonElement>) {
        event.preventDefault();
        client.createProduct(productModel, credentials)
            .then(onUpdate)
    }

    function update(event: React.FormEvent<HTMLButtonElement>) {
        event.preventDefault();
        client.updateProduct(productModel, credentials)
            .then(onUpdate)
    }

    function remove(event: React.FormEvent<HTMLButtonElement>) {
        event.preventDefault();
        client.removeProduct(product.id!!, credentials)
            .then(onUpdate)
    }

    function reserve(event: React.FormEvent<HTMLButtonElement>) {
        event.preventDefault();
        client.reserveProduct(product.id!!, reservation, credentials)
            .then(onUpdate)
        setReservation({amount: 0, seconds: 0})
    }

    return (
        <div>
            <h2>Selected Product</h2>
            <form>
                <label>
                    Name:
                    <input type="text" name="name" value={productModel.name} onChange={changeName}/>
                </label>
                <br/>
                <label>
                    Inventory:
                    <input type="number" name="inventory" value={productModel.inventory} onChange={changeInventory} />
                </label>
                <br/>
                {!product.id &&
                    <button type="submit" onClick={create}>Create</button>
                }
                {product.id &&
                    <>
                        <button type="submit" onClick={update}>Update</button>
                        <button type="submit" onClick={remove}>Delete</button>
                    </>
                }
            </form>
            {product.id &&
                <>
                    <h2>Reserve</h2>
                    <form>
                        <label>
                            Amount:
                            <input type="number" name="amount" value={reservation.amount} onChange={it => setReservation({...reservation, amount: parseInt(it.target.value)})} />
                        </label>
                        <br/>
                        <label>
                            Time in secods:
                            <input type="number" name="time" value={reservation.seconds} onChange={it => setReservation({...reservation, seconds: parseInt(it.target.value)})} />
                        </label>
                        <br/>
                        <button type="submit" onClick={reserve}>Reserve</button>
                    </form>
                </>
            }
        </div>
    );
}