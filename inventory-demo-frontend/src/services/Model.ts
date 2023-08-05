
export interface UserCredentials {
    name: string;
    password: string;
}

export interface Product {
    id: number | null
    name: string
    inventory: number
}

export interface Reservation {
    amount: number | undefined
    seconds: number | undefined
}