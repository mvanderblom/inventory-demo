import React, {useState} from 'react'
import {UserCredentials} from "../services/Model";

interface FormProps {
    onSubmit: (data: UserCredentials) => void;
}

export function LoginForm({onSubmit}: FormProps) {
    const [formData, setFormData] = useState<UserCredentials>({name: '', password: ''});

    function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
        const {name, value} = event.target;
        setFormData({...formData, [name]: value});
    }

    function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        onSubmit(formData);
    }

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Name:
                <input type="text" name="name" value={formData.name} onChange={handleInputChange}/>
            </label>
            <br/>
            <label>
                Password:
                <input type="password" name="password" value={formData.password} onChange={handleInputChange}/>
            </label>
            <br/>
            <button type="submit">Login</button>
        </form>
    );
}
