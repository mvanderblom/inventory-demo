import {UserCredentials} from "../components/LoginForm";
import {Reservation} from "../components/ProductForm";

export interface Product {
    id: number | null
    name: string
    inventory: number
}

export class InventoryApiClient {
    public async getProducts(credentials: UserCredentials): Promise<Product[]> {

        const response = await fetch('/api/v1/product/', {
            headers: this.getHeaders(credentials)
        });
        return await response.json();
    }

    public async createProduct(product: Product, credentials: UserCredentials): Promise<Product> {
        const response = await fetch(`/api/v1/product/`, {
            method: 'POST',
            headers: this.getHeaders(credentials),
            body: JSON.stringify({name: product.name, inventory: product.inventory})
          })
        return await response.json();
    }
    public async updateProduct(product: Product, credentials: UserCredentials) {
        const response = await fetch(`/api/v1/product/${product.id}`, {
            method: 'PUT',
            headers: this.getHeaders(credentials),
            body: JSON.stringify({name: product.name, inventory: product.inventory})
        })
        return await response.json();
    }

    public async removeProduct(id: number, credentials: UserCredentials) {
        const response = await fetch(`/api/v1/product/${id}`, {
            method: 'DELETE',
            headers: this.getHeaders(credentials)
        })
        return await response;
    }

    public async reserveProduct(id: number, reservation: Reservation, credentials: UserCredentials) {
        const response = await fetch(`/api/v1/product/${id}/reserve`, {
            method: 'PUT',
            headers: this.getHeaders(credentials),
            body: JSON.stringify(reservation)
        })
        return await response.json();
    }

    private getHeaders(credentials: UserCredentials) {
        let headers = new Headers();
        headers.set('Authorization', 'Basic ' + btoa(credentials.name + ":" + credentials.password));
        headers.set('Content-Type', 'application/json');
        return headers;
    }


}