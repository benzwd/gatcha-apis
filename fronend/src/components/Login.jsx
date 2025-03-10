import {useState} from "react"
import axios from "axios"
const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const response = await axios.post('http://localhost:8080/auth', {
                username, password
            });
            const token = response.data.token;
            localStorage.setItem('token', token);
            console.log('Authentification réussie, token:', token);

            window.location.reload();

        }catch (err){
            console.error('Erreur lors de la connexion:', err.response ? err.response.data : err.message);
            setError('Nom d\'utilisateur ou mot de passe incorrect.');
        }
    }
    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <div className="bg-white shadow-lg rounded-2xl p-8 w-96">
                <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">Connexion</h2>
                <form onSubmit={handleSubmit} className="space-y-4" method="post">
                    {error && <p className="text-red-500 text-center">{error}</p>}
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-800">
                            Nom d'utilisateur
                        </label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            className="mt-1 block w-full px-4 py-2 border rounded"
                            placeholder="Nom d'utilisateur"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-800">
                            Mot de passe
                        </label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            className="mt-1 block w-full px-4 py-2 border rounded"
                            placeholder="Mot de passe"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-amber-600 hover:bg-amber-700 cursor-pointer text-white font-medium py-2 rounded shadow transition duration-300"
                    >
                        Se connecter
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;
