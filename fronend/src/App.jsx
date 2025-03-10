import Login from "./components/Login.jsx";
import Navbar from "./components/Navbar.jsx";
import {use, useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [username, setUsername] = useState('');
    useEffect(() => {
        const checkToken = async () => {
            const token = localStorage.getItem('token');
            if (token) {
                try {
                    const response = await axios.post('http://localhost:8080/auth/validate-token', token, {
                        headers: {
                            'Content-Type': 'text/plain',
                        },
                    });
                    console.log('Token valide pour:', response.data);
                    setUsername(response.data);
                } catch (error) {
                    setUsername("none");

                    console.error('Token invalide:', error.response ? error.response.data : error.message);
                    localStorage.removeItem('token');
                }
            } else {
                console.log('Aucun token trouvé.');
            }
        };

        checkToken();
    }, []);
  return (
    <>
        <div className="flex flex-col">
            <Navbar username={username}/>

            { !username && <Login/> }
        </div>
    </>
  )
}

export default App;
