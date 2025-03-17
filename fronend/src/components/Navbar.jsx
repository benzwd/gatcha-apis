import {CirclePlus, CircleUser} from "lucide-react";
import axios from "axios";
const Navbar = ({username, onInvocationSuccess, handleModal}) => {
    const invocateMonster = async() => {
        const token = localStorage.getItem('token');
        try{
            const res = await axios.post('/api/invocation', null,{
                headers: {
                    'Authorization': token
                },
            });
            if (onInvocationSuccess) {
                onInvocationSuccess();
            }
        }catch (error){
            console.error('Invocation error:', error.response ? error.response.data : error.message);
        }
    };

    return (
        <div
            className="bg-neutral-800 fixed top-0 left-0 w-full h-14 flex justify-between items-center px-4 text-white z-50">
            <h1 className="text-xl font-semibold text-amber-500">Project WEBAPI</h1>
            <p className="text-white text-sm">Connected as : {username}</p>
            <div className="flex space-x-2">
                <div className="flex cursor-pointer hover:bg-amber-600 p-2 rounded-full duration-300" title={"Invoquer un monstre"}
                     onClick={invocateMonster}>
                    <CirclePlus/>
                </div>
                <div className="flex cursor-pointer hover:bg-amber-600 p-2 rounded-full duration-300" title={"Profil"} onClick={handleModal}>
                    <CircleUser />
                </div>
            </div>
        </div>
    );
};

export default Navbar;