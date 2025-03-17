import Login from "./components/Login.jsx";
import Navbar from "./components/Navbar.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import Card from "./components/Card.jsx";
import UserModal from "./components/UserModal.jsx";

function App() {
    const [username, setUsername] = useState('');
    const [monsters, setMonsters] = useState([]);
    const [user, setUser] = useState();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const handleOpenModal = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const getUserProfile = async () => {
        const token = localStorage.getItem('token');
        try{
            const res = await axios.get('/api/player/profile', {
                headers: {
                    'Authorization': token
                },
            });
            setUser(res.data);
        }catch(err){
            console.error('Profil invalide:', err.response ? err.response.data : err.message);
        }
    }
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
                    await getUserProfile();
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
    const getMonsters = async() => {
        const token = localStorage.getItem('token');
        try{
            const res = await axios.get('/api/monsters', {
                headers: {
                    'Authorization': token
                },
            });
            setMonsters([...res.data]);
        }catch (error){
            console.error('Monster error:', error.response ? error.response.data : error.message);
        }
    };
    useEffect(() => {
        getMonsters();
    }, []);

    const handleInvocationSuccess = () => {
        getMonsters();
    };
  return (
    <>
        <div className="flex flex-col mx-6">
            <Navbar username={username} onInvocationSuccess={() => handleInvocationSuccess()} handleModal={() => handleOpenModal()}/>
            { !username && <Login/> }
            { username &&
                <div className="flex items-center justify-center gap-3 mt-20 flex-wrap">
                    {monsters.map((monster) => (
                        <Card key={monster.id} id={monster.id}
                              ownerUsername={monster.ownerUsername}
                              elementalType={monster.elementalType}
                              level={monster.level}
                              hp={monster.hp}
                              atk={monster.atk}
                              def={monster.def}
                              vit={monster.vit}
                              xp={monster.xp}/>
                    ))}
                </div>
            }
            {isModalOpen && <UserModal user={user} onClose={handleCloseModal} />}
        </div>
    </>
  )
}

export default App;
